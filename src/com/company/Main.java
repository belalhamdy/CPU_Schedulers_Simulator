package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
/*    private static final long serialVersionUID = 1L;

    static {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
                true));
    }

    public Main(String title) {
        super(title);
        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setBackground(null);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDismissDelay(Integer.MAX_VALUE);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }


    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(7445, "JFreeSVG", "Warm-up");
        dataset.addValue(24448, "Batik", "Warm-up");
        dataset.addValue(4297, "JFreeSVG", "Test");
        dataset.addValue(21022, "Batik", "Test");
        return dataset;
    }
    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Performance: JFreeSVG vs Batik", null ,
                "Milliseconds" , dataset);
        chart.addSubtitle(new TextTitle("Time to generate 1000 charts in SVG "
                + "format (lower bars = better performance)"));
        chart.setBackgroundPaint(null);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(null);

        // ******************************************************************
        //  More than 150 demo applications are included with the JFreeChart
        //  Developer Guide...for more information, see:
        //
        //  >   http://www.object-refinery.com/jfreechart/guide.html
        //
        // ******************************************************************

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    public static void main(String[] args) {
        Main demo = new Main("JFreeChart: BarChartDemo1.java");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}*/ static Scanner in = new Scanner(System.in);
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
               simulator.PrintProcessList(Result);

           } catch (Exception e) {
               System.out.println(e.getMessage());
               e.printStackTrace();
           }
           System.out.println("-----------------------------------------------");
        }
        System.out.println("Program Ended");

    }
}
