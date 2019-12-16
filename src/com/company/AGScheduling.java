package com.company;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AGScheduling extends ProcessScheduling {
    List<Process> RoundRobin;


    public AGScheduling(List<Process> data) {
        super(data);
    }

    @Override
    public List<Process> Simulate() throws Exception {
//         Uncomment the next lines and input any thing in GUI to test the edge case
//        Queue.clear();
//        Queue.add(new Process("1", 5, 1, 1, 3, Color.MAGENTA));
//        Queue.add(new Process("2", 5, 3, 1, 3,Color.YELLOW));
//        Queue.add(new Process("3", 2, 6, 5, 3,Color.cyan));
//        Queue.add(new Process("4", 4, 4, 10, 3,Color.RED));
//        Queue.add(new Process("5", 3, 4, 3, 3,Color.GREEN));
//        Queue.add(new Process("6", 21, 3, 2, 3, Color.GRAY));
        List<Process> finished = new ArrayList<>();
        RoundRobin = new ArrayList<>();
        int currentTime = 0, nextStop, duration;

        Process current;
        Process nextProcess;
        while (!Queue.isEmpty()) {
            Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.AG, currentTime));
            nextProcess = Queue.remove(0);

            currentTime = Math.max(currentTime, nextProcess.ArrivalTime);
            while (nextProcess != null) {
                current = nextProcess;

                Queue.remove(current);
                RoundRobin.remove(current);

                Pair<Process, Integer> nextStartData = findSuitable(currentTime, current);
                nextProcess = nextStartData.getKey();
                nextStop = nextStartData.getValue();

                current.AddWorkingTime(currentTime, nextStop);

                duration = nextStop - currentTime;
                currentTime = nextStop;

                if (current.isFinished()) {
                    finished.add(current);
                    current.resetQuantam();
                } else {
                    RoundRobin.add(current);
                    Queue.add(current);
                    int amount = getNewQuantum(current.Quantum, duration);
                    current.increaseQuantum(amount);
                }
            }

        }

        return finished;
    }

    private int getNewQuantum(int oldQuantum, int duration) {
        if (duration == oldQuantum) return (int) Math.ceil(0.1 * getMeanQuantum());
        return oldQuantum - duration;
    }

    private double getMeanQuantum() {
        double sum = 0;
        int n = 0;
        for (Process p : RoundRobin) {
            sum += p.Quantum;
            ++n;
        }
        return sum / n;
    }

    private Pair<Process, Integer> findSuitable(int currentTime, Process current) {
        int halfQuantum = (int) Math.ceil((double) current.Quantum / 2.0);

        Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.ArrivalTime, Integer.MAX_VALUE));

        Process cut = null;
        for (Process p : Queue) {
            //for processes that arrive between current time and current time + half quantam, get the minimum AG (must be less than me)
            if (p.ArrivalTime <= currentTime + halfQuantum && p.AGFactor < current.AGFactor && (cut == null || p.AGFactor < cut.AGFactor)) {
                cut = p;
            }
            //otherwise, get the earliest that can cut me
            if (p.ArrivalTime > currentTime + halfQuantum && cut == null && p.AGFactor < current.AGFactor) {
                cut = p;
                break;
            }
            if (p.ArrivalTime > currentTime + current.Quantum)
                break;
        }
        int exitTime = Math.min(current.RemainingTime, current.Quantum) + currentTime;
        if (cut != null) exitTime = Math.max(cut.ArrivalTime,currentTime + Math.min(halfQuantum,current.RemainingTime));

        for (Process p : Queue) {
            if (p == cut) continue;

            if (p.ArrivalTime > currentTime && p.ArrivalTime <= exitTime)
                RoundRobin.add(p);
        }

        if (current.RemainingTime <= halfQuantum || cut == null) {
            return new Pair<>(RoundRobin.isEmpty() ? null : RoundRobin.get(0), exitTime);
        } else {
            return new Pair<>(cut, exitTime);
        }


    }

}