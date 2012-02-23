package com.reeltwo.plot.renderer;

import com.reeltwo.plot.Axis2D;
import com.reeltwo.plot.AxisSide;
import com.reeltwo.plot.Box2D;
import com.reeltwo.plot.DefaultFormatter;
import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.LabelFormatter;
import com.reeltwo.plot.Plot2D;

/**
 * Code to render a Graph2D object to a String.
 *
 * @author Richard Littin
 */
public class TextRenderer extends AbstractRenderer {
  private static final char SPACE = ' ';
  private static final char CR = '\n';

  private static final char[] LINE_CHARS = new char[] {'*', '#', '$', '%', '@', '&', '=', 'o'};
  // ANSI escape sequence color codes
  private static final String[] COLORS =
      new String[]{"",
    "31", "32", "34", "33", "35", "36",
    "1;31", "1;32", "1;34", "1;33", "1;35", "1;36",
    "41", "42", "44", "43", "45", "46",
    "1;41", "1;42", "1;44", "1;43", "1;45", "1;46"};


  @Override
  protected int getTextWidth(Object canvas, String text) {
    return text.length();
  }

  @Override
  protected int getTextHeight(Object canvas, String text) {
    return 1;
  }

  @Override
  protected int getTextDescent(Object canvas, String text) {
    return 0;
  }

  @Override
  protected void setColor(Object canvas, int colorIndex) {
    switch (colorIndex) {
    case BACKGROUND_COLOR_INDEX: ((Canvas) canvas).setColor(0);
    break;
    case FOREGROUND_COLOR_INDEX: ((Canvas) canvas).setColorDefault();
    break;
    default:
      ((Canvas) canvas).setColor(colorIndex % COLORS.length);
    };

  }

  @Override
  protected void setClip(Object canvas, int x, int y, int w, int h) {
    ((Canvas) canvas).setClipRectangle(x, y, w, h);
  }

  @Override
  protected void drawString(Object canvas, int x, int y, String text, boolean isVertical) {
    for (int k = 0; k < text.length(); k++) {
      ((Canvas) canvas).putChar(x + k, y, text.charAt(k));
    }
  }

  private char getLineChar() {
    return LINE_CHARS[getPointIndex() % LINE_CHARS.length];
  }

  @Override
  protected void drawPoint(Object canvas, int x, int y) {
    ((Canvas) canvas).putChar(x, y, getLineChar());
  }

  @Override
  protected void drawLine(Object canvas, int x1, int y1, int x2, int y2) {
    ((Canvas) canvas).putChar(x1, y1, x2, y2, getLineChar());
  }

  @Override
  protected void drawRectangle(Object canvas, int x, int y, int w, int h) {
    drawLine(canvas, x, y, x + w, y);
    drawLine(canvas, x, y, x, y + h);
    drawLine(canvas, x, y + h, x + w, y + h);
    drawLine(canvas, x + w, y, x + w, y + h);
  }

  @Override
  protected void fillRectangle(Object canvas, int x, int y, int w, int h) {
    for (int i = 0; i <= h; i++) {
      drawLine(canvas, x, y + i, x + w, y + i);
    }
  }


  @Override
  protected void drawCircle(Object canvas, int x, int y, int diameter) {
    final int radius = diameter / 2;

    drawPoint(canvas, x, y + radius);
    drawPoint(canvas, x, y - radius);
    drawPoint(canvas, x + radius, y);
    drawPoint(canvas, x - radius, y);

    final int r2 = radius * radius;
    int xr = 1;
    int yr = (int) (Math.sqrt(r2 - 1) + 0.5);
    while (xr < yr) {
      drawPoint(canvas, x + xr, y + yr);
      drawPoint(canvas, x + xr, y - yr);
      drawPoint(canvas, x - xr, y + yr);
      drawPoint(canvas, x - xr, y - yr);
      drawPoint(canvas, x + yr, y + xr);
      drawPoint(canvas, x + yr, y - xr);
      drawPoint(canvas, x - yr, y + xr);
      drawPoint(canvas, x - yr, y - xr);
      xr += 1;
      yr = (int) (Math.sqrt(r2 - xr * xr) + 0.5);
    }
    if (xr == yr) {
      drawPoint(canvas, x + xr, y + yr);
      drawPoint(canvas, x + xr, y - yr);
      drawPoint(canvas, x - xr, y + yr);
      drawPoint(canvas, x - xr, y - yr);
    }

  }

