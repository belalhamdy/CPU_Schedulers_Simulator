package com.company;

import java.io.InputStream;
import java.util.*;


public class ShortestJobFirstScheduling extends ProcessScheduling {

    public ShortestJobFirstScheduling(List<Process> data) {
        super(data);
    }

    @Override
    public List<Process> Simulate() throws Exception {
        List<Process> finished = new ArrayList<>();
        int currentTime = 0;
        Process current = null;

        while (Queue.size() > 0) {

            Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.RemainingTime, currentTime));

            current = Queue.remove(0);
            currentTime = Math.max(current.ArrivalTime, currentTime); // update the time to be the time of start of the next process
            current.AddWorkingTime(currentTime, currentTime + current.BurstTime);
            currentTime += current.BurstTime;

            finished.add(current);
        }

        return finished;
    }

}
