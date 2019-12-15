package com.company.GUI;

import com.company.Process;
import com.company.ProcessComparator;
import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GanttChart extends ApplicationFrame {
    private final List<Process> processes;
    private final int minimumTime, maximumTime;

    GanttChart(String name, List<Process> processes) {
        super(name);
        this.processes = processes;

        int maxTimeTemp = processes.get(0).getEndTime();
        int minTimeTemp = processes.get(0).getStartTime();
        for (Process curr : processes) {
            maxTimeTemp = Math.max(maxTimeTemp, curr.getEndTime());
            minTimeTemp = Math.min(minTimeTemp, curr.getStartTime());
        }

        maximumTime = maxTimeTemp + 1;
        minimumTime = Math.max(minTimeTemp - 1, 0);

        Collections.sort(processes, new ProcessComparator(ProcessComparator.ComparisonType.ID));
    }

    public void plot() {
        GanttCategoryDataset dataset = getDataset();
        JFreeChart chart = getDataChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 500));
        setContentPane(chartPanel);
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    private GanttCategoryDataset getDataset() {
        SimpleTimePeriod range = new SimpleTimePeriod(minimumTime, maximumTime);
        TaskSeriesCollection taskSeries = new TaskSeriesCollection();
        for (Process current : processes) {
            String name = current.Name;
            TaskSeries currentDurations = new TaskSeries(name);
            Task currentTask = new Task(name, range);
            for (Pair<Integer, Integer> duration : current.getWorkingTimes()) {
                Task subTask = new Task(name, new SimpleTimePeriod(duration.getKey(), duration.getValue()));
                currentTask.addSubtask(subTask);
                currentDurations.add(currentTask);
            }
            taskSeries.add(currentDurations);
        }

        return taskSeries;
    }

    private JFreeChart getDataChart(GanttCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createGanttChart(
                super.getTitle(), // chart title
                "Process", // domain axis label
                "Time", // range axis label
                dataset, // data
                true, // include legend
                true, // tooltips
                true // urls
        );
        chart.setBackgroundPaint(new Color(253, 255, 234));
        CategoryPlot plot = chart.getCategoryPlot();
        DateAxis axis = (DateAxis) plot.getRangeAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("S")); // S -> milliseconds
        axis.setTickUnit(new DateTickUnit(DateTickUnitType.MILLISECOND,1));

        CategoryItemRenderer renderer = plot.getRenderer();
        for (int i = 0; i < processes.size(); ++i) {
            renderer.setSeriesPaint(i, processes.get(i).color);
        }

        /*StandardChartTheme theme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
        String fontName = "Arial";
        theme.setTitlePaint( Color.decode( "#4572a7" ) );
        theme.setExtraLargeFont( new Font(fontName,Font.PLAIN, 16) ); //title
        theme.setLargeFont( new Font(fontName,Font.BOLD, 15)); //axis-title
        theme.setRegularFont( new Font(fontName,Font.PLAIN, 11));
        theme.setRangeGridlinePaint( Color.decode("#C0C0C0"));
        theme.setPlotBackgroundPaint( Color.white );
        theme.setChartBackgroundPaint( Color.white );
        theme.setGridBandPaint( Color.red );
        theme.setAxisOffset( new RectangleInsets(0,0,0,0) );
        theme.setBarPainter(new StandardBarPainter());
        theme.setAxisLabelPaint( Color.decode("#666666")  );
        theme.apply( chart );
        chart.getCategoryPlot().setOutlineVisible( false );
        chart.getCategoryPlot().getRangeAxis().setAxisLineVisible( false );
        chart.getCategoryPlot().getRangeAxis().setTickMarksVisible( false );
        chart.getCategoryPlot().setRangeGridlineStroke( new BasicStroke() );
        chart.getCategoryPlot().getRangeAxis().setTickLabelPaint( Color.decode("#666666") );
        chart.getCategoryPlot().getDomainAxis().setTickLabelPaint( Color.decode("#666666") );
        chart.setTextAntiAlias( true );
        chart.setAntiAlias( true );
        //chart.getCategoryPlot().getRenderer().setSeriesPaint( 0, Color.decode( "#4572a7" ));
        BarRenderer rend = (BarRenderer) chart.getCategoryPlot().getRenderer();
        rend.setShadowVisible( true );
        rend.setShadowXOffset( 2 );
        rend.setShadowYOffset( 0 );
        rend.setShadowPaint( Color.decode( "#C0C0C0"));
        rend.setMaximumBarWidth( 0.1);*/
        return chart;
    }

    /*private JFreeChart setColors(JFreeChart chart){
        XYPlot plot = (XYPlot) chart.getPlot();
        AbstractRenderer r1 = (AbstractRenderer) plot.getRenderer(0);
        AbstractRenderer r2 = (AbstractRenderer) plot.getRenderer(1);

        r1.setSeriesPaint();
    }*/
    void close() {
        this.setVisible(false);
        this.dispose();
    }

}
