package com.company;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Priorities should be from 0 to 20 only
public class PriorityScheduling extends ProcessScheduling {
    private static final int StarvationSolver = 1;

    public PriorityScheduling(List<Process> data) {
        super(data);
    }

    @Override
    public List<Process> Simulate() throws Exception {
        List<Process> finished = new ArrayList<>();
        int currentTime = 0;
        Process current = null;
        while (Queue.size() > 0) {
            Collections.sort(Queue, new ProcessComparator(ProcessComparator.ComparisonType.Priority, currentTime));

            current = Queue.remove(0);
            currentTime = Math.max(current.ArrivalTime, currentTime); // update the time to be the time of start of the next process

            current.AddWorkingTime(currentTime, currentTime + current.BurstTime);
            currentTime += current.BurstTime;
            UpdatePriorities(currentTime);

            finished.add(current);
        }
        return finished;
    }

    void UpdatePriorities(int currentTime) {
        int MIN_PRIORITY = 0;
        for (Process curr : Queue) {
            if (curr.ArrivalTime > currentTime) return;
            curr.Priority = Math.max(curr.Priority - StarvationSolver, MIN_PRIORITY);
        }
    }
}
