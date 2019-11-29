package com.company;

import java.util.Comparator;

public class ProcessComparator implements Comparator<Process> {

    public enum ComparisonType {ArrivalTime,BurstTime,Priority,RemainingTime}
    ComparisonType Type;
    int comparisonParameter;
    ProcessComparator(ComparisonType Type,int comparisonParameter){
        this.Type = Type;
        this.comparisonParameter = comparisonParameter;
    }
    ProcessComparator(ComparisonType Type){
        this(Type,Integer.MIN_VALUE);
    }
    // -1 -> o1 < o2
    // 0 -> o1 = o2
    // 1 -> o1 > o2
    @Override
    public int compare(Process o1, Process o2) {
        if (Type == ComparisonType.ArrivalTime) return ArrivalTimeCompare(o1,o2);
        else if (Type == ComparisonType.BurstTime) return BurstTimeCompare(o1,o2);
        else if (Type == ComparisonType.Priority) return PriorityCompare(o1,o2);
        else if (Type == ComparisonType.RemainingTime) return RemainingTimeCompare(o1,o2);
        else return 0;
    }
    private int ArrivalTimeCompare(Process a, Process b){
        return Integer.compare(a.ArrivalTime,b.ArrivalTime);
    }
    // for Shortest job scheduling and shortest remaining time as in shortest job remaining time = burst time
    // comparision parameter here expected to be the current system time
    private int BurstTimeCompare(Process a, Process b){
        // one process atleast is not in the system (not arrived yet)
        if (a.ArrivalTime > comparisonParameter || b.ArrivalTime > comparisonParameter) return Integer.compare(a.ArrivalTime,b.ArrivalTime);
        // else both are in the system so compare based on shortest time
        return Integer.compare(a.RemainingTime,b.RemainingTime);
    }
    private int PriorityCompare(Process a,Process b){
        return Integer.compare(a.Priority,b.Priority);
    }
    private int RemainingTimeCompare(Process a,Process b){
        return BurstTimeCompare(a,b);
    }


}
