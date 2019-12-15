package com.company;

import java.awt.*;
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
    final static DecimalFormat doubleFormat = new DecimalFormat("#.###");

    List<Process> Queue;
    int currentTime;

    ProcessScheduling(List<Process> Queue) {
        this.Queue = Queue;
        currentTime = 0;
    }


    public abstract List<Process> Simulate() throws Exception; // should return list of process each process contains list of working times

    public static double getAverageWaitingTime(List<Process> data) {
        double sum = 0;
        for (Process cur : data) sum += cur.getWaitingTime();
        return Double.parseDouble(doubleFormat.format(Math.abs(sum) / data.size()));
        //return sum/data.size();
    }

    public static double getAverageTurnaroundTime(List<Process> data) {
        double sum = 0;
        for (Process cur : data) sum += cur.getTurnaroundTime();
        return Double.parseDouble(doubleFormat.format(Math.abs(sum) / data.size()));
        //return sum/data.size();
    }
}
//Queue.add(new Process("1", 1, 7, Color.cyan));
//Queue.add(new Process("2", 3, 3,Color.LIGHT_GRAY));
//Queue.add(new Process("3", 6, 2,Color.YELLOW));
//Queue.add(new Process("4", 7, 10,Color.BLUE));
//Queue.add(new Process("5", 9, 8,Color.green));
//------------------------------------
// NO CONTEXT SWITCH
//Queue.add(new Process("1",0,8,Color.green));
//Queue.add(new Process("2",1,4,Color.YELLOW));
//Queue.add(new Process("3",2,2,Color.pink));
//Queue.add(new Process("4",3,1,Color.RED));
//Queue.add(new Process("5",4,3,Color.orange));
//Queue.add(new Process("6",5,2,Color.cyan));
//--------------------------------------
//Queue.add(new Process("1",1,6, Color.cyan));
//Queue.add(new Process("2",1,8,Color.LIGHT_GRAY));
//Queue.add(new Process("3",2,7,Color.YELLOW));
//Queue.add(new Process("4",3,3,Color.BLUE));