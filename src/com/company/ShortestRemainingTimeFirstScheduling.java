package com.company;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShortestRemainingTimeFirstScheduling extends ProcessScheduling {
    private static final int CONTEXT_SWITCH = 1;

    ShortestRemainingTimeFirstScheduling(InputStream inputStream) {
        super(inputStream);
    }

    ShortestRemainingTimeFirstScheduling(List<Process> data) {
        super(data);
    }

    @Override
    public List<Process> Simulate() throws Exception {
        for (Process curr : Queue) curr.setContext_Switch(CONTEXT_SWITCH);
        List<Process> finished = new ArrayList<>();
        int currentTime = 0, nextStop;
        Process current = null;
        while (Queue.size() > 0) {
            if (current != null) {
                if (current.isFinished()) finished.add(current);
                else Queue.add(current);
            }
            Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.RemainingTime, currentTime));
            current = Queue.remove(0);
            currentTime = Math.max(current.ArrivalTime, currentTime); // update the time to be the time of start of the next process
            nextStop = Math.min(getNextArrival(currentTime) + CONTEXT_SWITCH, currentTime + current.RemainingTime + CONTEXT_SWITCH);
            if (nextStop < currentTime) nextStop = currentTime + current.RemainingTime;
            current.AddWorkingTime(currentTime, nextStop-CONTEXT_SWITCH );
            currentTime = nextStop;
        }
        if (current != null) finished.add(current);
        return finished;
    }
    int getNextArrival(int currentTime){
        for (Process curr : Queue){
            if (curr.ArrivalTime>currentTime) return curr.ArrivalTime;
        }
        return Integer.MAX_VALUE-CONTEXT_SWITCH-1; // OVERFLOW
    }
}
