package com.company;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
Every child class can fill the queue either by getting a list from somewhere or taking it from console
 */
public abstract class ProcessScheduling {
    InputStream inputStream;
    List<Process> Queue;

    ProcessScheduling(InputStream inputStream) {
        this.inputStream = inputStream;
        Queue = EnterData();
    }

    ProcessScheduling(List<Process> data) {
        Queue = new ArrayList<>(data);
    }

    protected abstract List<Process> EnterData();

    public abstract List<Process> Simulate(); // should return list of process each process contains list of working times

    public static void PrintProcessList(List<Process> data) {
        //@TODO: Complete this function

    }
}
