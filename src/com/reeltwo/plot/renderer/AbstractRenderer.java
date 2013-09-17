package com.reeltwo.plot.renderer;

import java.util.ArrayList;
import java.util.Random;

import com.reeltwo.plot.Arrow2D;
import com.reeltwo.plot.ArrowPlot2D;
import com.reeltwo.plot.ArrowPlot2D.ArrowDirection;
import com.reeltwo.plot.ArrowPlot2D.ArrowHead;
import com.reeltwo.plot.Axis;
import com.reeltwo.plot.Edge;
import com.reeltwo.plot.BWPlot2D;
import com.reeltwo.plot.BWPlot2D.BoxWhiskerStyle;
import com.reeltwo.plot.BWPoint2D;
import com.reeltwo.plot.Box2D;
import com.reeltwo.plot.BoxPlot2D;
import com.reeltwo.plot.Circle2D;
import com.reeltwo.plot.CirclePlot2D;
import com.reeltwo.plot.CurvePlot2D;
import com.reeltwo.plot.Datum2D;
import com.reeltwo.plot.DefaultFormatter;
import com.reeltwo.plot.FillablePlot2D.FillStyle;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.GraphLine;
import com.reeltwo.plot.GraphLine.LineOrientation;
import com.reeltwo.plot.LabelFormatter;
import com.reeltwo.plot.Note2D;
import com.reeltwo.plot.NotePlot2D;
import com.reeltwo.plot.Plot2D;
import com.reeltwo.plot.Point2D;
import com.reeltwo.plot.PointPlot2D;
import com.reeltwo.plot.ScatterPlot2D;
import com.reeltwo.plot.ScatterPoint2D;
import com.reeltwo.plot.TextPlot2D;
import com.reeltwo.plot.TextPoint2D;


/**
 * Provides functions common across all renderers.
 *
 * @author Richard Littin
 */
public abstract class AbstractRenderer {
  protected static final int FOREGROUND_COLOR_INDEX = -1;
  protected static final int BACKGROUND_COLOR_INDEX = -2;

  private int mColorIndex = 0;
  private int mPointIndex = 0;
  private int mLineWidth = 1;
  private Mapping[] mMappings = null;

  /** A small class to hold information about tick spacing. */
  protected static class TicInfo {
    float mTic;
    float mMinorTic;
    int mStart;
    int mEnd;
    int mMinorStart;
    int mMinorEnd;
    int mMaxWidth;
    int mMaxHeight;
    LabelFormatter mLabelFormatter;

    void setNumDecimalDigits(float ticSize) {
      if (mLabelFormatter != null && mLabelFormatter instanceof DefaultFormatter) {
        ((DefaultFormatter) mLabelFormatter).setNumDecimalDigits(ticSize);
      }
    }
  }

  /**
   * Returns an array of screen to world mappings, one for each of the
   * 4 axes.  Mappings can be null if there is no mapping for an
   * axis.  The axis order {@code x0, y0, x1, y1}.
   *
   * @return an array of axis <code>Mapping</code>s
   */
  public Mapping[] getMappings() {
    return mMappings; // an array of Mapping[ x0, y0, x1, y1 ]
  }

  protected void setMappings(Mapping[] mappings) {
    mMappings = mappings;
  }

  /**
   * Returns the width in <code>canvas</code> units of the given
   * <code>text</code>.
   *
   * @param canvas drawing canvas
   * @param text a <code>String</code>
   * @return width in canvas units
   */
  abstract int getTextWidth(Object canvas, String text);

  /**
   * Returns the height in <code>canvas</code> units of the given
   * <code>text</code>.
   *
   * @param canvas drawing canvas
   * @param text a <code>String</code>
   * @return height in canvas units
   */
  abstract int getTextHeight(Object canvas, String text);

  /**
   * Returns the descent in <code>canvas</code> units of the given
   * <code>text</code>.
   *
   * @param canvas drawing canvas
   * @param text a <code>String</code>
   * @return descent in canvas units
   */
  abstract int getTextDescent(Object canvas, String text);

  /**
   * Sets the current drawing color.
   *
   * @param canvas drawing canvas
   * @param colorIndex color index
   */
  protected void setColor(Object canvas, int colorIndex) {
    mColorIndex = colorIndex;
  }

  /**
   * Returns the current drawing color index.
   *
   * @param canvas drawing canvas
   * @return color index
   */
  protected int getColor(Object canvas) {
    return mColorIndex;
  }

  /**
   * Sets the current drawing pattern.
   *
   * @param canvas drawing canvas
   * @param patternIndex pattern index
   */
  protected void setPattern(Object canvas, int patternIndex) {
    setColor(canvas, patternIndex);
  }

  protected void setPointIndex(int pointIndex) {
    mPointIndex = pointIndex;
  }

  protected int getPointIndex() {
    return mPointIndex;
  }

