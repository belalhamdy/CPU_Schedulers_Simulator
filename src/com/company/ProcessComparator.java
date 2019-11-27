package com.company;

import java.util.Comparator;

public class ProcessComparator implements Comparator<Process> {

    public enum ComparisonType {ArrivalTime,Priority,RemainingTime}
    ComparisonType Type;
    ProcessComparator(ComparisonType Type){
        this.Type = Type;
    }
    @Override
    public int compare(Process o1, Process o2) {
        if (Type == ComparisonType.ArrivalTime) return Integer.compare(o1.ArrivalTime,o2.ArrivalTime);
        else if (Type == ComparisonType.Priority) return Integer.compare(o1.Priority,o2.Priority);
        else if (Type == ComparisonType.RemainingTime) return Integer.compare(o1.RemainingTime,o2.RemainingTime);
        else return 0;
    }


}
