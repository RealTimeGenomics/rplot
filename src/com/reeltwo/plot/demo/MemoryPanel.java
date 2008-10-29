package com.reeltwo.plot.demo;

import com.reeltwo.plot.FillablePlot2D;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.Point2D;
import com.reeltwo.plot.PointPlot2D;
import com.reeltwo.plot.ui.PlotPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * JPanel that displays and updating graph of memory used over time.
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */

public class MemoryPanel extends JPanel {

  private static final int DEFAULT_SAMPLES = 100;
  private static final int DEFAULT_SAMPLE_PERIOD = 1000;

  private int mNumberOfSamples = DEFAULT_SAMPLES;
  private int mSamplePeriod = DEFAULT_SAMPLE_PERIOD;
  private boolean mKeepRunning = false;

  private MemoryMonitor mMonitor = MemoryMonitor.getInstance();

  private final JTextField mMaxMemText = new RTextField();
  private final JTextField mTotalMemText = new RTextField();
  private final JTextField mUsedMemText = new RTextField();

  private NumberFormat mMemFormat = new DecimalFormat("0.0 MB");

  private final PlotPanel mGraph;

  public MemoryPanel() {
    super();
    this.setLayout(new BorderLayout());

    JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    split.setDividerSize(5);
    split.setDividerLocation(100);

    JPanel memPanel = new JPanel();

    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    memPanel.setLayout(gridbag);
    memPanel.setOpaque(false);

    c.anchor = GridBagConstraints.WEST;
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 1;
    c.weighty = 0;
    c.insets = new Insets(3, 3, 3, 3);
    c.fill = GridBagConstraints.HORIZONTAL;

    gridbag.setConstraints(mMaxMemText, c);
    memPanel.add(mMaxMemText);

    c.gridy++;
    gridbag.setConstraints(mTotalMemText, c);
    memPanel.add(mTotalMemText);

    c.gridy++;

    gridbag.setConstraints(mUsedMemText, c);
    memPanel.add(mUsedMemText);

    mGraph = new PlotPanel(new Color[] {
                             new Color(0.0f, 0.0f, 1.0f, 0.75f),
                             new Color(0.0f, 1.0f, 0.75f, 0.5f),
                             Color.RED
                           });
    split.setRightComponent(mGraph);
    split.setLeftComponent(memPanel);

    mKeepRunning = true;
    monitor();

    refresh();
    this.add(split, BorderLayout.CENTER);
  }


  public void refresh() {
    Runtime r = Runtime.getRuntime();
    r.gc();
    final float max = mMonitor.getMaxMemory() / 1024.0f;
    final float total = mMonitor.getTotalMemory() / 1024.0f;
    final float used = mMonitor.getUsedMemory() / 1024.0f;
    mMaxMemText.setText("Max: " + mMemFormat.format(max));
    mTotalMemText.setText("Total: " + mMemFormat.format(total));
    mUsedMemText.setText("Used: " + mMemFormat.format(used));
  }


  private int mSampleIndex = 0;    
  private int mSampleIndex2 = -mNumberOfSamples + 1;
  private float[] mTotals = new float[mNumberOfSamples];
  private float[] mUsed = new float[mNumberOfSamples];


  private void monitor() {
    Thread t = new Thread() {
        public void run() {
          final float max = mMonitor.getMaxMemory() / 1024.0f;

          Point2D[] totalPoints = new Point2D[mNumberOfSamples + 2];
          Point2D[] usedPoints = new Point2D[mNumberOfSamples + 2];
          totalPoints[0] = new Point2D(0, 0);
          usedPoints[0] = new Point2D(0, 0);
          for (int i = 0; i < mNumberOfSamples; i++) {
            totalPoints[i + 1] = new Point2D(i, 0);
            usedPoints[i + 1] = new Point2D(i, 0);
          }
          totalPoints[mNumberOfSamples + 1] = new Point2D(mNumberOfSamples - 1, 0);
          usedPoints[mNumberOfSamples + 1] = new Point2D(mNumberOfSamples - 1, 0);

          Point2D[] maxPoints = new Point2D[] {
            new Point2D(0, max),
            new Point2D(100, max)
          };
          PointPlot2D mplot = new PointPlot2D();
          mplot.setLines(true);
          mplot.setPoints(false);
          mplot.setColor(2);

          PointPlot2D tplot = new PointPlot2D();
          tplot.setFill(FillablePlot2D.COLOR_FILL);

          PointPlot2D uplot = new PointPlot2D();
          uplot.setFill(FillablePlot2D.COLOR_FILL);

          while (mKeepRunning) {
            final Graph2D graph = new Graph2D();
            graph.addPlot(tplot);
            graph.addPlot(uplot);
            
            graph.setGrid(true);

            final float total = mMonitor.getTotalMemory() / 1024.0f;
            final float used = mMonitor.getUsedMemory() / 1024.0f;

            mMaxMemText.setText("Max: " + mMemFormat.format(max));
            mTotalMemText.setText("Total: " + mMemFormat.format(total));
            mUsedMemText.setText("Used: " + mMemFormat.format(used));

            mTotals[mSampleIndex] = total;
            mUsed[mSampleIndex] = used;

            int index;
            for (int i = 1; i <= mNumberOfSamples; i++) {
              index = Math.abs((mSampleIndex + i) % mNumberOfSamples);
              totalPoints[i].setY(mTotals[index]);
              totalPoints[i].setX(mSampleIndex2 + i);
              usedPoints[i].setY(mUsed[index]);
              usedPoints[i].setX(mSampleIndex2 + i);
            }
            totalPoints[0].setX(mSampleIndex2 + 1);
            usedPoints[0].setX(mSampleIndex2 + 1);
            totalPoints[mNumberOfSamples + 1].setX(mSampleIndex2 + mNumberOfSamples);
            usedPoints[mNumberOfSamples + 1].setX(mSampleIndex2 + mNumberOfSamples);

            tplot.setData(totalPoints);
            uplot.setData(usedPoints);
              
            final float ymax = graph.getYHi(0);
            if (max <= ymax) {
              maxPoints[0].setX(mSampleIndex2 < -1 ? 0 : mSampleIndex2 + 1);
              maxPoints[1].setX(mSampleIndex2 + mNumberOfSamples);
              mplot.setData(maxPoints);
              graph.addPlot(mplot);
            }

            graph.setXRange(mSampleIndex2 < -1 ? 0 : mSampleIndex2 + 1, mSampleIndex2 + mNumberOfSamples);

            mGraph.setGraph(graph);

            mSampleIndex = (mSampleIndex + 1) % mNumberOfSamples;
            mSampleIndex2++;
            try {
              sleep(mSamplePeriod);
            } catch (InterruptedException ie) {
              ; // ok
            }
          }
        }
      };
    t.start();
  }
  
  public void stop() {
    mKeepRunning = false;
  }

  private class RTextArea extends JTextArea {
    RTextArea() {
      super();
      this.setOpaque(false);
      this.setEditable(false);
      this.setLineWrap(true);
      this.setWrapStyleWord(true);
      this.setFont(new JLabel().getFont());
    }


    RTextArea(final String s) {
      this();
      setText(s);
    }
  }


  private class RTextField extends JTextField {
    RTextField() {
      super();
      this.setOpaque(false);
      this.setEditable(false);
      this.setBorder(null);
    }


    RTextField(final String s) {
      this();
      setText(s);
    }
  }
}
