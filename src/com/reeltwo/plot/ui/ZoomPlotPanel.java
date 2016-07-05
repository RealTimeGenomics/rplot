package com.reeltwo.plot.ui;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import com.reeltwo.plot.Axis;
import com.reeltwo.plot.Edge;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;

/**
 * A panel that has a special affinity with a plot panel to allow the
 * user to select and zoom in on arbitrary regions of the plot.
 *
 * @author Richard Littin
 */
public class ZoomPlotPanel extends JComponent {

  /** an id */
  private static final long serialVersionUID = -209546782221560829L;
  private final PlotPanel mPlotPanel;
  private final Container mContainer;

  private Point mPtOne = null;
  private Point mPtTwo = null;
  private Point mPtPNP = new Point(0, 0);
  private Point mPtPNPold = null;
  private Point mPtPNP1 = null;
  private Point mPtPNP2 = null;
  private int mPNPWidth;
  private int mPNPHeight;

  private Graph2D mGraph = null;
  private boolean mPicNPic = false;

  private final GraphicsRenderer mGraphicsRenderer;

  private final float[] mXLo = new float[2];
  private final float[] mXHi = new float[2];
  private final float[] mYLo = new float[2];
  private final float[] mYHi = new float[2];

  private boolean mOriginIsMin = false;

  private class ZoomListener extends MouseInputAdapter {

    private Component mMouseDownComponent;

    /** {@inheritDoc} */
    @Override
    public void mouseMoved(MouseEvent e) {
      //System.err.println("Mouse moved");
      redispatchMouseEvent(e);
    }

    /** {@inheritDoc} */
    @Override
    public void mouseDragged(MouseEvent e) {
      //System.err.println("Mouse Dragged");
      if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
        final Point p = e.getPoint();
        if (mPlotPanel == SwingUtilities.getDeepestComponentAt(mContainer, p.x, p.y)) {
          ZoomPlotPanel.this.setPointTwo(p);
          ZoomPlotPanel.this.repaint();
         // System.err.println("Mouse dragged in panel");
        }
      }
      redispatchMouseEvent(e);
    }


    /** {@inheritDoc} */
    @Override
    public void mouseClicked(MouseEvent e) {
      //System.err.println("Mouse Clicked");
      redispatchMouseEvent(e);
    }


    /** {@inheritDoc} */
    @Override
    public void mouseEntered(MouseEvent e) {
      //System.err.println("Mouse entered");
      redispatchMouseEvent(e);
    }


    /** {@inheritDoc} */
    @Override
    public void mouseExited(MouseEvent e) {
      //System.err.println("Mouse exited");
      redispatchMouseEvent(e);
    }


