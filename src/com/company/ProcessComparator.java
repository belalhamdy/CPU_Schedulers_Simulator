package com.company;

import java.util.Comparator;

import static com.company.ProcessComparator.ComparisonType.*;

public class ProcessComparator implements Comparator<Process> {

    public enum ComparisonType {ArrivalTime,BurstTime,Priority,RemainingTime,AG,ID}
    private ComparisonType Type;
    private int comparisonParameter;
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
        if (Type == ArrivalTime) return ArrivalTimeCompare(o1,o2);
        else if (Type == BurstTime) return BurstTimeCompare(o1,o2);
        else if (Type == Priority) return PriorityCompare(o1,o2);
        else if (Type == RemainingTime) return RemainingTimeCompare(o1,o2);
        else if (Type == AG) return AGCompare(o1,o2);
        else if (Type == ID) return IDCompare(o1,o2);
        else return 0;
    }

    private int IDCompare(Process o1, Process o2) {
        return Integer.compare(o1.id,o2.id);
    }

    private int AGCompare(Process a, Process b) {
        if (a.ArrivalTime > comparisonParameter || b.ArrivalTime > comparisonParameter || a.AGFactor == b.AGFactor) return ArrivalTimeCompare(a,b);
        return Integer.compare(a.AGFactor,b.AGFactor);
    }

    private int ArrivalTimeCompare(Process a, Process b){
        return Integer.compare(a.ArrivalTime,b.ArrivalTime);
    }
    // for Shortest job scheduling and shortest remaining time as in shortest job remaining time = burst time
    // comparision parameter here expected to be the current system time
    private int BurstTimeCompare(Process a, Process b){
        // one process at least is not in the system (not arrived yet)
        if (a.ArrivalTime > comparisonParameter || b.ArrivalTime > comparisonParameter) return ArrivalTimeCompare(a,b);
        // else both are in the system so compare based on shortest time
        return Integer.compare(a.RemainingTime,b.RemainingTime);
    }
    private int PriorityCompare(Process a,Process b){
        if (a.ArrivalTime > comparisonParameter || b.ArrivalTime > comparisonParameter || a.Priority == b.Priority) return ArrivalTimeCompare(a,b);
        return Integer.compare(a.Priority,b.Priority);
    }
    private int RemainingTimeCompare(Process a,Process b){
        return BurstTimeCompare(a,b);
    }


}