  // drawing primitives - protected
  protected void setLineWidth(Object canvas, int width) {
    mLineWidth = width;
  }

  protected int getLineWidth() {
    return mLineWidth;
  }

  protected abstract void setClip(Object canvas, int x, int y, int w, int h);

  protected abstract void drawString(Object canvas, int x, int y, String text, boolean vertical);
  protected void drawString(Object canvas, int x, int y, String text) {
    drawString(canvas, x, y, text, false);
  }
  protected abstract void drawPoint(Object canvas, int x, int y);
  protected abstract void drawLine(Object canvas, int x1, int y1, int x2, int y2);
  protected void drawHorizontalLine(Object canvas, int x1, int x2, int y) {
    drawLine(canvas, x1, y, x2, y);
  }
  protected void drawVerticalLine(Object canvas, int x, int y1, int y2) {
    drawLine(canvas, x, y1, x, y2);
  }
  protected abstract void drawRectangle(Object canvas, int x, int y, int w, int h);
  protected abstract void fillRectangle(Object canvas, int x, int y, int w, int h);
  protected abstract void drawCircle(Object canvas, int x, int y, int diameter);
  protected abstract void fillCircle(Object canvas, int x, int y, int diameter);
  protected abstract void drawPolygon(Object canvas, int[] xs, int[] ys);
  protected abstract void fillPolygon(Object canvas, int[] xs, int[] ys);

  // methods to help when drawing curves
  private Point2D tangent(int x1, int y1, int x2, int y2) {
    final float m = distance(x1, y1, x2, y2);
    if (m == 0) {
      return new Point2D(0.0f, 0.0f);
    }
    return new Point2D((x2 - x1) / m, (y2 - y1) / m);
  }

  private float distance(Point2D p1, Point2D p2) {
    return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
  }

  private float distance(float x1, float y1, float x2, float y2) {
    final float x = x2 - x1;
    final float y = y2 - y1;
    return (float) Math.sqrt(x * x + y * y);
  }

  private int distanceX(int x1, int x2) {
    return Math.abs(x2 - x1);
  }

  private Point2D bezier(int[] xs, int[] ys, double mu) {
    float x = 0, y = 0;
    double muk = 1;
    double munk = Math.pow(1 - mu, (double) xs.length - 1);

    for (int k = 0; k < xs.length; k++) {
      int nn = xs.length - 1;
      int kn = k;
      int nkn = nn - k;
      double blend = muk * munk;
      muk *= mu;
      munk /= 1 - mu;
      while (nn >= 1) {
        blend *= nn;
        nn--;
        if (kn > 1) {
          blend /= kn;
          kn--;
        }
        if (nkn > 1) {
          blend /= nkn;
          nkn--;
        }
      }
      x += xs[k] * blend;
      y += ys[k] * blend;
    }
    return new Point2D(x, y);
  }

  private Point2D cubicBezier(Point2D p0, Point2D p1, Point2D p2, Point2D p3, double mu) {
    final float cx = 3 * (p1.getX() - p0.getX());
    final float cy = 3 * (p1.getY() - p0.getY());
    final float bx = 3 * (p2.getX() - p1.getX()) - cx;
    final float by = 3 * (p2.getY() - p1.getY()) - cy;
    final float ax = p3.getX() - p0.getX() - cx - bx;
    final float ay = p3.getY() - p0.getY() - cy - by;

    return new Point2D((float) ((((ax * mu + bx) * mu) + cx) * mu + p0.getX()),
        (float) ((((ay * mu + by) * mu) + cy) * mu + p0.getY()));
  }


