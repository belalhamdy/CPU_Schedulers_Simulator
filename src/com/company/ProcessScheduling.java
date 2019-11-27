package com.company;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class ProcessScheduling {
    List<Process> Queue;
    InputStream inputStream;

    ProcessScheduling(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    protected abstract List<Process> EnterData();

    protected abstract List<Process> Simulate(List<Process> Queue); // should return list of process each process contains list of working times

    public List<Process> getSimulationResults() {
        return Simulate(EnterData());
    }

    public static void PrintProcessList(List<Process> data) {
        //@TODO: Complete this function

    }
}
