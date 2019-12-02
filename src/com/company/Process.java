package com.company;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// Times inserted as integer in microseconds
// Depending on type of simulation choose constructor
public class Process {
    String Name;
    int ArrivalTime, BurstTime, RemainingTime, Priority, Quantum,tempQuantum, AGFacor,id , Context_Switch;
    List<Pair<Integer,Integer>> QuantumHistory;
    Color color;
    List<Pair<Integer, Integer>> WorkingTimes;
    private static int LastPID = 0;

    Process(String Name, int ArrivalTime, int BurstTime, int Priority, int Quantum, Color color) {
        this.Name = Name;
        this.ArrivalTime = ArrivalTime;
        this.BurstTime = BurstTime;
        this.Priority = Priority;
        this.Quantum = Quantum;
        this.tempQuantum = Quantum;
        this.color = color;
        WorkingTimes = new ArrayList<>();
        RemainingTime = BurstTime;
        this.id = ++LastPID;
        this.Context_Switch = 0;

    }

    @Override
    public String toString() {
        return this.Name +" "+ this.ArrivalTime +" "+ this.BurstTime +" " +this.Priority+" " + this.getStartTime()+" "+this.getEndTime() + " "+ this.getWaitingTime();
    }

    Process(String Name, int ArrivalTime, int BurstTime) {
        this(Name, ArrivalTime, BurstTime, -1, -1, Color.BLACK);
    }

    Process(String Name, int ArrivalTime, int BurstTime, int Priority) {
        this(Name, ArrivalTime, BurstTime, Priority, -1, Color.BLACK);
    }

    Process(String Name, int ArrivalTime, int BurstTime, int Priority, int Quantum) {
        this(Name, ArrivalTime, BurstTime, Priority, Quantum, Color.BLACK);
        AGFacor = ArrivalTime + BurstTime + Priority;
        QuantumHistory = new ArrayList<>();
        QuantumHistory.add(new Pair<>(0,Quantum));
    }

    public List<Pair<Integer, Integer>> getWorkingTimes() {
        return WorkingTimes;
    }

    public void AddWorkingTime(int start, int end) throws Exception {
        WorkingTimes.add(new Pair<>(start, end+Context_Switch));
        RemainingTime -= (end - start);
        if (RemainingTime < 0) throw new Exception("This process took working time larger than it's burst time");
    }

    public boolean isFinished() {
        return RemainingTime == 0;
    }

    public int getWaitingTime() {
        return getTurnaroundTime() - BurstTime;
    }

    public int getTurnaroundTime() {
        return (getEndTime() - ArrivalTime) ;
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
    public void UpdateQuantum (int start,int end) throws Exception {
        int usedQuantum = end - start;
        //if (QuantumHistory == null || usedQuantum>Quantum) throw new Exception("INVALID" + Quantum + " " + usedQuantum);
        if (Quantum == usedQuantum) Quantum += (int) Math.ceil(0.1*(double)Quantum);
        else Quantum += (Quantum - usedQuantum);
        Quantum = Math.max(0, Quantum);
        QuantumHistory.add(new Pair<>(end,Quantum));
    }
    public void UpdateQuantum() throws Exception{
        UpdateQuantum(WorkingTimes.get(0).getKey(),WorkingTimes.get(WorkingTimes.size() - 1).getValue());
    }
    public void setContext_Switch(int amount){
        Context_Switch=amount;
    }
}