  @Override
  protected void fillCircle(Object canvas, int x, int y, int diameter) {
    drawCircle(canvas, x, y, diameter);
  }

  @Override
  protected void drawPolygon(Object canvas, int[] xs, int[] ys) {
    if (xs.length != 0) {
      for (int i = 1; i < xs.length; i++) {
        drawLine(canvas, xs[i - 1], ys[i - 1], xs[i], ys[i]);
      }
      drawLine(canvas, xs[xs.length - 1], ys[ys.length - 1], xs[0], ys[0]);
    }
  }

  @Override
  protected void fillPolygon(Object canvas, int[] xs, int[] ys) {
    // todo
    for (int y = 0; y < ((Canvas) canvas).getHeight(); y++) {
      for (int x = 0; x < ((Canvas) canvas).getWidth(); x++) {
        if (inside(xs, ys, x, y)) {
          drawPoint(canvas, x, y);
        }
      }
    }
  }


  /**
   * Renders the givne graph to a string that mimics a screen of
   * <code>screenWidth</code> characters by <code>screenHeight</code>
   * characters.  <code>inColor</code> produces ansi color codes if
   * set.
   *
   * @param graph a <code>Graph2D</code>
   * @param screenWidth screen width
   * @param screenHeight screen height
   * @param inColor render in color
   * @return rendered plot as a string
   */
  public String drawGraph(Graph2D graph, int screenWidth, int screenHeight, boolean inColor) {
    if (graph == null) {
      return null;
    }
    final Canvas canvas = new Canvas(screenWidth, screenHeight);
    final Mapping[] mapping = drawPeriphery(graph, canvas);
    drawData(canvas, graph.getPlots(), mapping);
    drawKey(graph, canvas);
    return canvas.toString(inColor);
  }


  private Mapping[] drawPeriphery(Graph2D graph, Canvas canvas) {
    final Mapping[] mapping = new Mapping[4];
    int sxlo = 0;
    int sxhi = canvas.getWidth() - 1;
    int sylo = canvas.getHeight() - 2;
    int syhi = 0;
    final String title = graph.getTitle();
    if (title.length() != 0) {
      syhi++;
    }
    if (graph.uses(Axis2D.X, AxisSide.ONE) && graph.getLabel(Axis2D.X, AxisSide.ONE).length() > 0) {
      sylo--;
    }
    if ((graph.uses(Axis2D.X, AxisSide.TWO) && graph.getLabel(Axis2D.X, AxisSide.TWO).length() > 0)
        || (graph.uses(Axis2D.Y, AxisSide.ONE) && graph.getLabel(Axis2D.Y, AxisSide.ONE).length() > 0)
        || (graph.uses(Axis2D.Y, AxisSide.TWO) && graph.getLabel(Axis2D.Y, AxisSide.TWO).length() > 0)) {
      syhi++;
    }

    if (graph.uses(Axis2D.X, AxisSide.ONE) && graph.isShowXTics(AxisSide.ONE)) {
      sylo--;
    }
    if (graph.uses(Axis2D.X, AxisSide.TWO) && graph.isShowXTics(AxisSide.TWO)) {
      syhi++;
    }

    canvas.setColorDefault();
    // titles
    final int xstart = canvas.getWidth() / 2 - title.length() / 2;
    for (int i = 0; i < title.length(); i++) {
      canvas.putChar(xstart + i, 0, title.charAt(i));
    }

    if (graph.isBorder()) {
      // Draw labels
      drawLabels(graph, canvas, sxlo, sylo, sxhi, syhi);

      final float xlo = graph.getLo(Axis2D.X, AxisSide.ONE);
      final float xhi = graph.getHi(Axis2D.X, AxisSide.ONE);
      final float ylo = graph.getLo(Axis2D.Y, AxisSide.ONE);
      final float yhi = graph.getHi(Axis2D.Y, AxisSide.ONE);
      final TicInfo yTicInfo = calcYTicSize(graph, AxisSide.ONE, ylo, yhi);
      if (yTicInfo != null) {
        sxlo = yTicInfo.mMaxWidth;
      }

      final float x2lo = graph.getLo(Axis2D.X, AxisSide.TWO);
      final float x2hi = graph.getHi(Axis2D.X, AxisSide.TWO);
      final float y2lo = graph.getLo(Axis2D.Y, AxisSide.TWO);
      final float y2hi = graph.getHi(Axis2D.Y, AxisSide.TWO);
      final TicInfo y2TicInfo = calcYTicSize(graph, AxisSide.TWO, y2lo, y2hi);
      if (y2TicInfo != null) {
        sxhi -= y2TicInfo.mMaxWidth;
      }
      mapping[0] = new Mapping(xlo, xhi, sxlo, sxhi);
      mapping[1] = new Mapping(ylo, yhi, sylo, syhi);
      mapping[2] = new Mapping(x2lo, x2hi, sxlo, sxhi);
      mapping[3] = new Mapping(y2lo, y2hi, sylo, syhi);

      // border
      drawBorder(canvas, sxlo, sylo, sxhi, syhi);

      final Box2D s = new Box2D(sxlo, sylo, sxhi, syhi);
      // scales
      drawYTics(graph, canvas, AxisSide.ONE, yTicInfo, mapping[1], s);
      drawYTics(graph, canvas, AxisSide.TWO, y2TicInfo, mapping[3], s);
      drawXTics(graph, canvas, AxisSide.ONE, mapping[0], xlo, xhi, s);
      drawXTics(graph, canvas, AxisSide.TWO, mapping[2], xlo, xhi, s);
    } else {
      mapping[0] = new Mapping(graph.getLo(Axis2D.X, AxisSide.ONE), graph.getHi(Axis2D.X, AxisSide.ONE), sxlo, sxhi);
      mapping[1] = new Mapping(graph.getLo(Axis2D.Y, AxisSide.ONE), graph.getHi(Axis2D.Y, AxisSide.ONE), sylo, syhi);
      mapping[2] = new Mapping(graph.getLo(Axis2D.X, AxisSide.TWO), graph.getHi(Axis2D.X, AxisSide.TWO), sxlo, sxhi);
      mapping[3] = new Mapping(graph.getLo(Axis2D.Y, AxisSide.TWO), graph.getHi(Axis2D.Y, AxisSide.TWO), sylo, syhi);
    }
    // set clipping rectangle for canvas - to keep inside graph...
    canvas.setClipRectangle(sxlo, syhi, sxhi + 1, sylo + 1);
    return mapping;
  }


