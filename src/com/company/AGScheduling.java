package com.company;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AGScheduling extends ProcessScheduling {
    int Quantum;
    AGScheduling(InputStream inputStream) {
        super(inputStream);
    }
    AGScheduling(List<Process> data) {
        super(data);
    }

    @Override
    void EnterData() {
        Queue = new ArrayList<>();
        /*Scanner in = new Scanner(System.in);
        System.out.println("Enter the Quantum of processes");
        Quantum = in.nextInt();
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
            Queue.add(new Process(Name, arrival, burst,priority,Quantum));
            System.out.println("Process " + i + " is added successfully\n");
        }*/
        TestCase();
    }
    private void TestCase(){
        Queue.add(new Process("a",0,17,4,4));
        Queue.add(new Process("b",3,6,9,4));
        Queue.add(new Process("c",4,10,3,4));
        Queue.add(new Process("d",29,4,8,4));
    }
    @Override
    public List<Process> Simulate() throws Exception {
        List<Process> finished = new ArrayList<>();
        int currentTime = 0,nextStop,duration;
        Process current = null;
        while (Queue.size()>0){
            if (current != null){
                if (current.isFinished()) finished.add(current);
                else Queue.add(current);
            }
            Collections.sort(Queue,new ProcessComparator(ProcessComparator.ComparisonType.Quantum, currentTime));
            current = Queue.remove(0);
            currentTime = Math.max(current.ArrivalTime,currentTime); // update the time to be the time of start of the next process
            duration = Math.max (Queue.get(0).ArrivalTime,(int) Math.ceil(0.5*(double)current.Quantum));
            nextStop = Math.min(currentTime + duration,current.RemainingTime);
            current.AddWorkingTime(currentTime,nextStop);
            current.UpdateQuantum();
            currentTime = nextStop;
        }
        if (current != null) finished.add(current);
        return finished;
    }
}