  private void doCurve(Object canvas, int[] xs, int[] ys, int type, boolean filled) {
    assert xs != null;
    assert ys != null;
    assert xs.length == ys.length;

    final Poly polygon = filled ? new Poly() : null;

    if (type == CurvePlot2D.BSPLINE) {
      final int m = 50;
      int x = 0, y = 0;
      boolean first = true;

      for (int i = 1; i < xs.length - 2; i++) {
        final float xA = xs[i - 1];
        final float xB = xs[i];
        final float xC = xs[i + 1];
        final float xD = xs[i + 2];
        final float yA = ys[i - 1];
        final float yB = ys[i];
        final float yC = ys[i + 1];
        final float yD = ys[i + 2];
        final float a3 = (-xA + 3 * (xB - xC) + xD) / 6;
        final float b3 = (-yA + 3 * (yB - yC) + yD) / 6;
        final float a2 = (xA - 2 * xB + xC) / 2;
        final float b2 = (yA - 2 * yB + yC) / 2;
        final float a1 = (xC - xA) / 2;
        final float b1 = (yC - yA) / 2;
        final float a0 = (xA + 4 * xB + xC) / 6;
        final float b0 = (yA + 4 * yB + yC) / 6;

        for (int j = 0; j <= m; j++) {
          final int x0 = x;
          final int y0 = y;
          final float t = (float) j / (float) m;
          x = (int) (((a3 * t + a2) * t + a1) * t + a0);
          y = (int) (((b3 * t + b2) * t + b1) * t + b0);

          if (filled) {
            polygon.addPoint(x, y);
          } else {
            if (first) {
              first = false;
            } else {
              drawLine(canvas, x0, y0, x, y);
            }
          }
        }
      }
    } else if (type == CurvePlot2D.BEZIER) {
      final int m = 50;
      int x = 0;
      int y = 0;
      boolean first = true;
      for (int j = 0; j < m; j++) {
        final Point2D p = bezier(xs, ys, j / (double) m);
        final int x0 = x;
        final int y0 = y;
        x = (int) p.getX();
        y = (int) p.getY();
        if (filled) {
          polygon.addPoint(x, y);
        } else {
          if (first) {
            first = false;
          } else {
            drawLine(canvas, x0, y0, x, y);
          }
        }
      }

      if (filled) {
        polygon.addPoint(xs[xs.length - 1], ys[ys.length - 1]);
      }
    } else if (type == CurvePlot2D.CUBIC_BEZIER) {
      int x = 0;
      int y = 0;
      boolean first = true;
      for (int i = 1; i < xs.length - 2; i++) {
        final int xim1 = xs[i - 1];
        final int xi = xs[i];
        final int xip1 = xs[i + 1];
        final int xip2 = xs[i + 2];

        final int yim1 = ys[i - 1];
        final int yi = ys[i];
        final int yip1 = ys[i + 1];
        final int yip2 = ys[i + 2];
        final Point2D pi = new Point2D(xi, yi);
        final Point2D pip1 = new Point2D(xip1, yip1);

        // create tangent vector parallel to (pim1, pip1) whose length if min of the X magnitudes of (pi, pim1) and (pi, pip1) divided by 3
        float minMag = distanceX(xi, xip1);
        float mag = distanceX(xi, xim1);
        if (mag != 0 && mag < minMag) { // 0 length - assume at first point
          minMag = mag;
        }
        minMag /= 3.0f;
        Point2D tangent = tangent(xim1, yim1, xip1, yip1);
        final float x1 = xi + minMag * tangent.getX();
        final float y1 = yi + minMag * tangent.getY();
        final Point2D p1 = new Point2D(x1, y1);

        // same thing around pip1
        minMag = distanceX(xip1, xi);
        mag = distanceX(xip1, xip2);
        if (mag != 0 && mag < minMag) {
          minMag = mag;
        }
        minMag /= 3.0f;
        tangent = tangent(xip2, yip2, xi, yi);

        final float x2 = xip1 + minMag * tangent.getX();
        final float y2 = yip1 + minMag * tangent.getY();
        final Point2D p2 = new Point2D(x2, y2);

        final int m2 = Math.max(10, (int) (distance(p1, p2) / 5.0f));
        for (int j = 0; j <= m2; j++) {
          final int x0 = x;
          final int y0 = y;
          final Point2D p = cubicBezier(pi, p1, p2, pip1, j / (double) m2);
          x = (int) p.getX();
          y = (int) p.getY();
          if (filled) {
            // get the next bezier point
            polygon.addPoint(x, y);
          } else {
            if (first) {
              first = false;
            } else {
              drawLine(canvas, x0, y0, x, y);
            }
          }
        }
      }
    }
    if (filled) {
      fillPolygon(canvas, polygon.getXs(), polygon.getYs());
    }
  }

  protected void drawCurve(Object canvas, int[] xs, int[] ys, int type) {
    doCurve(canvas, xs, ys, type, false);
  }

  protected void fillCurve(Object canvas, int[] xs, int[] ys, int type) {
    doCurve(canvas, xs, ys, type, true);
  }

  protected abstract int calculateKeyWidth(Object canvas, Graph2D graph);
  protected abstract int calculateKeyHeight(Object canvas, Graph2D graph, int screenWidth);

  // functions that actually plot the different types of plots
  protected Mapping[] createMappings(Graph2D graph, int sxlo, int sylo, int sxhi, int syhi) {
    final Mapping[] mappings = new Mapping[4]; // x1, y1, x2, y2
    mappings[0] = new Mapping(graph.getLo(Axis.X, Edge.MAIN), graph.getHi(Axis.X, Edge.MAIN), sxlo, sxhi, graph.isLogScale(Axis.X, Edge.MAIN));
    mappings[1] = new Mapping(graph.getLo(Axis.Y, Edge.MAIN), graph.getHi(Axis.Y, Edge.MAIN), sylo, syhi, graph.isLogScale(Axis.Y, Edge.MAIN));
    mappings[2] = new Mapping(graph.getLo(Axis.X, Edge.ALTERNATE), graph.getHi(Axis.X, Edge.ALTERNATE), sxlo, sxhi, graph.isLogScale(Axis.X, Edge.ALTERNATE));
    mappings[3] = new Mapping(graph.getLo(Axis.Y, Edge.ALTERNATE), graph.getHi(Axis.Y, Edge.ALTERNATE), sylo, syhi, graph.isLogScale(Axis.Y, Edge.ALTERNATE));
    return mappings;
  }

