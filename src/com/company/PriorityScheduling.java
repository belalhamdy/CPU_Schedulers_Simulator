package com.company;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
// Priorities should be from 0 to 20 only
public class PriorityScheduling extends ProcessScheduling {
    private final int StarvationSolver = 1;
    PriorityScheduling(InputStream inputStream) {
        super(inputStream);
    }
    PriorityScheduling(List<Process> data) {
        super(data);
    }

    @Override
     void EnterData() {
        Queue = new ArrayList<>();
        /*
        Queue.add(new Process("STARVING_2",0,7,5));
        Queue.add(new Process("1",0,11,2));
        Queue.add(new Process("2",5,28,0));
        Queue.add(new Process("3",12,2,3));
        Queue.add(new Process("4",2,10,1));
        Queue.add(new Process("STARVING_1",9,16,4));
        Queue.add(new Process("6",60,16,2));
        */
        /*
        Queue.add(new Process("1",0,10,3));
        Queue.add(new Process("2",0,1,1));
        Queue.add(new Process("3",0,2,4));
        Queue.add(new Process("4",0,1,5));
        Queue.add(new Process("5",0,5,2));
        //*/
        Scanner in = new Scanner(inputStream);
        System.out.print("Enter number of processes: ");
        int n = in.nextInt();
        for (int i = 1; i <= n; ++i) {
            System.out.print("Enter Name of process " + i + " : ");
            String Name = in.next();
            System.out.print("Enter Arrival Time of process " + i + " : ");
            int arrival = in.nextInt();
            System.out.print("Enter Burst Time of process " + i + " : ");
            int burst = in.nextInt();
            System.out.print("Enter Priority of process " + i + " : ");
            int priority = in.nextInt();
            Queue.add(new Process(Name, arrival, burst,priority));
            System.out.println("Process " + i + " is added successfully\n");
        }

    }

    @Override
    public List<Process> Simulate() throws Exception {
        List<Process> finished = new ArrayList<>();
        int currentTime = 0;
        Process current = null;
        while (Queue.size()>0){
            if (current != null) finished.add(current);
            Collections.sort(Queue,new ProcessComparator(ProcessComparator.ComparisonType.Priority, currentTime));
            current = Queue.remove(0);
            currentTime = Math.max(current.ArrivalTime,currentTime); // update the time to be the time of start of the next process
            current.AddWorkingTime(currentTime,currentTime+current.BurstTime);
            currentTime += current.BurstTime;
            UpdatePriorities(currentTime);
        }
        if (current != null) finished.add(current);
        return finished;
    }
    void UpdatePriorities(int currentTime){
        int MIN_PRIORITY = 0;
        for (Process curr : Queue){
            if (curr.ArrivalTime > currentTime) return;
            curr.Priority= Math.max(curr.Priority-StarvationSolver,MIN_PRIORITY);
        }
    }
}
