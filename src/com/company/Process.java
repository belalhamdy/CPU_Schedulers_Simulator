package com.company;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Times inserted as integer in microseconds
// Depending on type of simulation choose constructor
public class Process {
    String Name;
    int ArrivalTime, BurstTime, RemainingTime, Priority, Quantum, id;
    Color color;
    List<Pair<Integer, Integer>> WorkingTimes;
    private static int LastPID = 0;

    Process(String Name, int ArrivalTime, int BurstTime, int Priority, int Quantum, Color color) {
        this.Name = Name;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.Priority = Priority;
        this.Quantum = Quantum;
        this.color = color;
        WorkingTimes = new ArrayList<>();
        RemainingTime = BurstTime;
        this.id = ++LastPID;

    }

    @Override
    public String toString() {
        return this.Name +" "+ this.ArrivalTime +" "+ this.BurstTime +" " +" " + this.getStartTime()+" "+this.getEndTime();
    }

    Process(String Name, int ArrivalTime, int BurstTime) {
        this(Name, ArrivalTime, BurstTime, -1, -1, Color.BLACK);
    }

    Process(String Name, int ArrivalTime, int BurstTime, int Priority) {
        this(Name, ArrivalTime, BurstTime, Priority, -1, Color.BLACK);
    }

    Process(String Name, int ArrivalTime, int BurstTime, int Priority, int Quantum) {
        this(Name, ArrivalTime, BurstTime, Priority, Quantum, Color.BLACK);
    }

    public List<Pair<Integer, Integer>> getWorkingTimes() {
        return WorkingTimes;
    }

    public void AddWorkingTime(int start, int end) throws Exception {
        WorkingTimes.add(new Pair<>(start, end));
        RemainingTime -= (end - start);
        if (RemainingTime < 0) throw new Exception("This process took working time larger than it's burst time");
    }

    public boolean isFinished() {
        return RemainingTime == 0;
    }

    public int getWaitingTime() {
        return (getEndTime() - ArrivalTime - BurstTime);
    }

    public int getTurnaroundTime() {
        return (getEndTime() - ArrivalTime);
    }

    int getEndTime() {
        if (RemainingTime != 0)
            return Integer.MIN_VALUE; // process haven't finished yet so no one knows when it will finish
        return (WorkingTimes.get(WorkingTimes.size() - 1).getValue()); // gets the end of last working time
    }
    int getStartTime(){
        if (RemainingTime == BurstTime) return Integer.MIN_VALUE;
        return (WorkingTimes.get(0).getKey());
    }
}
