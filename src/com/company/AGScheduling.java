package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AGScheduling extends ProcessScheduling {
    List<Process> RoundRobin;
//handle process arrival after current process is processed


    public AGScheduling(List<Process> data) {
        super(data);
    }

    /*
    Assuming that if process is executing at 0, burst 10, Quantum 2 and the next will arrive at 5 it will be executed at 2 then it will stop execution and update it's quantum and return back to execution
    Process cut if (finished it's quantum | (finished ceil 50% of it's quantum & there exists another process in the queue) | finished it's remaining time)
    round robin contains the cut processes in order
     */
    @Override
    public List<Process> Simulate() throws Exception {
        List<Process> finished = new ArrayList<>();
        RoundRobin = new ArrayList<>();
        int currentTime = 0, nextStop, duration = 0;

        Process current;
        Process nextProcess;
        while (!Queue.isEmpty()) {
            nextProcess = Queue.remove(0);
            Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.AG, currentTime));

            currentTime = Math.max(currentTime, nextProcess.ArrivalTime);
            while (nextProcess != null) {
                current = nextProcess;

                Queue.remove(current);
                RoundRobin.remove(current);

                Pair<Process, Integer> nextStartData = findSuitableDRDR(currentTime, current);
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

    private Pair<Process, Integer> findSuitableDRDR(int currentTime, Process current) {
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
        if (cut != null) exitTime = Math.max(cut.ArrivalTime, currentTime + halfQuantum);

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

    private Pair<Process, Integer> findSuitable(int currentTime, Process current) {
        int halfQuantum = (int) Math.ceil((double) current.Quantum / 2.0);
        int nextArriveTime = Integer.MAX_VALUE, nextRoundRobinTime = Integer.MAX_VALUE;
        Process nextArrive = null, nextRoundRobin = null;

        Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.AG, currentTime + halfQuantum));

        if (Queue.isEmpty() && RoundRobin.isEmpty()) return new Pair<>(null, currentTime + current.RemainingTime);
        if (!Queue.isEmpty()) nextArrive = Queue.get(0);

        if (!RoundRobin.isEmpty()) {
            nextRoundRobin = RoundRobin.get(0);
            for (Process curr : RoundRobin) {
                if (curr.AGFactor < current.AGFactor) nextRoundRobin = curr;
            }
        }

        if (nextRoundRobin != null) {
            if (nextRoundRobin.AGFactor < current.AGFactor) nextRoundRobinTime = currentTime + halfQuantum;
            else nextRoundRobinTime = currentTime + Math.min(current.Quantum, current.RemainingTime);
        }

        if (nextArrive != null) nextArriveTime = nextArrive.ArrivalTime;


        if (current.RemainingTime + currentTime < Math.min(nextRoundRobinTime, nextArriveTime)) {
            if (nextRoundRobinTime < nextArriveTime) {
                RoundRobin.remove(nextRoundRobin);
                return new Pair<>(nextRoundRobin, current.RemainingTime + currentTime);
            } else {
                Queue.remove(nextArrive);
                return new Pair<>(nextArrive, current.RemainingTime + currentTime);
            }
        } else {
            if (nextRoundRobinTime < nextArriveTime) {
                RoundRobin.remove(nextRoundRobin);
                return new Pair<>(nextRoundRobin, nextRoundRobinTime);
            } else {
                Queue.remove(nextArrive);
                assert nextArrive != null;
                if (current.AGFactor < nextArrive.AGFactor)
                    return new Pair<>(nextArrive, Math.min(currentTime + current.RemainingTime, currentTime + current.Quantum));
                else
                    return new Pair<>(nextArrive, Math.min(Math.max(nextArriveTime, currentTime + halfQuantum), currentTime + current.RemainingTime));
            }

        }

    }

}