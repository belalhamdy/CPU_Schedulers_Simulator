package com.company;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcessScheduling {
    List<Process> Queue;

    ProcessScheduling(List<Process> Queue) {
        this.Queue = new ArrayList<>(Queue);
    }

    abstract List<Process> Simulate(); // should return list of process each process contains list of working times
    public List<Process> getSimulationResults(){
        return Simulate();
    }
}
