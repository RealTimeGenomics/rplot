package com.reeltwo.plot;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Structure containing all the attributes of a 2D graph.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class Graph2D implements Cloneable {
  public static final int CENTER = 0;
  public static final int LEFT = 1;
  public static final int RIGHT = 2;
  public static final int OUTSIDE = 3;
  public static final int TOP = 4;
  public static final int BOTTOM = 5;
  public static final int BELOW = 6;

  static final int NUM_X_AXES = 2;
  static final int NUM_Y_AXES = 2;

  /** graph title */
  private String mTitle = "";
  /** key title */
  private String mKeyTitle = "";
  private boolean mShowKey = true;
  private boolean mColoredKey = true; // whether to color key text
  private int mKeyHorizontalPosition = OUTSIDE;
  private int mKeyVerticalPosition = TOP;

  private Axis[] mXAxis;
  private Axis[] mYAxis;

  /** whether to display border lines and labels */
  private boolean mDisplayBorder = true;

  /** list of plots in graph */
  private ArrayList mPlots = new ArrayList();

  /** whether to show a vertical dashed line */
  private boolean mVertLine = false;
  /** where it is positioned */
  private float mVertLinePos = 0.0f;


  /** Default constructor. */
  public Graph2D() {
    mXAxis = new Axis[NUM_X_AXES];
    for (int i = 0; i < mXAxis.length; i++) {
      mXAxis[i] = new Axis();
    }

    mYAxis = new Axis[NUM_Y_AXES];
    for (int i = 0; i < mYAxis.length; i++) {
      mYAxis[i] = new Axis();
    }

    setRanges();
  }


  /**
   * Sets the graph's title.
   *
   * @param title some text
   */
  public void setTitle(String title) {
    if (title == null) {
      mTitle = "";
    } else {
      mTitle = title;
    }
  }


  /**
   * Returns the graph's title.
   *
   * @return some text
   */
  public String getTitle() {
    return mTitle;
  }


  /**
   * Sets the graph's key title.
   *
   * @param title some text
   */
  public void setKeyTitle(String title) {
    if (title == null) {
      mKeyTitle = "";
    } else {
      mKeyTitle = title;
    }
  }


  /**
   * Returns the graph's key title.
   *
   * @return some text
   */
  public String getKeyTitle() {
    return mKeyTitle;
  }


  public void setShowKey(boolean flag) {
    mShowKey = flag;
  }


  public boolean getShowKey() {
    return mShowKey;
  }

  public void setColoredKey(final boolean flag) {
    mColoredKey = flag;
  }
  
  
  public boolean getColoredKey() {
    return mColoredKey;
  }

  /**
   * Sets the horizontal position of the graph key.  Valid position
   * values are <code>LEFT</code>, <code>CENTER</code>,
   * <code>RIGHT</code> and <code>OUTSIDE</code> (the default).
   *
   * @param position a position value
   * @exception IllegalArgumentException if an invalid horizontal
   * position value is given
   */
  public void setKeyHorizontalPosition(int position) {
    switch (position) {
    case CENTER:
    case LEFT:
    case RIGHT:
    case OUTSIDE:
      mKeyHorizontalPosition = position;
      break;
    default:
      throw new IllegalArgumentException("Invalid horizontal position: " + position);
    }
  }

  /**
   * Returns the horizontal position of the key
   *
   * @return a position
   */
  public int getKeyHorizontalPosition() {
    return mKeyHorizontalPosition;
  }


  /**
   * Sets the vertical position of the graph key.  Valid position
   * values are <code>TOP</code> (the default), <code>CENTER</code>,
   * <code>BOTTOM</code> and <code>BELOW</code>.
   *
   * @param position a position value
   * @exception IllegalArgumentException if an invalid vertical
   * position value is given
   */
  public void setKeyVerticalPosition(int position) {
    switch (position) {
    case CENTER:
    case TOP:
    case BOTTOM:
    case BELOW:
      mKeyVerticalPosition = position;
      break;
    default:
      throw new IllegalArgumentException("Invalid vertical position: " + position);
    }
  }

  /**
   * Returns the vertical position of the key
   *
   * @return a position
   */
  public int getKeyVerticalPosition() {
    return mKeyVerticalPosition;
  }


  private Axis getXAxis(int i) {
    if (i < 0 || i > mXAxis.length) {
      throw new IllegalArgumentException("X axis index out of range.");
    }
    return mXAxis[i];
  }


  /**
   * Sets the graph's x axis label.
   *
   * @param label some text
   * @param i The new xLabel value.
   */
  public void setXLabel(int i, String label) {
    if (label == null) {
      getXAxis(i).mTitle = "";
    } else {
      getXAxis(i).mTitle = label;
    }
  }


  public void setXLabel(String label) {
    setXLabel(0, label);
  }


  /**
   * Returns the graph's x axis label.
   *
   * @param i TODO Description.
   * @return some text
   */
  public String getXLabel(int i) {
    return getXAxis(i).mTitle;
  }


  private Axis getYAxis(int i) {
    if (i < 0 || i > mYAxis.length) {
      throw new IllegalArgumentException("Y axis index out of range.");
    }
    return mYAxis[i];
  }


  /**
   * Sets the graph's y axis label.
   *
   * @param label some text
   * @param i The new yLabel value.
   */
  public void setYLabel(int i, String label) {
    if (label == null) {
      getYAxis(i).mTitle = "";
    } else {
      getYAxis(i).mTitle = label;
    }
  }


  public void setYLabel(String label) {
    setYLabel(0, label);
  }


  /**
   * Returns the graph's y axis label.
   *
   * @param i TODO Description.
   * @return some text
   */
  public String getYLabel(int i) {
    return getYAxis(i).mTitle;
  }


  /**
   * Sets whether or not to display a grid on the graph.
   *
   * @param flag whether to show grid
   */
  public void setGrid(boolean flag) {
    for (int i = 0; i < mXAxis.length; i++) {
      setXGrid(i, flag);
    }
    for (int i = 0; i < mYAxis.length; i++) {
      setYGrid(i, flag);
    }
  }


  /**
   * Sets whether or not to display a grid on the graph.
   *
   * @param flag whether to show grid
   * @param i The new xGrid value.
   */
  public void setXGrid(int i, boolean flag) {
    getXAxis(i).mShowGrid = flag;
  }


  /**
   * Returns whether or not to display a grid on the graph.
   *
   * @param i TODO Description.
   * @return whether to show grid
   */
  public boolean getXGrid(int i) {
    return getXAxis(i).mShowGrid;
  }


  /**
   * Sets whether or not to display a grid on the graph.
   *
   * @param flag whether to show grid
   * @param i The new yGrid value.
   */
  public void setYGrid(int i, boolean flag) {
    getYAxis(i).mShowGrid = flag;
  }


  /**
   * Returns whether or not to display a grid on the graph.
   *
   * @param i TODO Description.
   * @return whether to show grid
   */
  public boolean getYGrid(int i) {
    return getYAxis(i).mShowGrid;
  }


  /**
   * Sets whether or not to display border lines and labels on the
   * graph.
   *
   * @param flag whether to show border
   */
  public void setBorder(boolean flag) {
    mDisplayBorder = flag;
  }


  /**
   * Returns whether or not to display border lines and labels on the
   * graph.
   *
   * @return whether to show border
   */
  public boolean getBorder() {
    return mDisplayBorder;
  }


  /**
   * Sets the automatic calculation of X axis ranges.
   *
   * @param i The new autoScaleX value.
   */
  public void setAutoScaleX(int i) {
    getXAxis(i).mLoAuto = true;
    getXAxis(i).mHiAuto = true;
    setRanges();
  }


  /**
   * Sets the x axis to range from <code>lo</code> to hi. If <code>hi</code>
   * < <code>lo</code> the meaning of <code>lo</code> and <code>hi</code>
   * is swapped.
   *
   * @param lo lower end of range.
   * @param hi upper end of range.
   * @param i The new xRange value.
   */
  public void setXRange(int i, float lo, float hi) {
    if (lo <= hi) {
      getXAxis(i).mLo = lo;
      getXAxis(i).mHi = hi;
    } else {
      getXAxis(i).mLo = hi;
      getXAxis(i).mHi = lo;
    }
    getXAxis(i).mLoAuto = false;
    getXAxis(i).mHiAuto = false;
    setRanges();
  }


  public void setXRange(float lo, float hi) {
    setXRange(0, lo, hi);
  }


  /**
   * Sets the low end of the x axis range.
   *
   * @param x low end of range.
   * @param i The new xLo value.
   */
  public void setXLo(int i, float x) {
    if (x <= getXHi(i)) {
      getXAxis(i).mLo = x;
      getXAxis(i).mLoAuto = false;
      setRanges();
    }
  }


  /**
   * Returns the low end of the x axis range.
   *
   * @param i TODO Description.
   * @return low end of range.
   */
  public float getXLo(int i) {
    return getXAxis(i).mLo;
  }


  /**
   * Sets the high end of the x axis range.
   *
   * @param x high end of range.
   * @param i The new xHi value.
   */
  public void setXHi(int i, float x) {
    if (x >= getXLo(i)) {
      getXAxis(i).mHi = x;
      getXAxis(i).mHiAuto = false;
      setRanges();
    }
  }


  /**
   * Returns the high end of the x axis range.
   *
   * @param i TODO Description.
   * @return high end of range.
   */
  public float getXHi(int i) {
    return getXAxis(i).mHi;
  }


  /**
   * Sets the automatic calculation of Y axis ranges.
   *
   * @param i The new autoScaleY value.
   */
  public void setAutoScaleY(int i) {
    getYAxis(i).mLoAuto = true;
    getYAxis(i).mHiAuto = true;
    setRanges();
  }


  /**
   * Sets the x axis to range from <code>lo</code> to hi. If <code>hi</code>
   * < <code>lo</code> the meaning of <code>lo</code> and <code>hi</code>
   * is swapped.
   *
   * @param lo lower end of range.
   * @param hi upper end of range.
   * @param i The new yRange value.
   */
  public void setYRange(int i, float lo, float hi) {
    if (lo <= hi) {
      getYAxis(i).mLo = lo;
      getYAxis(i).mHi = hi;
    } else {
      getYAxis(i).mLo = hi;
      getYAxis(i).mHi = lo;
    }
    getYAxis(i).mLoAuto = false;
    getYAxis(i).mHiAuto = false;
    setRanges();
  }


  public void setYRange(float lo, float hi) {
    setYRange(0, lo, hi);
  }


  /**
   * Sets the low end of the x axis range.
   *
   * @param x low end of range.
   * @param i The new yLo value.
   */
  public void setYLo(int i, float x) {
    if (x <= getYHi(i)) {
      getYAxis(i).mLo = x;
      getYAxis(i).mLoAuto = false;
      setRanges();
    }
  }


  /**
   * Returns the low end of the x axis range.
   *
   * @param i TODO Description.
   * @return low end of range.
   */
  public float getYLo(int i) {
    return getYAxis(i).mLo;
  }


  /**
   * Sets the high end of the x axis range.
   *
   * @param x high end of range.
   * @param i The new yHi value.
   */
  public void setYHi(int i, float x) {
    if (x >= getYLo(i)) {
      getYAxis(i).mHi = x;
      getYAxis(i).mHiAuto = false;
      setRanges();
    }
  }


  /**
   * Returns the high end of the x axis range.
   *
   * @param i TODO Description.
   * @return high end of range.
   */
  public float getYHi(int i) {
    return getYAxis(i).mHi;
  }


  /**
   * Sets the automatic calculation of X axis tics.
   *
   * @param i which x axis
   */
  public void setAutoScaleXTic(int i) {
    getXAxis(i).mTicAuto = true;
    setRanges();
  }


  /**
   * Sets the tick spacing for the x axis. This value represents the
   * distance from the origin in the positive direction of the first
   * tick.
   *
   * @param i which x axis
   * @param tic tick spacing
   */
  public void setXTic(int i, float tic) {
    getXAxis(i).mTic = tic;
    getXAxis(i).mTicAuto = false;
  }

  
  /**
   * Returns the tick spacing for the x axis. This value represents the
   * distance from the origin in the positive direction of the first
   * tick.
   *
   * @param i which x axis
   * @return tick spacing
   */
  public float getXTic(int i) {
    return getXAxis(i).mTic;
  }


  /**
   * Sets the tick spacing for small tics on the x axis. This value
   * represents the distance from the origin in the positive direction
   * of the first tick.  A value of 0 or less will result in no minor
   * ticks being displayed (the default).
   *
   * @param i which x axis
   * @param tic tick spacing
   */
  public void setXMinorTic(int i, float tic) {
    getXAxis(i).mMinorTic = tic;    
  }


  /**
   * Returns the tick spacing for small tics on the x axis. This value
   * represents the distance from the origin in the positive direction
   * of the first tick. 
   *
   * @param i which x axis
   * @return tick spacing
   */
  public float getXMinorTic(int i) {
    return getXAxis(i).mMinorTic;
  }


  /**
   * Sets the automatic calculation of Y axis tics.
   *
   * @param i which y axis
   */
  public void setAutoScaleYTic(int i) {
    getYAxis(i).mTicAuto = true;
    setRanges();
  }


  /**
   * Sets the tick spacing for the y axis. This value represents the
   * distance from the origin in the positive direction of the first
   * tick.
   *
   * @param tic tick spacing
   * @param i The new yTic value.
   */
  public void setYTic(int i, float tic) {
    getYAxis(i).mTic = tic;
    getYAxis(i).mTicAuto = false;
  }


  /**
   * Returns the tick spacing for the y axis. This value represents the
   * distance from the origin in the positive direction of the first
   * tick.
   *
   * @param i TODO Description.
   * @return tick spacing
   */
  public float getYTic(int i) {
    return getYAxis(i).mTic;
  }


  /**
   * Sets the tick spacing for small tics on the y axis. This value
   * represents the distance from the origin in the positive direction
   * of the first tick.  A value of 0 or less will result in no minor
   * ticks being displayed (the default).
   *
   * @param i which y axis
   * @param tic tick spacing
   */
  public void setYMinorTic(int i, float tic) {
    getYAxis(i).mMinorTic = tic;    
  }


  /**
   * Returns the tick spacing for small tics on the y axis. This value
   * represents the distance from the origin in the positive direction
   * of the first tick. 
   *
   * @param i which y axis
   * @return tick spacing
   */
  public float getYMinorTic(int i) {
    return getYAxis(i).mMinorTic;
  }


  /**
   * Sets whether to show x tics on graph.
   *
   * @param flag whether to show x tics.
   * @param i The new showXTics value.
   */
  public void setShowXTics(int i, boolean flag) {
    getXAxis(i).mShowTics = flag;
  }


  /**
   * Returns whether to show x tics on graph.
   *
   * @param i TODO Description.
   * @return whether to show x tics.
   */
  public boolean getShowXTics(int i) {
    return getXAxis(i).mShowTics;
  }


  /**
   * Sets whether to show y tics on graph.
   *
   * @param flag whether to show y tics.
   * @param i The new showYTics value.
   */
  public void setShowYTics(int i, boolean flag) {
    getYAxis(i).mShowTics = flag;
  }


  /**
   * Returns whether to show y tics on graph.
   *
   * @param i TODO Description.
   * @return whether to show y tics.
   */
  public boolean getShowYTics(int i) {
    return getYAxis(i).mShowTics;
  }


  public void setXTicLabels(int i, String[] labels) {
    if (labels != null) {
      for (int l = 0; l < labels.length; l++) {
        if (labels[l] == null) {
          throw new NullPointerException("null X Tics label.");
        }
      }
    }
    getXAxis(i).mTicLabels = labels;
  }


  public String[] getXTicLabels(int i) {
    return getXAxis(i).mTicLabels;
  }


  public void setYTicLabels(int i, String[] labels) {
    if (labels != null) {
      for (int l = 0; l < labels.length; l++) {
        if (labels[l] == null) {
          throw new NullPointerException("null Y Tics label.");
        }
      }
    }
    getYAxis(i).mTicLabels = labels;
  }


  public String[] getYTicLabels(int i) {
    return getYAxis(i).mTicLabels;
  }


  public void setLogScaleX(int i, boolean flag) {
    getXAxis(i).mLogScale = flag;
    setRanges();
  }


  public boolean getLogScaleX(int i) {
    return getXAxis(i).mLogScale;
  }


  public void setLogScaleY(int i, boolean flag) {
    getYAxis(i).mLogScale = flag;
    setRanges();
  }


  public boolean getLogScaleY(int i) {
    return getYAxis(i).mLogScale;
  }


  /**
   * Adds a plot to the current set of plots for this graph.
   *
   * @param plot a Plot2D
   */
  public void addPlot(Plot2D plot) {
    if (plot != null) {
      mPlots.add(plot);
      setRanges();
    }
  }


  /**
   * Returns an array of the plots that are in this graph.
   *
   * @return an array of Plot2D's
   */
  public Plot2D[] getPlots() {
    return (Plot2D[]) mPlots.toArray(new Plot2D[mPlots.size()]);
  }


  /**
   * Returns whether any plot in this graph uses the specified x axis.
   *
   * @param i TODO Description.
   * @return whether any plot uses the specified x axis
   */
  public boolean usesX(int i) {
    Iterator it = mPlots.iterator();
    while (it.hasNext()) {
      Plot2D plot = (Plot2D) it.next();
      if (plot.getXAxis() == i) {
        return true;
      }
    }
    return false;
  }


  /**
   * Returns whether any plot in this graph uses the specified y axis.
   *
   * @param i TODO Description.
   * @return whether any plot uses the specified y axis
   */
  public boolean usesY(int i) {
    Iterator it = mPlots.iterator();
    while (it.hasNext()) {
      Plot2D plot = (Plot2D) it.next();
      if (plot.getYAxis() == i) {
        return true;
      }
    }
    return false;
  }


  /**
   * Calculates the distance between tics given the range of data
   * values.
   *
   * @param tmin range minimum
   * @param tmax range maximum
   * @return tic size
   */
  private float makeTics(float tmin, float tmax) {
    float xr;
    float xnorm;
    float tics;
    float tic;
    float l10;

    xr = Math.abs(tmax - tmin);

    if (xr == 0) {
      return 1.0f;
    }

    l10 = (float) (Math.log(xr) / PlotUtils.L10);
    xnorm = (float) Math.pow(10.0f, l10 - (float) ((l10 >= 0.0f) ? (int) l10 : ((int) l10 - 1)));
    if (xnorm <= 2) {
      tics = 0.2f;
    } else if (xnorm <= 5) {
      tics = 0.5f;
    } else {
      tics = 1.0f;
    }

    tic = tics * PlotUtils.pow(10.0f, (l10 >= 0.0f) ? (int) l10 : ((int) l10 - 1));
    return tic;
  }


  /**
   * Calculates and sets the ranges of the x and y axes if their
   * corresponding autoscale flags are set.
   */
  private void setRanges() {
    for (int i = 0; i < mXAxis.length; i++) {
      Axis xAxis = mXAxis[i];
      if (xAxis.mLogScale || xAxis.mLoAuto || xAxis.mHiAuto || xAxis.mTicAuto) {
        boolean rangeSet = false;
        float xlo = Float.MAX_VALUE;
        float xhi = Float.MIN_VALUE;

        Iterator it = mPlots.iterator();
        while (it.hasNext()) {
          Plot2D plot = (Plot2D) it.next();
          if (plot.getXAxis() == i) {
            float plotXLo = plot.getXLo();
            if (plotXLo < xlo) {
              xlo = plotXLo;
            }
            float plotXHi = plot.getXHi();
            if (plotXHi > xhi) {
              xhi = plotXHi;
            }
            rangeSet = true;
          }
        }

        if (!rangeSet) {
          xlo = xhi = 0.0f;
        }

        setRanges(xAxis, xlo, xhi);
      }
    }

    for (int i = 0; i < mYAxis.length; i++) {
      Axis yAxis = mYAxis[i];
      if (yAxis.mLogScale || yAxis.mLoAuto || yAxis.mHiAuto || yAxis.mTicAuto) {
        boolean rangeSet = false;
        float ylo = Float.MAX_VALUE;
        float yhi = Float.MIN_VALUE;

        Iterator it = mPlots.iterator();
        while (it.hasNext()) {
          Plot2D plot = (Plot2D) it.next();
          if (plot.getYAxis() == i) {
            float plotYLo = plot.getYLo();
            if (plotYLo < ylo) {
              ylo = plotYLo;
            }
            float plotYHi = plot.getYHi();
            if (plotYHi > yhi) {
              yhi = plotYHi;
            }
            rangeSet = true;
          }
        }

        if (!rangeSet) {
          ylo = yhi = 0.0f;
        }

        setRanges(yAxis, ylo, yhi);
      }
    }
  }

  
  private void setRanges(Axis axis, float lo, float hi) {
    if (axis.mLogScale) {
      if (axis.mLoAuto) {
        axis.mLo = (lo <= 0.0f) ? 1.0f : (float) PlotUtils.floor10(lo);
      }
      if (axis.mHiAuto) {
        axis.mHi = (hi <= 0.0f) ? 1.0f : (float) PlotUtils.ceil10(hi);
      }
      if (axis.mHi <= axis.mLo) {
        axis.mHi = axis.mLo * 10.0f;
      }
      axis.mTic = 1.0f;
    } else {
      if (!axis.mLoAuto) {
        lo = axis.mLo;
      }
      if (!axis.mHiAuto) {
        hi = axis.mHi;
      }
      float tic = axis.mTic;
      if (axis.mTicAuto) {
        tic = makeTics(lo, hi);
      }
      lo = tic * (float) Math.floor(lo / tic);
      hi = tic * (float) Math.ceil(hi / tic);
      boolean same = false;
      if (lo == hi) { // same so do some autoscaling
        if (axis.mLoAuto) {
          lo -= tic;
        }
        if (axis.mHiAuto) {
          hi += tic;
        }
        if (lo == hi) { // still the same
          lo -= tic;
          hi += tic;
          same = true;
        }
      }
      if (axis.mLoAuto || same) {
        axis.mLo = lo;
      }
      if (axis.mHiAuto || same) {
        axis.mHi = hi;
      }
      if (axis.mTicAuto || same) {
        axis.mTic = makeTics(axis.mLo, axis.mHi);
      }
    }
  }


  /**
   * Sets draw a vertical (dashed) line at the given x co-ordinate to
   * on.
   *
   * @param x an X axis co-ordinate.
   */
  public void setVerticalLine(float x) {
    mVertLine = true;
    mVertLinePos = x;
  }


  /** Turns draw a vertical (dashed) line off. */
  public void unsetVerticalLine() {
    mVertLine = false;
  }


  /**
   * Returns whether to draw the vertical (dashed) line.
   *
   * @return whether to draw
   */
  public boolean getVerticalLine() {
    return mVertLine;
  }


  /**
   * Returns position along X axis to draw the vertical (dashed) line.
   *
   * @return X axis value
   */
  public float getVerticalLinePos() {
    return mVertLinePos;
  }


  // inherited
  public Object clone() throws CloneNotSupportedException {
    // only need to clone the members of this object
    // used same data arrays as in this object

    Graph2D g = (Graph2D) super.clone();

    g.mXAxis = new Axis[mXAxis.length];
    g.mYAxis = new Axis[mYAxis.length];

    for (int i = 0; i < mXAxis.length; i++) {
      g.mXAxis[i] = (Axis) mXAxis[i].clone();
    }
    for (int i = 0; i < mYAxis.length; i++) {
      g.mYAxis[i] = (Axis) mYAxis[i].clone();
    }

    g.mPlots = new ArrayList(mPlots);

    return g;
  }


  /**
   * <code>Axis</code> description here.
   *
   */
  private static class Axis implements Cloneable {
    private String mTitle = "";
    private float mLo;
    private float mHi;
    private boolean mLoAuto = true;
    private boolean mHiAuto = true;
    private float mTic;
    private float mMinorTic;
    private boolean mTicAuto = true;
    private String[] mTicLabels = null;
    private boolean mShowGrid = false;
    private boolean mShowTics = true;
    private boolean mLogScale = false;

    public Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }
}

