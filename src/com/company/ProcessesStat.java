package com.company;

import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;

public class ProcessesStat extends ApplicationFrame {
    double AverageWaiting,AverageTurn;
    List<Process> processes;
    public ProcessesStat(List<Process> data) {
        super("Statistics");
        this.processes = data;
        AverageTurn = ProcessScheduling.getAverageTurnaroundTime(data);
        AverageWaiting = ProcessScheduling.getAverageWaitingTime(data);
        Collections.sort(processes, new ProcessComparator(ProcessComparator.ComparisonType.ID));

    }
    void plot(){
        String[] cols = {"Process ID","Process Name","Arrival Time", "Burst Time","Start Time" , "End Time" , "Waiting Time", "Turnaround Time"};
        DefaultTableModel model = new DefaultTableModel(cols,0);
        JTable table = new JTable(model);
        for (Process curr : processes){
            model.addRow(new Object[]{curr.id,curr.Name,curr.ArrivalTime,curr.BurstTime,curr.getStartTime(),curr.getEndTime(),curr.getWaitingTime() ,curr.getTurnaroundTime()});
        }
        table.setAutoCreateRowSorter(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row >= processes.size() ? Color.LIGHT_GRAY : processes.get(row).color);
                return c;
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        JLabel lblHeading = new JLabel(getTitle());
        lblHeading.setFont(new Font("Arial", Font.PLAIN,24));

        this.getContentPane().setLayout(new BorderLayout());

        this.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        this.getContentPane().add(scrollPane,BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(550, 200);
        this.setVisible(true);
    }
    void close(){
        this.setVisible(false);
        this.dispose();
        //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }
    void showAverages()
    {
        String infoMessage = "Average Waiting Time = " + AverageWaiting + "\nAverage Turnaround Time = " + AverageTurn;
        JOptionPane.showMessageDialog(null, infoMessage, "Averages", JOptionPane.PLAIN_MESSAGE);

    }
}
