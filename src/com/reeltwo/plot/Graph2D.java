package com.reeltwo.plot;

import java.util.ArrayList;

/**
 * Structure containing all the attributes of a 2D graph.
 *
 * @author Richard Littin
 */

public class Graph2D implements Cloneable {
  /**
   * Possible positions for the key
   */
  public enum KeyPosition {
    /** Center of plot */
    CENTER,
    /** Left side of plot */
    LEFT,
    /** Right side of plot */
    RIGHT,
    /** Outside right edge of plot */
    OUTSIDE,
    /** Top of plot */
    TOP,
    /** Bottom of plot */
    BOTTOM,
    /** Below bottom edge of plot */
    BELOW
  }
  
  protected static final int NUM_X_AXES = 2;
  protected static final int NUM_Y_AXES = 2;

  private static final LabelFormatter DEFAULT_FORMATTER = new DefaultFormatter();

  /** graph title */
  private String mTitle = "";
  /** key title */
  private String mKeyTitle = "";
  private boolean mShowKey = true;
  private boolean mColoredKey = true; // whether to color key text
  private KeyPosition mKeyHorizontalPosition = KeyPosition.OUTSIDE;
  private KeyPosition mKeyVerticalPosition = KeyPosition.TOP;

  private Axis[] mXAxis;
  private Axis[] mYAxis;

  /** whether to display border lines and labels */
  private boolean mDisplayBorder = true;