  @Override
  protected int calculateKeyWidth(Object canvas, Graph2D graph) {
    int keyWidth = 0;
    if (graph.isShowKey()) {
      final String keyTitle = graph.getKeyTitle();
      if (keyTitle != null) {
        keyWidth += getTextWidth(canvas, keyTitle);
      } else {
        keyWidth += getTextWidth(canvas, "KEY");
      }
    }

    keyWidth += 1; // for ':'

    final Plot2D[] plots = graph.getPlots();
    for (int j = 0; j < plots.length; j++) {
      final Plot2D plot = plots[j];
      final String dtitle = plot.getTitle();
      if (dtitle != null && dtitle.length() != 0
          && plot.getData() != null && plot.getData().length != 0) {
        final int sw = getTextWidth(canvas, dtitle) + 4; // + point and spaces and comma
        keyWidth += sw;
      }
    }
    if (plots.length > 0) {
      keyWidth -= 1; // remove last comma
    }
    return keyWidth;
  }

  @Override
  protected int calculateKeyHeight(Object canvas, Graph2D graph, int screenWidth) {
    return graph.isShowKey() ? getTextHeight(canvas, "A") : 0;
  }

  private void drawKey(Graph2D graph, Canvas canvas) {
    canvas.setColorDefault();
    canvas.setClipRectangle(0, 0, canvas.getWidth(), canvas.getHeight());
    String key = graph.getKeyTitle();
    if (key == null || key.length() == 0) {
      key = "KEY";
    }
    key += ":";
    for (int i = 0; i < key.length(); i++) {
      canvas.putChar(i, canvas.getHeight() - 1, key.charAt(i));
    }

    int offset = key.length() + 1;

    boolean comma = false;
    final Plot2D[] plots = graph.getPlots();
    for (int j = 0; j < plots.length; j++) {
      setPointIndex(j);
      if ((key = plots[j].getTitle()) != null && key.length() != 0) {
        if (comma) {
          setColor(canvas, FOREGROUND_COLOR_INDEX);
          canvas.putChar(offset, canvas.getHeight() - 1, ',');
          offset += 2;
        }
        setColor(canvas, plots[j].getColor());
        drawPoint(canvas, offset, canvas.getHeight() - 1);
        offset += 2;
        for (int i = 0; i < key.length(); i++) {
          canvas.putChar(offset + i, canvas.getHeight() - 1, key.charAt(i));
        }
        offset += key.length();
        comma = true;
      }
    }
  }


