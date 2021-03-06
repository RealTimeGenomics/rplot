package com.reeltwo.plot.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;

/**
 * Plots a graph in a Swing JPanel described by the data in a Plot data
 * object.
 *
 * @author Richard Littin
 */

public class PlotPanel extends JPanel {
  private static final long serialVersionUID = -5783223316468154958L;
  /** the data that is to be plotted */
  private Graph2D mGraph = null;
  private Mapping[] mMapping = null;

  protected final GraphicsRenderer mGraphicsRenderer;

  private boolean mBufferGraphs = false;
  private BufferedImage mBI = null;

  private ToolTipProvider mToolTipProvider = null;

  protected GraphPrinter mGraphPrinter = new GraphPrinter();
  protected GraphSaver mGraphSaver = new GraphSaver();
  protected boolean mPlainSave = true;

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
    setBorder(new EmptyBorder(5, 5, 5, 5));
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
  @Override
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

  /**
   * Returns an action allowing the graph to be printed.
   *
   * @return an <code>Action</code>
   */
  public Action getPrintAction() {
    return new AbstractAction("Print") {
      private static final long serialVersionUID = 3366443762504745619L;

      public void actionPerformed(ActionEvent e) {
        mGraphPrinter.getGraphicsRenderer().setRendererConfig(mGraphicsRenderer);
        if (mPlainSave) {
          // Explicitly remove the background gradient / shadow to save "plain" images.
          mGraphPrinter.getGraphicsRenderer().setGraphBGColor(null, null);
          mGraphPrinter.getGraphicsRenderer().setGraphShadowWidth(0);
        }
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
      private static final long serialVersionUID = 1943785041287184839L;

      public void actionPerformed(ActionEvent e) {
        mGraphSaver.getGraphicsRenderer().setRendererConfig(mGraphicsRenderer);
        if (mPlainSave) {
          // Explicitly remove the background gradient / shadow to save "plain" images.
          mGraphSaver.getGraphicsRenderer().setGraphBGColor(null, null);
          mGraphSaver.getGraphicsRenderer().setGraphShadowWidth(0);
        }
        mGraphSaver.saveGraph(getGraph());
      }
    };
  }

  /**
   * Returns an action allowing a snapshot of the graph to be rendered
   * in a separate dialog.
   *
   * @return an <code>Action</code>
   */
  public Action getSnapShotAction() {
    return new AbstractAction("Snap Shot", null) {
      private static final long serialVersionUID = 4440241041537940185L;

      public void actionPerformed(ActionEvent e) {
        try {
          final PlotDialog pd = new PlotDialog();
          pd.setLocationRelativeTo(PlotPanel.this);
          pd.setRendererConfig(PlotPanel.this);
          pd.setTitle("Snap Shot");
          pd.getPlotPanel().setPlainSave(mPlainSave);
          pd.setGraph((Graph2D) mGraph.clone());
          pd.setVisible(true);
        } catch (final CloneNotSupportedException cnse) {
          System.err.println("Failed to clone graph: " + cnse.getMessage());
        }
      }
    };
  }


  /**
   * Sets the renderer configuration to match that of the provided panel. This
   * includes settings such as patterns, colors, but not graph data.
   *
   * @param plotPanel the model panel
   */
  public void setRendererConfig(PlotPanel plotPanel) {
    mGraphicsRenderer.setRendererConfig(plotPanel.mGraphicsRenderer);
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
   * @param plain if set, saved and printed images will be "plain"
   */
  public void setPlainSave(boolean plain) {
    mPlainSave = plain;
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
   * Overrides {@code JPanel.paintComponent} and actually renders the graph in
   * the given Graphics object.
   *
   * @param g a Graphics component.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    final Dimension d = getSize();
    final Insets i = getInsets();

    final int screenWidth = d.width - i.left - i.right;
    final int screenHeight = d.height - i.top - i.bottom;

    if (mBufferGraphs) {
      BufferedImage bi = mBI;
      if (bi == null || bi.getWidth() != d.width || bi.getHeight() != d.height) {
        bi = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        mBI = bi;
        final Graphics g2 = bi.createGraphics();
        g2.setColor(getBackground());
        g2.fillRect(0, 0, d.width, d.height);
        g2.setFont(g.getFont());
        mGraphicsRenderer.drawGraph(mGraph, g2, i.left, i.top, screenWidth, screenHeight);
        mMapping = mGraphicsRenderer.getMappings();
      }
      g.drawImage(bi, 0, 0, null);
    } else {
      g.setColor(getBackground());
      g.fillRect(0, 0, d.width, d.height);
      g.setFont(g.getFont());
      mGraphicsRenderer.drawGraph(mGraph, g, i.left, i.top, screenWidth, screenHeight);
      mMapping = mGraphicsRenderer.getMappings();
    }
    //System.err.println("Set mapping:" + mMapping);
    if (mMapping != null && mToolTipProvider != null) {
      mToolTipProvider.setMaps(mMapping[0], mMapping[1]);
    }
  }
}
