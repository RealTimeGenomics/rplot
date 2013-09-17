package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of notes on a 2D graph.
 *
 * @author Jonathan Purvis
 */
public class NotePlot2D extends FillablePlot2D {

  /**
   * Default constructor setting the default axes used to the bottom
   * x axis and the left y axis.
   */
  public NotePlot2D() {
    super();
  }


  /**
   * Constructor setting which axes to use in the plot.
   *
   * @param x x axis to use.
   * @param y y axis to use.
   */
  public NotePlot2D(Edge x, Edge y) {
    super(x, y);
  }


  /**
   * Sets the data used in this plot.
   *
   * @param data an array of Datum2D's
   */
  @Override
  public void setData(Datum2D[] data) {
    if (!(data instanceof Note2D[])) {
      throw new UnsupportedOperationException("Cannot set " + data.getClass().getName() + " in NotePlot2D.");
    }
    super.setData(data);
  }

  /**
   * Gets the data used as a <code>BoxPlot2D</code>.
   *
   * @return a <code>BoxPlot2D</code>.
   */
  public BoxPlot2D getBoxPlot() {
    Note2D[] data = (Note2D[]) getData();
    BoxPlot2D boxPlot = new BoxPlot2D(); //TODO: find the axes and pass them in the constructor
    boxPlot.setData(data);
    boxPlot.setFill(getFill());
    boxPlot.setColor(getColor());
    boxPlot.setBorder(isBorder());
    return boxPlot;
  }

  /**
   * Gets the data used as a <code>ArrowPlot2D</code>.
   *
   * @return a <code>ArrowPlot2D</code>.
   */
  public ArrowPlot2D getArrowPlot() {
    Note2D[] data = (Note2D[]) getData();
    ArrowPlot2D arrowPlot = new ArrowPlot2D(); //TODO: find the axes and pass them in the constructor
    Arrow2D[] arrowData = new Arrow2D[data.length];
    for (int i = 0; i < data.length; i++) {
      arrowData[i] = data[i].getArrow();
    }
    arrowPlot.setData(arrowData);
    return arrowPlot;
  }

}