  private void drawBorder(Canvas canvas, int sxlo, int sylo, int sxhi, int syhi) {
    for (int i = sxlo + 1; i < sxhi; i++) {
      canvas.putChar(i, sylo, '-');
      canvas.putChar(i, syhi, '-');
    }
    for (int i = syhi + 1; i < sylo; i++) {
      canvas.putChar(sxlo, i, '|');
      canvas.putChar(sxhi, i, '|');
    }
  }


  private void drawYTics(Graph2D graph, Canvas canvas, AxisSide whichTic, TicInfo ticInfo, Mapping mapping, Box2D s) {
    final int sxlo = (int) s.getXLo();
    final int sxhi = (int) s.getXHi();
    final int sylo = (int) s.getYLo();
    final int syhi = (int) s.getYHi();
    if (graph.uses(Axis2D.Y, whichTic) && graph.isShowYTics(whichTic)) {
      ticInfo.setNumDecimalDigits(ticInfo.mTic);
      for (int k = ticInfo.mStart; k <= ticInfo.mEnd; k++) {
        final float num = ticInfo.mTic * k;
        final int y = (int) mapping.worldToScreen(num);

        if (y >= syhi && y <= sylo) {
          final String snum = ticInfo.mLabelFormatter.format(num);
          final int yy = (whichTic == AxisSide.ONE) ? sxlo - snum.length() : sxhi + 1;
          for (int i = 0; i < snum.length(); i++) {
            final char ch = snum.charAt(i);
            canvas.putChar(yy + i, y, ch);
          }
          if (whichTic == AxisSide.ONE) {
            canvas.putChar(sxlo, y, '+');
            if (!graph.uses(Axis2D.Y, AxisSide.TWO)) {
              canvas.putChar(sxhi, y, '+');
            }
          } else {
            canvas.putChar(sxhi, y, '+');
            if (!graph.uses(Axis2D.Y, AxisSide.ONE)) {
              canvas.putChar(sxlo, y, '+');
            }
          }
        }
      }
    }
  }


  private void drawXTics(Graph2D graph, Canvas canvas, AxisSide whichTic, Mapping mapping, float xlo, float xhi, Box2D s) {
    final int sxlo = (int) s.getXLo();
    final int sxhi = (int) s.getXHi();
    final int sylo = (int) s.getYLo();
    final int syhi = (int) s.getYHi();
    // Note: sylo and syhi are swapped in the Box2D object.

    if (graph.uses(Axis2D.X, whichTic) && graph.isShowXTics(whichTic)) {
      final float xtic = graph.getXTic(whichTic);

      final int start = (int) (xlo / xtic);
      final int end = (int) (xhi / xtic);
      final LabelFormatter lf = graph.getTicLabelFormatter(Axis2D.X, whichTic);
      if (lf instanceof DefaultFormatter) {
        ((DefaultFormatter) lf).setNumDecimalDigits(xtic);
      }
      for (int k = start; k <= end; k++) {
        final float num = xtic * k;
        final int x = (int) mapping.worldToScreen(num);

        if (x >= sxlo && x <= sxhi) {
          if (whichTic == AxisSide.ONE) {
            canvas.putChar(x, syhi, '+');
            if (!graph.uses(Axis2D.X, AxisSide.TWO)) {
              canvas.putChar(x, sylo, '+');
            }
          } else {
            canvas.putChar(x, sylo, '+');
            if (!graph.uses(Axis2D.X, AxisSide.ONE)) {
              canvas.putChar(x, syhi, '+');
            }
          }

          final String snum = lf.format(num);

          final int xx = x - snum.length() / 2;
          for (int i = 0; i < snum.length(); i++) {
            final char ch = snum.charAt(i);
            canvas.putChar(xx + i, (whichTic == AxisSide.ONE) ? syhi + 1 : sylo - 1, ch);
          }
        }
      }
    }
  }


