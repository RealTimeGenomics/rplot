package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;

/**
 * Plots a graph in a Swing JPanel described by the data in a Plot data
 * object.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class PlotPanel extends JPanel {
  /** the data that is to be plotted */
  private Graph2D mGraph = null;
  private Mapping[] mMapping = null;

  private final GraphicsRenderer mGraphicsRenderer;

  private boolean mBufferGraphs = false;
  private BufferedImage mBI = null;

  private final Color [] mColors;

  /** Default constructor. */
  public PlotPanel() {
    this(AWTDefaults.COLORS);
  }

  public PlotPanel(boolean bufferGraphs) {
    this(AWTDefaults.COLORS, bufferGraphs);
  }

  public PlotPanel(Color [] lineColors) {
    this(lineColors, false);
  }

  public PlotPanel(Color [] lineColors, boolean bufferGraphs) {
    super();
    if (lineColors == null || lineColors.length == 0) {
      throw new IllegalArgumentException("Must be at least one color.");
    }
    for (int i = 0; i < lineColors.length; i++) {
      if (lineColors[i] == null) {
        throw new NullPointerException("null color given.");
      }
    }
    mColors = lineColors;
    mGraphicsRenderer = new GraphicsRenderer(mColors);
    setBufferGraphs(bufferGraphs);
  }

  private GraphPrinter mGraphPrinter = null;
  private GraphSaver mGraphSaver = null;

  public Action [] getPlotActions() {
    Action printAction = new AbstractAction("Print") {
        public void actionPerformed(ActionEvent e) {
          if (mGraphPrinter == null) {
            mGraphPrinter = new GraphPrinter();
          }
          mGraphPrinter.printGraph(getGraph());
        }
      };
    Action saveAction = new AbstractAction("Save Image") {
        public void actionPerformed(ActionEvent e) {
          if (mGraphSaver == null) {
            mGraphSaver = new GraphSaver();
          }
          mGraphSaver.saveGraph(getGraph());
        }
      };
    //Action snapShotAction = new AbstractAction("Snap Shot", null) {
    //public void actionPerformed(ActionEvent e) {
          /*try {
            SwingPlot sp = new SwingPlot("Snap Shot");
            sp.plot((Graph2D) mGraph.clone());
          } catch (CloneNotSupportedException cnse) {
            Logger.error("CMPP2", "Failed to clone graph: " + cnse.getMessage());
            }*/
    //  }
    //};

    return new Action[] {printAction, saveAction}; //, snapShotAction};
  }


  public Color [] getColors() {
    return mColors;
  }

  public void setGraphBGColor(Color topColor, Color bottomColor) {
    mGraphicsRenderer.setGraphBGColor(topColor, bottomColor);
  }

  public void setGraphShadowWidth(int width) {
    mGraphicsRenderer.setGraphShadowWidth(width);
  }

  /**
   * Draws a graph with the given plot data.
   *
   * @param graph the graph to render
   */
  public void setGraph(Graph2D graph) {
    mBI = null;
    mGraph = graph;
    updateUI();
  }


  /**
   * return the plot data that the panel is holding
   *
   * @return The graph value.
   */
  public Graph2D getGraph() {
    return mGraph;
  }


  public Mapping[] getMapping() {
    return mMapping;
  }


  public void setAntialiasing(boolean flag) {
    mGraphicsRenderer.setAntialiasing(flag);
  }

  public void setTextAntialiasing(boolean flag) {
    mGraphicsRenderer.setTextAntialiasing(flag);
  }

  /**
   * Sets whether to buffer graph images.  Speeds things up when it
   * comes to redrawing the same graph, at the tradeoff of extra
   * memory use. 
   *
   * @param flag whether to buffer graphs
   */
  public void setBufferGraphs(boolean flag) {
    mBufferGraphs = flag;
  }

  /**
   * Overrides JPanel.paintComponent and actually renders the graph in
   * the given Graphics object.
   *
   * @param g a Graphics component.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Dimension d = getSize();

    final int screenWidth = d.width;
    final int screenHeight = d.height;

    if (mBufferGraphs) {
      BufferedImage bi = mBI;
      if (bi == null || bi.getWidth() != screenWidth || bi.getHeight() != screenHeight) {
        bi = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        mBI = bi;
        Graphics g2 = bi.createGraphics();
        g2.setColor(getBackground());
        g2.fillRect(0, 0, screenWidth, screenHeight);

        mGraphicsRenderer.drawGraph(mGraph, g2, screenWidth, screenHeight);
        mMapping = mGraphicsRenderer.getMappings();
      }
      g.drawImage(bi, 0, 0, null);
    } else {      
      mGraphicsRenderer.drawGraph(mGraph, g, screenWidth, screenHeight);
      mMapping = mGraphicsRenderer.getMappings();
    }
  }
}
