package com.reeltwo.plot.renderer;

import com.reeltwo.plot.ArrowPlot2D;
import com.reeltwo.plot.BWPlot2D;
import com.reeltwo.plot.BWPoint2D;
import com.reeltwo.plot.BoxPlot2D;
import com.reeltwo.plot.CirclePlot2D;
import com.reeltwo.plot.CurvePlot2D;
import com.reeltwo.plot.Datum2D;
import com.reeltwo.plot.FillablePlot2D;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.GraphLine;
import com.reeltwo.plot.Plot2D;
import com.reeltwo.plot.PlotUtils;
import com.reeltwo.plot.PointPlot2D;
import com.reeltwo.plot.ScatterPlot2D;
import com.reeltwo.plot.TextPlot2D;
import com.reeltwo.plot.patterns.DefaultColorGroup;
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
  private Color mGraphTopColor = null;
  private Color mGraphBottomColor = null;
  private int mGraphShadowWidth = 0;

  private boolean mTextAntialiasing = false;
  private boolean mAllAntialiasing = false;

  private Color [] mColors = null;
  private Paint [] mPatterns = null;

  private Color mBackgroundColor = Color.WHITE;
  private Color mForegroundColor = Color.BLACK;
  private Color mGridColor = Color.LIGHT_GRAY;

  /**
   * Creates a new <code>GraphicsRenderer</code>.
   */
  public GraphicsRenderer() {
    this((Color []) new DefaultColorGroup().getPatterns());
  }

  /**
   * Creates a new <code>GraphicsRenderer</code> setting the
   * <code>colors</code> to use.
   *
   * @param colors an array of <code>Color</code>s
   */
  public GraphicsRenderer(Color [] colors) {
    this(colors, null);
  }

  /**
   * Creates a new <code>GraphicsRenderer</code>  setting the
   * <code>colors</code> and <code>patterns</code> to use.
   *
   * @param colors an array of <code>Color</code>s
   * @param patterns an array of patterns
   */
  public GraphicsRenderer(Color [] colors, Paint [] patterns) {
    setColors(colors);
    setPatterns(patterns);
  }

  /**
   * Sets the <code>colors</code> to use.
   *
   * @param colors an array of <code>Color</code>s
   */
  public void setColors(Color [] colors) {
    if (colors == null) {
      throw new NullPointerException("null colors given");
    }
    for (int i = 0; i < colors.length; i++) {
      if (colors[i] == null) {
        throw new NullPointerException("null color given");
      }
    }
    if (mPatterns == mColors) {
      mPatterns = colors;
    }
    mColors = colors;
  }

  /**
   * Sets the <code>patterns</code> to use.
   *
   * @param patterns an array of patterns
   */
  public void setPatterns(Paint [] patterns) {
    if (patterns == null) {
      mPatterns = mColors;
    } else {
      for (int i = 0; i < patterns.length; i++) {
        if (patterns[i] == null) {
          throw new NullPointerException("null color given");
        }
      }
      mPatterns = patterns;
    }
  }

  /**
   * Returns the plot colors.
   *
   * @return an array of <code>Color</code>s
   */
  public Color [] getColors() {
    return mColors;
  }

  /**
   * Returns the plot patterns.
   *
   * @return an array of patterns
   */
  public Paint [] getPatterns() {
    return mPatterns;
  }

  // from AbstractRenderer
  protected int getTextWidth(Object canvas, String text) {
    FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    int width = text == null ? 0 : fm.stringWidth(text);
    return width;
  }

  protected int getTextHeight(Object canvas, String text) {
    FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    return fm.getHeight();
  }

  protected int getTextDescent(Object canvas, String text) {
    FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    return fm.getMaxDescent();
  }

  protected void setColor(Object canvas, int colorIndex) {
    switch (colorIndex) {
    case BACKGROUND_COLOR_INDEX: ((Graphics) canvas).setColor(mBackgroundColor);
      break;
    case FOREGROUND_COLOR_INDEX: ((Graphics) canvas).setColor(mForegroundColor);
      break;
    default:
      ((Graphics) canvas).setColor(mColors[colorIndex % mColors.length]);
    };
  }

  protected void setPattern(Object canvas, int patternIndex) {
    ((Graphics2D) canvas).setPaint(mPatterns[patternIndex % mPatterns.length]);
  }

  protected void setLineWidth(Object canvas, int width) {
    super.setLineWidth(canvas, width);

    if (width > 1) {
      ((Graphics2D) canvas).setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    } else {
      ((Graphics2D) canvas).setStroke(new BasicStroke());
    }
  }

  protected void setClip(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).setClip(x, y, w, h);
  }

  protected void drawString(Object canvas, int x, int y, String text) {
    ((Graphics) canvas).drawString(text, x, y);
  }

  protected void drawPoint(Object canvas, int x, int y) {
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

  protected void drawLine(Object canvas, int x1, int y1, int x2, int y2) {
    ((Graphics) canvas).drawLine(x1, y1, x2, y2);
  }

  protected void drawRectangle(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).drawRect(x, y, w, h);
  }

  protected void fillRectangle(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).fillRect(x, y, w, h);
  }

  protected void drawCircle(Object canvas, int x, int y, int diameter) {
    ((Graphics) canvas).drawOval((int) (x - diameter / 2.0f), (int) (y - diameter / 2.0f), diameter, diameter);
  }

  protected void fillCircle(Object canvas, int x, int y, int diameter) {
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

  protected void drawPolygon(Object canvas, int [] xs, int [] ys) {
    ((Graphics) canvas).drawPolygon(createPolygon(xs, ys));
  }

  protected void fillPolygon(Object canvas, int [] xs, int [] ys) {
    ((Graphics) canvas).fillPolygon(createPolygon(xs, ys));
  }

  // render specific
  /**
   * Turns text antialiasing on if <code>flag</code> is true.
   *
   * @param flag antialias switch
   */
  public void setTextAntialiasing(boolean flag) {
    mTextAntialiasing = flag;
  }

  /**
   * Turns antialiasing on if <code>flag</code> is true.
   *
   * @param flag antialias switch
   */
  public void setAntialiasing(boolean flag) {
    mAllAntialiasing = flag;
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
    mGraphTopColor = topColor;
    mGraphBottomColor = bottomColor;
  }

  /**
   * Sets the background color for the whole graph.
   *
   * @param color a <code>Color</code>
   */
  public void setBackground(Color color) {
    mBackgroundColor = color;
  }

  /**
   * Sets the forground color for the whole graph.  This is the color
   * used for border lines and text.
   *
   * @param color a <code>Color</code>
   */
  public void setForeground(Color color) {
    mForegroundColor = color;
  }

  /**
   * Sets the color for drawing grid lines.
   *
   * @param color a <code>Color</code>
   */
  public void setGridColor(Color color) {
    mGridColor = color;
  }

  /**
   * Sets the width of the shadow around the graph border.
   *
   * @param width shadow width
   */
  public void setGraphShadowWidth(int width) {
    mGraphShadowWidth = width;
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
   * @param screenWidth width of drawing region
   * @param screenHeight height of drawing region
   */
  public void drawGraph(Graph2D graph, Graphics g, int screenWidth, int screenHeight) {
    Mapping[] mapping = null;
    setMappings(null);
    if (graph != null) {
      setColor(g, FOREGROUND_COLOR_INDEX);
      setClip(g, 0, 0, screenWidth, screenHeight);
      int sxlo = 0;
      int sxhi = screenWidth - 1;
      int sylo = screenHeight - 1;
      int syhi = 0;
      String title = graph.getTitle();
      int tHeight = getTextHeight(g, "A");
      if (title.length() > 0) {
        syhi += tHeight;
      }

      TicInfo y2TicInfo = null;
      TicInfo xTicInfo = null;
      int keyX = 0;
      if (graph.isBorder()) {
        int keyWidth = 0;
        if (graph.isShowKey()) {
          if (graph.getKeyVerticalPosition() == Graph2D.BELOW) {
            final int keyHeight = calculateKeyHeight(g, graph, screenWidth);
            sylo -= keyHeight + 2;
          } else if (graph.getKeyHorizontalPosition() == Graph2D.OUTSIDE) {
            keyWidth = calculateKeyWidth(g, graph);
            sxhi -= keyWidth + 2;
          }
        }
        if (graph.usesX(0) && graph.getXLabel(0).length() > 0) {
          sylo -= tHeight + 2;
          // draw x label later when border width is known
        }
        if (graph.usesY(0) && graph.getYLabel(0).length() > 0) {
          g.drawString(graph.getYLabel(0), sxlo, tHeight * (1 + (title.length() > 0 ? 1 : 0)));
        }
        if (graph.usesY(1) && graph.getYLabel(1).length() > 0) {
          String yLabel = graph.getYLabel(1);
          g.drawString(yLabel, sxhi - getTextWidth(g, yLabel), tHeight * (1 + (title.length() > 0 ? 1 : 0)));
        }
        if ((graph.usesX(1) && graph.getXLabel(1).length() > 0)
            || (graph.usesY(0) && graph.getYLabel(0).length() > 0)
            || (graph.usesY(1) && graph.getYLabel(1).length() > 0)) {
          syhi += tHeight;
        }
        // extra height for digits on axes
        if ((graph.usesY(0) && graph.isShowYTics(0))
            || (graph.usesY(1) && graph.isShowYTics(1))) {
          syhi += tHeight / 2;
        }
        TicInfo [] ticInfos = createTicInfos(g, graph);
        TicInfo yTicInfo = ticInfos[1];
        if (yTicInfo != null) {
          sxlo += yTicInfo.mMaxWidth + 2;
        }
        y2TicInfo = ticInfos[3];
        if (y2TicInfo != null) {
          sxhi -= y2TicInfo.mMaxWidth + 2;
        }
        xTicInfo = ticInfos[0];
        if (xTicInfo != null) {
          sylo -= xTicInfo.mMaxHeight;
          if (!(graph.usesY(0) && graph.isShowYTics(0))) {
            sxlo += xTicInfo.mMaxWidth / 2 + 2;
          }
          if (!(graph.usesY(1) && graph.isShowYTics(1)) && keyWidth == 0) {
            sxhi -= xTicInfo.mMaxWidth / 2 + 2;
          }
        }
        TicInfo x2TicInfo = ticInfos[2];
        if (x2TicInfo != null) {
          syhi += xTicInfo.mMaxHeight;
          if (!graph.isShowYTics(0) && !(graph.usesX(0) && graph.isShowXTics(0))) {
            sxlo += x2TicInfo.mMaxWidth / 2 + 2;
          }
          if (!graph.isShowYTics(1) && !(graph.usesX(0) && graph.isShowXTics(0))) {
            sxhi -= x2TicInfo.mMaxWidth / 2 + 2;
          }
        }
        mapping = createMappings(graph, sxlo, sylo, sxhi, syhi);
        drawGraphArea(g, sxlo, sylo, sxhi, syhi);
        setupAntialiasing(g);
        drawYTics(graph, g, 0, yTicInfo, mapping[1], sxlo, sxhi, sylo, syhi);
        drawYTics(graph, g, 1, y2TicInfo, mapping[3], sxlo, sxhi, sylo, syhi);
        drawXTics(graph, g, 0, xTicInfo, mapping[0], sxlo, sxhi, sylo, syhi);
        drawXTics(graph, g, 1, x2TicInfo, mapping[2], sxlo, sxhi, sylo, syhi);
        setColor(g, FOREGROUND_COLOR_INDEX);
        String xLabel;
        if (graph.usesX(0) && (xLabel = graph.getXLabel(0)).length() > 0) {
          final int extra = tHeight + ((graph.usesX(0) && graph.isShowXTics(0)) ? xTicInfo.mMaxHeight : 0);
          g.drawString(xLabel, (sxhi + sxlo) / 2 - getTextWidth(g, xLabel) / 2, sylo + extra);
        }
        if (graph.usesX(1) && (xLabel = graph.getXLabel(1)).length() > 0) {
          g.drawString(xLabel, (sxhi + sxlo) / 2 - getTextWidth(g, xLabel) / 2, tHeight * (1 + (title.length() > 0 ? 1 : 0)));
        }
        // draw border
        setColor(g, FOREGROUND_COLOR_INDEX);
        g.drawRect(sxlo, syhi, sxhi - sxlo, sylo - syhi);
      } else {
        drawGraphArea(g, sxlo, sylo, sxhi, syhi);
        mapping = createMappings(graph, sxlo, sylo, sxhi, syhi);
      }
      setColor(g, FOREGROUND_COLOR_INDEX);
      // draw title
      if (title.length() > 0) {
        g.drawString(title, (sxhi + sxlo) / 2 - g.getFontMetrics().stringWidth(title) / 2, tHeight);
      }
      // set clip so nothing appears outside border
      setClip(g, sxlo, syhi, sxhi - sxlo + 1, sylo - syhi + 1);
      drawData(g, graph.getPlots(), mapping);
      //drawVerticalLine(graph, g, mapping[0], sylo, syhi);
      if (y2TicInfo != null) { sxhi += y2TicInfo.mMaxWidth + 2; }
      if (xTicInfo != null) { sylo += xTicInfo.mMaxHeight; }
      if (graph.usesX(0) && graph.getXLabel(0).length() > 0) { sylo += tHeight; }
      drawKey(graph, g, screenWidth, screenHeight, sxlo, sylo, sxhi, syhi);
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


  protected int calculateKeyWidth(Object canvas, Graph2D graph) {
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

  protected int calculateKeyHeight(Object canvas, Graph2D graph, int screenWidth) {
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

    if (keyHeight > 1 && graph.getKeyVerticalPosition() == Graph2D.BELOW) {
      // need to take screen width into account...
      final int keyWidth = calculateKeyWidth(canvas, graph);
      final int cols = keyWidth == 0 ? 0 : Math.max(1, screenWidth / keyWidth);
      final int rows = cols == 0 ? 0 : 1 + (keyHeight - 1) / cols;

      //System.err.println("keyheight " + keyHeight + " : " + cols + " : " + rows);

      keyHeight = rows;
    }

    return keyHeight * getTextHeight(canvas, "A");
  }

  private void drawGraphArea(Graphics g, int sxlo, int sylo, int sxhi, int syhi) {
    if (mGraphTopColor != null) {
      if (mGraphBottomColor == null || mGraphBottomColor.equals(mGraphTopColor)) {
        g.setColor(mGraphTopColor);
        g.fillRect(sxlo, syhi, sxhi - sxlo, sylo - syhi);
      } else {
        try {
          Graphics2D g2d = (Graphics2D) g;
          Paint paint = g2d.getPaint();
          GradientPaint gpaint = new GradientPaint(sxlo, sylo, mGraphBottomColor, sxlo, syhi, mGraphTopColor);
          g2d.setPaint(gpaint);
          g.fillRect(sxlo, syhi, sxhi - sxlo, sylo - syhi);
          g2d.setPaint(paint);
        } catch (ClassCastException cce) {
          System.err.println("Graphics rendering problem: " + cce.getMessage());
        }
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


  private void drawYTics(Graph2D graph, Graphics g, int whichTic, TicInfo yTicInfo, Mapping mapping, int sxlo, int sxhi, int sylo, int syhi) {
    if (graph.usesY(whichTic) && graph.isShowYTics(whichTic)) {
      setColor(g, FOREGROUND_COLOR_INDEX);
      FontMetrics fm = g.getFontMetrics();
      int tHeight = fm.getHeight();
      setNumDecimalDigits(yTicInfo.mTic);
      
      if (graph.isLogScaleY(whichTic)) {
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
            if (graph.isYGrid(whichTic)) {
              g.setColor(mGridColor);
              g.drawLine(sxlo + 4, y, sxhi - 4, y);
              setColor(g, FOREGROUND_COLOR_INDEX);
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
            if (graph.isYGrid(whichTic)) {
              g.setColor(mGridColor);
              g.drawLine(sxlo + 4, y, sxhi - 4, y);
              setColor(g, FOREGROUND_COLOR_INDEX);
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


  private void drawXTics(Graph2D graph, Graphics g, int whichTic, TicInfo xTicInfo, Mapping mapping, int sxlo, int sxhi, int sylo, int syhi) {
    if (graph.usesX(whichTic) && graph.isShowXTics(whichTic)) {
      setColor(g, FOREGROUND_COLOR_INDEX);
      FontMetrics fm = g.getFontMetrics();
      int tHeight = fm.getHeight();
      setNumDecimalDigits(xTicInfo.mTic);

      if (graph.isLogScaleX(whichTic)) {
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
            if (graph.isXGrid(whichTic)) {
              g.setColor(mGridColor);
              g.drawLine(x, sylo - 4, x, syhi + 4);
              setColor(g, FOREGROUND_COLOR_INDEX);
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
            if (graph.isXGrid(whichTic)) {
              g.setColor(mGridColor);
              g.drawLine(x, sylo - 4, x, syhi + 4);
              setColor(g, FOREGROUND_COLOR_INDEX);
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

  protected void drawGraphLine(Object canvas, GraphLine line, Mapping convertX, Mapping convertY) {
    BasicStroke stroke = (BasicStroke) ((Graphics2D) canvas).getStroke();
    if (line.getType() == GraphLine.DASHES) {
      ((Graphics2D) canvas).setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit(), new float[] {5.0f, 5.0f}, 0.0f));
    } else if (line.getType() == GraphLine.DOTS) {
      ((Graphics2D) canvas).setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit(), new float[] {1.0f, 5.0f}, 0.0f));
    }

    super.drawGraphLine(canvas, line, convertX, convertY);

    ((Graphics2D) canvas).setStroke(stroke);
  }

  private int calcKeyX(Graph2D graph, Graphics g, int sxlo, int sxhi, int keyWidth) {
    int keyX;
    if (graph.getKeyVerticalPosition() == Graph2D.BELOW) {
      keyX = 0;
    } else {
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
    }
    return keyX;
  }

  private int calcKeyY(Graph2D graph, Graphics g, int screenWidth, int sylo, int syhi, int keyHeight) {
    int keyY;
    int position = graph.getKeyVerticalPosition();
    if (position == Graph2D.BOTTOM) {
      keyY = sylo - keyHeight - 2;
    } else if (position == Graph2D.CENTER) {
      keyY = (syhi + sylo - keyHeight) / 2;
    } else if (position == Graph2D.BELOW) {
      keyY = sylo;
    } else { // assume TOP by default
      keyY = syhi + 2;
    }
    return keyY;
  }

  private void drawKey(Graph2D graph, Graphics g, int screenWidth, int screenHeight, int sxlo, int sylo, int sxhi, int syhi) {
    if (graph.isBorder() && graph.isShowKey()) {
      setClip(g, 0, 0, screenWidth, screenHeight);
      String keyTitle = graph.getKeyTitle();
      int tHeight = getTextHeight(g, "A");
      final int keyWidth = calculateKeyWidth(g, graph);
      int keyX = calcKeyX(graph, g, sxlo, sxhi, keyWidth);
      final int keyHeight = calculateKeyHeight(g, graph, screenWidth);
      int keyY = calcKeyY(graph, g, screenWidth, sylo, syhi, keyHeight);
      if (keyTitle != null && keyTitle.length() != 0) {
        setColor(g, FOREGROUND_COLOR_INDEX);
        int yy = keyY + tHeight;
        g.drawString(keyTitle, keyX + 5, yy);
      }
      final int cols = keyWidth == 0 ? 0 : Math.max(1, screenWidth / keyWidth);
      final int rows = keyHeight / tHeight;
      Plot2D[] plots = graph.getPlots();
      int keyLineWidth = getKeyLineWidth(g);
      int j = 0;
      for (int c = 0; c < cols; c++) {
        int r = (c == 0) ? ((keyTitle != null && keyTitle.length() != 0) ? 1 : 0) : 0;
        for (; r < rows && j < plots.length; r++) {
          setPointIndex(j);
          String dtitle = null;
          Plot2D plot = null;
          while (j < plots.length && dtitle == null) {
            plot = plots[j++];
            dtitle = plot.getTitle();
            if (dtitle == null || dtitle.length() == 0 || plot.getData() == null || plot.getData().length == 0) {
              dtitle = null;
            }
          }
          if (dtitle != null) {
            final int xx = keyX + c * keyWidth;
            int yy = keyY + (r + 1) * tHeight;
            if ((plot instanceof TextPlot2D && ((TextPlot2D) plot).isUseFGColor())
                || (plot instanceof FillablePlot2D && ((FillablePlot2D) plot).getFill() == FillablePlot2D.PATTERN_FILL)) {
              setColor(g, FOREGROUND_COLOR_INDEX);
            } else if (graph.isColoredKey()) {
              setColor(g, plot.getColor());
            } else {
              setColor(g, FOREGROUND_COLOR_INDEX);
            }
            g.drawString(dtitle, xx + keyLineWidth + 10, yy);
            if (!graph.isColoredKey()) {
              setColor(g, plot.getColor());
            }
            yy -= tHeight / 2 - 2;
            int doFill = (plot instanceof FillablePlot2D) ? ((FillablePlot2D) plot).getFill() : FillablePlot2D.NO_FILL;
            if (doFill == FillablePlot2D.PATTERN_FILL) {
              setPattern(g, plot.getColor());
            }
            boolean doBorder = (plot instanceof FillablePlot2D) ? ((FillablePlot2D) plot).isBorder() : false;
            Stroke s = null;
            if (plot.getLineWidth() > 1) {
              s = ((Graphics2D) g).getStroke();
              ((Graphics2D) g).setStroke(new BasicStroke(plot.getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            }
            int keyX5 = xx + 5;          
            if (plot instanceof PointPlot2D) {
              PointPlot2D lplot = (PointPlot2D) plot;
              boolean doLines = lplot.isLines();
              boolean doPoints = lplot.isPoints();
              if (doFill != FillablePlot2D.NO_FILL) {
                Polygon polygon = new Polygon();
                polygon.addPoint(keyX5, yy + tHeight / 2 - 1);
                polygon.addPoint(keyX5 + keyLineWidth / 2, yy - tHeight / 2);
                polygon.addPoint(keyX5 + keyLineWidth, yy + tHeight / 2 - 1);
                g.fillPolygon(polygon);
                if (doBorder) {
                  setColor(g, FOREGROUND_COLOR_INDEX);
                  g.drawPolygon(polygon);
                }
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
            } else if (plot instanceof ArrowPlot2D) {
              ArrowPlot2D aplot = (ArrowPlot2D) plot;
              g.drawLine(keyX5, yy, keyX5 + keyLineWidth, yy);
              Poly p = arrowHead(keyX5, yy, keyX5 + keyLineWidth, yy, aplot.getHeadWidth(), aplot.getHeadHeight(), aplot.getHeadType());
              int [] xs = p.getXs();
              int [] ys = p.getYs();
              fillPolygon(g, xs, ys);
              drawPolygon(g, xs, ys);
            } else if (plot instanceof BWPlot2D) {
              BWPlot2D lplot = (BWPlot2D) plot;
              g.drawLine(keyX5, yy, keyX5 + keyLineWidth, yy);
              g.drawRect(keyX5 + keyLineWidth / 2, yy, 0, 0);
            } else if (plot instanceof CurvePlot2D) {
              CurvePlot2D cplot = (CurvePlot2D) plot;
              if (doFill != FillablePlot2D.NO_FILL) {
                g.fillArc(keyX5, yy - tHeight / 4, keyLineWidth, tHeight - 2, 0, 180);
                if (doBorder) {
                  setColor(g, FOREGROUND_COLOR_INDEX);
                  g.drawArc(keyX5, yy - tHeight / 4, keyLineWidth, tHeight - 2, 0, 180);
                }
              } else {
                g.drawArc(keyX5, yy - tHeight / 4, keyLineWidth, tHeight - 2, 0, 180);
              }
            } else if (plot instanceof BoxPlot2D) {
              BoxPlot2D bplot = (BoxPlot2D) plot;
              if (doFill != FillablePlot2D.NO_FILL) {
                g.fillRect(keyX5, yy - tHeight / 2, keyLineWidth, tHeight - 2);
                if (doBorder) {
                  setColor(g, FOREGROUND_COLOR_INDEX);
                  g.drawRect(keyX5, yy - tHeight / 2, keyLineWidth, tHeight - 2);
                }
              } else {
                g.drawRect(keyX5, yy - tHeight / 2, keyLineWidth, tHeight - 2);
              }
            } else if (plot instanceof ScatterPlot2D) {
              g.drawRect(keyX5 + keyLineWidth / 2, yy, 1, 1);
            } else if (plot instanceof CirclePlot2D) {
              CirclePlot2D cplot = (CirclePlot2D) plot;
              if (doFill != FillablePlot2D.NO_FILL) {
                fillCircle(g, keyX5 + 1 + keyLineWidth / 2, yy, tHeight - 2);
                if (doBorder) {
                  setColor(g, FOREGROUND_COLOR_INDEX);
                  drawCircle(g, keyX5 + 1 + keyLineWidth / 2, yy, tHeight - 2);
                }
              } else {
                drawCircle(g, keyX5 + 1 + keyLineWidth / 2, yy, tHeight - 2);
              }
            } else if (plot instanceof TextPlot2D) {
              TextPlot2D tplot = (TextPlot2D) plot;
              String text = "abc";
              FontMetrics fm = g.getFontMetrics();
              int sw = getTextWidth(g, text);
              final int color = tplot.isUseFGColor() ? FOREGROUND_COLOR_INDEX : tplot.getColor();
              setColor(g, color);
              if (tplot.isInvert()) {
                g.fillRect(keyX5, yy - tHeight / 2 + fm.getMaxDescent() - 2, sw, tHeight);
                setColor(g, BACKGROUND_COLOR_INDEX);
              }
              g.drawString(text, keyX5, yy + tHeight / 2 - 2);
            }
            if (s != null) { ((Graphics2D) g).setStroke(s); }
          }
        }
      }
    }
  }

  // specific plots...

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
