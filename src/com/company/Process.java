package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

// Times inserted as integer in microseconds
// Depending on type of simulation choose constructor
public class Process {
    int ArrivalTime, BurstTime,RemainingTime,Priority, Quantum;
    List<Pair<Integer, Integer>> WorkingTimes;

    Process(int ArrivalTime, int BurstTime, int Priority,int Quantum) {
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.Priority = Priority;
        this.Quantum = Quantum;
        this.WorkingTimes = new ArrayList<>();
    }
    Process (int ArrivalTime, int BurstTime){
        this(ArrivalTime,BurstTime,-1,-1);
    }
    Process (int ArrivalTime, int BurstTime,int Priority){
        this(ArrivalTime,BurstTime,Priority,-1);
    }

    public List<Pair<Integer, Integer>> getWorkingTimes() {
        return WorkingTimes;
    }
    public void AddWorkingTime(int start,int end) throws Exception {
        WorkingTimes.add(new Pair<>(start,end));
        RemainingTime -= (end-start);
        if (RemainingTime < 0) throw new Exception("This process took working time larger than it's burst time");
    }
    public int getWaitingTime(){
        if (RemainingTime != 0) return Integer.MAX_VALUE; // process haven't finished yet so no one knows when it will finish
        int endTime = WorkingTimes.get(WorkingTimes.size()-1).getValue(); // gets the end of last working time
        return (endTime-ArrivalTime-BurstTime);
    }
}
