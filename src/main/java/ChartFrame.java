import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.tabbedui.VerticalLayout;
import rx.functions.Action1;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChartFrame extends JFrame {

    private JPanel chartPanel;
    private JPanel dataPanel;

    private XYSeriesCollection dataset;

    private DataService dataService;

    public ChartFrame() {
        dataService = DataService.getInstance();

        setTitle("Chart Demo");
        chartPanel = createChartPanel();
        dataPanel = createDataPanel();

        add(chartPanel, BorderLayout.WEST);
        add(dataPanel, BorderLayout.EAST);

        dataService.getData().subscribe(
                new Action1<Map<Double, Double>>() {
                    public void call(Map<Double, Double> response) {
                        updateData(response);
                    }
                }
        );
    }

    private JPanel createDataPanel() {
        JPanel panel = new DataPanel(new VerticalLayout());
//        panel.setPreferredSize(new Dimension(590, 600));
        return panel;
    }

    private JPanel createChartPanel() {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
//        panel.setPreferredSize(new Dimension(590, 300));
        return panel;
    }

    private JFreeChart createChart(XYSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "y = x^2",
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint    (Color.lightGray);
        plot.setDomainGridlinePaint(Color.white    );
        plot.setRangeGridlinePaint (Color.white    );
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible   (true);
            renderer.setBaseShapesFilled    (true);
            renderer.setDrawSeriesLineAsPath(true);
        }
        return chart;
    }

    private XYSeriesCollection createDataset()
    {
        dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("f(x)");
        dataset.addSeries(series);
        return dataset;
    }

    public void updateData(Map<Double, Double> data) {
        Iterator it = data.keySet().iterator();

//        XYSeries series = dataset.getSeries(0);
        XYSeries series = new XYSeries("");
        while (it.hasNext()) {
            Object key = it.next();
            series.add(Double.parseDouble(key.toString()), (double) data.get(key));
        }

        dataset.removeSeries(0);
        dataset.addSeries(series);
//        repaint();
    }
}
