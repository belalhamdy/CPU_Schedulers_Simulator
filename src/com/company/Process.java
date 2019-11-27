package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

// Times inserted as integer in microseconds
public class Process {
    int ArrivalTime, BurstTime,RemainingTime,Priority;
    List<Pair<Integer, Integer>> WorkingTimes;

    Process(int ArrivalTime, int BurstTime, int Priority) {
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.WorkingTimes = new ArrayList<>();
        this.Priority = Priority;
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
