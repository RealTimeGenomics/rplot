package com.reeltwo.plot;

import com.reeltwo.plot.FillablePlot2D.FillStyle;

/**
 * JUnit tests for the FillablePlot2D abstract class.
 *
 * @author Richard Littin
 */
public abstract class AbstractFillablePlot2DTest extends AbstractPlot2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public AbstractFillablePlot2DTest(String name) {
    super(name);
  }

  public void testFill() {
    final FillablePlot2D plot = (FillablePlot2D) getPlot();
    assertEquals(FillStyle.NONE, plot.getFill());
    plot.setFill(FillStyle.NONE);
    assertEquals(FillStyle.NONE, plot.getFill());
    plot.setFill(FillStyle.COLOR);
    assertEquals(FillStyle.COLOR, plot.getFill());
    plot.setFill(FillStyle.PATTERN);
    assertEquals(FillStyle.PATTERN, plot.getFill());
  }

}
