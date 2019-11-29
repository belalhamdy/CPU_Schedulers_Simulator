package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        List<Process> Result;
        ProcessScheduling simulator;
       loop: while (true) {
            System.out.println("1- Shortest- Job First (SJF)\n2- Shortest- Remaining Time First (SRTF)\n3- Priority Scheduling\n4- AG Scheduling\n0- Exit");
            int c = in.nextInt();
            switch (c) {
                case 1:
                    simulator = new ShortestJobFirstScheduling(System.in);
                    break;
                case 2:
                    simulator = new ShortestRemainingTimeFirstScheduling(System.in);
                    break;
                case 3:
                    simulator = new PriorityScheduling(System.in);
                    break;
                case 4:
                    simulator = new AGScheduling(System.in);
                    break;
                default:
                    break loop;
            }
           try {
               Result = simulator.Simulate();
               ProcessScheduling.PrintProcessList(Result);
           } catch (Exception e) {
               System.out.println(e.getMessage());
           }
           System.out.println("-----------------------------------------------");
        }
        System.out.println("Program Ended");

    }
}