  private TicInfo calcXTicInfo(Object canvas, Graph2D graph, Edge whichTic) {
    if (graph.uses(Axis.X, whichTic) && graph.isShowTics(Axis.X, whichTic)) {
      final TicInfo ticInfo = new TicInfo();
      ticInfo.mTic = graph.getTic(Axis.X, whichTic);
      ticInfo.mMinorTic = graph.getMinorTic(Axis.X, whichTic);
      ticInfo.setNumDecimalDigits(ticInfo.mTic);
      ticInfo.mStart = (int) (graph.getLo(Axis.X, whichTic) / ticInfo.mTic);
      ticInfo.mEnd = (int) (graph.getHi(Axis.X, whichTic) / ticInfo.mTic);
      if (ticInfo.mMinorTic > 0.0f) {
        ticInfo.mMinorStart = (int) (graph.getLo(Axis.X, whichTic) / ticInfo.mMinorTic);
        ticInfo.mMinorEnd = (int) (graph.getHi(Axis.X, whichTic) / ticInfo.mMinorTic);
      }

      ticInfo.mLabelFormatter = graph.getTicLabelFormatter(Axis.X, whichTic);

      ticInfo.mMaxWidth = 0;
      ticInfo.mMaxHeight = 0;
      for (int k = ticInfo.mStart; k <= ticInfo.mEnd; k++) {
        final float num = ticInfo.mTic * k;
        final String snum = ticInfo.mLabelFormatter.format(num);
        final String[] nums = snum.split("\n");
        for (int i = 0; i < nums.length; i++) {
          final int width = getTextWidth(canvas, nums[i]);
          if (width > ticInfo.mMaxWidth) {
            ticInfo.mMaxWidth = width;
          }
        }
        final int height = nums.length * getTextHeight(canvas, snum);
        if (height > ticInfo.mMaxHeight) {
          ticInfo.mMaxHeight = height;
        }
      }
      return ticInfo;
    }
    return null;
  }

  private TicInfo calcYTicInfo(Object canvas, Graph2D graph, Edge whichTic) {
    if (graph.uses(Axis.Y, whichTic) && graph.isShowTics(Axis.Y, whichTic)) {
      final TicInfo ticInfo = new TicInfo();
      ticInfo.mTic = graph.getTic(Axis.Y, whichTic);
      ticInfo.setNumDecimalDigits(ticInfo.mTic);
      ticInfo.mStart = (int) (graph.getLo(Axis.Y, whichTic) / ticInfo.mTic);
      ticInfo.mEnd = (int) (graph.getHi(Axis.Y, whichTic) / ticInfo.mTic);

      ticInfo.mLabelFormatter =  graph.getTicLabelFormatter(Axis.Y, whichTic);

      ticInfo.mMaxWidth = 0;
      ticInfo.mMaxHeight = 0;
      for (int k = ticInfo.mStart; k <= ticInfo.mEnd; k++) {
        final float num = ticInfo.mTic * k;
        final String snum = ticInfo.mLabelFormatter.format(num);
        final int width = getTextWidth(canvas, snum);
        if (width > ticInfo.mMaxWidth) {
          ticInfo.mMaxWidth = width;
        }
        final int height = getTextHeight(canvas, snum);
        if (height > ticInfo.mMaxHeight) {
          ticInfo.mMaxHeight = height;
        }
      }
      return ticInfo;
    }
    return null;
  }

  protected TicInfo[] createTicInfos(Object canvas, Graph2D graph) {
    final TicInfo[] ticInfos = new TicInfo[4]; // x1, y1, x2, y2
    ticInfos[0] = calcXTicInfo(canvas, graph, Edge.MAIN);
    ticInfos[1] = calcYTicInfo(canvas, graph, Edge.MAIN);
    ticInfos[2] = calcXTicInfo(canvas, graph, Edge.ALTERNATE);
    ticInfos[3] = calcYTicInfo(canvas, graph, Edge.ALTERNATE);
    return ticInfos;
  }

