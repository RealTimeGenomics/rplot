package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * ZoomPlotPanel.java
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class ZoomPlotPanel extends JComponent {

  private final PlotPanel mPlotPanel;

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

  private GraphicsRenderer mGraphicsRenderer;

  private float[] mXLo = new float[2];
  private float[] mXHi = new float[2];
  private float[] mYLo = new float[2];
  private float[] mYHi = new float[2];


  public ZoomPlotPanel(PlotPanel panel) {
    MouseInputAdapter listener = new ZoomListener(this, panel);
    addMouseListener(listener);
    addMouseMotionListener(listener);
    mPlotPanel = panel;
    mGraphicsRenderer = new GraphicsRenderer(mPlotPanel.getColors());
  }

  
  public Action[] getZoomActions() {
    Action zoomOutAction = new AbstractAction("Zoom Out", null) {
        public void actionPerformed(ActionEvent e) {
          //System.err.println("Zoom Out");
          Graph2D graph = mPlotPanel.getGraph();
          if (graph != null) {
            for (int i = 0; i < 2; i++) {
              graph.setXRange(i, mXLo[i], mXHi[i]);
              graph.setYRange(i, mYLo[i], mYHi[i]);
            }
          
            mPlotPanel.setGraph(graph);
          }
        }
      };
    Action pnpAction = new AbstractAction("Pic In Pic On", null) {
        public void actionPerformed(ActionEvent e) {
          mPicNPic = !mPicNPic;
          putValue("Name", "Pic In Pic " + (mPicNPic ? "Off" : "On"));
          ZoomPlotPanel.this.repaint();
        }
      };
    return new Action[] {zoomOutAction, pnpAction};
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


  public void paint(Graphics g) {
    if (mPtOne != null && mPtTwo != null && !mPtOne.equals(mPtTwo)) {
      g.setColor(Color.BLACK);
      int x = (int) Math.min(mPtOne.getX(), mPtTwo.getX());
      int y = (int) Math.min(mPtOne.getY(), mPtTwo.getY());
      int width = (int) Math.abs(mPtOne.getX() - mPtTwo.getX());
      int height = (int) Math.abs(mPtOne.getY() - mPtTwo.getY());
      g.drawRect(x, y, width, height);
    }

    if (mPicNPic) {
      if (mGraph != null) {
        Dimension d = getSize();
        int screenWidth = (int) d.getWidth();
        int screenHeight = (int) d.getHeight();
        mPNPWidth = screenWidth * 9 / 20;
        mPNPHeight = screenHeight * 9 / 20;

        if (mPtPNP1 != null && mPtPNP2 != null) {
          int x = (int) (mPtPNPold.getX() + mPtPNP2.getX() - mPtPNP1.getX());
          int y = (int) (mPtPNPold.getY() + mPtPNP2.getY() - mPtPNP1.getY());
          mPtPNP = new Point(x, y);
        }
        int x = Math.max(0, Math.min((int) mPtPNP.getX(), screenWidth - mPNPWidth - 1));
        int y = Math.max(0, Math.min((int) mPtPNP.getY(), screenHeight - mPNPHeight - 1));
        mPtPNP = new Point(x, y);

        int pnpX = (int) mPtPNP.getX();
        int pnpY = (int) mPtPNP.getY();
        g.setColor(Color.WHITE);
        g.fillRect(pnpX, pnpY, mPNPWidth, mPNPHeight);
        g.setColor(Color.BLACK);
        g.drawRect(pnpX, pnpY, mPNPWidth, mPNPHeight);
        Graphics g2 = g.create(pnpX, pnpY, mPNPWidth, mPNPHeight);
        mGraphicsRenderer.drawGraph(mGraph, g2, pnpX, pnpY, mPNPWidth, mPNPHeight);
      }
    }
  }


  protected void zoomIn() {
    //System.err.println("Zooming ...");

    Mapping[] mapping = mPlotPanel.getMapping();

    if (mapping != null && mGraph != null) {
      if (mPtOne != null && mPtTwo != null && mPtOne.x != mPtTwo.x && mPtOne.y != mPtTwo.y) {

        for (int i = 0; i < 2; i++) {
          Mapping map = mapping[i * 2];
          if (mGraph.usesX(i) && (Math.abs(map.getWorldMax() - map.getWorldMin()) > 0.01f)) {
            float mapOneX = map.screenToWorld((float) mPtOne.getX());
            float mapTwoX = map.screenToWorld((float) mPtTwo.getX());
            mGraph.setXRange(i, Math.min(mapOneX, mapTwoX), Math.max(mapOneX, mapTwoX));
          }

          map = mapping[i * 2 + 1];
          if (mGraph.usesY(i) && (Math.abs(map.getWorldMax() - map.getWorldMin()) > 0.01f)) {
            float mapOneY = map.screenToWorld((float) mPtOne.getY());
            float mapTwoY = map.screenToWorld((float) mPtTwo.getY());
            mGraph.setYRange(i, Math.min(mapOneY, mapTwoY), Math.max(mapOneY, mapTwoY));
          }
        }

        if (!mPicNPic) {
          Graph2D graph = mPlotPanel.getGraph();
          for (int i = 0; i < 2; i++) {
            Mapping map = mapping[i * 2];
            if (graph.usesX(i) && (Math.abs(map.getWorldMax() - map.getWorldMin()) > 0.01f)) {
              float mapOneX = map.screenToWorld((float) mPtOne.getX());
              float mapTwoX = map.screenToWorld((float) mPtTwo.getX());
              graph.setXRange(i, Math.min(mapOneX, mapTwoX), Math.max(mapOneX, mapTwoX));
            }

            map = mapping[i * 2 + 1];
            if (graph.usesY(i) && (Math.abs(map.getWorldMax() - map.getWorldMin()) > 0.01f)) {
              float mapOneY = map.screenToWorld((float) mPtOne.getY());
              float mapTwoY = map.screenToWorld((float) mPtTwo.getY());
              graph.setYRange(i, Math.min(mapOneY, mapTwoY), Math.max(mapOneY, mapTwoY));
            }
          }
          mPlotPanel.setGraph(graph);
        }
      }
    }
    mPtOne = mPtTwo = null;
    mPtPNP1 = mPtPNP2 = null;
  }


  public void setGraph(Graph2D graph) {
    try {
      mGraph = (Graph2D) graph.clone();
      mGraph.setShowKey(false);
      for (int i = 0; i < 2; i++) {
        mGraph.setXLabel(i, "");
        mGraph.setYLabel(i, "");
      }
      mGraph.setTitle("");
    } catch (CloneNotSupportedException cnse) {
      cnse.printStackTrace();
    }
    for (int i = 0; i < 2; i++) {
      mXLo[i] = graph.getXLo(i);
      mXHi[i] = graph.getXHi(i);
      mYLo[i] = graph.getYLo(i);
      mYHi[i] = graph.getYHi(i);
    }
    mPlotPanel.setGraph(graph);
  }

  public Graph2D getGraph() {
    return mGraph;
  }
}

