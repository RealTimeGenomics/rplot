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
  
  /** X axis */
  public static final Axis X = Axis.X;
  /** Y axis */
  public static final Axis Y = Axis.Y;
  /** Left/bottom axis */
  public static final Edge ONE = Edge.MAIN;
  /** Right/top axis */
  public static final Edge TWO = Edge.ALTERNATE;
  
  /** graph title */
  private String mTitle = "";
  /** key title */
  private String mKeyTitle = "";
  private boolean mShowKey = true;
  private boolean mColoredKey = true; // whether to color key text
  private KeyPosition mKeyHorizontalPosition = KeyPosition.OUTSIDE;
  private KeyPosition mKeyVerticalPosition = KeyPosition.TOP;

  private final HashMap<Edge, GraphAxis> mXAxes = new HashMap<Edge, GraphAxis>();
  private final HashMap<Edge, GraphAxis> mYAxes = new HashMap<Edge, GraphAxis>();

  /** whether to display border lines and labels */
  private boolean mDisplayBorder = true;

  /** list of plots in graph */
  private ArrayList<Plot2D> mPlots = new ArrayList<Plot2D>();

  /** Default constructor. */
  public Graph2D() {
    for (Edge a : Edge.values()) {
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

  private GraphAxis getAxis(Axis axis, Edge side) {
    return (axis == Axis.X ? mXAxes : mYAxes).get(side);
  }

  /**
   * Sets label for given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @param label some text
   */
  public void setLabel(Axis axis, Edge side, String label) {
    getAxis(axis, side).setTitle(label);
  }

  /**
   * Shortcut to set first axis label.
   *
   * @param axis graph axis
   * @param label some text
   */
  public void setLabel(Axis axis, String label) {
    setLabel(axis, ONE, label);
  }

  /**
   * Returns the label for figen axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @return label
   */
  public String getLabel(Axis axis, Edge side) {
    return getAxis(axis, side).getTitle();
  }


  /**
   * Sets whether or not to display a grid on the graph.
   *
   * @param flag whether to show grid
   */
  public void setGrid(boolean flag) {
    for (Edge side : Edge.values()) {
      for (Axis axis : Axis.values()) {
        setGrid(axis, side, flag);
      }
    }
  }

  /**
   * Sets whether or not to display a grid for the given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @param flag whether to show grid
   */
  public void setGrid(Axis axis, Edge side, boolean flag) {
    getAxis(axis, side).setShowGrid(flag);
  }

  /**
   * Returns whether or not to display a grid for the given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @return whether to show grid
   */
  public boolean isGrid(Axis axis, Edge side) {
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
   * Sets the automatic calculation of range on given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   */
  public void setAutoScale(Axis axis, Edge side) {
    final GraphAxis ga = getAxis(axis, side);
    ga.setLoAuto(true);
    ga.setHiAuto(true);
    setRanges();
  }

  /**
   * Sets the range from <code>lo</code> to <code>hi</code> for the
   * given axis and side. If <code>hi</code> &lt; <code>lo</code>
   * the meaning of <code>lo</code> and <code>hi</code> is swapped.
   *
   * @param axis graph axis
   * @param side axis side
   * @param lo lower end of range
   * @param hi upper end of range
   */
  public void setRange(Axis axis, Edge side, float lo, float hi) {
    getAxis(axis, side).setRange(lo, hi);
    setRanges();
  }

  /**
   * Shortcut to set first axis range.
   *
   * @param axis graph axis
   * @param lo lower end of range
   * @param hi upper end of range
   */
  public void setRange(Axis axis, float lo, float hi) {
    setRange(axis, ONE, lo, hi);
  }

  /**
   * Sets the low end of the given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @param x low end of range
   */
  public void setLo(Axis axis, Edge side, float x) {
    getAxis(axis, side).setLo(x);
    setRanges();
  }

  /**
   * Returns the low end of range for the given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @return low end of range
   */
  public float getLo(Axis axis, Edge side) {
    return getAxis(axis, side).getLo();
  }

  /**
   * Sets the high end of the given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @param x high end of range
   */
  public void setHi(Axis axis, Edge side, float x) {
    getAxis(axis, side).setHi(x);
    setRanges();
  }

  /**
   * Returns the high end of range for the axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @return high end of range
   */
  public float getHi(Axis axis, Edge side) {
    return getAxis(axis, side).getHi();
  }

  /**
   * Sets the automatic calculation of tics on given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   */
  public void setAutoScaleTic(Axis axis, Edge side) {
    getAxis(axis, side).setTicAuto(true);
    setRanges();
  }

  /**
   * Sets the tick spacing for the given axis and side.
   * This value represents the distance from the origin in the
   * positive direction of the first tick.
   *
   * @param axis graph axis
   * @param side axis side
   * @param tic tick spacing
   */
  public void setTic(Axis axis, Edge side, float tic) {
    getAxis(axis, side).setTic(tic);
  }

  /**
   * Returns the tick spacing for the given axis and side.
   * This value represents the distance from the origin in the
   * positive direction of the first tick.
   *
   * @param axis graph axis
   * @param side axis side
   * @return tick spacing
   */
  public float getTic(Axis axis, Edge side) {
    return getAxis(axis, side).getTic();
  }

  /**
   * Sets the tick spacing for small tics on the given axis and side.
   * This value represents the
   * distance from the origin in the positive direction of the first
   * tick.  A value of 0 or less will result in no minor ticks being
   * displayed (the default).
   *
   * @param axis graph axis
   * @param side axis side
   * @param tic tick spacing
   */
  public void setMinorTic(Axis axis, Edge side, float tic) {
    getAxis(axis, side).setMinorTic(tic);
  }

  /**
   * Returns the tick spacing for small tics on the given axis and side.
   * This value represents the
   * distance from the origin in the positive direction of the first
   * tick.
   *
   * @param axis graph axis
   * @param side axis side
   * @return tick spacing
   */
  public float getMinorTic(Axis axis, Edge side) {
    return getAxis(axis, side).getMinorTic();
  }

  /**
   * Sets whether to show tics on given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @param flag whether to show tics
   */
  public void setShowTics(Axis axis, Edge side, boolean flag) {
    getAxis(axis, side).setShowTics(flag);
  }

  /**
   * Returns whether to show tics on given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @return whether to show tics
   */
  public boolean isShowTics(Axis axis, Edge side) {
    return getAxis(axis, side).isShowTics();
  }

  /**
   * Set the label formatter to use for formatting of numbers on the given
   * axis and side. If a null formatter is given then the default formatter
   * is used.
   *
   * @param axis graph axis
   * @param side axis side
   * @param lf a label formatter
   */
  public void setTicLabelFormatter(Axis axis, Edge side, LabelFormatter lf) {
    getAxis(axis, side).setLabelFormatter(lf);
  }

  /**
   * Returns the label formatter used for the given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @return <code>LabelFormatter</code>
   */
  public LabelFormatter getTicLabelFormatter(Axis axis, Edge side) {
    final LabelFormatter lf = getAxis(axis, side).getLabelFormatter();
    return lf == null ? DEFAULT_FORMATTER : lf;
  }

  /**
   * Sets whether to use a log scale on the given axis and side.
   * Default is not to use log scale.
   *
   * @param axis graph axis
   * @param side axis side
   * @param flag whether to use log scale
   */
  public void setLogScale(Axis axis, Edge side, boolean flag) {
    getAxis(axis, side).setLogScale(flag);
    setRanges();
  }

  /**
   * Returns whether to use log scale on given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @return whether using log scale
   */
  public boolean isLogScale(Axis axis, Edge side) {
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
   * Returns whether any plot in this graph uses the given axis and side.
   *
   * @param axis graph axis
   * @param side axis side
   * @return whether any plot uses the y axis
   */
  public boolean uses(Axis axis, Edge side) {
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
    for (Edge side : Edge.values()) {
      final GraphAxis xAxis = mXAxes.get(side);
      if (xAxis.isLogScale() || xAxis.isLoAuto() || xAxis.isHiAuto() || xAxis.isTicAuto()) {
        boolean rangeSet = false;
        float xlo = Float.MAX_VALUE;
        float xhi = Float.MIN_VALUE;

        for (final Plot2D plot : mPlots) {
          if (!(plot instanceof GraphLine)) {
            if (plot.uses(Axis.X, side)) {
              final float plotXLo = plot.getLo(Axis.X);
              if (plotXLo < xlo) {
                xlo = plotXLo;
              }
              final float plotXHi = plot.getHi(Axis.X);
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

    for (Edge side : Edge.values()) {
      final GraphAxis yAxis = mYAxes.get(side);
      if (yAxis.isLogScale() || yAxis.isLoAuto() || yAxis.isHiAuto() || yAxis.isTicAuto()) {
        boolean rangeSet = false;
        float ylo = Float.MAX_VALUE;
        float yhi = Float.MIN_VALUE;

        for (final Plot2D plot : mPlots) {
          if (!(plot instanceof GraphLine)) {
            if (plot.uses(Axis.Y, side)) {
              final float plotYLo = plot.getLo(Axis.Y);
              if (plotYLo < ylo) {
                ylo = plotYLo;
              }
              final float plotYHi = plot.getHi(Axis.Y);
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
    final Graph2D g = new Graph2D();
        
    g.mTitle = mTitle;
    g.mKeyTitle = mKeyTitle;
    g.mShowKey = mShowKey;
    g.mColoredKey = mColoredKey;
    g.mKeyHorizontalPosition = mKeyHorizontalPosition;
    g.mKeyVerticalPosition = mKeyVerticalPosition;
    g.mDisplayBorder = mDisplayBorder;

    
    
    for (Edge i : Edge.values()) {
      g.mXAxes.put(i, (GraphAxis) mXAxes.get(i).clone());
      g.mYAxes.put(i, (GraphAxis) mYAxes.get(i).clone());
    }
    g.mPlots = new ArrayList<Plot2D>(mPlots);
    return g;
  }
}

