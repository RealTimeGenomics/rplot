package com.reeltwo.plot.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import com.reeltwo.plot.ArrowPlot2D;
import com.reeltwo.plot.Axis;
import com.reeltwo.plot.BWPlot2D;
import com.reeltwo.plot.BWPlot2D.BoxWhiskerStyle;
import com.reeltwo.plot.BWPoint2D;
import com.reeltwo.plot.BoxPlot2D;
import com.reeltwo.plot.CirclePlot2D;
import com.reeltwo.plot.CurvePlot2D;
import com.reeltwo.plot.Datum2D;
import com.reeltwo.plot.Edge;
import com.reeltwo.plot.FillablePlot2D;
import com.reeltwo.plot.FillablePlot2D.FillStyle;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.GraphLine;
import com.reeltwo.plot.GraphLine.LineStyle;
import com.reeltwo.plot.KeyPosition;
import com.reeltwo.plot.Plot2D;
import com.reeltwo.plot.PlotUtils;
import com.reeltwo.plot.PointPlot2D;
import com.reeltwo.plot.ScatterPlot2D;
import com.reeltwo.plot.TextPlot2D;
import com.reeltwo.plot.patterns.DefaultColorGroup;

/**
 * Code to render a Graph2D object onto a Graphics.
 *
 * @author Richard Littin
 */
public class GraphicsRenderer extends AbstractRenderer {
  private Color mGraphTopColor = null;
  private Color mGraphBottomColor = null;
  private int mGraphShadowWidth = 0;

  private boolean mTextAntialiasing = false;
  private boolean mAllAntialiasing = false;

  private Color[] mColors = null;
  private Paint[] mPatterns = null;

  private Color mBackgroundColor = Color.WHITE;
  private Color mForegroundColor = Color.BLACK;
  private Color mGridColor = Color.LIGHT_GRAY;

  private static class Screen {
    final int mXLo;
    final int mXHi;
    final int mYLo;
    final int mYHi;
    Screen(int xlo, int ylo, int xhi, int yhi) {
      mXLo = xlo;
      mYLo = ylo;
      mXHi = xhi;
      mYHi = yhi;
    }
    int getXLo() {
      return mXLo;
    }
    int getXHi() {
      return mXHi;
    }
    int getYLo() {
      return mYLo;
    }
    int getYHi() {
      return mYHi;
    }
  }

  /**
   * Creates a new <code>GraphicsRenderer</code>.
   */
  public GraphicsRenderer() {
    this((Color[]) new DefaultColorGroup().getPatterns());
  }

  /**
   * Creates a new <code>GraphicsRenderer</code> setting the
   * <code>colors</code> to use.
   *
   * @param colors an array of <code>Color</code>s
   */
  public GraphicsRenderer(Color[] colors) {
    this(colors, null);
  }

  /**
   * Creates a new <code>GraphicsRenderer</code>  setting the
   * <code>colors</code> and <code>patterns</code> to use.
   *
   * @param colors an array of <code>Color</code>s
   * @param patterns an array of patterns
   */
  public GraphicsRenderer(Color[] colors, Paint[] patterns) {
    setColors(colors);
    setPatterns(patterns);
  }

  /**
   * Sets the renderer configuration to match that of the provided graphics renderer. This
   * includes settings such as patterns, colors, but not graph data.
   *
   * @param graphicsRenderer the model renderer
   */
  public void setRendererConfig(GraphicsRenderer graphicsRenderer) {
    setColors(graphicsRenderer.mColors.clone());
    setPatterns(graphicsRenderer.mPatterns.clone());
    setBackground(graphicsRenderer.mBackgroundColor);
    setForeground(graphicsRenderer.mForegroundColor);
    setGraphBGColor(graphicsRenderer.mGraphTopColor, graphicsRenderer.mGraphBottomColor);
    setGridColor(graphicsRenderer.mGridColor);
    setAntialiasing(graphicsRenderer.mAllAntialiasing);
    setTextAntialiasing(graphicsRenderer.mTextAntialiasing);
    setGraphShadowWidth(graphicsRenderer.mGraphShadowWidth);
  }


