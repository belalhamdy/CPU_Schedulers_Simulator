package com.company;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
Every child class can fill the queue either by getting a list from somewhere or taking it from console
system out is used implicitly inside the classes without declaring an output stream for them for future gui purpose
 */
public abstract class ProcessScheduling {
    static DecimalFormat doubleFormat;

    InputStream inputStream;
    List<Process> Queue;
    int currentTime;
    ProcessScheduling(InputStream inputStream,List<Process> Queue){
        this.inputStream = inputStream;
        this.Queue = Queue;
        if (this.Queue == null) {
            Queue = new ArrayList<>();
            EnterData();
        }
        currentTime = 0;
        this.doubleFormat =new DecimalFormat("#.###");
    }
    ProcessScheduling(InputStream inputStream) {
        this(inputStream,null);
    }

    ProcessScheduling(List<Process> data) {
        this(null,data);
    }
    // Default data input for shortest job first and shortest remaining time first
     void EnterData(){
         Queue = new ArrayList<>();
         Queue.add(new Process("1",0,8));
         Queue.add(new Process("2",1,4));
         Queue.add(new Process("3",2,2));
         Queue.add(new Process("4",3,1));
         Queue.add(new Process("5",4,3));
         Queue.add(new Process("6",5,2));
         /*
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
             Queue.add(new Process(Name, arrival, burst));
             System.out.println("Process " + i + " is added successfully\n");
         }*/
     }

    public abstract List<Process> Simulate() throws Exception; // should return list of process each process contains list of working times
    public void PrintProcessList(List<Process> data) {
        for (Process curr : data) System.out.println(curr);
        System.out.println("Average waiting time : " + getAverageWaitingTime(data));
        System.out.println("Average turnaround time : " + getAverageTurnaroundTime(data));

    }
    public static double getAverageWaitingTime(List<Process> data){
        double sum = 0;
        for (Process cur : data) sum+=cur.getWaitingTime();
        return Double.parseDouble(doubleFormat.format(Math.abs(sum)/data.size()));
        //return sum/data.size();
    }
    public static double getAverageTurnaroundTime(List<Process> data){
        double sum = 0;
        for (Process cur : data) sum+=cur.getTurnaroundTime();
        return Double.parseDouble(doubleFormat.format(Math.abs(sum)/data.size()));
        //return sum/data.size();
    }
}
//Queue.add(new Process("1",1,7));
//Queue.add(new Process("2",3,3));
//Queue.add(new Process("3",6,2));
//Queue.add(new Process("4",7,10));
//Queue.add(new Process("5",9,8));
//------------------------------------
// NO CONTEXT SWITCH
//Queue.add(new Process("1",0,8));
//Queue.add(new Process("2",1,4));
//Queue.add(new Process("3",2,2));
//Queue.add(new Process("4",3,1));
//Queue.add(new Process("5",4,3));
//Queue.add(new Process("6",5,2));
//--------------------------------------
