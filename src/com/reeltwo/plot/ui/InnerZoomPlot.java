package com.reeltwo.plot.ui;


import com.reeltwo.plot.Axis;
import com.reeltwo.plot.Edge;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * A zoomable version of plot panel that doesn't try to control the whole world and allows
 * user to select and zoom in on arbitrary regions of the plot.
 *
 * Pnp is untested.
 *
 * @author Richard Littin
 * @author Kurt Gaastra
 */
public class InnerZoomPlot extends PlotPanel {

  /** an id */
  private static final long serialVersionUID = -209546782221560829L;

  private Point mPtOne = null;
  private Point mPtTwo = null;
  private Point mPtPNP = new Point(0, 0);
  private Point mPtPNPold = null;
  private Point mPtPNP1 = null;
  private Point mPtPNP2 = null;
  private int mPNPWidth;
  private int mPNPHeight;

  private boolean mPicNPic = false;

  private final GraphicsRenderer mGraphicsRenderer;

  private final float[] mXLo = new float[2];
  private final float[] mXHi = new float[2];
  private final float[] mYLo = new float[2];
  private final float[] mYHi = new float[2];

  private boolean mOriginIsMin = false;

  private class ZoomListener extends MouseInputAdapter {
    @Override
    public void mouseDragged(MouseEvent e) {
      //System.err.println("Mouse Dragged");
      if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
        final Point p = e.getPoint();
//        if (InnerZoomPlot.this == SwingUtilities.getDeepestComponentAt(InnerZoomPlot.this, p.x, p.y)) {
          InnerZoomPlot.this.setPointTwo(p);
          InnerZoomPlot.this.repaint();
         // System.err.println("Mouse dragged in panel");
//        }
      }
    }


    /** {@inheritDoc} */
    @Override
    public void mousePressed(MouseEvent e) {
      //System.err.println("Mouse Pressed");
      if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
        final Point p = e.getPoint();
        final Component deepestComponentAt = SwingUtilities.getDeepestComponentAt(InnerZoomPlot.this, p.x, p.y);
        if (InnerZoomPlot.this == deepestComponentAt) {
          InnerZoomPlot.this.setPointOne(p);
          InnerZoomPlot.this.setPointTwo(p);
          InnerZoomPlot.this.repaint();
        }
      }
    }


    /** {@inheritDoc} */
    @Override
    public void mouseReleased(MouseEvent e) {
      //System.err.println("Mouse Released");
      if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
        final Point p = e.getPoint();
        InnerZoomPlot.this.setPointTwo(p);
        InnerZoomPlot.this.zoomIn();
        InnerZoomPlot.this.repaint();
      }
    }
  }

  /**
   * Creates a new <code>InnerZoomPlot</code> using the given plot
   * panel to render graphs.
   *
   */
  public InnerZoomPlot() {
    final MouseInputAdapter listener = new ZoomListener();
    addMouseListener(listener);
    addMouseMotionListener(listener);
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
        final Graph2D graph = getGraph();
        if (graph != null) {
          for (int i = 0; i < 2; i++) {
            final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;
            graph.setRange(Axis.X, a, mXLo[i], mXHi[i]);
            graph.setRange(Axis.Y, a, mYLo[i], mYHi[i]);
          }

          setGraph(graph);
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
        InnerZoomPlot.this.repaint();
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
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (mPtOne != null && mPtTwo != null && !mPtOne.equals(mPtTwo)) {
      g.setColor(Color.BLACK);
      final int x = (int) Math.min(mPtOne.getX(), mPtTwo.getX());
      final int y = (int) Math.min(mPtOne.getY(), mPtTwo.getY());
      final int width = (int) Math.abs(mPtOne.getX() - mPtTwo.getX());
      final int height = (int) Math.abs(mPtOne.getY() - mPtTwo.getY());
      g.drawRect(x, y, width, height);
    }

    if (mPicNPic) {
      if (getGraph() != null) {
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
        mGraphicsRenderer.drawGraph(getGraph(), g2, mPNPWidth, mPNPHeight);
      }
    }
  }

  private Point ppPoint(Point p) {
    return SwingUtilities.convertPoint(this, p, this);
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

    final Mapping[] mapping = this.getMapping();

    if (mapping != null && getGraph() != null) {
      if (mPtOne != null && mPtTwo != null && mPtOne.x != mPtTwo.x && mPtOne.y != mPtTwo.y) {

        final Point ptOne = ppPoint(mPtOne);
        final Point ptTwo = ppPoint(mPtTwo);

        graphMap(mapping, ptOne, ptTwo, getGraph());

        if (!mPicNPic) {
          final Graph2D graph = getGraph();
          graphMap(mapping, ptOne, ptTwo, graph);
          this.setGraph(graph);
        }
      }
    }
    mPtOne = mPtTwo = null;
    mPtPNP1 = mPtPNP2 = null;
  }

  private void graphMap(Mapping[] mapping, Point ptOne, Point ptTwo, Graph2D graph) {
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
      return;
    } else {
      for (int i = 0; i < 2; i++) {
        final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;
        mXLo[i] = graph.getLo(Axis.X, a);
        mXHi[i] = graph.getHi(Axis.X, a);
        mYLo[i] = graph.getLo(Axis.Y, a);
        mYHi[i] = graph.getHi(Axis.Y, a);
      }
      final Graph2D oldGraph = getGraph();
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
    super.setGraph(graph);
  }
}

