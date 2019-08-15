package graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author vighnesh
 */
public class ParticleThread implements Runnable{
    @Override
    public void run() {
        
        // Get object from InvestmentPSO class
        ArrayList<Particle> particles = Algorithm.particles;
        MainJFrame mj = Algorithm.mj;
        
        
               
        // Create dataset                    
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Particles ");
//        XYSeries series4 = new XYSeries("Optimistic gBest ");
//        XYSeries series2 = new XYSeries("Most likely values");
//        XYSeries series5 = new XYSeries("Most likely gBest ");
//        XYSeries series3 = new XYSeries("Pessimistic values");
//        XYSeries series6 = new XYSeries("Pessimistic gBest ");
        
        
    
        for(Map.Entry<Integer, Integer> entry : Algorithm.particleTreeMap.entrySet()){
            
              series1.add(entry.getKey(), entry.getValue());
//            series2.add(p.data(1), 3);
//            series3.add(p.data(2),5);
//            series4.add(AlgosProject.particles.get(AlgosProject.gBest).data(0),2);
//            series5.add(AlgosProject.particles.get(AlgosProject.gBest).data(1),4);
//            series6.add(AlgosProject.particles.get(AlgosProject.gBest).data(2),6);
        }

        dataset.addSeries(series1);
//        dataset.addSeries(series2);
//        dataset.addSeries(series3);
//        dataset.addSeries(series4);
//        dataset.addSeries(series5);
//        dataset.addSeries(series6);
        XYDataset xydataset = dataset;
                        
                        
                            
        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
        "Amount to be invested vs Amount that can be expected!", 
        "Returns", "Investment", xydataset);
        
        

    
        //Changes background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,228,196));
        
        
        

        
        
                  
   
        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        mj.getContentPane().removeAll();
        mj.setContentPane(panel);                        
                        
        mj.setVisible(true);        
        
        
    }
    
}