  private TicInfo calcYTicSize(Graph2D graph, AxisSide whichTic, float ylo, float yhi) {
    if (graph.uses(Axis2D.Y, whichTic) && graph.isShowYTics(whichTic)) {
      final TicInfo ticInfo = new TicInfo();
      ticInfo.mTic = graph.getYTic(whichTic);
      ticInfo.setNumDecimalDigits(ticInfo.mTic);
      ticInfo.mStart = (int) (ylo / ticInfo.mTic);
      ticInfo.mEnd = (int) (yhi / ticInfo.mTic);
      ticInfo.mMaxWidth = 0;
      ticInfo.mLabelFormatter = graph.getTicLabelFormatter(Axis2D.Y, whichTic);
      for (int k = ticInfo.mStart; k <= ticInfo.mEnd; k++) {
        final float num = ticInfo.mTic * k;
        final String snum = ticInfo.mLabelFormatter.format(num);
        if (snum.length() > ticInfo.mMaxWidth) {
          ticInfo.mMaxWidth = snum.length();
        }
      }
      return ticInfo;
    }
    return null;
  }


  private void drawLabels(Graph2D graph, Canvas canvas, int sxlo, int sylo, int sxhi, int syhi) {
    String ylabel = graph.getLabel(Axis2D.Y, AxisSide.ONE);
    if (graph.uses(Axis2D.Y, AxisSide.ONE) && ylabel.length() > 0) {
      for (int i = 0; i < ylabel.length(); i++) {
        canvas.putChar(i, syhi - 2, ylabel.charAt(i));
      }
    }
    ylabel = graph.getLabel(Axis2D.Y, AxisSide.TWO);
    if (graph.uses(Axis2D.Y, AxisSide.TWO) && ylabel.length() > 1) {
      for (int i = 0; i < ylabel.length(); i++) {
        canvas.putChar(sxhi + 1 - ylabel.length() + i, syhi - 2, ylabel.charAt(i));
      }
    }

    final int centerWidth = (sxhi + 1) / 2;
    String xlabel = graph.getLabel(Axis2D.X, AxisSide.ONE);
    if (graph.uses(Axis2D.X, AxisSide.ONE) && xlabel.length() > 0) {
      final int labelHeight = sylo + 1 + ((graph.uses(Axis2D.X, AxisSide.ONE) && graph.isShowXTics(AxisSide.ONE)) ? 1 : 0);
      final int xstart = centerWidth - xlabel.length() / 2;
      for (int i = 0; i < xlabel.length(); i++) {
        canvas.putChar(xstart + i, labelHeight, xlabel.charAt(i));
      }
    }
    xlabel = graph.getLabel(Axis2D.X, AxisSide.TWO);
    if (graph.uses(Axis2D.X, AxisSide.TWO) && xlabel.length() > 0) {
      final int xstart = centerWidth - xlabel.length() / 2;
      for (int i = 0; i < xlabel.length(); i++) {
        canvas.putChar(xstart + i, syhi - 2, xlabel.charAt(i));
      }
    }
  }


