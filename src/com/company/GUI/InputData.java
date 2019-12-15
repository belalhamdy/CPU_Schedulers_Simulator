package com.company.GUI;

import com.company.*;
import com.company.GUI.GanttChart;
import com.company.GUI.ProcessesStat;
import com.company.GUI.QuantumHistory;
import com.company.Process;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputData {
    private JPanel pnlMain;
    private JTable queueTbl;
    private JButton simulateButton;
    private JButton endSimulationButton;
    private JTextField Nametxt;
    private JSpinner Arrivalspn;
    private JSpinner Burstspn;
    private JSpinner Priorityspn;
    private JSpinner Quantumspn;
    private JButton addButton;
    private JComboBox<String> colorcbx;
    private JComboBox<String> simulationType;


    List<Process> queue = new ArrayList<>();
    GanttChart chart = null;
    QuantumHistory history = null;
    ProcessesStat stat = null;
    public InputData(){
        JFrame form = new JFrame("CPU Scheduler");
        form.setResizable(false);

        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setContentPane(pnlMain);
        form.pack();
        form.setVisible(true);

        form.setSize(600,500);
        DefaultTableModel  modelTable = new DefaultTableModel(null, new String[]{"ID", "Name", "Arrival Time" ,"Burst Time", "Priority", "Quantum", "AG Factor"});
        queueTbl.setModel(modelTable);

        SpinnerNumberModel modelSpinner;
        //Set up Arrival spn
        modelSpinner = (SpinnerNumberModel) Arrivalspn.getModel();
        modelSpinner.setMinimum(0);
        modelSpinner.setValue(0);

        //Set up Burst spn
        modelSpinner = (SpinnerNumberModel) Burstspn.getModel();
        modelSpinner.setMinimum(1);
        modelSpinner.setValue(1);

        //Set up Priority spn
        modelSpinner = (SpinnerNumberModel) Priorityspn.getModel();
        modelSpinner.setMinimum(0);
        modelSpinner.setValue(0);

        //Set up Quantum spn
        modelSpinner = (SpinnerNumberModel) Quantumspn.getModel();
        modelSpinner.setMinimum(1);
        modelSpinner.setValue(1);

        simulationType.addItem("Shortest Job first");
        simulationType.addItem("Shortest Remaining Time first");
        simulationType.addItem("Priority Scheduler");
        simulationType.addItem("AG Factor Scheduler");
        simulationType.setSelectedIndex(0);

        colorcbx.addItem("Cyan");
        colorcbx.addItem("Light Gray");
        colorcbx.addItem("Dark Gray");
        colorcbx.addItem("Yellow");
        colorcbx.addItem("Blue");
        colorcbx.addItem("Pink");
        colorcbx.addItem("Magenta");
        colorcbx.addItem("Green");
        colorcbx.addItem("Orange");
        colorcbx.addItem("Red");
        colorcbx.setSelectedIndex(0);

        endSimulationButton.setEnabled(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addToQueue();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    INVALID(ex.getMessage());
                }
            }
        });
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startSimulation();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    INVALID(ex.getMessage());
                }
            }
        });
        endSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endSimulation();
            }
        });
    }

    private void endSimulation() {
        clearAll();
        if (chart != null) chart.close();
        if (stat != null) stat.close();
        if (history != null) history.close();
        endSimulationButton.setEnabled(false);
        simulateButton.setEnabled(true);
    }

    void clearAll(){
        queue.clear();

        resetVariables();

        DefaultTableModel tblmodel = new DefaultTableModel(null, new String[]{"ID", "Name", "Arrival Time" ,"Burst Time", "Priority", "Quantum", "AG Factor"});
        queueTbl.setModel(tblmodel);

        simulateButton.setEnabled(true);
        endSimulationButton.setEnabled(false);

        simulationType.setSelectedIndex(0);
        colorcbx.setSelectedIndex(0);

    }
    void resetVariables(){

        Nametxt.setText("");

        Burstspn.setValue(1);
        Arrivalspn.setValue(0);
        Quantumspn.setValue(1);
        Priorityspn.setValue(0);
    }
    void INVALID(String msg){
        JOptionPane.showMessageDialog(null, msg, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    Color getColor(String name){
        switch (name){
            case "Cyan": return Color.cyan;
            case "Light Gray": return Color.LIGHT_GRAY;
            case "Dark Gray": return Color.DARK_GRAY;
            case "Yellow": return Color.YELLOW;
            case "Blue": return Color.BLUE;
            case "Pink": return Color.PINK;
            case "Magenta": return Color.MAGENTA;
            case "Green": return Color.GREEN;
            case "Orange": return Color.ORANGE;
            case "Red": return Color.RED;
            default: return Color.BLACK;
        }
    }
    void addToQueue() throws Exception{
        String name = Nametxt.getText();
        if (name == null || name.isEmpty()) throw new Exception("Name cannot be empty.");

        int burst = (int) Burstspn.getValue();
        int arrival = (int) Arrivalspn.getValue();
        int priority = (int) Priorityspn.getValue();
        int quantum = (int) Quantumspn.getValue();
        Color color = getColor(Objects.requireNonNull(colorcbx.getSelectedItem()).toString());

        Process curr = new Process(name,arrival,burst,priority,quantum,color);
        queue.add(curr);
        DefaultTableModel model = (DefaultTableModel) queueTbl.getModel();
        model.addRow(new Object[]{curr.id,curr.Name,curr.ArrivalTime,curr.BurstTime,curr.Priority,curr.Quantum,curr.AGFactor});

        resetVariables();

    }
    void startSimulation() throws Exception {
        if (queue == null || queue.isEmpty()) throw new Exception("Queue cannot be empty.");

        int type = simulationType.getSelectedIndex();
        ProcessScheduling simulator;

        if (type == 0) simulator = new ShortestJobFirstScheduling(queue);
        else if (type == 1) simulator = new ShortestRemainingTimeFirstScheduling(queue);
        else if (type == 2) simulator = new PriorityScheduling(queue);
        else if (type == 3) simulator = new AGScheduling(queue);
        else throw new Exception("We have faced an error please try again later.");

        List<Process> Result = simulator.Simulate();
        if (Result != null) {
            stat = new ProcessesStat(Result);
            stat.plot();
            chart = new GanttChart(pnlMain.getName(), Result);
            chart.plot();
            if (type == 3){
                history = new QuantumHistory(Result);
                history.plot();
            }
        }
        simulateButton.setEnabled(false);
        endSimulationButton.setEnabled(true);
    }

}