  protected void drawData(Object canvas, Plot2D[] plots, Mapping[] mapping) {
    int colorIndex = 0;

    for (int j = 0; j < plots.length; j++) {
      final Plot2D plot = plots[j];
      if (!(plot instanceof GraphLine)) {
        final int color = plot.getColor();
        if (color < 0) {
          plot.setColor(colorIndex);
          colorIndex++;
        } else if (color >= colorIndex) {
          colorIndex = color + 1;
        }
      }
    }

    for (int j = 0; j < plots.length; j++) {
      final Plot2D plot = plots[j];
      final Mapping convertX = mapping[2 * (plot.uses(Axis.X, Edge.MAIN) ? 0 : 1)];
      final Mapping convertY = mapping[2 * (plot.uses(Axis.Y, Edge.MAIN) ? 0 : 1) + 1];

      int lineWidth = plot.getLineWidth();
      if (lineWidth < 1) {
        lineWidth = 1;
      }
      setLineWidth(canvas, lineWidth);

      if (plot instanceof GraphLine) {
        drawGraphLine(canvas, (GraphLine) plot, convertX, convertY);
      } else {
        setPointIndex(j);
        if (plot instanceof PointPlot2D) {
          drawPointPlot(canvas, (PointPlot2D) plot, convertX, convertY);
        } else if (plot instanceof ArrowPlot2D) {
          drawArrowPlot(canvas, (ArrowPlot2D) plot, convertX, convertY);
        } else if (plot instanceof BWPlot2D) {
          drawBWPlot(canvas, (BWPlot2D) plot, convertX, convertY);
        } else if (plot instanceof CurvePlot2D) {
          drawCurvePlot(canvas, (CurvePlot2D) plot, convertX, convertY);
        } else if (plot instanceof TextPlot2D) {
          drawTextPlot(canvas, (TextPlot2D) plot, convertX, convertY);
        } else if (plot instanceof ScatterPlot2D) {
          drawScatterPlot(canvas, (ScatterPlot2D) plot, convertX, convertY);
        } else if (plot instanceof BoxPlot2D) {
          drawBoxPlot(canvas, (BoxPlot2D) plot, convertX, convertY);
        } else if (plot instanceof CirclePlot2D) {
          drawCirclePlot(canvas, (CirclePlot2D) plot, convertX, convertY);
        } else if (plot instanceof NotePlot2D) {
          drawNotePlot(canvas, (NotePlot2D) plot, convertX, convertY);
        }
      }
    }
    setLineWidth(canvas, 1);
  }

  protected void drawGraphLine(Object canvas, GraphLine line, Mapping convertX, Mapping convertY) {
    setColor(canvas, FOREGROUND_COLOR_INDEX);
    if (line.getOrientation() == LineOrientation.VERTICAL) {
      final int sptX = (int) convertX.worldToScreen(line.getLocation());
      drawLine(canvas, sptX, (int) convertY.getScreenMin(), sptX, (int) convertY.getScreenMax());
    } else {
      final int sptY = (int) convertY.worldToScreen(line.getLocation());
      drawLine(canvas, (int) convertX.getScreenMin(), sptY, (int) convertX.getScreenMax(), sptY);
    }
  }

  protected void drawPointPlot(Object canvas, PointPlot2D lplot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = lplot.getData();
    if (points != null && points.length != 0) {
      final boolean doPoints = lplot.isPoints();
      final boolean doLines = lplot.isLines();
      final FillStyle doFill = lplot.getFill();
      final boolean doBorder = lplot.isBorder();

      if (doFill == FillStyle.PATTERN) {
        setPattern(canvas, lplot.getColor());
      } else {
        setColor(canvas, lplot.getColor());
      }

      if (doFill != FillStyle.NONE) {
        final Poly polygon = new Poly();
        for (int i = 0; i < points.length; i++) {
          final Point2D point = (Point2D) points[i];
          final int sptX = (int) convertX.worldToScreen(point.getX());
          final int sptY = (int) convertY.worldToScreen(point.getY());
          polygon.addPoint(sptX, sptY);
        }

        fillPolygon(canvas, polygon.getXs(), polygon.getYs());
        if (doBorder) {
          setColor(canvas, FOREGROUND_COLOR_INDEX);
          drawPolygon(canvas, polygon.getXs(), polygon.getYs());
          if (doFill == FillStyle.PATTERN) {
            setPattern(canvas, lplot.getColor());
          } else {
            setColor(canvas, lplot.getColor());
          }
        }
      } else {
        Point2D point = (Point2D) points[0];
        int lastX = (int) convertX.worldToScreen(point.getX());
        int lastY = (int) convertY.worldToScreen(point.getY());
        if (doPoints) {
          drawPoint(canvas, lastX, lastY);
        }
        drawLine(canvas, lastX, lastY, lastX, lastY);
        for (int i = 1; i < points.length; i++) {
          point = (Point2D) points[i];
          final int sptX = (int) convertX.worldToScreen(point.getX());
          final int sptY = (int) convertY.worldToScreen(point.getY());
          if (sptX != lastX || sptY != lastY) {
            if (doLines) {
              drawLine(canvas, lastX, lastY, sptX, sptY);
            }
            if (doPoints) {
              drawPoint(canvas, sptX, sptY);
            }
            drawLine(canvas, sptX, sptY, sptX, sptY);

            lastX = sptX;
            lastY = sptY;
          }
        }
      }
    }
  }

