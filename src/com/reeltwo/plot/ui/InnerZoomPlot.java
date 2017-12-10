package com.reeltwo.plot.ui;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import com.reeltwo.plot.Axis;
import com.reeltwo.plot.Box2D;
import com.reeltwo.plot.Edge;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;

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

  private Graph2D mWholeGraph;

  private final GraphicsRenderer mGraphicsRenderer;
  private final AbstractAction mDefaultZoomAction;
  private final AbstractAction mUndoZoomAction;

  // Wraps up a set of upper and lower bounds for main and alternate axes
  private static class ZoomBounds {
    final Box2D[] mAxesBounds = new Box2D[2];
    public ZoomBounds() {
      mAxesBounds[0] = new Box2D(0, 0, 0, 0);
      mAxesBounds[1] = new Box2D(0, 0, 0, 0);
    }
    public void fromGraph(Graph2D graph) {
      for (int i = 0; i < 2; i++) {
        final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;
        mAxesBounds[i].setXRange(graph.getLo(Axis.X, a), graph.getHi(Axis.X, a));
        mAxesBounds[i].setYRange(graph.getLo(Axis.Y, a), graph.getHi(Axis.Y, a));
      }
    }
    public void toGraph(Graph2D graph) {
      for (int i = 0; i < 2; i++) {
        final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;
        graph.setRange(Axis.X, a, mAxesBounds[i].getXLo(), mAxesBounds[i].getXHi());
        graph.setRange(Axis.Y, a, mAxesBounds[i].getYLo(), mAxesBounds[i].getYHi());
      }
    }
    public boolean hasSameBounds(Graph2D graph) {
      for (int i = 0; i < 2; i++) {
        final Edge a = i == 0 ? Edge.MAIN : Edge.ALTERNATE;
        if (graph.getLo(Axis.X, a) != mAxesBounds[i].getXLo()
          || graph.getHi(Axis.X, a) != mAxesBounds[i].getXHi()
          || graph.getLo(Axis.Y, a) != mAxesBounds[i].getYLo()
          || graph.getHi(Axis.Y, a) != mAxesBounds[i].getYHi()) {
          return false;
        }
      }
      return true;
    }
    public String toString() {
      return mAxesBounds[0].toString() + " (main), " + mAxesBounds[1].toString() + " (alt)";
    }
  }

  // Allow external users to save/restore zoom configuration
  public static class ZoomConfiguration {
    private final ZoomBounds mDefault = new ZoomBounds();
    private Stack<ZoomBounds> mStack = new Stack<>();
  }

  private ZoomConfiguration mZoom = new ZoomConfiguration();

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
    mUndoZoomAction = new AbstractAction("Undo Zoom", null) {
      @Override
      public void actionPerformed(ActionEvent e) {
        final Graph2D graph = InnerZoomPlot.super.getGraph();
        if (graph != null && !mZoom.mStack.isEmpty()) {
          mZoom.mStack.pop(); // Discard current zoom level
          ZoomBounds bounds = mZoom.mStack.isEmpty() ? mZoom.mDefault : mZoom.mStack.peek();
          bounds.toGraph(graph);
          InnerZoomPlot.super.setGraph(graph);
          setEnabled(!mZoom.mStack.isEmpty());
        }
      }
    };
    setEnabled(!mZoom.mStack.isEmpty());
    mDefaultZoomAction = new AbstractAction("Default Zoom", null) {
      @Override
      public void actionPerformed(ActionEvent e) {
        final Graph2D graph = InnerZoomPlot.super.getGraph();
        if (graph != null) {
          mZoom.mDefault.toGraph(graph);
          InnerZoomPlot.super.setGraph(graph);
          mZoom.mStack.clear();
          mUndoZoomAction.setEnabled(!mZoom.mStack.isEmpty());
        }
      }
    };
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
  public Action getDefaultZoomAction() {
    return mDefaultZoomAction;
  }

  /**
   * Returns an action that returns the zooms to the previous level.
   *
   * @return an <code>Action</code>
   */
  public Action getUndoZoomAction() {
    return mUndoZoomAction;
  }

  /**
   * @return true if the current plot is zoomed in
   */
  public boolean isZoomed() {
    final Graph2D graph = InnerZoomPlot.super.getGraph();
    if (graph != null) {
      return !mZoom.mDefault.hasSameBounds(graph);
    }
    return false;
  }

  public ZoomConfiguration getZoomConfiguration() {
    return mZoom;
  }
  public void setZoomConfiguration(ZoomConfiguration config) {
    mZoom = config;
    ZoomBounds bounds = mZoom.mStack.isEmpty() ? mZoom.mDefault : mZoom.mStack.peek();
    getUndoZoomAction().setEnabled(!mZoom.mStack.isEmpty());
    final Graph2D graph = InnerZoomPlot.super.getGraph();
    if (graph != null) {
      bounds.toGraph(graph);
      InnerZoomPlot.super.setGraph(graph);
    }
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
      if (mWholeGraph != null) {
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
        mGraphicsRenderer.drawGraph(mWholeGraph, g2, mPNPWidth, mPNPHeight);
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
    final Mapping[] mapping = this.getMapping();
    if (mapping != null && mWholeGraph != null) {
      if (mPtOne != null && mPtTwo != null && mPtOne.x != mPtTwo.x && mPtOne.y != mPtTwo.y) {
        //System.err.println("Zooming ...");

        final Point ptOne = ppPoint(mPtOne);
        final Point ptTwo = ppPoint(mPtTwo);

        graphMap(mapping, ptOne, ptTwo, mWholeGraph);

        if (!mPicNPic) {
          final Graph2D graph = super.getGraph();
          graphMap(mapping, ptOne, ptTwo, graph);
          addZoomLevel(graph);
          super.setGraph(graph);
        }
      }
    }
    mPtOne = mPtTwo = null;
    mPtPNP1 = mPtPNP2 = null;
  }

  protected void addZoomLevel(Graph2D graph) {
    ZoomBounds zoom = new ZoomBounds();
    zoom.fromGraph(graph);
    mZoom.mStack.push(zoom);
    mUndoZoomAction.setEnabled(!mZoom.mStack.isEmpty());
    //System.err.println("Zoom stack has " + mZoom.mStack.size() + " levels");
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
      try {
        mWholeGraph = (Graph2D) graph.clone();
        mWholeGraph.setShowKey(false);
        for (Edge i : Edge.values()) {
          mWholeGraph.setLabel(Axis.X, i, "");
          mWholeGraph.setLabel(Axis.Y, i, "");
        }
        mWholeGraph.setTitle("");
      } catch (final CloneNotSupportedException cnse) {
        System.err.println(cnse.getMessage());
        //cnse.printStackTrace();
      }
      mZoom.mDefault.fromGraph(graph);
      //System.err.println("Updating Axes bounds with retention " + retainZoom + " (zoom stack has " + mZoom.mStack.size() + " levels). Default Zoom: " + mZoom.mDefault);
      final Graph2D oldGraph = super.getGraph();
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