  private static boolean inside(int[] xs, int[] ys, int x, int y) {
    if (xs == null || xs.length <= 2) {
      return false;
    }
    int hits = 0;

    int lastx = xs[xs.length - 1];
    int lasty = ys[ys.length - 1];
    int curx;
    int cury;

    // Walk the edges of the polygon
    for (int i = 0; i < xs.length; lastx = curx, lasty = cury, i++) {
      curx = xs[i];
      cury = ys[i];

      if (cury == lasty) {
        continue;
      }

      int leftx;
      if (curx < lastx) {
        if (x >= lastx) {
          continue;
        }
        leftx = curx;
      } else {
        if (x >= curx) {
          continue;
        }
        leftx = lastx;
      }

      float test1;
      float test2;
      if (cury < lasty) {
        if (y < cury || y >= lasty) {
          continue;
        }
        if (x < leftx) {
          hits++;
          continue;
        }
        test1 = x - curx;
        test2 = y - cury;
      } else {
        if (y < lasty || y >= cury) {
          continue;
        }
        if (x < leftx) {
          hits++;
          continue;
        }
        test1 = x - lastx;
        test2 = y - lasty;
      }
      if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
        hits++;
      }
    }
    return (hits & 1) != 0;
  }


  private static class Canvas {
    private final int mWidth, mHeight;
    private int mClipLeft, mClipRight, mClipTop, mClipBottom;
    private final char[] mCanvas; // grid of characters
    private final byte[] mColors; // grid of colors
    private byte mColor; // current color to use


    Canvas(int width, int height) {
      mWidth = width;
      mHeight = height;
      setClipRectangle(0, 0, width, height);

      mCanvas = new char[mHeight * (mWidth + 1)];
      mColors = new byte[mHeight * (mWidth + 1)];
      int i = 0;
      for (int y = 0; y < mHeight; y++) {
        for (int x = 0; x < mWidth; x++) {
          mColors[i] = 0;
          mCanvas[i++] = SPACE;
        }
        mCanvas[i++] = CR;
      }
    }

    private int getWidth() {
      return mWidth;
    }

    private int getHeight() {
      return mHeight;
    }

    private void setColorDefault() {
      mColor = 0;
    }


    private void setColor(int color) {
      mColor = (byte) color;
    }

    private void setClipRectangle(int left, int top, int right, int bottom) {
      mClipLeft = Math.max(left, 0);
      mClipRight = Math.min(right, mWidth);
      mClipTop = Math.max(top, 0);
      mClipBottom = Math.min(bottom, mHeight);
    }


    private void putChar(int x, int y, char c) {
      if (x >= mClipLeft && x < mClipRight && y >= mClipTop && y < mClipBottom) {
        // in bounds
        mCanvas[y * (mWidth + 1) + x] = c;
        mColors[y * (mWidth + 1) + x] = mColor;
      }
    }
    //private void putChar(Point2D pt,char c) {
    //putChar((int)pt.getX(),(int)pt.getY(),c);
    //}
    //private void putChar(Point2D ptStart, Point2D ptEnd, char c) {
    //putChar((int) ptStart.getX(), (int) ptStart.getY(), (int) ptEnd.getX(), (int) ptEnd.getY(), c);
    //}


    private void putChar(int startX, int startY, int endX, int endY, char c) {
      int sx;
      int ex;
      int sy;
      int ey;

      if (Math.abs(endX - startX) >= Math.abs(endY - startY)) {
        if (startX <= endX) {
          sx = startX;
          sy = startY;
          ex = endX;
          ey = endY;
        } else {
          ex = startX;
          ey = startY;
          sx = endX;
          sy = endY;
        }
      } else {
        if (startY <= endY) {
          sx = startX;
          sy = startY;
          ex = endX;
          ey = endY;
        } else {
          ex = startX;
          ey = startY;
          sx = endX;
          sy = endY;
        }
      }

      if (ex - sx >= ey - sy) {
        final int range = ex - sx;
        if (range == 0) {
          putChar(sx, sy, c);
        } else {
          for (int i = 0; i <= range; i++) {
            putChar(sx + i, sy + (ey - sy) * i / range, c);
          }
        }
      } else {
        final int range = ey - sy;
        if (range == 0) {
          putChar(sx, sy, c);
        } else {
          for (int i = 0; i <= range; i++) {
            putChar(sx + (ex - sx) * i / range, sy + i, c);
          }
        }
      }
      //putChar(sx,sy,'s');
      //putChar(ex,ey,'e');
    }


    //private char getChar(int x, int y) {
    //if (x >= 0 && x <= mWidth && y >= 0 && y <= mHeight) {
    //return mCanvas[y * (mWidth + 1) + x];
    //}
    //return '\0';
    //}
    //private char getChar(Point2D pt) {
    //return getChar((int)pt.getX(),(int)pt.getY());
    //}
    @Override
    public String toString() {
      return toString(false);
    }


    public String toString(boolean inColor) {
      final String cr = System.getProperty("line.separator");
      if (inColor || !cr.equals("" + CR)) {
        final StringBuffer s = new StringBuffer();
        //String lineChars = new String(LINE_CHARS);

        for (int i = 0; i < mCanvas.length; i++) {
          //int index = lineChars.indexOf(mCanvas[i]);
          final int color = mColors[i];
          if (inColor && color > 0) {
            s.append("\033[")
            .append(COLORS[color % COLORS.length])
            .append("m")
            .append(mCanvas[i])
            .append("\033[0m");
          } else {
            if (mCanvas[i] == CR) {
              s.append(cr);
            } else {
              s.append(mCanvas[i]);
            }
          }
        }
        return s.toString();
      }
      return new String(mCanvas);
    }
  }
}