    /** {@inheritDoc} */
    @Override
    public void mousePressed(MouseEvent e) {
      //System.err.println("Mouse Pressed");
      if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
        final Point p = e.getPoint();
        final Component deepestComponentAt = SwingUtilities.getDeepestComponentAt(mContainer, p.x, p.y);
        if (mPlotPanel == deepestComponentAt) {
          ZoomPlotPanel.this.setPointOne(p);
          ZoomPlotPanel.this.setPointTwo(p);
          ZoomPlotPanel.this.repaint();
          mMouseDownComponent = null;
        } else {
          mMouseDownComponent = deepestComponentAt;
        }
      }
      redispatchMouseEvent(e);
    }


    /** {@inheritDoc} */
    @Override
    public void mouseReleased(MouseEvent e) {
      //System.err.println("Mouse Released");
      if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
        final Point p = e.getPoint();
        ZoomPlotPanel.this.setPointTwo(p);
        ZoomPlotPanel.this.zoomIn();
        ZoomPlotPanel.this.repaint();
      }
      redispatchMouseEvent(e);
      mMouseDownComponent = null;
    }


    private void redispatchMouseEvent(MouseEvent e) {
      final Point containerPoint = e.getPoint();

      final Component component = mMouseDownComponent != null ? mMouseDownComponent : SwingUtilities.getDeepestComponentAt(mContainer, containerPoint.x, containerPoint.y);

      //System.err.println("Container = " + mContainer);
      //System.err.println("Component = " + component);
      if (component == null) {
        return;
      }
      final Point componentPoint = SwingUtilities.convertPoint(ZoomPlotPanel.this, containerPoint, component);
      component.dispatchEvent(new MouseEvent(component, e.getID(), e.getWhen(), e.getModifiers(),
          componentPoint.x, componentPoint.y,
          e.getClickCount(), e.isPopupTrigger()));
    }
  }

  /**
   * Creates a new <code>ZoomPlotPanel</code> using the given plot
   * panel to render graphs.
   *
   * @param panel a <code>PlotPanel</code>
   * @param container Content pane from parent container
   */
  public ZoomPlotPanel(PlotPanel panel, Container container) {
    final MouseInputAdapter listener = new ZoomListener();
    addMouseListener(listener);
    addMouseMotionListener(listener);
    mPlotPanel = panel;
    mContainer = container;
    mGraphicsRenderer = new GraphicsRenderer();
  }
  
  /**
   * Sets things so the origin is the lowest point that can be zoomed to
   * @param flag origin is min
   */
  public void setOriginIsMin(boolean flag) {
    mOriginIsMin = flag;
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
   * Returns an action that resets the zoom.
   *
   * @return an <code>Action</code>
   */
  public Action getZoomOutAction() {
    return new AbstractAction("Zoom Out", null) {
      /** an id */
      private static final long serialVersionUID = 343566108639393382L;

      @Override
      public void actionPerformed(ActionEvent e) {
        //System.err.println("Zoom Out");
        final Graph2D graph = mPlotPanel.getGraph();
        if (graph != null) {
          for (int i = 0; i < 2; i++) {
            final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;
            graph.setRange(Axis.X, a, mXLo[i], mXHi[i]);
            graph.setRange(Axis.Y, a, mYLo[i], mYHi[i]);
          }

          mPlotPanel.setGraph(graph);
        }
      }
    };
  }

  /**
   * Returns an action allowing picture in picture mode to be turned
   * on and off.
   *
   * @return an <code>Action</code>
   */
  public Action getPNPAction() {
    return new AbstractAction("Pic In Pic On", null) {
      /** an id */
      private static final long serialVersionUID = -4772586876399697951L;

      @Override
      public void actionPerformed(ActionEvent e) {
        mPicNPic = !mPicNPic;
        putValue("Name", "Pic In Pic " + (mPicNPic ? "Off" : "On"));
        ZoomPlotPanel.this.repaint();
      }
    };
  }


  protected void setPointOne(Point p) {
    if (mPicNPic) {
      if (p.getX() >= mPtPNP.getX() && p.getX() <= (mPtPNP.getX() + mPNPWidth)
          && p.getY() >= mPtPNP.getY() && p.getY() <= (mPtPNP.getY() + mPNPHeight)) {
        // inside Pic in Pic rectangle
        mPtPNPold = mPtPNP;
        mPtPNP1 = p;
      } else {
        mPtOne = p;
      }
    } else {
      mPtOne = p;
    }
  }

  protected void setPointTwo(Point p) {
    if (mPicNPic) {
      if (mPtPNP1 != null) {
        mPtPNP2 = p;
      } else {
        mPtTwo = p;
      }
    } else {
      mPtTwo = p;
    }
  }

  /** {@inheritDoc} */
  @Override
  public void paint(Graphics g) {
    if (mPtOne != null && mPtTwo != null && !mPtOne.equals(mPtTwo)) {
      g.setColor(Color.BLACK);
      final int x = (int) Math.min(mPtOne.getX(), mPtTwo.getX());
      final int y = (int) Math.min(mPtOne.getY(), mPtTwo.getY());
      final int width = (int) Math.abs(mPtOne.getX() - mPtTwo.getX());
      final int height = (int) Math.abs(mPtOne.getY() - mPtTwo.getY());
      g.drawRect(x, y, width, height);
    }

    if (mPicNPic) {
      if (mGraph != null) {
        final Dimension d = getSize();
        final int screenWidth = (int) d.getWidth();
        final int screenHeight = (int) d.getHeight();
        mPNPWidth = screenWidth * 9 / 20;
        mPNPHeight = screenHeight * 9 / 20;

        if (mPtPNP1 != null && mPtPNP2 != null) {
          final int x = (int) (mPtPNPold.getX() + mPtPNP2.getX() - mPtPNP1.getX());
          final int y = (int) (mPtPNPold.getY() + mPtPNP2.getY() - mPtPNP1.getY());
          mPtPNP = new Point(x, y);
        }
        final int x = Math.max(0, Math.min((int) mPtPNP.getX(), screenWidth - mPNPWidth - 1));
        final int y = Math.max(0, Math.min((int) mPtPNP.getY(), screenHeight - mPNPHeight - 1));
        mPtPNP = new Point(x, y);

        final int pnpX = (int) mPtPNP.getX();
        final int pnpY = (int) mPtPNP.getY();
        g.setColor(Color.WHITE);
        g.fillRect(pnpX, pnpY, mPNPWidth, mPNPHeight);
        g.setColor(Color.BLACK);
        g.drawRect(pnpX, pnpY, mPNPWidth, mPNPHeight);
        final Graphics g2 = g.create(pnpX, pnpY, mPNPWidth, mPNPHeight);
        mGraphicsRenderer.drawGraph(mGraph, g2, mPNPWidth, mPNPHeight);
      }
    }
  }

  private Point ppPoint(Point p) {
    return SwingUtilities.convertPoint(ZoomPlotPanel.this, p, mPlotPanel);
  }

  private float rangeMin(float s, float e) {
    float rangeMin = Math.min(s, e);
    if (mOriginIsMin) {
      rangeMin = Math.max(0.0f, rangeMin);
    }
    return rangeMin;
  }

  private float rangeMax(float s, float e) {
    float rangeMax = Math.max(s, e);
    if (mOriginIsMin) {
      rangeMax = Math.max(0.0f, rangeMax);
    }
    return rangeMax;
  }

  protected void zoomIn() {
    //System.err.println("Zooming ...");

    final Mapping[] mapping = mPlotPanel.getMapping();

    if (mapping != null && mGraph != null) {
      if (mPtOne != null && mPtTwo != null && mPtOne.x != mPtTwo.x && mPtOne.y != mPtTwo.y) {

        final Point ptOne = ppPoint(mPtOne);
        final Point ptTwo = ppPoint(mPtTwo);

        for (int i = 0; i < 2; i++) {
          Mapping map = mapping[i * 2];
          final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;

          if (mGraph.uses(Axis.X, a) && (Math.abs(map.getWorldMax() - map.getWorldMin()) > 0.01f)) {
            final float mapOneX = map.screenToWorld((float) ptOne.getX());
            final float mapTwoX = map.screenToWorld((float) ptTwo.getX());
            final float rangeMin = rangeMin(mapOneX, mapTwoX);
            final float rangeMax = rangeMax(mapOneX, mapTwoX);
            if (rangeMin != rangeMax) {
              mGraph.setRange(Axis.X, a, rangeMin, rangeMax);
            }
          }

          map = mapping[i * 2 + 1];
          if (mGraph.uses(Axis.Y, a) && (Math.abs(map.getWorldMax() - map.getWorldMin()) > 0.01f)) {
            final float mapOneY = map.screenToWorld((float) ptOne.getY());
            final float mapTwoY = map.screenToWorld((float) ptTwo.getY());
            final float rangeMin = rangeMin(mapOneY, mapTwoY);
            final float rangeMax = rangeMax(mapOneY, mapTwoY);
            if (rangeMin != rangeMax) {
              mGraph.setRange(Axis.Y, a, rangeMin, rangeMax);
            }
          }
        }

        if (!mPicNPic) {
          final Graph2D graph = mPlotPanel.getGraph();
          for (int i = 0; i < 2; i++) {
            Mapping map = mapping[i * 2];
            final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;

            if (graph.uses(Axis.X, a) && (Math.abs(map.getWorldMax() - map.getWorldMin()) > 0.01f)) {
              final float mapOneX = map.screenToWorld((float) ptOne.getX());
              final float mapTwoX = map.screenToWorld((float) ptTwo.getX());
              final float rangeMin = rangeMin(mapOneX, mapTwoX);
              final float rangeMax = rangeMax(mapOneX, mapTwoX);
              if (rangeMin != rangeMax) {
                graph.setRange(Axis.X, a, rangeMin, rangeMax);
              }
            }

            map = mapping[i * 2 + 1];
            if (graph.uses(Axis.Y, a) && (Math.abs(map.getWorldMax() - map.getWorldMin()) > 0.01f)) {
              final float mapOneY = map.screenToWorld((float) ptOne.getY());
              final float mapTwoY = map.screenToWorld((float) ptTwo.getY());
              final float rangeMin = rangeMin(mapOneY, mapTwoY);
              final float rangeMax = rangeMax(mapOneY, mapTwoY);
              if (rangeMin != rangeMax) {
                graph.setRange(Axis.Y, a, rangeMin, rangeMax);
              }
            }
          }
          mPlotPanel.setGraph(graph);
        }
      }
    }
    mPtOne = mPtTwo = null;
    mPtPNP1 = mPtPNP2 = null;
  }


  /**
   * Sets the graph to plot.
   *
   * @param graph a <code>Graph2D</code>
   */
  public void setGraph(Graph2D graph) {
    setGraph(graph, false);
  }

  /**
   * Sets the graph to plot.
   *
   * @param graph a <code>Graph2D</code>
   * @param retainZoom whether to reset zoom
   */
  public void setGraph(Graph2D graph, boolean retainZoom) {
    if (graph == null) {
      mGraph = null;
    } else {
      try {
        mGraph = (Graph2D) graph.clone();
        mGraph.setShowKey(false);
        for (Edge i : Edge.values()) {
          mGraph.setLabel(Axis.X, i, "");
          mGraph.setLabel(Axis.Y, i, "");
        }
        mGraph.setTitle("");
      } catch (final CloneNotSupportedException cnse) {
        System.err.println(cnse.getMessage());
        //cnse.printStackTrace();
      }
      for (int i = 0; i < 2; i++) {
        final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;
        mXLo[i] = graph.getLo(Axis.X, a);
        mXHi[i] = graph.getHi(Axis.X, a);
        mYLo[i] = graph.getLo(Axis.Y, a);
        mYHi[i] = graph.getHi(Axis.Y, a);
      }
      final Graph2D oldGraph = mPlotPanel.getGraph();
      if (retainZoom && oldGraph != null) {
        for (Axis axis : Axis.values()) {
          for (Edge edge : Edge.values()) {
            if (oldGraph.uses(axis, edge)) {
              final float hi = oldGraph.getHi(axis, edge);
              final float lo = oldGraph.getLo(axis, edge);
              //System.err.println(axis + " " + edge + " " + lo + " " + hi);
              //mGraph.setRange(axis, edge, lo, hi);
              graph.setRange(axis, edge, lo, hi);
            }
          }
        }
      }
    }
    mPlotPanel.setGraph(graph);
  }

  /**
   * Returns the currently plotted graph.
   *
   * @return a <code>Graph2D</code>
   */
  public Graph2D getGraph() {
    return mGraph;
  }

  /** {@inheritDoc} */
  @Override
  public String getToolTipText(MouseEvent arg0) {
    return mPlotPanel.getToolTipText(arg0);
  }
}

