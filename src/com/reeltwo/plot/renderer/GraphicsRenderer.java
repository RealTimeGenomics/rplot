package com.reeltwo.plot.renderer;

import com.reeltwo.plot.BWPlot2D;
import com.reeltwo.plot.BWPoint2D;
import com.reeltwo.plot.BoxPlot2D;
import com.reeltwo.plot.CirclePlot2D;
import com.reeltwo.plot.CurvePlot2D;
import com.reeltwo.plot.Datum2D;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.Plot2D;
import com.reeltwo.plot.PlotUtils;
import com.reeltwo.plot.PointPlot2D;
import com.reeltwo.plot.ScatterPlot2D;
import com.reeltwo.plot.TextPlot2D;
import com.reeltwo.plot.TextPoint2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * Code to render a Graph2D object onto a Graphics.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class GraphicsRenderer extends AbstractRenderer {
  private Color mGraphColor = null;
  private int mGraphShadowWidth = 0;

  private boolean mTextAntialiasing = false;
  private boolean mAllAntialiasing = false;

  private final Color [] mColors;

  /** Private to prevent instantiation. */
  public GraphicsRenderer(Color [] colors) {
    if (colors == null) {
      throw new NullPointerException("null colors given");
    }
    for (int i = 0; i < colors.length; i++) {
      if (colors[i] == null) {
        throw new NullPointerException("null color given");
      }
    }
    mColors = colors;
  }

  // from AbstractRenderer
  public int getTextWidth(Object canvas, String text) {
    FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    int width = text == null ? 0 : fm.stringWidth(text);
    return width;
  }

  public int getTextHeight(Object canvas, String text) {
    FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    return fm.getHeight();
  }

  public int getTextDescent(Object canvas, String text) {
    FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    return fm.getMaxDescent();
  }

  public int setPlotColor(Object canvas, Plot2D plot, int colorIndex) {
    int plotColorIndex = plot.getColor();
    if (plotColorIndex < 0) {
      plotColorIndex = colorIndex;
      colorIndex = (colorIndex + 1) % mColors.length;
      if (colorIndex == 0) {
        colorIndex = 1;
      }
      plot.setColor(plotColorIndex);
    } else if (plotColorIndex >= mColors.length) {
      plotColorIndex = (plotColorIndex + 1) % mColors.length;
      if (plotColorIndex == 0) {
        plotColorIndex = 1;
      }
      plot.setColor(plotColorIndex);
    }
    ((Graphics) canvas).setColor(mColors[plotColorIndex]);
    return colorIndex; 
  }

  public void setLineWidth(Object canvas, int width) {
    super.setLineWidth(canvas, width);

    if (width > 1) {
      ((Graphics2D) canvas).setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    } else {
      ((Graphics2D) canvas).setStroke(new BasicStroke());
    }
  }

  public void setClip(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).setClip(x, y, w, h);
  }

  public void drawString(Object canvas, int x, int y, String text) {
    ((Graphics) canvas).drawString(text, x, y);
  }

  public void drawPoint(Object canvas, int x, int y) {
    Graphics g = (Graphics) canvas;
    switch (getPointIndex() % 6) {
    case 0:
      g.drawLine(x, y - 2, x + 2, y);
      g.drawLine(x + 2, y, x, y + 2);
      g.drawLine(x, y + 2, x - 2, y);
      g.drawLine(x - 2, y, x, y - 2);
      break;
    case 1:
      g.drawLine(x - 2, y, x + 2, y);
      g.drawLine(x, y - 2, x, y + 2);
      break;
    case 2:
      g.drawRect(x - 2, y - 2, 4, 4);
      break;
    case 3:
      g.drawLine(x - 2, y - 2, x + 2, y + 2);
      g.drawLine(x + 2, y - 2, x - 2, y + 2);
      break;
    case 4:
      g.drawLine(x, y - 2, x + 2, y + 2);
      g.drawLine(x + 2, y + 2, x - 2, y + 2);
      g.drawLine(x - 2, y + 2, x, y - 2);
      break;
    case 5:
      g.drawLine(x - 2, y, x + 2, y);
      g.drawLine(x, y - 2, x, y + 2);
      g.drawLine(x - 2, y - 2, x + 2, y + 2);
      g.drawLine(x + 2, y - 2, x - 2, y + 2);
      break;
    }
  }

  public void drawLine(Object canvas, int x1, int y1, int x2, int y2) {
    ((Graphics) canvas).drawLine(x1, y1, x2, y2);
  }

  public void drawRectangle(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).drawRect(x, y, w, h);
  }

  public void fillRectangle(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).fillRect(x, y, w, h);
  }

  public void drawCircle(Object canvas, int x, int y, int diameter) {
    ((Graphics) canvas).drawOval((int) (x - diameter / 2.0f), (int) (y - diameter / 2.0f), diameter, diameter);
  }

  public void fillCircle(Object canvas, int x, int y, int diameter) {
    ((Graphics) canvas).fillOval((int) (x - diameter / 2.0f), (int) (y - diameter / 2.0f), diameter, diameter);
  }

  private Polygon createPolygon(int [] xs, int [] ys) {
    assert xs != null;
    assert ys != null;
    assert xs.length == ys.length;
    Polygon polygon = new Polygon();
    for (int i = 0; i < xs.length; i++) {
      polygon.addPoint(xs[i], ys[i]);
    }
    return polygon;
  }

  public void drawPolygon(Object canvas, int [] xs, int [] ys) {
    ((Graphics) canvas).drawPolygon(createPolygon(xs, ys));
  }

  public void fillPolygon(Object canvas, int [] xs, int [] ys) {
    ((Graphics) canvas).fillPolygon(createPolygon(xs, ys));
  }

  // render specific
  public void setTextAntialiasing(boolean flag) {
    mTextAntialiasing = flag;
  }

  public void setAntialiasing(boolean flag) {
    mAllAntialiasing = flag;
  }


  public void setGraphColor(Color color) {
    mGraphColor = color;
  }


  public void setGraphShadowWidth(int n) {
    mGraphShadowWidth = n;
  }


  private static int getKeyLineWidth(Graphics g) {
    FontMetrics fm = g.getFontMetrics();
    return fm.stringWidth("mm");
  }

  /**
   * Actually draws the graph on the given Graphics. Offsets and screen
   * dimensions are used to provide limits on the area to draw in. The
   * mappings from world to screen data points for each axis pair is
   * returned (in order x1,y1,x2,y2,...).
   *
   * @param graph the Graph2D to draw
   * @param g a Graphics to draw on
   * @param offX X offset into drawing region
   * @param offY Y offset into drawing region
   * @param screenWidth width of drawing region
   * @param screenHeight height of drawing region
   * @return an array of world to screen mappings
   */
  public void drawGraph(Graph2D graph, Graphics g, 
                        int offX, int offY, int screenWidth, int screenHeight) {
    Mapping[] mapping = null;
    setMappings(null);
    if (graph != null) {
      g.setColor(Color.BLACK);
      setClip(g, 0, 0, screenWidth, screenHeight);
      int sxlo = 0;
      int sxhi = screenWidth;
      int sylo = screenHeight;
      int syhi = 0;

      String title = graph.getTitle();
      int tHeight = getTextHeight(g, "A");
      if (title.length() > 0) {
        syhi += tHeight;
      }

      TicInfo [] ticInfos = null;

      int keyX = 0;
      if (graph.getBorder()) {
        int keyWidth = 0;
        if (graph.getShowKey()) {
          if (graph.getKeyHorizontalPosition() == Graph2D.OUTSIDE) {
            keyWidth = calculateKeyWidth(g, graph);
            sxhi -= keyWidth + 2;
          }
        }

        if (graph.usesX(0) && graph.getXLabel(0).length() > 0) {
          sylo -= tHeight + 4;
          // draw x label later when border width is known
        }
        if (graph.usesY(0) && graph.getYLabel(0).length() > 0) {
          g.drawString(graph.getYLabel(0), sxlo, offY + tHeight * (1 + (title.length() > 0 ? 1 : 0)));
        }
        if (graph.usesY(1) && graph.getYLabel(1).length() > 0) {
          String yLabel = graph.getYLabel(1);
          g.drawString(yLabel, sxhi - getTextWidth(g, yLabel), offY + tHeight * (1 + (title.length() > 0 ? 1 : 0)));
        }

        if ((graph.usesX(1) && graph.getXLabel(1).length() > 0)
            || (graph.usesY(0) && graph.getYLabel(0).length() > 0)
            || (graph.usesY(1) && graph.getYLabel(1).length() > 0)) {
          syhi += tHeight;
        }

        // extra height for digits on axes
        if ((graph.usesY(0) && graph.getShowYTics(0))
            || (graph.usesY(1) && graph.getShowYTics(1))) {
          syhi += tHeight / 2;
        }

        ticInfos = createTicInfos(g, graph);

        TicInfo yTicInfo = ticInfos[1];
        if (yTicInfo != null) {
          sxlo += yTicInfo.mMaxWidth + 2;
        }

        TicInfo y2TicInfo = ticInfos[3];
        if (y2TicInfo != null) {
          sxhi -= y2TicInfo.mMaxWidth + 2;
        }
        
        TicInfo xTicInfo = ticInfos[0];
        if (xTicInfo != null) {
          sylo -= xTicInfo.mMaxHeight;
          if (!(graph.usesY(0) && graph.getShowYTics(0))) {
            sxlo += xTicInfo.mMaxWidth / 2 + 2;
          }
          if (!(graph.usesY(1) && graph.getShowYTics(1)) && keyWidth == 0) {
            sxhi -= xTicInfo.mMaxWidth / 2 + 2;
          }
        }

        TicInfo x2TicInfo = ticInfos[2];
        if (x2TicInfo != null) {
          syhi += xTicInfo.mMaxHeight;
          if (!graph.getShowYTics(0) && !(graph.usesX(0) && graph.getShowXTics(0))) {
            sxlo += x2TicInfo.mMaxWidth / 2 + 2;
          }
          if (!graph.getShowYTics(1) && !(graph.usesX(0) && graph.getShowXTics(0))) {
            sxhi -= x2TicInfo.mMaxWidth / 2 + 2;
          }
        }

        mapping = createMappings(graph, sxlo, sylo, sxhi, syhi);

        drawGraphArea(g, sxlo, sxhi, sylo, syhi);
        setupAntialiasing(g);

        drawYTics(graph, g, 0, yTicInfo, mapping[1], sxlo, sxhi, sylo, syhi);
        drawYTics(graph, g, 1, y2TicInfo, mapping[3], sxlo, sxhi, sylo, syhi);
        drawXTics(graph, g, 0, xTicInfo, mapping[0], sxlo, sxhi, sylo, syhi);
        drawXTics(graph, g, 1, x2TicInfo, mapping[2], sxlo, sxhi, sylo, syhi);

        g.setColor(Color.BLACK);
        String xLabel;
        if (graph.usesX(0) && (xLabel = graph.getXLabel(0)).length() > 0) {
          g.drawString(xLabel, (sxhi + sxlo) / 2 - getTextWidth(g, xLabel) / 2, offY + screenHeight - 4);
        }
        if (graph.usesX(1) && (xLabel = graph.getXLabel(1)).length() > 0) {
          g.drawString(xLabel, (sxhi + sxlo) / 2 - getTextWidth(g, xLabel) / 2, offY + tHeight * (1 + (title.length() > 0 ? 1 : 0)));
        }

        // draw border
        g.setColor(Color.BLACK);
        g.drawRect(sxlo, syhi, sxhi - sxlo, sylo - syhi);
      } else {
        drawGraphArea(g, sxlo, sxhi, sylo, syhi);

        mapping = createMappings(graph, sxlo, sylo, sxhi, syhi);
      }

      g.setColor(Color.BLACK);
      // draw title
      if (title.length() > 0) {
        g.drawString(title, (sxhi + sxlo) / 2 - g.getFontMetrics().stringWidth(title) / 2, offY + tHeight);
      }
      // set clip so nothing appears outside border
      setClip(g, sxlo, syhi, sxhi - sxlo + 1, sylo - syhi + 1);

      drawData(g, graph.getPlots(), mapping);
      drawVerticalLine(graph, g, mapping[0], sylo, syhi);

      if (ticInfos[3] != null) {
        sxhi += ticInfos[3].mMaxWidth + 2;
      }
      drawKey(graph, g, screenWidth, screenHeight, offX, offY, sxlo, sylo, sxhi, syhi);
    }
    setMappings(mapping);
  }

  private void setupAntialiasing(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    if (mTextAntialiasing) {
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                          RenderingHints.VALUE_RENDER_QUALITY);
    }
    if (mAllAntialiasing) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                          RenderingHints.VALUE_RENDER_QUALITY);
    }
  }


  public int calculateKeyWidth(Object canvas, Graph2D graph) {
    int keyWidth = 0;
    String keyTitle = graph.getKeyTitle();
    if (keyTitle != null) {
      int sw = getTextWidth(canvas, keyTitle);
      if (sw > keyWidth) {
        keyWidth = sw;
      }
    }
    int keyLineWidth = getKeyLineWidth((Graphics) canvas);
    Plot2D[] plots = graph.getPlots();
    for (int j = 0; j < plots.length; j++) {
      Plot2D plot = plots[j];
      String dtitle = plot.getTitle();
      if (dtitle != null && dtitle.length() != 0
          && plot.getData() != null && plot.getData().length != 0) {
        int sw = getTextWidth(canvas, dtitle) + keyLineWidth + 10;
        if (sw > keyWidth) {
          keyWidth = sw;
        }
      }
    }
    return keyWidth;
  }

  public int calculateKeyHeight(Object canvas, Graph2D graph) {
    int keyHeight = 0;
    if (graph.getKeyTitle() != null) {
      keyHeight++;
    }
    Plot2D[] plots = graph.getPlots();
    for (int j = 0; j < plots.length; j++) {
      Plot2D plot = plots[j];
      String dtitle = plot.getTitle();
      if (dtitle != null && dtitle.length() != 0
          && plot.getData() != null && plot.getData().length != 0) {
        keyHeight++;
      }
    }
    return keyHeight * getTextHeight(canvas, "A");
  }

  private void drawGraphArea(Graphics g, int sxlo, int sxhi, int sylo, int syhi) {
    if (mGraphColor != null) {
      try {
        Graphics2D g2d = (Graphics2D) g;
        Paint paint = g2d.getPaint();
        GradientPaint gpaint = new GradientPaint(sxlo, sylo, Color.WHITE, sxlo, syhi, mGraphColor);
        g2d.setPaint(gpaint);
        g.fillRect(sxlo, syhi, sxhi - sxlo, sylo - syhi);
        g2d.setPaint(paint);
      } catch (ClassCastException cce) {
        g.setColor(mGraphColor);
        g.fillRect(sxlo, syhi, sxhi - sxlo, sylo - syhi);
      }
    }

    float base = 0.625f;
    float multiplier = (1.0f - base) / mGraphShadowWidth;
    for (int d = 1; d <= mGraphShadowWidth; d++) {
      float fraction = base + multiplier * d;
      g.setColor(new Color(fraction, fraction, fraction));
      g.drawLine(sxlo + d, sylo + d, sxhi + d, sylo + d);
      g.drawLine(sxhi + d, sylo + d, sxhi + d, syhi + d);
    }
  }


  private void drawYTics(Graph2D graph, Graphics g, int whichTic, TicInfo yTicInfo, Mapping mapping, 
                         int sxlo, int sxhi, int sylo, int syhi) {
    if (graph.usesY(whichTic) && graph.getShowYTics(whichTic)) {
      g.setColor(Color.BLACK);
      FontMetrics fm = g.getFontMetrics();
      int tHeight = fm.getHeight();
      setNumDecimalDigits(yTicInfo.mTic);
      
      if (graph.getLogScaleY(whichTic)) {
        // log scale...
        float start = (float) PlotUtils.floor10(graph.getYLo(whichTic));
        float end = (float) PlotUtils.ceil10(graph.getYHi(whichTic));
        //System.err.println("min = " + start + ", max = " + end);
        for (int i = 1; i <= (int) (end / start); i *= 10) {
          for (int j = 1; j < 10 && i * j <= (int) yTicInfo.mEnd; j++) {
            float num = start * i * j;
            int y = (int) mapping.worldToScreen(num);
            if (y >= syhi && y <= sylo) {
              if ((whichTic == 1) || !graph.usesY(1)) {
                g.drawLine(sxhi, y, sxhi - (j == 1 ? 4 : 2), y);
              }
              if ((whichTic == 0) || !graph.usesY(0)) {
                g.drawLine(sxlo, y, sxlo + (j == 1 ? 4 : 2), y);
              }
            }
          }
          float num = start * i;
          int y = (int) mapping.worldToScreen(num);
          if (y >= syhi && y <= sylo) {
            if (graph.getYGrid(whichTic)) {
              g.setColor(Color.LIGHT_GRAY);
              g.drawLine(sxlo + 4, y, sxhi - 4, y);
              g.setColor(Color.BLACK);
            }
            String snum = num >= 1 ? mNF.format(num) : "" + num;
            //System.err.println("Y: " + num + " -> " + snum);
            g.drawString(snum, (whichTic == 1) ? (sxhi + 4) : (sxlo - getTextWidth(g, snum) - 2), y + tHeight / 2 - 2);
          }
        }
      } else {
        int labelIndex = 0;
        for (int k = yTicInfo.mStart; k <= yTicInfo.mEnd; k++) {
          float num = yTicInfo.mTic * k;
        
          int y = (int) mapping.worldToScreen(num);
        
          if (y >= syhi && y <= sylo) {
            if ((whichTic == 1) || !graph.usesY(1)) {
              g.drawLine(sxhi, y, sxhi - 4, y);
            }
            if ((whichTic == 0) || !graph.usesY(0)) {
              g.drawLine(sxlo, y, sxlo + 4, y);
            }
            if (graph.getYGrid(whichTic)) {
              g.setColor(Color.LIGHT_GRAY);
              g.drawLine(sxlo + 4, y, sxhi - 4, y);
              g.setColor(Color.BLACK);
            }
          
            String snum = mNF.format(num);
            if (yTicInfo.mLabels != null && yTicInfo.mLabels.length != 0) {
              snum = yTicInfo.mLabels[labelIndex]; //k - yStart];
              labelIndex = (labelIndex + 1) % yTicInfo.mLabels.length;
            }
            g.drawString(snum, (whichTic == 1) ? (sxhi + 4) : (sxlo - getTextWidth(g, snum) - 2), y + tHeight / 2 - 2);
          }
        }

        if (yTicInfo.mMinorTic > 0.0f) {
          for (int k = yTicInfo.mMinorStart; k <= yTicInfo.mMinorEnd; k++) {
            float num = yTicInfo.mMinorTic * k;
            int y = (int) mapping.worldToScreen(num);
            if (y >= syhi && y <= sylo) {
              if ((whichTic == 1) || !graph.usesY(1)) {
                g.drawLine(sxhi, y, sxhi - 2, y);
              }
              if ((whichTic == 0) || !graph.usesY(0)) {
                g.drawLine(sxlo, y, sxlo + 2, y);
              }
            }
          }
        }
      }
    }
  }


  private void drawXTics(Graph2D graph, Graphics g, int whichTic, TicInfo xTicInfo, Mapping mapping, 
                         int sxlo, int sxhi, int sylo, int syhi) {
    if (graph.usesX(whichTic) && graph.getShowXTics(whichTic)) {
      g.setColor(Color.BLACK);
      FontMetrics fm = g.getFontMetrics();
      int tHeight = fm.getHeight();
      setNumDecimalDigits(xTicInfo.mTic);

      if (graph.getLogScaleX(whichTic)) {
        // log scale...
        float start = (float) PlotUtils.floor10(graph.getXLo(whichTic));
        float end = (float) PlotUtils.ceil10(graph.getXHi(whichTic));
        //System.err.println("min = " + start + ", max = " + end);
        for (int i = 1; i <= (int) (end / start); i *= 10) {
          for (int j = 1; j < 10 && i * j <= (int) xTicInfo.mEnd; j++) {
            float num = start * i * j;
            int x = (int) mapping.worldToScreen(num);
            if (x >= sxlo && x <= sxhi) {
              if ((whichTic == 0) || !graph.usesX(0)) {
                g.drawLine(x, sylo, x, sylo - (j == 1 ? 4 : 2));
              }
              if ((whichTic == 1) || !graph.usesX(1)) {
                g.drawLine(x, syhi, x, syhi + (j == 1 ? 4 : 2));
              }
            }
          }
          float num = start * i;
          int x = (int) mapping.worldToScreen(num);
          if (x >= sxlo && x <= sxhi) {
            if (graph.getXGrid(whichTic)) {
              g.setColor(Color.LIGHT_GRAY);
              g.drawLine(x, sylo - 4, x, syhi + 4);
              g.setColor(Color.BLACK);
            }
            String snum = num >= 1 ? mNF.format(num) : "" + num;
            //System.err.println("X: " + num + " -> " + snum);
            g.drawString(snum, x - getTextWidth(g, snum) / 2, (whichTic == 0) ? (sylo + tHeight) : (syhi - tHeight / 2));
          }
        }
      } else {
        int labelIndex = 0;
        for (int k = xTicInfo.mStart; k <= xTicInfo.mEnd; k++) {
          float num = xTicInfo.mTic * k;
          int x = (int) mapping.worldToScreen(num);
        
          if (x >= sxlo && x <= sxhi) {
            if ((whichTic == 0) || !graph.usesX(0)) {
              g.drawLine(x, sylo, x, sylo - 4);
            }
            if ((whichTic == 1) || !graph.usesX(1)) {
              g.drawLine(x, syhi, x, syhi + 4);
            }
            if (graph.getXGrid(whichTic)) {
              g.setColor(Color.LIGHT_GRAY);
              g.drawLine(x, sylo - 4, x, syhi + 4);
              g.setColor(Color.BLACK);
            }

            String snum = mNF.format(num);
            if (xTicInfo.mLabels != null && xTicInfo.mLabels.length != 0) {
              snum = xTicInfo.mLabels[labelIndex]; //k - xStart];
              labelIndex = (labelIndex + 1) % xTicInfo.mLabels.length;
            }
            String [] nums = snum.split("\n");
            for (int i = 0; i < nums.length; i++) {
              String snum2 = nums[i];
              g.drawString(snum2, x - getTextWidth(g, snum2) / 2, (whichTic == 0) ? (sylo + tHeight + i * tHeight) : (syhi - tHeight / 2 - i * tHeight));
            }
          }
        }

        if (xTicInfo.mMinorTic > 0.0f) {
          for (int k = xTicInfo.mMinorStart; k <= xTicInfo.mMinorEnd; k++) {
            float num = xTicInfo.mMinorTic * k;
            int x = (int) mapping.worldToScreen(num);
        
            if (x >= sxlo && x <= sxhi) {
              if ((whichTic == 0) || !graph.usesX(0)) {
                g.drawLine(x, sylo, x, sylo - 2);
              }
              if ((whichTic == 1) || !graph.usesX(1)) {
                g.drawLine(x, syhi, x, syhi + 2);
              }
            }
          }
        }
      }
    }
  }


  private void drawVerticalLine(Graph2D graph, Graphics g, Mapping mapping, int sylo, int syhi) {
    // draw vertical dashed line
    if (graph.getVerticalLine()) {
      g.setColor(Color.BLACK);
      int sptX = (int) mapping.worldToScreen(graph.getVerticalLinePos());
      for (int i = syhi; i < sylo; i += 10) {
        g.drawLine(sptX, i, sptX, (i + 5) < sylo ? i + 5 : sylo);
      }
    }
  }


  private void drawKey(Graph2D graph, Graphics g, int screenWidth, int screenHeight, 
                       int offX, int offY, int sxlo, int sylo, int sxhi, int syhi) {
    if (graph.getBorder() && graph.getShowKey()) {
      setClip(g, 0, 0, offX + screenWidth, offY + screenHeight);
      String keyTitle = graph.getKeyTitle();
      int tHeight = getTextHeight(g, "A");

      int keyX;
      int keyWidth = calculateKeyWidth(g, graph);
      int position = graph.getKeyHorizontalPosition();
      if (position == Graph2D.OUTSIDE) {
        keyX = sxhi + 2;
      } else if (position == Graph2D.LEFT) {
        keyX = sxlo + 2;
      } else if (position == Graph2D.CENTER) {
        keyX = (sxhi + sxlo - keyWidth) / 2;
      } else { // assume RIGHT by default
        keyX = sxhi - keyWidth - 2;
      }

      int keyY;
      int keyHeight = calculateKeyHeight(g, graph);
      position = graph.getKeyVerticalPosition();
      if (position == Graph2D.BOTTOM) {
        keyY = sylo - keyHeight - 2;
      } else if (position == Graph2D.CENTER) {
        keyY = (syhi + sylo - keyHeight) / 2;
        //} else if (position == Graph2D.BELOW) { need to think about this one...
        //keyY = sylo + 
      } else { // assume TOP by default
        keyY = syhi + 2;
      }

      int keyIndex = 1;
      if (keyTitle != null && keyTitle.length() != 0) {
        g.setColor(Color.BLACK);
        int yy = keyY + tHeight;
        g.drawString(keyTitle, keyX + 5, yy);
        keyIndex++;
      }

      Plot2D[] plots = graph.getPlots();
      int keyLineWidth = getKeyLineWidth(g);
      for (int j = 0; j < plots.length; j++) {
        setPointIndex(j);
        Plot2D plot = plots[j];
        String dtitle = plot.getTitle();
        if (dtitle != null && dtitle.length() != 0
            && plot.getData() != null && plot.getData().length != 0) {
          g.setColor(mColors[plot.getColor()]);
          //int sw = getTextWidth(g, dtitle);

          int yy = keyY + keyIndex * tHeight;
          g.drawString(dtitle, keyX + keyLineWidth + 10, yy);

          yy -= tHeight / 2 - 2;

          int lineWidth = plot.getLineWidth();
          Stroke s = null;
          if (lineWidth > 1) {
            s = ((Graphics2D) g).getStroke();
            ((Graphics2D) g).setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
          }

          int keyX5 = keyX + 5;          
          if (plot instanceof PointPlot2D) {
            PointPlot2D lplot = (PointPlot2D) plot;
            boolean doLines = lplot.getLines();
            boolean doPoints = lplot.getPoints();
            boolean doFill = lplot.getFill();
            if (doFill) {
              Polygon polygon = new Polygon();
              polygon.addPoint(keyX5, yy + tHeight / 2 - 1);
              polygon.addPoint(keyX5 + keyLineWidth / 2, yy - tHeight / 2);
              polygon.addPoint(keyX5 + keyLineWidth, yy + tHeight / 2 - 1);
              g.fillPolygon(polygon);
            } else {
              if (doPoints) {
                drawPoint(g, keyX5 + keyLineWidth / 2, yy);
              }
              if (doLines) {
                g.drawLine(keyX5, yy, keyX5 + keyLineWidth, yy);
              }
              //g.drawRect(keyX5 + keyLineWidth / 2, yy, 0, 0);
              g.drawLine(keyX5 + keyLineWidth / 2, yy, keyX5 + keyLineWidth / 2, yy);
            }
          } else if (plot instanceof BWPlot2D) {
            BWPlot2D lplot = (BWPlot2D) plot;
            g.drawLine(keyX5, yy, keyX5 + keyLineWidth, yy);
            g.drawRect(keyX5 + keyLineWidth / 2, yy, 0, 0);
          } else if (plot instanceof CurvePlot2D) {
            CurvePlot2D cplot = (CurvePlot2D) plot;
            boolean doFill = cplot.getFill();
            if (doFill) {
              g.fillArc(keyX5, yy - tHeight / 4, keyLineWidth, tHeight - 2, 0, 180);
            } else {
              g.drawArc(keyX5, yy - tHeight / 4, keyLineWidth, tHeight - 2, 0, 180);
            }
          } else if (plot instanceof BoxPlot2D) {
            BoxPlot2D bplot = (BoxPlot2D) plot;
            boolean doFill = bplot.getFill();
            boolean doBorder = bplot.getBorder();
            if (doFill) {
              g.fillRect(keyX5, yy - tHeight / 2, keyLineWidth, tHeight - 2);
              if (doBorder) {
                Color c = g.getColor();
                g.setColor(Color.BLACK);
                g.drawRect(keyX5, yy - tHeight / 2, keyLineWidth, tHeight - 2);
                g.setColor(c);
              }
            } else {
              g.drawRect(keyX5, yy - tHeight / 2, keyLineWidth, tHeight - 2);
            }
          } else if (plot instanceof ScatterPlot2D) {
            g.drawRect(keyX5 + keyLineWidth / 2, yy, 1, 1);
          } else if (plot instanceof CirclePlot2D) {
            CirclePlot2D cplot = (CirclePlot2D) plot;
            boolean doFill = cplot.getFill();
            if (doFill) {
              fillCircle(g, keyX5 + 1 + keyLineWidth / 2, yy, tHeight - 2);
            } else {
              drawCircle(g, keyX5 + 1 + keyLineWidth / 2, yy, tHeight - 2);
            }
          } else if (plot instanceof TextPlot2D) {
            TextPlot2D tplot = (TextPlot2D) plot;
            String text = "abc";
            FontMetrics fm = g.getFontMetrics();
            int sw = getTextWidth(g, text);
            if (tplot.isInvert()) {
              g.fillRect(keyX5, yy - tHeight / 2 + fm.getMaxDescent() - 2, sw, tHeight);
              Color c = g.getColor();
              g.setColor(Color.WHITE);
              g.drawString(text, keyX5, yy + tHeight / 2 - 2);
              g.setColor(c);
            } else {
              g.drawString(text, keyX5, yy + tHeight / 2 - 2);
            }
          }
          if (s != null) {
            ((Graphics2D) g).setStroke(s);
          }
          keyIndex++;
        }
      }
    }
  }

  // specitic plots...
  protected void drawTextPlot(Object canvas, TextPlot2D tplot, Mapping convertX, Mapping convertY) {
    if (tplot.isInvert()) {
      int tHeight = getTextHeight(canvas, "A");
      
      int halign = tplot.getHorizontalAlignment();
      switch (halign) {
      case TextPlot2D.LEFT: halign = 0; break;
      case TextPlot2D.CENTER: halign = 1; break;
      case TextPlot2D.RIGHT: halign = 2; break;
      }

      int valign = tplot.getVerticalAlignment();
      int descent = getTextDescent(canvas, "A");
      switch (valign) {
      case TextPlot2D.CENTER: valign = tHeight / 2 - descent; break;
      case TextPlot2D.BASELINE: valign = 0; break;
      case TextPlot2D.TOP: valign = tHeight - descent; break;
      case TextPlot2D.BOTTOM: valign = -descent; break;
      }
      
      Datum2D[] points = tplot.getData();
      if (points != null && points.length != 0) {
        Graphics g = (Graphics) canvas;
        for (int i = 0; i < points.length; i++) {
          TextPoint2D point = (TextPoint2D) points[i];
          String text = point.getText();
          int sptX = (int) convertX.worldToScreen(point.getX());
          int sptY = (int) convertY.worldToScreen(point.getY());
          int sw = getTextWidth(canvas, text);
          
          fillRectangle(canvas, (int) (sptX - halign * sw / 2.0f), (int) (sptY + valign - tHeight + descent), sw, tHeight);
          Color c = g.getColor();
          g.setColor(Color.WHITE);
          drawString(canvas, (int) (sptX - halign * sw / 2.0f), (int) (sptY + valign), text);
          g.setColor(c);
        }
      }
    } else {
      super.drawTextPlot(canvas, tplot, convertX, convertY);
    }
  }


  protected void drawBWPlot(Object canvas, BWPlot2D bwplot, Mapping convertX, Mapping convertY) {
    Datum2D[] points = bwplot.getData();
    if (points != null && points.length != 0) {
      if (bwplot.getType() == BWPlot2D.JOINED) {
        Graphics g = (Graphics) canvas;
        Color old = g.getColor();
        g.setColor(new Color((old.getRed() + 255) / 2, (old.getGreen() + 255) / 2, (old.getBlue() + 255) / 2, 127));
        Polygon polygon = new Polygon();
        for (int i = 0; i < points.length; i++) {
          BWPoint2D point = (BWPoint2D) points[i];
          int sptX = (int) convertX.worldToScreen(point.getX());
          int sptY = (int) convertY.worldToScreen(point.getY(0));
          polygon.addPoint(sptX, sptY);
        }
        for (int i = points.length - 1; i >= 0; i--) {
          BWPoint2D point = (BWPoint2D) points[i];
          int sptX = (int) convertX.worldToScreen(point.getX());
          int sptY = (int) convertY.worldToScreen(point.getY(4));
          polygon.addPoint(sptX, sptY);
        }
        g.fillPolygon(polygon);

        g.setColor(new Color(old.getRed(), old.getGreen(), old.getBlue(), 96));
        polygon = new Polygon();
        for (int i = 0; i < points.length; i++) {
          BWPoint2D point = (BWPoint2D) points[i];
          int sptX = (int) convertX.worldToScreen(point.getX());
          int sptY = (int) convertY.worldToScreen(point.getY(1));
          polygon.addPoint(sptX, sptY);
        }
        for (int i = points.length - 1; i >= 0; i--) {
          BWPoint2D point = (BWPoint2D) points[i];
          int sptX = (int) convertX.worldToScreen(point.getX());
          int sptY = (int) convertY.worldToScreen(point.getY(3));
          polygon.addPoint(sptX, sptY);
        }
        g.fillPolygon(polygon);

        g.setColor(old);

        BWPoint2D point = (BWPoint2D) points[0];
        int lastX = (int) convertX.worldToScreen(point.getX());
        int lastY = (int) convertY.worldToScreen(point.getY(2));
      
        drawRectangle(canvas, lastX, lastY, 0, 0);

        for (int i = 1; i < points.length; i++) {
          point = (BWPoint2D) points[i];
          int sptX = (int) convertX.worldToScreen(point.getX());
          int sptY = (int) convertY.worldToScreen(point.getY(2));
          drawLine(canvas, lastX, lastY, sptX, sptY);
          lastX = sptX;
          lastY = sptY;
          drawRectangle(canvas, sptX, sptY, 0, 0);
        }
      } else {
        super.drawBWPlot(canvas, bwplot, convertX, convertY);
      }
    }
  }

}
