package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShortestRemainingTimeFirstScheduling extends ProcessScheduling {
    private static final int CONTEXT_SWITCH = 1;

    public ShortestRemainingTimeFirstScheduling(List<Process> data) {
        super(data);
    }

    @Override
    public List<Process> Simulate() throws Exception {
        for (Process curr : Queue) curr.setContext_Switch(CONTEXT_SWITCH);

        List<Process> finished = new ArrayList<>();

        int currentTime = 0, nextStop;

        Process current = null;
        while (Queue.size() > 0) {

            Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.RemainingTime, currentTime));

            current = Queue.remove(0);

            currentTime = Math.max(current.ArrivalTime, currentTime); // update the time to be the time of start of the next process
            nextStop = Math.min(getNextArrival(currentTime, current.RemainingTime), currentTime + current.RemainingTime) + CONTEXT_SWITCH;

//            if (nextStop < currentTime) nextStop = currentTime + current.RemainingTime;
            current.AddWorkingTime(currentTime, nextStop - CONTEXT_SWITCH);

            currentTime = nextStop;

            if (current.isFinished()) finished.add(current);
            else Queue.add(current);
        }
        return finished;
    }

    int getNextArrival(int currentTime, int rem) {
        for (Process curr : Queue) {
            if (curr.ArrivalTime > currentTime && curr.RemainingTime < (rem - (curr.ArrivalTime - currentTime)))
                return curr.ArrivalTime;
        }
        return Integer.MAX_VALUE;
    }
}
