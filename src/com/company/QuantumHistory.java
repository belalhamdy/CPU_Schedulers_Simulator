package com.company;

import com.sun.org.apache.bcel.internal.generic.JsrInstruction;
import javafx.util.Pair;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class QuantumHistory extends ApplicationFrame {

    private final List<Process> processes;

    public QuantumHistory(List<Process> result) {
        super("Quantum History");
        this.processes = result;
        Collections.sort(processes, new ProcessComparator(ProcessComparator.ComparisonType.ID));

    }

    void plot() {

        TextArea data = new TextArea();
        JScrollPane pane = new JScrollPane(data);
        JLabel lblHeading = new JLabel(getTitle());
        lblHeading.setFont(new Font("Arial", Font.PLAIN,24));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.setSize(550, 350);
        this.setVisible(true);
        this.getContentPane().add(pane, BorderLayout.CENTER);
        StringBuilder quantums = new StringBuilder();
        for(Process curr : processes){
            quantums.append("  ").append("Process: ").append(curr.id).append(" : ");
            List<Pair<Integer,Integer>> currQuantum = curr.QuantumHistory;
            for (Pair<Integer,Integer> p : currQuantum){
                quantums.append(p.getKey()).append(" -> ").append(p.getValue()).append("    ");
            }
            quantums.append("\n\n");
        }
        data.setEditable(false);
        data.setText(quantums.toString());
    }

    void close() {
        this.setVisible(false);
        this.dispose();
    }
}
