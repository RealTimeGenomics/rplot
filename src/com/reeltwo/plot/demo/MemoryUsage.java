package com.reeltwo.plot.demo;

import com.reeltwo.plot.FillablePlot2D;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.Point2D;
import com.reeltwo.plot.PointPlot2D;
import com.reeltwo.plot.ui.PlotPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * A dialog displaying memory use over time.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */
public class MemoryUsage extends JDialog {
  private volatile boolean mKeepRunning = false;

  private static final int DEFAULT_SAMPLES = 100;
  private static final int DEFAULT_SAMPLE_PERIOD = 1000;

  private int mNumberOfSamples = DEFAULT_SAMPLES;
  private int mSamplePeriod = DEFAULT_SAMPLE_PERIOD;

  private MemoryMonitor mMonitor = MemoryMonitor.getInstance();

  MemoryUsage() {
    super();
    setTitle("Memory Usage");

    JPanel mainPanel = new JPanel(new BorderLayout());

    final MemoryPanel mp = new MemoryPanel();

    mainPanel.add(mp, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

    buttonPanel.add(new JButton(
      new AbstractAction("GC") {
        public void actionPerformed(ActionEvent e) {
          mp.refresh();
        }
      }));
    buttonPanel.add(new JButton(
      new AbstractAction("Close") {
        public void actionPerformed(ActionEvent e) {
          close();
        }
      }));
    // make all buttons same width and height
    Component[] components = buttonPanel.getComponents();
    int maxX = 0;
    int maxY = 0;
    for (int i = 0; i < components.length; i++) {
      Dimension d = components[i].getPreferredSize();
      if (d.width > maxX) {
        maxX = d.width;
      }
      if (d.height > maxY) {
        maxY = d.height;
      }
    }
    Dimension d = new Dimension(maxX, maxY);
    for (int i = 0; i < components.length; i++) {
      ((JButton) components[i]).setPreferredSize(d);
    }

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    setContentPane(mainPanel);

    setSize(new Dimension(500, 200));
    setLocationRelativeTo(getOwner());

    addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          close();
        }
      });
  }


  void close() {
    mKeepRunning = false;
    dispose();
  }


  /**
   * If 1 or more arguments are given, MemoryUsageDialog will attempt
   * to invoke the first argument's main method with the remaining
   * arguments as parameters. It will exit if any exceptions are
   * thrown.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      MemoryUsage mud = new MemoryUsage() {
          public void close() {
            super.close();
            System.exit(0);
          }
        };
      mud.setVisible(true);

      //args[0] => class name
      //args[1]..args[n-1] => command line parameters
      if (args.length >= 1) {
        Class c = ClassLoader.getSystemClassLoader().loadClass(args[0]);
        Method m = c.getMethod("main", new Class[]{String[].class});
        String[] a = new String[args.length - 1];
        System.arraycopy(args, 1, a, 0, a.length);
        System.out.println("Invoking: " + c.getName() + ".main()");
        m.invoke(c, new Object[]{a});
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
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


  private NumberFormat mMemFormat = new DecimalFormat("0.0 MB");


  private class MemoryPanel extends JPanel {

    private final JTextField mMaxMemText = new RTextField();
    private final JTextField mTotalMemText = new RTextField();
    private final JTextField mUsedMemText = new RTextField();

    private final PlotPanel mGraph;


    MemoryPanel() {
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


    private void refresh() {
      Runtime r = Runtime.getRuntime();
      r.gc();
      final float max = mMonitor.getMaxMemory() / 1024.0f;
      final float total = mMonitor.getTotalMemory() / 1024.0f;
      final float used = mMonitor.getUsedMemory() / 1024.0f;
      final float free = total - used;
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

            Runtime r = Runtime.getRuntime();

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
              final float free = total - used;

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
  }

}