  /** list of plots in graph */
  private ArrayList<Plot2D> mPlots = new ArrayList<Plot2D>();

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
    mTitle = (title == null) ? "" : title;
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
    mKeyTitle = (title == null) ? "" : title;
  }

  /**
   * Returns the graph's key title.
   *
   * @return some text
   */
  public String getKeyTitle() {
    return mKeyTitle;
  }

  /**
   * Sets whether to show the key.  Default is to show key.
   *
   * @param flag show key
   */
  public void setShowKey(boolean flag) {
    mShowKey = flag;
  }

  /**
   * Returns whether to show the key.
   *
   * @return show key
   */
  public boolean isShowKey() {
    return mShowKey;
  }

  /**
   * Set whether to use plots color in key text or to use the
   * forground color.  Default is to use colors.
   *
   * @param flag use plot colors in text
   */
  public void setColoredKey(final boolean flag) {
    mColoredKey = flag;
  }

  /**
   * Returns whether using plot colors in key text.
   *
   * @return using plot colors in text
   */
  public boolean isColoredKey() {
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
  public void setKeyHorizontalPosition(KeyPosition position) {
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
  public KeyPosition getKeyHorizontalPosition() {
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
  public void setKeyVerticalPosition(KeyPosition position) {
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
  public KeyPosition getKeyVerticalPosition() {
    return mKeyVerticalPosition;
  }

  private Axis getXAxis(int i) {
    if (i < 0 || i > mXAxis.length) {
      throw new IllegalArgumentException("X axis index out of range.");
    }
    return mXAxis[i];
  }

  /**
   * Sets the graph's <code>i</code><sup>th</sup> x axis <code>label</code>.
   *
   * @param i x axis index
   * @param label some text
   */
  public void setXLabel(int i, String label) {
    getXAxis(i).setTitle(label);
  }

  /**
   * Shortcut to set bottom x axis label.
   *
   * @param label some text
   */
  public void setXLabel(String label) {
    setXLabel(0, label);
  }

  /**
   * Returns the graph's <code>i</code><sup>th</sup> x axis label.
   *
   * @param i x axis index
   * @return label
   */
  public String getXLabel(int i) {
    return getXAxis(i).getTitle();
  }

  private Axis getYAxis(int i) {
    if (i < 0 || i > mYAxis.length) {
      throw new IllegalArgumentException("Y axis index out of range.");
    }
    return mYAxis[i];
  }

  /**
   * Sets the graph's <code>i</code><sup>th</sup> y axis <code>label</code>.
   *
   * @param i y axis index
   * @param label some text
   */
  public void setYLabel(int i, String label) {
    getYAxis(i).setTitle(label);
  }

  /**
   * Shortcut to set left y axis label.
   *
   * @param label some text
   */
  public void setYLabel(String label) {
    setYLabel(0, label);
  }

  /**
   * Returns the graph's <code>i</code><sup>th</sup> y axis label.
   *
   * @param i y axis index
   * @return label
   */
  public String getYLabel(int i) {
    return getYAxis(i).getTitle();
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
   * Sets whether or not to display a grid for the
   * <code>i</code><sup>th</sup> x axis.
   *
   * @param i x axis index
   * @param flag whether to show grid
   */
  public void setXGrid(int i, boolean flag) {
    getXAxis(i).setShowGrid(flag);
  }

  /**
   * Returns whether or not to display a grid for the
   * <code>i</code><sup>th</sup> x axis.
   *
   * @param i x axis index
   * @return whether to show grid
   */
  public boolean isXGrid(int i) {
    return getXAxis(i).isShowGrid();
  }

  /**
   * Sets whether or not to display a grid for the
   * <code>i</code><sup>th</sup> y axis.
   *
   * @param i y axis index
   * @param flag whether to show grid
   */
  public void setYGrid(int i, boolean flag) {
    getYAxis(i).setShowGrid(flag);
  }

  /**
   * Returns whether or not to display a grid for the
   * <code>i</code><sup>th</sup> y axis.
   *
   * @param i y axis index
   * @return whether to show grid
   */
  public boolean isYGrid(int i) {
    return getYAxis(i).isShowGrid();
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
  public boolean isBorder() {
    return mDisplayBorder;
  }

  /**
   * Sets the automatic calculation of <code>i</code><sup>th</sup> X
   * axis range.
   *
   * @param i x axis index
   */
  public void setAutoScaleX(int i) {
    getXAxis(i).setLoAuto(true);
    getXAxis(i).setHiAuto(true);
    setRanges();
  }

  /**
   * Sets the <code>i</code><sup>th</sup> x axis to range from
   * <code>lo</code> to hi. If <code>hi</code> &lt; <code>lo</code>
   * the meaning of <code>lo</code> and <code>hi</code> is swapped.
   *
   * @param i x axis index
   * @param lo lower end of range
   * @param hi upper end of range
   */
  public void setXRange(int i, float lo, float hi) {
    getXAxis(i).setRange(lo, hi);
    setRanges();
  }

  /**
   * Shortcut to set bottom x axis range.
   *
   * @param lo lower end of range
   * @param hi upper end of range
   */
  public void setXRange(float lo, float hi) {
    setXRange(0, lo, hi);
  }

  /**
   * Sets the low end of the <code>i</code><sup>th</sup> x axis range.
   *
   * @param i x axis index
   * @param x low end of range
   */
  public void setXLo(int i, float x) {
    getXAxis(i).setLo(x);
    setRanges();
  }

  /**
   * Returns the low end of the <code>i</code><sup>th</sup> x axis range.
   *
   * @param i x axis index
   * @return low end of range
   */
  public float getXLo(int i) {
    return getXAxis(i).getLo();
  }

  /**
   * Sets the high end of the <code>i</code><sup>th</sup> x axis
   * range.
   *
   * @param i x axis index
   * @param x high end of range
   */
  public void setXHi(int i, float x) {
    getXAxis(i).setHi(x);
    setRanges();
  }

  /**
   * Returns the high end of the <code>i</code><sup>th</sup> x axis
   * range.
   *
   * @param i x axis index
   * @return high end of range
   */
  public float getXHi(int i) {
    return getXAxis(i).getHi();
  }

  /**
   * Sets the automatic calculation of <code>i</code><sup>th</sup> Y
   * axis range.
   *
   * @param i yaxis index
   */
  public void setAutoScaleY(int i) {
    getYAxis(i).setLoAuto(true);
    getYAxis(i).setHiAuto(true);
    setRanges();
  }

  /**
   * Sets the <code>i</code><sup>th</sup> y axis to range from
   * <code>lo</code> to hi. If <code>hi</code> < <code>lo</code> the
   * meaning of <code>lo</code> and <code>hi</code> is swapped.
   *
   * @param i y axis index
   * @param lo lower end of range
   * @param hi upper end of range
   */
  public void setYRange(int i, float lo, float hi) {
    getYAxis(i).setRange(lo, hi);
    setRanges();
  }

  /**
   * Shortcut to set left y axis range.
   *
   * @param lo lower end of range
   * @param hi upper end of range
   */
  public void setYRange(float lo, float hi) {
    setYRange(0, lo, hi);
  }

  /**
   * Sets the low end of the <code>i</code><sup>th</sup> y axis range.
   *
   * @param i y axis index
   * @param x low end of range
   */
  public void setYLo(int i, float x) {
    getYAxis(i).setLo(x);
    setRanges();
  }

  /**
   * Returns the low end of the <code>i</code><sup>th</sup> y axis range.
   *
   * @param i y axis index
   * @return low end of range
   */
  public float getYLo(int i) {
    return getYAxis(i).getLo();
  }

  /**
   * Sets the high end of the <code>i</code><sup>th</sup> y axis range.
   *
   * @param i y axis index
   * @param x high end of range
   */
  public void setYHi(int i, float x) {
    getYAxis(i).setHi(x);
    setRanges();
  }

  /**
   * Returns the high end of the <code>i</code><sup>th</sup> y axis range.
   *
   * @param i y axis index
   * @return high end of range
   */
  public float getYHi(int i) {
    return getYAxis(i).getHi();
  }

  /**
   * Sets the automatic calculation of <code>i</code><sup>th</sup> X axis tics.
   *
   * @param i x axis index
   */
  public void setAutoScaleXTic(int i) {
    getXAxis(i).setTicAuto(true);
    setRanges();
  }

  /**
   * Sets the tick spacing for the <code>i</code><sup>th</sup> x
   * axis. This value represents the distance from the origin in the
   * positive direction of the first tick.
   *
   * @param i x axis index
   * @param tic tick spacing
   */
  public void setXTic(int i, float tic) {
    getXAxis(i).setTic(tic);
  }

  /**
   * Returns the tick spacing for the <code>i</code><sup>th</sup> x
   * axis. This value represents the distance from the origin in the
   * positive direction of the first tick.
   *
   * @param i x axis index
   * @return tick spacing
   */
  public float getXTic(int i) {
    return getXAxis(i).getTic();
  }

  /**
   * Sets the tick spacing for small tics on the
   * <code>i</code><sup>th</sup> x axis. This value represents the
   * distance from the origin in the positive direction of the first
   * tick.  A value of 0 or less will result in no minor ticks being
   * displayed (the default).
   *
   * @param i x axis index
   * @param tic tick spacing
   */
  public void setXMinorTic(int i, float tic) {
    getXAxis(i).setMinorTic(tic);
  }

  /**
   * Returns the tick spacing for small tics on the
   * <code>i</code><sup>th</sup> x axis. This value represents the
   * distance from the origin in the positive direction of the first
   * tick.
   *
   * @param i x axis index
   * @return tick spacing
   */
  public float getXMinorTic(int i) {
    return getXAxis(i).getMinorTic();
  }

  /**
   * Sets the automatic calculation of <code>i</code><sup>th</sup> Y axis tics.
   *
   * @param i y axis index
   */
  public void setAutoScaleYTic(int i) {
    getYAxis(i).setTicAuto(true);
    setRanges();
  }

  /**
   * Sets the tick spacing for the <code>i</code><sup>th</sup> y
   * axis. This value represents the distance from the origin in the
   * positive direction of the first tick.
   *
   * @param i y axis index
   * @param tic tick spacing
   */
  public void setYTic(int i, float tic) {
    getYAxis(i).setTic(tic);
  }

  /**
   * Returns the tick spacing for the <code>i</code><sup>th</sup> y
   * axis. This value represents the distance from the origin in the
   * positive direction of the first tick.
   *
   * @param i y axis index
   * @return tick spacing
   */
  public float getYTic(int i) {
    return getYAxis(i).getTic();
  }

  /**
   * Sets the tick spacing for small tics on the
   * <code>i</code><sup>th</sup> y axis. This value represents the
   * distance from the origin in the positive direction of the first
   * tick.  A value of 0 or less will result in no minor ticks being
   * displayed (the default).
   *
   * @param i y axis index
   * @param tic tick spacing
   */
  public void setYMinorTic(int i, float tic) {
    getYAxis(i).setMinorTic(tic);
  }

  /**
   * Returns the tick spacing for small tics on the
   * <code>i</code><sup>th</sup> y axis. This value represents the
   * distance from the origin in the positive direction of the first
   * tick.
   *
   * @param i y axis index
   * @return tick spacing
   */
  public float getYMinorTic(int i) {
    return getYAxis(i).getMinorTic();
  }

  /**
   * Sets whether to show <code>i</code><sup>th</sup> x tics on graph.
   *
   * @param i x axis index
   * @param flag whether to show tics
   */
  public void setShowXTics(int i, boolean flag) {
    getXAxis(i).setShowTics(flag);
  }

  /**
   * Returns whether to show <code>i</code><sup>th</sup> x tics on graph.
   *
   * @param i x axis index
   * @return whether to show tics
   */
  public boolean isShowXTics(int i) {
    return getXAxis(i).isShowTics();
  }

  /**
   * Sets whether to show <code>i</code><sup>th</sup> y tics on graph.
   *
   * @param i y axis index
   * @param flag whether to show tics
   */
  public void setShowYTics(int i, boolean flag) {
    getYAxis(i).setShowTics(flag);
  }

  /**
   * Returns whether to show <code>i</code><sup>th</sup> y tics on graph.
   *
   * @param i y axis index
   * @return whether to show tics
   */
  public boolean isShowYTics(int i) {
    return getYAxis(i).isShowTics();
  }

  /**
   * Set the label formatter to use for formatting of numbers on the
   * <code>i</code><sup>th</sup> x axis.  If a null formatter is given
   * then the default formatter is used.
   *
   * @param i x axis index
   * @param lf a label formatter
   */
  public void setXTicLabelFormatter(int i, LabelFormatter lf) {
    getXAxis(i).setLabelFormatter(lf);
  }

  /**
   * Returns the label formater used for the
   * <code>i</code><sup>th</sup> x axis.
   *
   * @param i x axis index
   * @return <code>LabelFormatter</code>
   */
  public LabelFormatter getXTicLabelFormatter(int i) {
    final LabelFormatter lf = getXAxis(i).getLabelFormatter();
    return lf == null ? DEFAULT_FORMATTER : lf;
  }

  /**
   * Set the label formatter to use for formatting of numbers on the
   * <code>i</code><sup>th</sup> y axis.  If a null formatter is given
   * then the default formatter is used.
   *
   * @param i y axis index
   * @param lf a label formatter
   */
  public void setYTicLabelFormatter(int i, LabelFormatter lf) {
    getYAxis(i).setLabelFormatter(lf);
  }

  /**
   * Returns the label formater used for the
   * <code>i</code><sup>th</sup> y axis.
   *
   * @param i y axis index
   * @return <code>LabelFormatter</code>
   */
  public LabelFormatter getYTicLabelFormatter(int i) {
    final LabelFormatter lf = getYAxis(i).getLabelFormatter();
    return lf == null ? DEFAULT_FORMATTER : lf;
  }

  /**
   * Sets whether to use a log scale on the
   * <code>i</code><sup>th</sup> x axis. Default is not to used log
   * scale.
   *
   * @param i x axis index
   * @param flag whether to use log scale
   */
  public void setLogScaleX(int i, boolean flag) {
    getXAxis(i).setLogScale(flag);
    setRanges();
  }

  /**
   * Returns whether to use log scale on the
   * <code>i</code><sup>th</sup> x axis.
   *
   * @param i x axis index
   * @return whether using log scale
   */
  public boolean isLogScaleX(int i) {
    return getXAxis(i).isLogScale();
  }

  /**
   * Sets whether to use a log scale on the
   * <code>i</code><sup>th</sup> y axis. Default is not to used log
   * scale.
   *
   * @param i y axis index
   * @param flag whether to use log scale
   */
  public void setLogScaleY(int i, boolean flag) {
    getYAxis(i).setLogScale(flag);
    setRanges();
  }

  /**
   * Returns whether to use log scale on the
   * <code>i</code><sup>th</sup> y axis.
   *
   * @param i y axis index
   * @return whether using log scale
   */
  public boolean isLogScaleY(int i) {
    return getYAxis(i).isLogScale();
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
    return mPlots.toArray(new Plot2D[mPlots.size()]);
  }

  /**
   * Returns whether any plot in this graph uses the
   * <code>i</code><sup>th</sup> x axis.
   *
   * @param i x axis index
   * @return whether any plot uses the axis
   */
  public boolean usesX(int i) {
    for (final Plot2D plot : mPlots) {
      if (plot.getXAxis() == i) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns whether any plot in this graph uses the <code>i</code><sup>th</sup> y axis.
   *
   * @param i y axis index
   * @return whether any plot uses the y axis
   */
  public boolean usesY(int i) {
    for (final Plot2D plot : mPlots) {
      if (plot.getYAxis() == i) {
        return true;
      }
    }
    return false;
  }

  /**
   * Calculates and sets the ranges of the x and y axes if their
   * corresponding autoscale flags are set.
   */
  private void setRanges() {
    for (int i = 0; i < mXAxis.length; i++) {
      final Axis xAxis = mXAxis[i];
      if (xAxis.isLogScale() || xAxis.isLoAuto() || xAxis.isHiAuto() || xAxis.isTicAuto()) {
        boolean rangeSet = false;
        float xlo = Float.MAX_VALUE;
        float xhi = Float.MIN_VALUE;

        for (final Plot2D plot : mPlots) {
          if (!(plot instanceof GraphLine)) {
            if (plot.getXAxis() == i) {
              final float plotXLo = plot.getXLo();
              if (plotXLo < xlo) {
                xlo = plotXLo;
              }
              final float plotXHi = plot.getXHi();
              if (plotXHi > xhi) {
                xhi = plotXHi;
              }
              rangeSet = true;
            }
          }
        }
        if (!rangeSet) {
          xlo = xhi = 0.0f;
        }
        xAxis.setAutoRange(xlo, xhi);
      }
    }

    for (int i = 0; i < mYAxis.length; i++) {
      final Axis yAxis = mYAxis[i];
      if (yAxis.isLogScale() || yAxis.isLoAuto() || yAxis.isHiAuto() || yAxis.isTicAuto()) {
        boolean rangeSet = false;
        float ylo = Float.MAX_VALUE;
        float yhi = Float.MIN_VALUE;

        for (final Plot2D plot : mPlots) {
          if (!(plot instanceof GraphLine)) {
            if (plot.getYAxis() == i) {
              final float plotYLo = plot.getYLo();
              if (plotYLo < ylo) {
                ylo = plotYLo;
              }
              final float plotYHi = plot.getYHi();
              if (plotYHi > yhi) {
                yhi = plotYHi;
              }
              rangeSet = true;
            }
          }
        }
        if (!rangeSet) {
          ylo = yhi = 0.0f;
        }
        yAxis.setAutoRange(ylo, yhi);
      }
    }
  }


  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {
    // only need to clone the members of this object
    // used same data arrays as in this object
    final Graph2D g = (Graph2D) super.clone();
    g.mXAxis = new Axis[mXAxis.length];
    g.mYAxis = new Axis[mYAxis.length];
    for (int i = 0; i < mXAxis.length; i++) {
      g.mXAxis[i] = (Axis) mXAxis[i].clone();
    }
    for (int i = 0; i < mYAxis.length; i++) {
      g.mYAxis[i] = (Axis) mYAxis[i].clone();
    }
    g.mPlots = new ArrayList<Plot2D>(mPlots);
    return g;
  }
}