  /**
   * Sets the <code>colors</code> to use.
   *
   * @param colors an array of <code>Color</code>s
   */
  public void setColors(Color[] colors) {
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
  public void setPatterns(Paint[] patterns) {
    if (patterns == null) {
      mPatterns = mColors;
    } else {
      for (int i = 0; i < patterns.length; i++) {
        if (patterns[i] == null) {
          throw new NullPointerException("null pattern given");
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
  public Color[] getColors() {
    return mColors;
  }

  /**
   * Returns the plot patterns.
   *
   * @return an array of patterns
   */
  public Paint[] getPatterns() {
    return mPatterns;
  }

  // from AbstractRenderer
  @Override
  protected int getTextWidth(Object canvas, String text) {
    final FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    final int width = text == null ? 0 : fm.stringWidth(text);
    return width;
  }

  @Override
  protected int getTextHeight(Object canvas, String text) {
    final FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    return fm.getHeight();
  }

  @Override
  protected int getTextDescent(Object canvas, String text) {
    final FontMetrics fm = ((Graphics) canvas).getFontMetrics();
    return fm.getMaxDescent();
  }

  @Override
  protected void setColor(Object canvas, int colorIndex) {
    switch (colorIndex) {
    case BACKGROUND_COLOR_INDEX: ((Graphics) canvas).setColor(mBackgroundColor);
    break;
    case FOREGROUND_COLOR_INDEX: ((Graphics) canvas).setColor(mForegroundColor);
    break;
    default:
      ((Graphics) canvas).setColor(mColors[colorIndex % mColors.length]);
    }
  }

  @Override
  protected void setPattern(Object canvas, int patternIndex) {
    ((Graphics2D) canvas).setPaint(mPatterns[patternIndex % mPatterns.length]);
  }

  @Override
  protected void setLineWidth(Object canvas, int width) {
    super.setLineWidth(canvas, width);
    if (width > 1) {
      ((Graphics2D) canvas).setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    } else {
      ((Graphics2D) canvas).setStroke(new BasicStroke());
    }
  }

  @Override
  protected void setClip(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).setClip(x, y, w, h);
  }

  @Override
  protected void drawString(Object canvas, int x, int y, String text, boolean isVertical) {
    if (isVertical) {
      drawVerticalString((Graphics) canvas, x, y, text);
    } else {
      ((Graphics) canvas).drawString(text, x, y);
    }
  }

  @Override
  protected void drawPoint(Object canvas, int x, int y) {
    final Graphics g = (Graphics) canvas;
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
    default:
      throw new RuntimeException("Programmer error");
    }
  }

  @Override
  protected void drawLine(Object canvas, int x1, int y1, int x2, int y2) {
    ((Graphics) canvas).drawLine(x1, y1, x2, y2);
  }

  @Override
  protected void drawRectangle(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).drawRect(x, y, w, h);
  }

  @Override
  protected void fillRectangle(Object canvas, int x, int y, int w, int h) {
    ((Graphics) canvas).fillRect(x, y, w, h);
  }

  @Override
  protected void drawCircle(Object canvas, int x, int y, int diameter) {
    ((Graphics) canvas).drawOval((int) (x - diameter / 2.0f), (int) (y - diameter / 2.0f), diameter, diameter);
  }

  @Override
  protected void fillCircle(Object canvas, int x, int y, int diameter) {
    ((Graphics) canvas).fillOval((int) (x - diameter / 2.0f), (int) (y - diameter / 2.0f), diameter, diameter);
  }

  private Polygon createPolygon(int[] xs, int[] ys) {
    assert xs != null;
    assert ys != null;
    assert xs.length == ys.length;
    final Polygon polygon = new Polygon();
    for (int i = 0; i < xs.length; i++) {
      polygon.addPoint(xs[i], ys[i]);
    }
    return polygon;
  }

  @Override
  protected void drawPolygon(Object canvas, int[] xs, int[] ys) {
    ((Graphics) canvas).drawPolygon(createPolygon(xs, ys));
  }

  @Override
  protected void fillPolygon(Object canvas, int[] xs, int[] ys) {
    ((Graphics) canvas).fillPolygon(createPolygon(xs, ys));
  }

  @Override
  protected void drawPolyline(Object canvas, int[] xs, int[] ys) {
    assert xs.length == ys.length;
    ((Graphics) canvas).drawPolyline(xs, ys, xs.length);
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
   * Sets the foreground color for the whole graph.  This is the color
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
    final FontMetrics fm = g.getFontMetrics();
    return fm.stringWidth("mm");
  }

  /**
   * Actually draws the graph on the given Graphics. Screen
   * dimensions are used to provide limits on the area to draw in. The
   * mappings from world to screen data points for each axis pair is
   * returned (in order {@code x1,y1,x2,y2},...).
   *
   * @param graph the Graph2D to draw
   * @param g a Graphics to draw on
   * @param screenWidth width of drawing region
   * @param screenHeight height of drawing region
   */
  public void drawGraph(Graph2D graph, Graphics g, int screenWidth, int screenHeight) {
    drawGraph(graph, g, 0, 0, screenWidth, screenHeight);
  }

  /**
   * Actually draws the graph on the given Graphics. Offsets and screen
   * dimensions are used to provide limits on the area to draw in. The
   * mappings from world to screen data points for each axis pair is
   * returned (in order {@code x1,y1,x2,y2},...).
   *
   * @param graph the Graph2D to draw
   * @param g a Graphics to draw on
   * @param offsetX the horizontal offset
   * @param offsetY the vertical offset
   * @param screenWidth width of drawing region
   * @param screenHeight height of drawing region
   */
  public void drawGraph(Graph2D graph, Graphics g, int offsetX, int offsetY, int screenWidth, int screenHeight) {
    Mapping[] mapping = null;
    setMappings(null);
    if (graph != null) {
      setupAntialiasing(g);
      int sxlo = offsetX;
      int sxhi = offsetX + screenWidth - 1;
      int sylo = offsetY + screenHeight - 1;
      int syhi = offsetY;
      setClip(g, offsetX, offsetY, screenWidth, screenHeight);
      setColor(g, FOREGROUND_COLOR_INDEX);
      final String title = graph.getTitle();
      final int tHeight = getTextHeight(g, "A");
      if (title.length() > 0) {
        syhi += tHeight;
      }

      TicInfo y2TicInfo = null;
      TicInfo xTicInfo = null;
      if (graph.isBorder()) {
        int keyWidth = 0;
        if (graph.isShowKey()) {
          if (graph.getKeyVerticalPosition() == KeyPosition.BELOW) {
            final int keyHeight = calculateKeyHeight(g, graph, screenWidth);
            sylo -= keyHeight + 2;
          } else if (graph.getKeyHorizontalPosition() == KeyPosition.OUTSIDE) {
            keyWidth = calculateKeyWidth(g, graph);
            sxhi -= keyWidth + 2;
          }
        }
        if (graph.uses(Axis.X, Edge.MAIN) && graph.getLabel(Axis.X, Edge.MAIN).length() > 0) {
          sylo -= tHeight + 2;
          // draw x label later when border width is known
        }
        if (graph.uses(Axis.Y, Edge.MAIN) && graph.getLabel(Axis.Y, Edge.MAIN).length() > 0) {
          g.drawString(graph.getLabel(Axis.Y, Edge.MAIN), sxlo, offsetY + tHeight * (1 + (title.length() > 0 ? 1 : 0)));
        }
        if (graph.uses(Axis.Y, Edge.ALTERNATE) && graph.getLabel(Axis.Y, Edge.ALTERNATE).length() > 0) {
          final String yLabel = graph.getLabel(Axis.Y, Edge.ALTERNATE);
          g.drawString(yLabel, sxhi - getTextWidth(g, yLabel), offsetY + tHeight * (1 + (title.length() > 0 ? 1 : 0)));
        }
        if ((graph.uses(Axis.X, Edge.ALTERNATE) && graph.getLabel(Axis.X, Edge.ALTERNATE).length() > 0)
            || (graph.uses(Axis.Y, Edge.MAIN) && graph.getLabel(Axis.Y, Edge.MAIN).length() > 0)
            || (graph.uses(Axis.Y, Edge.ALTERNATE) && graph.getLabel(Axis.Y, Edge.ALTERNATE).length() > 0)) {
          syhi += tHeight;
        }
        // extra height for digits on axes
        if ((graph.uses(Axis.Y, Edge.MAIN) && graph.isShowTics(Axis.Y, Edge.MAIN))
            || (graph.uses(Axis.Y, Edge.ALTERNATE) && graph.isShowTics(Axis.Y, Edge.ALTERNATE))) {
          syhi += tHeight / 2;
        }
        final TicInfo[] ticInfos = createTicInfos(g, graph);
        final TicInfo yTicInfo = ticInfos[1];
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
          if (!(graph.uses(Axis.Y, Edge.MAIN) && graph.isShowTics(Axis.Y, Edge.MAIN))) {
            sxlo += xTicInfo.mMaxWidth / 2 + 2;
          }
          if (!(graph.uses(Axis.Y, Edge.ALTERNATE) && graph.isShowTics(Axis.Y, Edge.ALTERNATE)) && keyWidth == 0) {
            sxhi -= xTicInfo.mMaxWidth / 2 + 2;
          }
        }
        final TicInfo x2TicInfo = ticInfos[2];
        if (x2TicInfo != null) {
          syhi += x2TicInfo.mMaxHeight;
          if (!graph.isShowTics(Axis.Y, Edge.MAIN) && !(graph.uses(Axis.X, Edge.MAIN) && graph.isShowTics(Axis.X, Edge.MAIN))) {
            sxlo += x2TicInfo.mMaxWidth / 2 + 2;
          }
          if (!graph.isShowTics(Axis.Y, Edge.ALTERNATE) && !(graph.uses(Axis.X, Edge.MAIN) && graph.isShowTics(Axis.X, Edge.MAIN))) {
            sxhi -= x2TicInfo.mMaxWidth / 2 + 2;
          }
        }

        final Screen s = new Screen(sxlo, sylo, sxhi, syhi);

        mapping = createMappings(graph, sxlo, sylo, sxhi, syhi);
        drawGraphArea(g, sxlo, sylo, sxhi, syhi);
        drawYTics(graph, g, Edge.MAIN, yTicInfo, mapping[1], s);
        drawYTics(graph, g, Edge.ALTERNATE, y2TicInfo, mapping[3], s);
        drawXTics(graph, g, Edge.MAIN, xTicInfo, mapping[0], s);
        drawXTics(graph, g, Edge.ALTERNATE, x2TicInfo, mapping[2], s);
        setColor(g, FOREGROUND_COLOR_INDEX);
        String xLabel;
        if (graph.uses(Axis.X, Edge.MAIN) && (xLabel = graph.getLabel(Axis.X, Edge.MAIN)).length() > 0) {
          final int extra = tHeight + ((graph.uses(Axis.X, Edge.MAIN) && graph.isShowTics(Axis.X, Edge.MAIN)) ? xTicInfo.mMaxHeight : 0);
          g.drawString(xLabel, (sxhi + sxlo) / 2 - getTextWidth(g, xLabel) / 2, sylo + extra);
        }
        if (graph.uses(Axis.X, Edge.ALTERNATE) && (xLabel = graph.getLabel(Axis.X, Edge.ALTERNATE)).length() > 0) {
          g.drawString(xLabel, (sxhi + sxlo) / 2 - getTextWidth(g, xLabel) / 2, offsetY + tHeight * (1 + (title.length() > 0 ? 1 : 0)));
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
        g.drawString(title, (sxhi + sxlo) / 2 - g.getFontMetrics().stringWidth(title) / 2, offsetY + tHeight);
      }
      // set clip so nothing appears outside border
      setClip(g, sxlo, syhi, sxhi - sxlo + 1, sylo - syhi + 1);
      drawData(g, graph.getPlots(), mapping);
      if (graph.getKeyVerticalPosition() == KeyPosition.BELOW) {
        if (xTicInfo != null) { sylo += xTicInfo.mMaxHeight; }
        if (graph.uses(Axis.X, Edge.MAIN) && graph.getLabel(Axis.X, Edge.MAIN).length() > 0) { sylo += tHeight; }
      } else if (graph.getKeyHorizontalPosition() == KeyPosition.OUTSIDE) {
        if (y2TicInfo != null) { sxhi += y2TicInfo.mMaxWidth + 2; }
      }

      drawKey(graph, g, offsetX, offsetY, screenWidth, screenHeight, sxlo, sylo, sxhi, syhi);
    }
    setMappings(mapping);
  }

  private void setupAntialiasing(Graphics g) {
    final Graphics2D g2 = (Graphics2D) g;
    if (mTextAntialiasing) {
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
    if (mAllAntialiasing) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
  }

  @Override
  protected int calculateKeyWidth(Object canvas, Graph2D graph) {
    int keyWidth = 0;
    final String keyTitle = graph.getKeyTitle();
    if (keyTitle != null) {
      final int sw = getTextWidth(canvas, keyTitle);
      if (sw > keyWidth) {
        keyWidth = sw;
      }
    }
    final int keyLineWidth = getKeyLineWidth((Graphics) canvas);
    final Plot2D[] plots = graph.getPlots();
    for (int j = 0; j < plots.length; j++) {
      final Plot2D plot = plots[j];
      final String dtitle = plot.getTitle();
      if (dtitle != null && dtitle.length() != 0
          && plot.getData() != null && plot.getData().length != 0) {
        final int sw = getTextWidth(canvas, dtitle) + keyLineWidth + 10;
        if (sw > keyWidth) {
          keyWidth = sw;
        }
      }
    }
    return keyWidth;
  }

  @Override
  protected int calculateKeyHeight(Object canvas, Graph2D graph, int screenWidth) {
    int keyHeight = 0;
    if (graph.getKeyTitle() != null) {
      keyHeight++;
    }
    final Plot2D[] plots = graph.getPlots();
    for (int j = 0; j < plots.length; j++) {
      final Plot2D plot = plots[j];
      final String dtitle = plot.getTitle();
      if (dtitle != null && dtitle.length() != 0
          && plot.getData() != null && plot.getData().length != 0) {
        keyHeight++;
      }
    }

    if (keyHeight > 1 && graph.getKeyVerticalPosition() == KeyPosition.BELOW) {
      // need to take screen width into account...
      final int keyWidth = calculateKeyWidth(canvas, graph);
      final int cols = keyWidth == 0 ? 0 : Math.max(1, screenWidth / keyWidth);
      final int rows = cols == 0 ? 0 : 1 + (keyHeight - 1) / cols;
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
          final Graphics2D g2d = (Graphics2D) g;
          final Paint paint = g2d.getPaint();
          final GradientPaint gpaint = new GradientPaint(sxlo, sylo, mGraphBottomColor, sxlo, syhi, mGraphTopColor);
          g2d.setPaint(gpaint);
          g.fillRect(sxlo, syhi, sxhi - sxlo, sylo - syhi);
          g2d.setPaint(paint);
        } catch (final ClassCastException cce) {
          System.err.println("Graphics rendering problem: " + cce.getMessage());
        }
      }
    }

    final float base = 0.625f;
    final float multiplier = (1.0f - base) / mGraphShadowWidth;
    for (int d = 1; d <= mGraphShadowWidth; d++) {
      final float fraction = base + multiplier * d;
      g.setColor(new Color(fraction, fraction, fraction));
      g.drawLine(sxlo + d, sylo + d, sxhi + d, sylo + d);
      g.drawLine(sxhi + d, sylo + d, sxhi + d, syhi + d);
    }
  }


  private void drawYTics(Graph2D graph, Graphics g, Edge whichTic, TicInfo yTicInfo, Mapping mapping, Screen s) {
    final int sxlo = s.getXLo();
    final int sxhi = s.getXHi();
    final int sylo = s.getYLo();
    final int syhi = s.getYHi();
    if (graph.uses(Axis.Y, whichTic) && graph.isShowTics(Axis.Y, whichTic)) {
      setColor(g, FOREGROUND_COLOR_INDEX);
      final FontMetrics fm = g.getFontMetrics();
      final int tHeight = fm.getHeight();
      yTicInfo.setNumDecimalDigits(yTicInfo.mTic);
      int gridlo = (whichTic == Edge.MAIN) || !graph.uses(Axis.Y, Edge.MAIN) ? sxlo + 4 : sxlo;
      int gridhi = (whichTic == Edge.ALTERNATE) || !graph.uses(Axis.Y, Edge.ALTERNATE) ? sxhi - 4 : sxhi;

      if (graph.isLogScale(Axis.Y, whichTic)) {
        // log scale...
        final float start = (float) PlotUtils.floor10(graph.getLo(Axis.Y, whichTic));
        final float end = (float) PlotUtils.ceil10(graph.getHi(Axis.Y, whichTic));
        //System.err.println("min = " + start + ", max = " + end);
        for (int i = 1; i <= (int) (end / start); i *= 10) {
          for (int j = 1; j < 10 && i * j <= yTicInfo.mEnd; j++) {
            final float num = start * i * j;
            final int y = (int) mapping.worldToScreen(num);
            if (y >= syhi && y <= sylo) {
              if ((whichTic == Edge.ALTERNATE) || !graph.uses(Axis.Y, Edge.ALTERNATE)) {
                g.drawLine(sxhi, y, sxhi - (j == 1 ? 4 : 2), y);
              }
              if ((whichTic == Edge.MAIN) || !graph.uses(Axis.Y, Edge.MAIN)) {
                g.drawLine(sxlo, y, sxlo + (j == 1 ? 4 : 2), y);
              }
            }
          }
          final float num = start * i;
          final int y = (int) mapping.worldToScreen(num);
          if (y >= syhi && y <= sylo) {
            if (graph.isGrid(Axis.Y, whichTic)) {
              g.setColor(mGridColor);
              g.drawLine(gridlo, y, gridhi, y);
              setColor(g, FOREGROUND_COLOR_INDEX);
            }
            final String snum = num >= 1 ? yTicInfo.mLabelFormatter.format(num) : "" + num;
            //System.err.println("Y: " + num + " -> " + snum);
            g.drawString(snum, (whichTic == Edge.ALTERNATE) ? (sxhi + 4) : (sxlo - getTextWidth(g, snum) - 2), y + tHeight / 2 - 2);
          }
        }
      } else {
        for (int k = yTicInfo.mStart; k <= yTicInfo.mEnd; k++) {
          final float num = yTicInfo.mTic * k;

          final int y = (int) mapping.worldToScreen(num);

          if (y >= syhi && y <= sylo) {
            if ((whichTic == Edge.ALTERNATE) || !graph.uses(Axis.Y, Edge.ALTERNATE)) {
              g.drawLine(sxhi, y, sxhi - 4, y);
            }
            if ((whichTic == Edge.MAIN) || !graph.uses(Axis.Y, Edge.MAIN)) {
              g.drawLine(sxlo, y, sxlo + 4, y);
            }
            if (graph.isGrid(Axis.Y, whichTic)) {
              g.setColor(mGridColor);
              g.drawLine(gridlo, y, gridhi, y);
              setColor(g, FOREGROUND_COLOR_INDEX);
            }

            final String snum = yTicInfo.mLabelFormatter.format(num);
            g.drawString(snum, (whichTic == Edge.ALTERNATE) ? (sxhi + 4) : (sxlo - getTextWidth(g, snum) - 2), y + tHeight / 2 - 2);
          }
        }

        if (yTicInfo.mMinorTic > 0.0f) {
          for (int k = yTicInfo.mMinorStart; k <= yTicInfo.mMinorEnd; k++) {
            final float num = yTicInfo.mMinorTic * k;
            final int y = (int) mapping.worldToScreen(num);
            if (y >= syhi && y <= sylo) {
              if ((whichTic == Edge.ALTERNATE) || !graph.uses(Axis.Y, Edge.ALTERNATE)) {
                g.drawLine(sxhi, y, sxhi - 2, y);
              }
              if ((whichTic == Edge.MAIN) || !graph.uses(Axis.Y, Edge.MAIN)) {
                g.drawLine(sxlo, y, sxlo + 2, y);
              }
            }
          }
        }
      }
    }
  }


  private void drawXTics(Graph2D graph, Graphics g, Edge whichTic, TicInfo xTicInfo, Mapping mapping, Screen s) {
    final int sxlo = s.getXLo();
    final int sxhi = s.getXHi();
    final int sylo = s.getYLo();
    final int syhi = s.getYHi();
    if (graph.uses(Axis.X, whichTic) && graph.isShowTics(Axis.X, whichTic)) {
      setColor(g, FOREGROUND_COLOR_INDEX);
      final FontMetrics fm = g.getFontMetrics();
      final int tHeight = fm.getHeight();
      xTicInfo.setNumDecimalDigits(xTicInfo.mTic);
      int gridlo = (whichTic == Edge.MAIN) || !graph.uses(Axis.X, Edge.MAIN) ? sylo - 4 : sylo;
      int gridhi = (whichTic == Edge.ALTERNATE) || !graph.uses(Axis.X, Edge.ALTERNATE) ? syhi + 4 : syhi;

      if (graph.isLogScale(Axis.X, whichTic)) {
        // log scale...
        final float start = (float) PlotUtils.floor10(graph.getLo(Axis.X, whichTic));
        final float end = (float) PlotUtils.ceil10(graph.getHi(Axis.X, whichTic));
        //System.err.println("min = " + start + ", max = " + end);
        for (int i = 1; i <= (int) (end / start); i *= 10) {
          for (int j = 1; j < 10 && i * j <= xTicInfo.mEnd; j++) {
            final float num = start * i * j;
            final int x = (int) mapping.worldToScreen(num);
            if (x >= sxlo && x <= sxhi) {
              if ((whichTic == Edge.MAIN) || !graph.uses(Axis.X, Edge.MAIN)) {
                g.drawLine(x, sylo, x, sylo - (j == 1 ? 4 : 2));
              }
              if ((whichTic == Edge.ALTERNATE) || !graph.uses(Axis.X, Edge.ALTERNATE)) {
                g.drawLine(x, syhi, x, syhi + (j == 1 ? 4 : 2));
              }
            }
          }
          final float num = start * i;
          final int x = (int) mapping.worldToScreen(num);
          if (x >= sxlo && x <= sxhi) {
            if (graph.isGrid(Axis.X, whichTic)) {
              g.setColor(mGridColor);
              g.drawLine(x, gridlo, x, gridhi);
              setColor(g, FOREGROUND_COLOR_INDEX);
            }
            final String snum = num >= 1 ? xTicInfo.mLabelFormatter.format(num) : "" + num;
            //System.err.println("X: " + num + " -> " + snum);
            g.drawString(snum, x - getTextWidth(g, snum) / 2, (whichTic == Edge.MAIN) ? (sylo + tHeight) : (syhi - tHeight / 2));
          }
        }
      } else {
        for (int k = xTicInfo.mStart; k <= xTicInfo.mEnd; k++) {
          final float num = xTicInfo.mTic * k;
          final int x = (int) mapping.worldToScreen(num);

          if (x >= sxlo && x <= sxhi) {
            if ((whichTic == Edge.MAIN) || !graph.uses(Axis.X, Edge.MAIN)) {
              g.drawLine(x, sylo, x, sylo - 4);
            }
            if ((whichTic == Edge.ALTERNATE) || !graph.uses(Axis.X, Edge.ALTERNATE)) {
              g.drawLine(x, syhi, x, syhi + 4);
            }
            if (graph.isGrid(Axis.X, whichTic)) {
              g.setColor(mGridColor);
              g.drawLine(x, gridlo, x, gridhi);
              setColor(g, FOREGROUND_COLOR_INDEX);
            }

            final String snum = xTicInfo.mLabelFormatter.format(num);
            final String[] nums = snum.split("\n");
            for (int i = 0; i < nums.length; i++) {
              final String snum2 = nums[i];
              g.drawString(snum2, x - getTextWidth(g, snum2) / 2, (whichTic == Edge.MAIN) ? (sylo + tHeight + i * tHeight) : (syhi - tHeight / 2 - i * tHeight));
            }
          }
        }

        if (xTicInfo.mMinorTic > 0.0f) {
          for (int k = xTicInfo.mMinorStart; k <= xTicInfo.mMinorEnd; k++) {
            final float num = xTicInfo.mMinorTic * k;
            final int x = (int) mapping.worldToScreen(num);

            if (x >= sxlo && x <= sxhi) {
              if ((whichTic == Edge.MAIN) || !graph.uses(Axis.X, Edge.MAIN)) {
                g.drawLine(x, sylo, x, sylo - 2);
              }
              if ((whichTic == Edge.ALTERNATE) || !graph.uses(Axis.X, Edge.ALTERNATE)) {
                g.drawLine(x, syhi, x, syhi + 2);
              }
            }
          }
        }
      }
    }
  }

  @Override
  protected void drawGraphLine(Object canvas, GraphLine line, Mapping convertX, Mapping convertY) {
    final BasicStroke stroke = (BasicStroke) ((Graphics2D) canvas).getStroke();
    if (line.getStyle() == LineStyle.DASHES) {
      ((Graphics2D) canvas).setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit(), new float[] {5.0f, 5.0f}, 0.0f));
    } else if (line.getStyle() == LineStyle.DOTS) {
      ((Graphics2D) canvas).setStroke(new BasicStroke(stroke.getLineWidth(), stroke.getEndCap(), stroke.getLineJoin(), stroke.getMiterLimit(), new float[] {1.0f, 5.0f}, 0.0f));
    }
    super.drawGraphLine(canvas, line, convertX, convertY);
    ((Graphics2D) canvas).setStroke(stroke);
  }

  private int calcKeyX(Graph2D graph, int sxlo, int sxhi, int keyWidth) {
    int keyX;
    if (graph.getKeyVerticalPosition() == KeyPosition.BELOW) {
      keyX = 0;
    } else {
      final KeyPosition position = graph.getKeyHorizontalPosition();
      if (position == KeyPosition.OUTSIDE) {
        keyX = sxhi + 2;
      } else if (position == KeyPosition.LEFT) {
        keyX = sxlo + 2;
      } else if (position == KeyPosition.CENTER) {
        keyX = (sxhi + sxlo - keyWidth) / 2;
      } else { // assume RIGHT by default
        keyX = sxhi - keyWidth - 10;
      }
    }
    return keyX;
  }

  private int calcKeyY(Graph2D graph, int sylo, int syhi, int keyHeight) {
    int keyY;
    final KeyPosition position = graph.getKeyVerticalPosition();
    if (position == KeyPosition.BOTTOM) {
      keyY = sylo - keyHeight - 2;
    } else if (position == KeyPosition.CENTER) {
      keyY = (syhi + sylo - keyHeight) / 2;
    } else if (position == KeyPosition.BELOW) {
      keyY = sylo;
    } else { // assume TOP by default
      keyY = syhi + 2;
    }
    return keyY;
  }

  private void drawKey(Graph2D graph, Graphics g, int offsetX, int offsetY, int screenWidth, int screenHeight, int sxlo, int sylo, int sxhi, int syhi) {
    if (graph.isBorder() && graph.isShowKey()) {
      setClip(g, offsetX, offsetY, screenWidth, screenHeight);
      final String keyTitle = graph.getKeyTitle();
      final int tHeight = getTextHeight(g, "A");
      final int keyWidth = calculateKeyWidth(g, graph);
      final int keyX = calcKeyX(graph, sxlo, sxhi, keyWidth);
      final int keyHeight = calculateKeyHeight(g, graph, screenWidth);
      final int keyY = calcKeyY(graph, sylo, syhi, keyHeight);
      if (keyTitle != null && keyTitle.length() != 0) {
        setColor(g, FOREGROUND_COLOR_INDEX);
        final int yy = keyY + tHeight;
        g.drawString(keyTitle, keyX + 5, yy);
      }
      final int cols = keyWidth == 0 ? 0 : Math.max(1, screenWidth / keyWidth);
      final int rows = keyHeight / tHeight;
      final Plot2D[] plots = graph.getPlots();
      final int keyLineWidth = getKeyLineWidth(g);
      int j = 0;
      for (int c = 0; c < cols; c++) {
        int r = (c == 0) ? ((keyTitle != null && keyTitle.length() != 0) ? 1 : 0) : 0;
        for (; r < rows && j < plots.length; r++) {
          String dtitle = null;
          Plot2D plot = null;
          while (j < plots.length && dtitle == null) {
            setPointIndex(j);
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
                || (plot instanceof FillablePlot2D && ((FillablePlot2D) plot).getFill() == FillStyle.PATTERN)) {
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
            final FillStyle doFill = (plot instanceof FillablePlot2D) ? ((FillablePlot2D) plot).getFill() : FillStyle.NONE;
            if (doFill == FillStyle.PATTERN) {
              setPattern(g, plot.getColor());
            }
            final boolean doBorder = (plot instanceof FillablePlot2D) && ((FillablePlot2D) plot).isBorder();
            Stroke s = null;
            if (plot.getLineWidth() > 1) {
              s = ((Graphics2D) g).getStroke();
              ((Graphics2D) g).setStroke(new BasicStroke(plot.getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            }
            final int keyX5 = xx + 5;
            if (plot instanceof PointPlot2D) {
              final PointPlot2D lplot = (PointPlot2D) plot;
              final boolean doLines = lplot.isLines();
              final boolean doPoints = lplot.isPoints();
              if (doFill != FillStyle.NONE) {
                final Polygon polygon = new Polygon();
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
                if (!doPoints && !doLines) {
                  g.drawLine(keyX5 + keyLineWidth / 2, yy, keyX5 + keyLineWidth / 2, yy);
                }
              }
            } else if (plot instanceof ArrowPlot2D) {
              final ArrowPlot2D aplot = (ArrowPlot2D) plot;
              g.drawLine(keyX5, yy, keyX5 + keyLineWidth, yy);
              final Poly p = arrowHead(keyX5, yy, keyX5 + keyLineWidth, yy, aplot.getHeadWidth(), aplot.getHeadHeight(), aplot.getHeadType());
              final int[] xs = p.getXs();
              final int[] ys = p.getYs();
              fillPolygon(g, xs, ys);
              drawPolygon(g, xs, ys);
            } else if (plot instanceof BWPlot2D) {
              g.drawLine(keyX5, yy, keyX5 + keyLineWidth, yy);
              g.drawRect(keyX5 + keyLineWidth / 2, yy, 0, 0);
            } else if (plot instanceof CurvePlot2D) {
              if (doFill != FillStyle.NONE) {
                g.fillArc(keyX5, yy - tHeight / 4, keyLineWidth, tHeight - 2, 0, 180);
                if (doBorder) {
                  setColor(g, FOREGROUND_COLOR_INDEX);
                  g.drawArc(keyX5, yy - tHeight / 4, keyLineWidth, tHeight - 2, 0, 180);
                }
              } else {
                g.drawArc(keyX5, yy - tHeight / 4, keyLineWidth, tHeight - 2, 0, 180);
              }
            } else if (plot instanceof BoxPlot2D) {
              if (doFill != FillStyle.NONE) {
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
              if (doFill != FillStyle.NONE) {
                fillCircle(g, keyX5 + 1 + keyLineWidth / 2, yy, tHeight - 2);
                if (doBorder) {
                  setColor(g, FOREGROUND_COLOR_INDEX);
                  drawCircle(g, keyX5 + 1 + keyLineWidth / 2, yy, tHeight - 2);
                }
              } else {
                drawCircle(g, keyX5 + 1 + keyLineWidth / 2, yy, tHeight - 2);
              }
            } else if (plot instanceof TextPlot2D) {
              final TextPlot2D tplot = (TextPlot2D) plot;
              final String text = "abc";
              final FontMetrics fm = g.getFontMetrics();
              final int sw = getTextWidth(g, text);
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

  @Override
  protected void drawBWPlot(Object canvas, BWPlot2D bwplot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = bwplot.getData();
    if (points != null && points.length != 0) {
      if (bwplot.getStyle() == BoxWhiskerStyle.JOINED) {
        final Graphics g = (Graphics) canvas;
        final Color old = g.getColor();
        g.setColor(new Color((old.getRed() + 255) / 2, (old.getGreen() + 255) / 2, (old.getBlue() + 255) / 2, 127));
        Polygon polygon = new Polygon();
        for (int i = 0; i < points.length; i++) {
          final BWPoint2D point = (BWPoint2D) points[i];
          final int sptX = (int) convertX.worldToScreen(point.getX());
          final int sptY = (int) convertY.worldToScreen(point.getY(0));
          polygon.addPoint(sptX, sptY);
        }
        for (int i = points.length - 1; i >= 0; i--) {
          final BWPoint2D point = (BWPoint2D) points[i];
          final int sptX = (int) convertX.worldToScreen(point.getX());
          final int sptY = (int) convertY.worldToScreen(point.getY(4));
          polygon.addPoint(sptX, sptY);
        }
        g.fillPolygon(polygon);

        g.setColor(new Color(old.getRed(), old.getGreen(), old.getBlue(), 96));
        polygon = new Polygon();
        for (int i = 0; i < points.length; i++) {
          final BWPoint2D point = (BWPoint2D) points[i];
          final int sptX = (int) convertX.worldToScreen(point.getX());
          final int sptY = (int) convertY.worldToScreen(point.getY(1));
          polygon.addPoint(sptX, sptY);
        }
        for (int i = points.length - 1; i >= 0; i--) {
          final BWPoint2D point = (BWPoint2D) points[i];
          final int sptX = (int) convertX.worldToScreen(point.getX());
          final int sptY = (int) convertY.worldToScreen(point.getY(3));
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
          final int sptX = (int) convertX.worldToScreen(point.getX());
          final int sptY = (int) convertY.worldToScreen(point.getY(2));
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

  private static void drawVerticalString(Graphics g, int x, int y, String text) {
    final Color transparent = new Color(255, 255, 255, 0);
    final FontMetrics fontMetrics = g.getFontMetrics();
    final int width = fontMetrics.stringWidth(text);
    final int height = fontMetrics.getHeight();
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    final Graphics g2 = img.getGraphics();
    g2.setFont(g.getFont());
    g2.setColor(transparent);
    g2.fillRect(0, 0, width, height);
    g2.setColor(g.getColor());
    g2.drawString(text, 0, fontMetrics.getAscent());
    img = rotate(img, width, height);
    g.drawImage(img, x, y, transparent, null);
  }

  private static BufferedImage rotate(BufferedImage img, int width, int height) {
    final BufferedImage img2 = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
    final int[] pixels = new int[width];
    for (int i = 0; i < height; i++) {
      img.getRGB(0, i, width, 1, pixels, 0, width);
      reverse(pixels);
      img2.setRGB(i, 0, 1, width, pixels, 0, 1);
    }
    return img2;
  }

  private static void reverse(int[] a) {
    for (int i = 0; i < a.length / 2; i++) {
      final int j = a.length - 1 - i;
      final int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
    }
  }
}
