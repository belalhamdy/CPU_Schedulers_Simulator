package com.company;

import org.jfree.ui.ApplicationFrame;

import java.util.List;

public class QuantumHistory extends ApplicationFrame {

    private final List<Process> processes;

    public QuantumHistory(List<Process> result) {
        super("Quantum History");
        this.processes = result;
    }
    void plot(){
        //@TODO: find suitable way to plot them
    }
    void close(){
        this.setVisible(false);
        this.dispose();
    }
}