  protected Poly arrowHead(int x1, int y1, int x2, int y2, float w, float h, ArrowHead type) {
    final Poly poly = new Poly();

    if (x1 == x2 && y1 == y2) { // just do a diamond
      final int t = (int) ((w + h) / 4.0f);
      poly.addPoint(x2 + t, y2);
      poly.addPoint(x2, y2 + t);
      poly.addPoint(x2 - t, y2);
      poly.addPoint(x2, y2 - t);
      poly.addPoint(x2 + t, y2);
    } else {
      final int xdiff = x2 - x1;
      final int ydiff = y2 - y1;
      final float length = (float) Math.sqrt(xdiff * xdiff + ydiff * ydiff);
      final float xh = x2 - h * xdiff / length;
      final float yh = y2 - h * ydiff / length;

      final float normX1 = ydiff / length;
      final float normY1 = -xdiff / length;

      final float w2 = w / 2.0f;

      poly.addPoint(x2, y2);
      poly.addPoint((int) (w2 * normX1 + xh), (int) (w2 * normY1 + yh));

      if (type != ArrowHead.TRIANGLE) {
        final float h2 = (type.ordinal() - 1) * h / 2;
        poly.addPoint((int) (x2 - h2 * xdiff / length), (int) (y2 - h2 * ydiff / length));
      }

      poly.addPoint((int) (-w2 * normX1 + xh), (int) (-w2 * normY1 + yh));
      poly.addPoint(x2, y2);
    }
    return poly;
  }

  protected void drawArrowPlot(Object canvas, ArrowPlot2D aplot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = aplot.getData();
    if (points != null && points.length != 0) {
      final ArrowHead head = aplot.getHeadType();
      final ArrowDirection direction = aplot.getDirection();
      final float width = aplot.getHeadWidth();
      final float height = aplot.getHeadHeight();

      setColor(canvas, aplot.getColor());

      for (int i = 0; i < points.length; i++) {
        final Arrow2D arrow = (Arrow2D) points[i];
        final int sptX1 = (int) convertX.worldToScreen(arrow.getX1());
        final int sptY1 = (int) convertY.worldToScreen(arrow.getY1());
        final int sptX2 = (int) convertX.worldToScreen(arrow.getX2());
        final int sptY2 = (int) convertY.worldToScreen(arrow.getY2());

        drawLine(canvas, sptX1, sptY1, sptX2, sptY2);

        if (direction == ArrowDirection.FORWARD || direction == ArrowDirection.BOTH) {
          final Poly polygon = arrowHead(sptX1, sptY1, sptX2, sptY2, width, height, head);
          final int[] xs = polygon.getXs();
          final int[] ys = polygon.getYs();
          fillPolygon(canvas, xs, ys);
          drawPolygon(canvas, xs, ys);
        }
        if (direction == ArrowDirection.REVERSE || direction == ArrowDirection.BOTH) {
          final Poly polygon = arrowHead(sptX2, sptY2, sptX1, sptY1, width, height, head);
          final int[] xs = polygon.getXs();
          final int[] ys = polygon.getYs();
          fillPolygon(canvas, xs, ys);
          drawPolygon(canvas, xs, ys);
        }
      }
    }
  }

  protected void drawBWPlot(Object canvas, BWPlot2D bwplot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = bwplot.getData();
    if (points != null && points.length != 0) {
      setColor(canvas, bwplot.getColor());
      if (bwplot.getStyle() == BoxWhiskerStyle.STANDARD) {
        final int width = bwplot.getWidth();
        for (int i = 0; i < points.length; i++) {
          final BWPoint2D point = (BWPoint2D) points[i];
          final int sptX = (int) convertX.worldToScreen(point.getX());
          int sptY1 = (int) convertY.worldToScreen(point.getY(0));
          int sptY2 = (int) convertY.worldToScreen(point.getY(1));
          drawLine(canvas, sptX, sptY1, sptX, sptY2);

          sptY1 = (int) convertY.worldToScreen(point.getY(3));
          drawRectangle(canvas, sptX - width / 2, sptY1, width, sptY2 - sptY1);

          sptY2 = (int) convertY.worldToScreen(point.getY(4));
          drawLine(canvas, sptX, sptY1, sptX, sptY2);

          sptY1 = (int) convertY.worldToScreen(point.getY(2));
          drawLine(canvas, sptX - width / 2, sptY1, sptX + width / 2, sptY1);
        }
      } else if (bwplot.getStyle() == BoxWhiskerStyle.MINIMAL) {
        for (int i = 0; i < points.length; i++) {
          final BWPoint2D point = (BWPoint2D) points[i];
          final int sptX = (int) convertX.worldToScreen(point.getX());
          int sptY1 = (int) convertY.worldToScreen(point.getY(0));
          int sptY2 = (int) convertY.worldToScreen(point.getY(1));
          drawLine(canvas, sptX, sptY1, sptX, sptY2);

          sptY1 = (int) convertY.worldToScreen(point.getY(3));
          sptY2 = (int) convertY.worldToScreen(point.getY(4));
          drawLine(canvas, sptX, sptY1, sptX, sptY2);

          sptY1 = (int) convertY.worldToScreen(point.getY(2));
          drawPoint(canvas, sptX, sptY1);
        }
        // } else if (bwplot.getType() == BWPlot2D.JOINED) {
        // can't be handled in general???
      }
    }
  }

