package com.company;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class AveragePriceGraphing {
    public static void makeGraph(HashMap<String, ArrayList<Product>> groups, String title) {
        var frame = new JFrame(title);
        frame.add(AveragePriceGraphing.createPanel(groups));
        frame.setSize(new Dimension(800, 800));
        frame.setVisible(true);
    }
    
    private static JPanel createPanel(HashMap<String, ArrayList<Product>> groups) {
        var chart = createChart(createDataset(groups));
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        var panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setPreferredSize(new Dimension(600, 300));
        return panel;
    }
    
    private static JFreeChart createChart(CategoryDataset dataset) {
        var chart = ChartFactory.createBarChart("График средней цены разных видов товаров",
                "Виды товаров", "Средняя цена", dataset);
        chart.addSubtitle(new TextTitle("С учетом их количества на складе и их стоимости"));
        chart.setBackgroundPaint(Color.white);
        
        var plot = (CategoryPlot) chart.getPlot();
        
        var rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        var renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        
        return chart;
    }
    
    private static CategoryDataset createDataset(HashMap<String, ArrayList<Product>> groups) {
        var dataset = new DefaultCategoryDataset();
        groups.keySet()
                .stream()
                .map(s -> new Processing.Pair<>(s, Processing.getAveragePrice(groups.get(s))))
                .sorted(Comparator.comparing(Processing.Pair::getRight))
                .forEach(p -> dataset.addValue(p.getRight(), p.getLeft(), ""));
        return dataset;
    }
}
