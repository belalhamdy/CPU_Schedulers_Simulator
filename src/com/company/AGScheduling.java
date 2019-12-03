package com.company;

import javafx.util.Pair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AGScheduling extends ProcessScheduling {
    int Quantum;
    List<Process> RoundRobin;

    AGScheduling(InputStream inputStream) {
        super(inputStream);
    }

    AGScheduling(List<Process> data) {
        super(data);
    }

    @Override
    void EnterData() {
        Queue = new ArrayList<>();
        Queue.add(new Process("1", 0, 17, 4, 4));
        Queue.add(new Process("2", 3, 6, 9, 4));
        Queue.add(new Process("3", 4, 10, 3, 4));
        Queue.add(new Process("4", 29, 4, 8, 4));
        /*Scanner in = new Scanner(System.in);
        System.out.println("Enter the Quantum of processes");
        Quantum = in.nextInt();
        System.out.print("Enter number of processes: ");
        int n = in.nextInt();
        for (int i = 1; i <= n; ++i) {
            System.out.print("Enter Name of process " + i + " : ");
            String Name = in.next();
            System.out.print("Enter Arrival Time of process " + i + " : ");
            int arrival = in.nextInt();
            System.out.print("Enter Burst Time of process " + i + " : ");
            int burst = in.nextInt();
            System.out.print("Enter Priority of process " + i + " : ");
            int priority = in.nextInt();
            Queue.add(new Process(Name, arrival, burst,priority,Quantum));
            System.out.println("Process " + i + " is added successfully\n");
        }*/
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

        Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.AG, currentTime));
        Process current = null, nextProcess = Queue.remove(0);

        while (nextProcess != null) {
            if (current != null) {
                if (current.isFinished()) {
                    finished.add(current);
                    current.setQuantum(-current.Quantum);
                } else {
                    int amount = getNewQuantum(current.Quantum, duration,nextProcess);
                    current.setQuantum(amount);
                    RoundRobin.add(current);
                }
            }
            if(currentTime == 25)
            {
                System.out.println("hi");
            }
            current = nextProcess;
            Pair<Process, Integer> nextStartData = findSuitable(currentTime, current);

            currentTime = Math.max(current.ArrivalTime, currentTime); // update the time to be the time of start of the next process

            nextProcess = nextStartData.getKey();
            nextStop = nextStartData.getValue();

            current.AddWorkingTime(currentTime, nextStop);

            duration = nextStop - currentTime;
            currentTime = nextStop;
        }

        if (current != null){
            current.setQuantum(-current.Quantum);
            finished.add(current);
        }
        return finished;
    }

    private int getNewQuantum(int oldQuantum, int duration,Process nextProcess) {
        if (duration == oldQuantum) return (int) Math.ceil(0.1*getMeanQuantum(nextProcess));
        return oldQuantum - duration;
    }

    private double getMeanQuantum(Process nextProcess) {
        double sum = nextProcess.Quantum;
        int n = 1;
        for (Process p : RoundRobin) {
                sum += p.Quantum;
                ++n;
        }
        return sum / n;
    }

    private Pair<Process, Integer> findSuitable(int currentTime, Process current) {
        int halfQuantum = (int) Math.ceil((double) current.Quantum / 2.0);
        int nextArriveTime = Integer.MAX_VALUE, nextRoundRobinTime = Integer.MAX_VALUE;
        Process nextArrive = null, nextRoundRobin = null;

        Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.AG, currentTime + halfQuantum));
        if (Queue.isEmpty() && RoundRobin.isEmpty()) return new Pair<>(null,currentTime + current.RemainingTime);
        if (!Queue.isEmpty()) nextArrive = Queue.get(0);

        if (!RoundRobin.isEmpty()) {
            nextRoundRobin = RoundRobin.get(0);
            for (Process curr : RoundRobin) {
                if (curr.AGFactor < current.AGFactor) nextRoundRobin = curr;
            }
        }

        if (nextRoundRobin != null) {
            if (nextRoundRobin.AGFactor < current.AGFactor) nextRoundRobinTime = currentTime + halfQuantum;
            else nextRoundRobinTime = currentTime + Math.min(current.Quantum,current.RemainingTime);
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
                if (current.AGFactor< nextArrive.AGFactor)return new Pair<>(nextArrive, Math.min(currentTime + current.RemainingTime, currentTime + current.Quantum));
                else   return new Pair<>(nextArrive, Math.max(nextArriveTime, currentTime + halfQuantum));
            }

        }

    }

}