  protected void drawTextPlot(Object canvas, TextPlot2D tplot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = tplot.getData();
    final int tHeight = getTextHeight(canvas, "A");

    int halign = 0;
    switch (tplot.getHorizontalAlignment()) {
    case LEFT: halign = 0; break;
    case CENTER: halign = 1; break;
    case RIGHT: halign = 2; break;
    default:
      throw new IllegalStateException("Invalid halign");
    }

    int valign = 0;
    final int descent = getTextDescent(canvas, "A");
    switch (tplot.getVerticalAlignment()) {
    case CENTER: valign = tHeight / 2 - descent; break;
    case BASELINE: valign = 0; break;
    case TOP: valign = tHeight - descent; break;
    case BOTTOM: valign = -descent; break;
    default:
      throw new IllegalStateException("Invalid valign");
    }

    final int color = tplot.isUseFGColor() ? FOREGROUND_COLOR_INDEX : tplot.getColor();
    setColor(canvas, color);

    if (points != null && points.length != 0) {
      for (int i = 0; i < points.length; i++) {
        final TextPoint2D point = (TextPoint2D) points[i];
        final String text = point.getText();
        final int sptX = (int) convertX.worldToScreen(point.getX());
        final int sptY = (int) convertY.worldToScreen(point.getY());
        final int sw = getTextWidth(canvas, text);

        if (tplot.isInvert()) {
          setColor(canvas, color);
          fillRectangle(canvas, (int) (sptX - halign * sw / 2.0f), sptY + valign - tHeight + descent, sw, tHeight);
          setColor(canvas, BACKGROUND_COLOR_INDEX);
        }
        if (tplot.isVertical()) {
          drawString(canvas, sptX - tHeight / 2, sptY - halign * sw, text, true);
        } else {
          drawString(canvas, (int) (sptX - halign * sw / 2.0f), sptY + valign, text);
        }
      }
    }
  }

  protected void drawScatterPlot(Object canvas, ScatterPlot2D splot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = splot.getData();
    if (points != null && points.length != 0) {
      setColor(canvas, splot.getColor());
      final float scatterFactor = Math.abs(splot.getScatterFactor()) + 1;
      final Random random = new Random();
      for (int i = 0; i < points.length; i++) {
        final ScatterPoint2D point = (ScatterPoint2D) points[i];

        final float sptX = convertX.worldToScreen(point.getX());
        final float sptY = convertY.worldToScreen(point.getY());

        if (point.getNumberOfPoints() > 0) {
          final float sf = (float) (scatterFactor * Math.log(point.getNumberOfPoints()));
          for (int p = 0; p < point.getNumberOfPoints(); p++) {
            final double radius = random.nextGaussian() * sf;
            final double angle = random.nextFloat() * (2 * Math.PI);
            final float xx = (float) (radius * Math.sin(angle));
            final float yy = (float) (radius * Math.cos(angle));
            drawRectangle(canvas, (int) (sptX + xx), (int) (sptY + yy), 1, 1);
          }
        }
      }
    }
  }

  protected void drawBoxPlot(Object canvas, BoxPlot2D bplot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = bplot.getData();

    if (points != null && points.length != 0) {
      final FillStyle doFill = bplot.getFill();
      final boolean doBorder = bplot.isBorder();
      if (doFill == FillStyle.PATTERN) {
        setPattern(canvas, bplot.getColor());
      } else {
        setColor(canvas, bplot.getColor());
      }
      for (int i = 0; i < points.length; i++) {
        final Box2D box = (Box2D) points[i];

        final int x = (int) convertX.worldToScreen(box.getLeft());
        final int y = (int) convertY.worldToScreen(box.getTop());
        final int width = (int) convertX.worldToScreen(box.getRight()) - x;
        final int height = (int) convertY.worldToScreen(box.getBottom()) - y;

        if (doFill != FillStyle.NONE) {
          fillRectangle(canvas, x, y, width, height);
          if (doBorder) {
            setColor(canvas, FOREGROUND_COLOR_INDEX);
            drawRectangle(canvas, x, y, width, height);
            if (doFill == FillStyle.PATTERN) {
              setPattern(canvas, bplot.getColor());
            } else {
              setColor(canvas, bplot.getColor());
            }
          }
        } else {
          drawRectangle(canvas, x, y, width, height);
        }
      }
    }
  }

