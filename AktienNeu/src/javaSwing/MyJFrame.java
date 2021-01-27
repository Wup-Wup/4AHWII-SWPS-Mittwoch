package javaSwing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import database.DatabaseTimeSeries;

public class MyJFrame {
	
	JFrame frame;
	ChartPanel chartPanel;
	
	public MyJFrame(String name) {
		
		this.frame = new JFrame(name);
		this.frame.setSize(1500,1000);
		this.frame.setVisible(true);
	}
	
	public JFreeChart createNewChart(String tableName, TreeMap<Date,BigDecimal> data200Avg, TreeMap<Date,BigDecimal> data) {
		
		TimeSeries ts1 = new TimeSeries(tableName);
		for(Date d : data.keySet()) {
			BigDecimal value = data.get(d);
			LocalDate date = d.toLocalDate();
			int day = date.getDayOfMonth();
			int month = date.getMonthValue();
			int year = date.getYear();
			ts1.add(new Day(day, month, year), value);
		}
		TimeSeries ts2 = new TimeSeries("200 avg");
		for(Date d : data200Avg.keySet()) {
			BigDecimal value = data200Avg.get(d);
			LocalDate date = d.toLocalDate();
			int day = date.getDayOfMonth();
			int month = date.getMonthValue();
			int year = date.getYear();
			ts2.add(new Day(day, month, year), value);
		}
		TimeSeriesCollection ts_collection = new TimeSeriesCollection();
        ts_collection.addSeries(ts1);
        ts_collection.addSeries(ts2);
        
        JFreeChart jChart = ChartFactory.createTimeSeriesChart(tableName, "Date", "Close Value in $", ts_collection, true, true ,false);
        XYPlot plot = (XYPlot) jChart.getPlot();
        plot.getRenderer().setDefaultStroke(new BasicStroke(2.0f));
        ((AbstractRenderer) plot.getRenderer()).setAutoPopulateSeriesStroke(false);
        ((AbstractRenderer) plot.getRenderer()).setSeriesPaint(0,  new Color(0, 0, 0));
        ((AbstractRenderer) plot.getRenderer()).setSeriesPaint(1,  new Color(255, 255, 255));
        return jChart;

	}
	
	public void changeChart(String tableName, TreeMap<Date,BigDecimal> data200Avg, TreeMap<Date,BigDecimal> data, int back) {
		JFreeChart jChart = createNewChart(tableName, data200Avg, data) ;
		if(this.chartPanel != null) {
			this.frame.remove(this.chartPanel);
		}
		this.chartPanel = new ChartPanel(jChart);
		chartPanel.setMaximumDrawHeight(4000);
		chartPanel.setMaximumDrawWidth(4000);
		this.chartPanel.setChart(jChart);
		this.frame.add(chartPanel);
		this.frame.revalidate();
		this.frame.repaint();
		if(back==1) {
			jChart.getPlot().setBackgroundPaint(Color.decode("#2cab4e"));
		}else {
			jChart.getPlot().setBackgroundPaint( Color.decode("#b84949"));
		}
		this.frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	}
}
