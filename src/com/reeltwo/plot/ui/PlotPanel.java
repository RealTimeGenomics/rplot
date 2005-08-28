package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;

/**
 * Plots a graph in a Swing JPanel described by the data in a Plot data
 * object.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public class PlotPanel extends JPanel {
  /** the data that is to be plotted */
  private Graph2D mGraph = null;
  private Mapping[] mMapping = null;

  private final GraphicsRenderer mGraphicsRenderer;

  private boolean mBufferGraphs = false;
  private BufferedImage mBI = null;

  private ToolTipProvider mToolTipProvider = null;
    
  /** Default constructor. */
  public PlotPanel() {
    this(false);
  }

  /**
   * Creates a new <code>PlotPanel</code> allowing graphs to be
   * buffered.  Buffering helps speed things up but uses more memory.
   *
   * @param bufferGraphs whether to buffer graphs
   */
  public PlotPanel(boolean bufferGraphs) {
    setBufferGraphs(bufferGraphs);
    mGraphicsRenderer = new GraphicsRenderer();
  }

  /**
   * Creates a new <code>PlotPanel</code> setting the colors to use.
   *
   * @param lineColors an array of <code>Color</code>s
   */
  public PlotPanel(Color[] lineColors) {
    this(lineColors, false);
  }

  /**
   * Creates a new <code>PlotPanel</code> setting the colors to use
   * and allowing graphs to be buffered.
   *
   * @param lineColors an array of <code>Color</code>s
   * @param bufferGraphs whether to buffer graphs
   */
  public PlotPanel(Color[] lineColors, boolean bufferGraphs) {
    this(bufferGraphs);
    if (lineColors == null || lineColors.length == 0) {
      throw new IllegalArgumentException("Must be at least one color.");
    }
    for (int i = 0; i < lineColors.length; i++) {
      if (lineColors[i] == null) {
        throw new NullPointerException("null color given.");
      }
    }
    setColors(lineColors);
  }

  /** {@inheritDoc} */
  public String getToolTipText(MouseEvent event) {
    if (mToolTipProvider != null) {
      return mToolTipProvider.getToolTipText(event.getX(), event.getY());
    }
    return null;
  }

  /**
   * Sets the ToolTipProvider to allow position dependent tool tip
   * text. 
   *
   * @param provider a <code>ToolTipProvider</code>
   */
  public void setToolTipProvider(ToolTipProvider provider) {
    mToolTipProvider = provider;
    setToolTipText(mToolTipProvider == null ? null : "");
  }

  private GraphPrinter mGraphPrinter = null;
  private GraphSaver mGraphSaver = null;

  /**
   * Returns an action allowing the graph to be printed.
   *
   * @return an <code>Action</code>
   */
  public Action getPrintAction() {
    return new AbstractAction("Print") {
        public void actionPerformed(ActionEvent e) {
          if (mGraphPrinter == null) {
            mGraphPrinter = new GraphPrinter();
          }
          mGraphPrinter.setColors(getColors());
          mGraphPrinter.setPatterns(getPatterns());
          mGraphPrinter.printGraph(getGraph());
        }
      };
  }

  /**
   * Returns an action allowing the graph to be saved.
   *
   * @return an <code>Action</code>
   */
  public Action getSaveImageAction() {
    return new AbstractAction("Save Image") {
        public void actionPerformed(ActionEvent e) {
          if (mGraphSaver == null) {
            mGraphSaver = new GraphSaver();
          }
          mGraphSaver.setColors(getColors());
          mGraphSaver.setPatterns(getPatterns());
          mGraphSaver.saveGraph(getGraph());
        }
      };
  }

  /**
   * Returns an action allowing a shapshot of the graph to be rendered
   * in a separate dialog.
   *
   * @return an <code>Action</code>
   */
  public Action getSnapShotAction() {
    return new AbstractAction("Snap Shot", null) {
        public void actionPerformed(ActionEvent e) {
          try {
            PlotDialog pd = new PlotDialog();
            pd.setTitle("Snap Shot");
            pd.setGraph((Graph2D) mGraph.clone());
            pd.setVisible(true);
          } catch (CloneNotSupportedException cnse) {
            System.err.println("Failed to clone graph: " + cnse.getMessage());
          }
        }
      };
  }

  /**
   * Sets the colors to render plots with.
   *
   * @param colors an array of colors
   */
  public void setColors(Color[] colors) {
    mGraphicsRenderer.setColors(colors);
  }

  /**
   * Returns the plot colors.
   *
   * @return an array of <code>Color</code>s
   */
  public Color[] getColors() {
    return mGraphicsRenderer.getColors();
  }

  /**
   * Sets the patterns to use when rendering plots.
   *
   * @param patterns an array of patterns
   */
  public void setPatterns(Paint[] patterns) {
    mGraphicsRenderer.setPatterns(patterns);
  }

  /**
   * Returns the plot patterns.
   *
   * @return an array of patterns
   */
  public Paint[] getPatterns() {
    return mGraphicsRenderer.getPatterns();
  }


  /**
   * Sets the graphs background colors.  The color is blended from
   * <code>topColor</code> to <code>bottomColor</code> from top to
   * bottom in the graph.
   *
   * @param topColor a <code>Color</code>
   * @param bottomColor a <code>Color</code>
   */
  public void setGraphBGColor(Color topColor, Color bottomColor) {
    mGraphicsRenderer.setGraphBGColor(topColor, bottomColor);
  }

  /**
   * Sets the width of the shadow around the graph border.
   *
   * @param width shadow width
   */
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
   * Returns the plot data that the panel is holding
   *
   * @return a graph
   */
  public Graph2D getGraph() {
    return mGraph;
  }


  /**
   * Returns the mapping of graph elements to screen co-ords.
   *
   * @return an array <code>Mapping</code>s
   */
  public Mapping[] getMapping() {
    return mMapping;
  }


  /**
   * Turns antialiasing on or off based on <code>flag</code>.
   *
   * @param flag antialiasing switch
   */
  public void setAntialiasing(boolean flag) {
    mGraphicsRenderer.setAntialiasing(flag);
  }

  /**
   * Turns text antialiasing on or off based on <code>flag</code>.
   *
   * @param flag antialiasing switch
   */
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
    //System.err.println("Set mapping:" + mMapping);
    if (mMapping != null && mToolTipProvider != null) {
      mToolTipProvider.setMaps(mMapping[0], mMapping[1]);
    }
  }
}
