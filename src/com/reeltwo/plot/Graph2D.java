package com.reeltwo.plot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Structure containing all the attributes of a 2D graph.
 *
 * @author Richard Littin
 */

public class Graph2D implements Cloneable {

  private static final LabelFormatter DEFAULT_FORMATTER = new DefaultFormatter();

  /** graph title */
  private String mTitle = "";
  /** key title */
  private String mKeyTitle = "";
  private boolean mShowKey = true;
  private boolean mColoredKey = true; // whether to color key text
  private KeyPosition mKeyHorizontalPosition = KeyPosition.OUTSIDE;
  private KeyPosition mKeyVerticalPosition = KeyPosition.TOP;

  private final HashMap<AxisSide, GraphAxis> mXAxes = new HashMap<AxisSide, GraphAxis>();
  private final HashMap<AxisSide, GraphAxis> mYAxes = new HashMap<AxisSide, GraphAxis>();

  /** whether to display border lines and labels */
  private boolean mDisplayBorder = true;

  /** list of plots in graph */
  private ArrayList<Plot2D> mPlots = new ArrayList<Plot2D>();

  /** Default constructor. */
  public Graph2D() {
    for (AxisSide a : AxisSide.values()) {
      mXAxes.put(a, new GraphAxis());
      mYAxes.put(a, new GraphAxis());
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

  private GraphAxis getAxis(Axis2D axis, AxisSide side) {
    return (axis == Axis2D.X ? mXAxes : mYAxes).get(side);
  }

  /**
   * Sets the graph's <code>i</code><sup>th</sup> x axis <code>label</code>.
   *
   * @param i x axis index
   * @param label some text
   */
  public void setLabel(Axis2D axis, AxisSide side, String label) {
    getAxis(axis, side).setTitle(label);
  }

  /**
   * Shortcut to set bottom x axis label.
   *
   * @param label some text
   */
  public void setLabel(Axis2D axis, String label) {
    setLabel(axis, AxisSide.ONE, label);
  }

  /**
   * Returns the graph's <code>i</code><sup>th</sup> x axis label.
   *
   * @param i x axis index
   * @return label
   */
  public String getLabel(Axis2D axis, AxisSide side) {
    return getAxis(axis, side).getTitle();
  }


  /**
   * Sets whether or not to display a grid on the graph.
   *
   * @param flag whether to show grid
   */
  public void setGrid(boolean flag) {
    for (AxisSide side : AxisSide.values()) {
      for (Axis2D axis : Axis2D.values()) {
        setGrid(axis, side, flag);
      }
    }
  }

  /**
   * Sets whether or not to display a grid for the
   * <code>i</code><sup>th</sup> x axis.
   *
   * @param i x axis index
   * @param flag whether to show grid
   */
  public void setGrid(Axis2D axis, AxisSide side, boolean flag) {
    getAxis(axis, side).setShowGrid(flag);
  }

  /**
   * Returns whether or not to display a grid for the
   * <code>i</code><sup>th</sup> x axis.
   *
   * @param i x axis index
   * @return whether to show grid
   */
  public boolean isGrid(Axis2D axis, AxisSide side) {
    return getAxis(axis, side).isShowGrid();
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
  public void setAutoScale(Axis2D axis, AxisSide side) {
    final GraphAxis ga = getAxis(axis, side);
    ga.setLoAuto(true);
    ga.setHiAuto(true);
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
  public void setRange(Axis2D axis, AxisSide side, float lo, float hi) {
    getAxis(axis, side).setRange(lo, hi);
    setRanges();
  }

  /**
   * Shortcut to set bottom x axis range.
   *
   * @param lo lower end of range
   * @param hi upper end of range
   */
  public void setRange(Axis2D axis, float lo, float hi) {
    setRange(axis, AxisSide.ONE, lo, hi);
  }

  /**
   * Sets the low end of the <code>i</code><sup>th</sup> x axis range.
   *
   * @param i x axis index
   * @param x low end of range
   */
  public void setLo(Axis2D axis, AxisSide side, float x) {
    getAxis(axis, side).setLo(x);
    setRanges();
  }

  /**
   * Returns the low end of the <code>i</code><sup>th</sup> x axis range.
   *
   * @param i x axis index
   * @return low end of range
   */
  public float getLo(Axis2D axis, AxisSide side) {
    return getAxis(axis, side).getLo();
  }

  /**
   * Sets the high end of the <code>i</code><sup>th</sup> x axis
   * range.
   *
   * @param i x axis index
   * @param x high end of range
   */
  public void setHi(Axis2D axis, AxisSide side, float x) {
    getAxis(axis, side).setHi(x);
    setRanges();
  }

  /**
   * Returns the high end of the <code>i</code><sup>th</sup> x axis
   * range.
   *
   * @param i x axis index
   * @return high end of range
   */
  public float getHi(Axis2D axis, AxisSide side) {
    return getAxis(axis, side).getHi();
  }

  /**
   * Sets the automatic calculation of <code>i</code><sup>th</sup> X axis tics.
   *
   * @param i x axis index
   */
  public void setAutoScaleXTic(AxisSide i) {
    getAxis(Axis2D.X, i).setTicAuto(true);
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
  public void setXTic(AxisSide i, float tic) {
    getAxis(Axis2D.X, i).setTic(tic);
  }

  /**
   * Returns the tick spacing for the <code>i</code><sup>th</sup> x
   * axis. This value represents the distance from the origin in the
   * positive direction of the first tick.
   *
   * @param i x axis index
   * @return tick spacing
   */
  public float getXTic(AxisSide i) {
    return getAxis(Axis2D.X, i).getTic();
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
  public void setXMinorTic(AxisSide i, float tic) {
    getAxis(Axis2D.X, i).setMinorTic(tic);
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
  public float getXMinorTic(AxisSide i) {
    return getAxis(Axis2D.X, i).getMinorTic();
  }

  /**
   * Sets the automatic calculation of <code>i</code><sup>th</sup> Y axis tics.
   *
   * @param i y axis index
   */
  public void setAutoScaleYTic(AxisSide i) {
    getAxis(Axis2D.Y, i).setTicAuto(true);
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
  public void setYTic(AxisSide i, float tic) {
    getAxis(Axis2D.Y, i).setTic(tic);
  }

  /**
   * Returns the tick spacing for the <code>i</code><sup>th</sup> y
   * axis. This value represents the distance from the origin in the
   * positive direction of the first tick.
   *
   * @param i y axis index
   * @return tick spacing
   */
  public float getYTic(AxisSide i) {
    return getAxis(Axis2D.Y, i).getTic();
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
  public void setYMinorTic(AxisSide i, float tic) {
    getAxis(Axis2D.Y, i).setMinorTic(tic);
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
  public float getYMinorTic(AxisSide i) {
    return getAxis(Axis2D.Y, i).getMinorTic();
  }

  /**
   * Sets whether to show <code>i</code><sup>th</sup> x tics on graph.
   *
   * @param i x axis index
   * @param flag whether to show tics
   */
  public void setShowXTics(AxisSide i, boolean flag) {
    getAxis(Axis2D.X, i).setShowTics(flag);
  }

  /**
   * Returns whether to show <code>i</code><sup>th</sup> x tics on graph.
   *
   * @param i x axis index
   * @return whether to show tics
   */
  public boolean isShowXTics(AxisSide i) {
    return getAxis(Axis2D.X, i).isShowTics();
  }

  /**
   * Sets whether to show <code>i</code><sup>th</sup> y tics on graph.
   *
   * @param i y axis index
   * @param flag whether to show tics
   */
  public void setShowYTics(AxisSide i, boolean flag) {
    getAxis(Axis2D.Y, i).setShowTics(flag);
  }

  /**
   * Returns whether to show <code>i</code><sup>th</sup> y tics on graph.
   *
   * @param i y axis index
   * @return whether to show tics
   */
  public boolean isShowYTics(AxisSide i) {
    return getAxis(Axis2D.Y, i).isShowTics();
  }

  /**
   * Set the label formatter to use for formatting of numbers on the
   * <code>i</code><sup>th</sup> y axis.  If a null formatter is given
   * then the default formatter is used.
   *
   * @param i y axis index
   * @param lf a label formatter
   */
  public void setTicLabelFormatter(Axis2D axis, AxisSide side, LabelFormatter lf) {
    getAxis(axis, side).setLabelFormatter(lf);
  }

  /**
   * Returns the label formater used for the
   * <code>i</code><sup>th</sup> y axis.
   *
   * @param i y axis index
   * @return <code>LabelFormatter</code>
   */
  public LabelFormatter getTicLabelFormatter(Axis2D axis, AxisSide side) {
    final LabelFormatter lf = getAxis(axis, side).getLabelFormatter();
    return lf == null ? DEFAULT_FORMATTER : lf;
  }

  /**
   * Sets whether to use a log scale on the
   * <code>i</code><sup>th</sup> y axis. Default is not to used log
   * scale.
   *
   * @param i y axis index
   * @param flag whether to use log scale
   */
  public void setLogScale(Axis2D axis, AxisSide side, boolean flag) {
    getAxis(axis, side).setLogScale(flag);
    setRanges();
  }

  /**
   * Returns whether to use log scale on the
   * <code>i</code><sup>th</sup> y axis.
   *
   * @param i y axis index
   * @return whether using log scale
   */
  public boolean isLogScale(Axis2D axis, AxisSide side) {
    return getAxis(axis, side).isLogScale();
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
   * Returns whether any plot in this graph uses the <code>i</code><sup>th</sup> y axis.
   *
   * @param i y axis index
   * @return whether any plot uses the y axis
   */
  public boolean uses(Axis2D axis, AxisSide side) {
    for (final Plot2D plot : mPlots) {
      if (plot.uses(axis, side)) {
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
    for (AxisSide side : AxisSide.values()) {
      final GraphAxis xAxis = mXAxes.get(side);
      if (xAxis.isLogScale() || xAxis.isLoAuto() || xAxis.isHiAuto() || xAxis.isTicAuto()) {
        boolean rangeSet = false;
        float xlo = Float.MAX_VALUE;
        float xhi = Float.MIN_VALUE;

        for (final Plot2D plot : mPlots) {
          if (!(plot instanceof GraphLine)) {
            if (plot.uses(Axis2D.X, side)) {
              final float plotXLo = plot.getLo(Axis2D.X);
              if (plotXLo < xlo) {
                xlo = plotXLo;
              }
              final float plotXHi = plot.getHi(Axis2D.X);
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

    for (AxisSide side : AxisSide.values()) {
      final GraphAxis yAxis = mYAxes.get(side);
      if (yAxis.isLogScale() || yAxis.isLoAuto() || yAxis.isHiAuto() || yAxis.isTicAuto()) {
        boolean rangeSet = false;
        float ylo = Float.MAX_VALUE;
        float yhi = Float.MIN_VALUE;

        for (final Plot2D plot : mPlots) {
          if (!(plot instanceof GraphLine)) {
            if (plot.uses(Axis2D.Y, side)) {
              final float plotYLo = plot.getLo(Axis2D.Y);
              if (plotYLo < ylo) {
                ylo = plotYLo;
              }
              final float plotYHi = plot.getHi(Axis2D.Y);
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
    for (AxisSide i : AxisSide.values()) {
      g.mXAxes.put(i, (GraphAxis) mXAxes.get(i).clone());
      g.mYAxes.put(i, (GraphAxis) mYAxes.get(i).clone());
    }
    g.mPlots = new ArrayList<Plot2D>(mPlots);
    return g;
  }
}

