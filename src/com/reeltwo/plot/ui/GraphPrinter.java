package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


/**
 * GraphPrinter.java
 *
 *
 * @author Richard Littin (richard@reeltwo.com) 
 * @version $Revision$
 */
public class GraphPrinter implements Printable {

  private Color [] mColors = AWTDefaults.COLORS;
  private Paint [] mPatterns = null;
  private Graph2D mGraph = null;

  private int mFontSize = 0; // use default font size

  private PrinterJob mPrintJob = null;

  public GraphPrinter() {
    mPrintJob = PrinterJob.getPrinterJob();
    mPrintJob.setPrintable(this);
  }

  public void setFontSize(int size) {
    mFontSize = size;
  }

  public int getFontSize() {
    return mFontSize;
  }

  public void setColors(Color [] colors) {
    if (colors == null) {
      throw new NullPointerException("no colors given");
    }
    mColors = colors;
  }

  public void setPatterns(Paint [] patterns) {
    mPatterns = patterns;
  }

  public void printGraph(Graph2D graph) {
    mGraph = graph;
    if (mGraph != null) {
      if (mPrintJob.printDialog()) {
        try {
          mPrintJob.print();
        } catch (PrinterException pe) {
          pe.printStackTrace();
        }
      }
    }
  }

  // implementation of java.awt.print.Printable interface

  /**
   * Renders the current graph to a printer graphics device.
   *
   * @param g Graphics to draw on
   * @param pf a PageFormat
   * @param pi page index 
   * @return whether page exists
   * @exception PrinterException if an error occurs
   */
  public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
    if (mGraph == null || pi >= 1) {
      return Printable.NO_SUCH_PAGE;
    }
    if (mFontSize > 0) {
      Font old = g.getFont();
      g.setFont(new Font(old.getName(), old.getStyle(), mFontSize));
    }

    pf.setOrientation(PageFormat.LANDSCAPE);
    GraphicsRenderer gr = new GraphicsRenderer(mColors, mPatterns);
    Graphics g2 = g.create((int) pf.getImageableX(), (int) pf.getImageableY(),
                           (int) pf.getImageableWidth(), (int) pf.getImageableHeight());
    gr.drawGraph(mGraph, g2, (int) pf.getImageableWidth(), (int) pf.getImageableHeight());
    return Printable.PAGE_EXISTS;
  }
  
}