  protected void drawCirclePlot(Object canvas, CirclePlot2D cplot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = cplot.getData();

    if (points != null && points.length != 0) {
      final FillStyle doFill = cplot.getFill();
      final boolean doBorder = cplot.isBorder();

      if (doFill == FillStyle.PATTERN) {
        setPattern(canvas, cplot.getColor());
      } else {
        setColor(canvas, cplot.getColor());
      }

      for (int i = 0; i < points.length; i++) {
        final Circle2D circle = (Circle2D) points[i];
        final float diameter = circle.getDiameter();

        final int x = (int) convertX.worldToScreen(circle.getX());
        final int y = (int) convertY.worldToScreen(circle.getY());

        final int idiameter = (int) diameter + 1;

        if (doFill != FillStyle.NONE) {
          fillCircle(canvas, x, y, idiameter);
          if (doBorder) {
            setColor(canvas, FOREGROUND_COLOR_INDEX);
            drawCircle(canvas, x, y, idiameter);
            if (doFill == FillStyle.PATTERN) {
              setPattern(canvas, cplot.getColor());
            } else {
              setColor(canvas, cplot.getColor());
            }
          }
        } else {
          drawCircle(canvas, x, y, idiameter);
        }
      }
    }
  }

  protected void drawCurvePlot(Object canvas, CurvePlot2D cplot, Mapping convertX, Mapping convertY) {
    final Point2D[] points = (Point2D[]) cplot.getData();

    if (points != null && points.length != 0) {
      final int type = cplot.getType();
      final FillStyle doFill = cplot.getFill();
      final boolean doBorder = cplot.isBorder();

      if (doFill == FillStyle.PATTERN) {
        setPattern(canvas, cplot.getColor());
      } else {
        setColor(canvas, cplot.getColor());
      }

      final int[] xs = new int[points.length];
      final int[] ys = new int[points.length];
      for (int i = 0; i < points.length; i++) {
        xs[i] = (int) convertX.worldToScreen(points[i].getX());
        ys[i] = (int) convertY.worldToScreen(points[i].getY());
      }

      if (doFill != FillStyle.NONE) {
        fillCurve(canvas, xs, ys, type);
        if (doBorder) {
          setColor(canvas, FOREGROUND_COLOR_INDEX);
          drawCurve(canvas, xs, ys, type);
          if (doFill == FillStyle.PATTERN) {
            setPattern(canvas, cplot.getColor());
          } else {
            setColor(canvas, cplot.getColor());
          }
        }
      } else {
        drawCurve(canvas, xs, ys, type);
      }
    }
  }

  protected void drawNotePlot(Object canvas, NotePlot2D nplot, Mapping convertX, Mapping convertY) {
    final Datum2D[] points = nplot.getData();

    if (points != null && points.length != 0) {
      drawBoxPlot(canvas, nplot.getBoxPlot(), convertX, convertY);
      drawArrowPlot(canvas, nplot.getArrowPlot(), convertX, convertY);

      final int tHeight = getTextHeight(canvas, "A");
      final int descent = getTextDescent(canvas, "A");

      setColor(canvas, FOREGROUND_COLOR_INDEX);

      for (int i = 0; i < points.length; i++) {
        final Note2D note = (Note2D) points[i];
        final String[] lines = note.getText().split("\n");

        int maxWidth = 0;
        for (int j = 0; j < lines.length; j++) {
          maxWidth = Math.max(maxWidth, getTextWidth(canvas, lines[j]));
        }

        final int x = (int) convertX.worldToScreen(note.getLeft());
        final int y = (int) convertY.worldToScreen(note.getTop());
        final int width = (int) convertX.worldToScreen(note.getRight()) - x;
        final int height = (int) convertY.worldToScreen(note.getBottom()) - y;

        final int xOffset = x + (width - maxWidth) / 2;
        final int yOffset = y + (height - tHeight * lines.length) / 2 + tHeight - descent;

        for (int j = 0; j < lines.length; j++) {
          drawString(canvas, xOffset, yOffset + tHeight * j, lines[j]);
        }
      }
    }
  }

  // our own special polygon class
  protected static class Poly {
    final ArrayList<Point2D> mPoints = new ArrayList<Point2D>();

    public void addPoint(int x, int y) {
      mPoints.add(new Point2D(x, y));
    }

    public int[] getXs() {
      final int[] xs = new int[mPoints.size()];
      for (int i = 0; i < mPoints.size(); i++) {
        xs[i] = (int) mPoints.get(i).getX();
      }
      return xs;
    }

    public int[] getYs() {
      final int[] ys = new int[mPoints.size()];
      for (int i = 0; i < mPoints.size(); i++) {
        ys[i] = (int) mPoints.get(i).getY();
      }
      return ys;
    }
  }
}
