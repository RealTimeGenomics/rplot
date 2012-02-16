package com.reeltwo.plot;

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
    assertEquals(FillablePlot2D.NO_FILL, plot.getFill());
    plot.setFill(FillablePlot2D.NO_FILL);
    assertEquals(FillablePlot2D.NO_FILL, plot.getFill());
    plot.setFill(FillablePlot2D.COLOR_FILL);
    assertEquals(FillablePlot2D.COLOR_FILL, plot.getFill());
    plot.setFill(FillablePlot2D.PATTERN_FILL);
    assertEquals(FillablePlot2D.PATTERN_FILL, plot.getFill());
  }

  public void testBadArguments() {
    final FillablePlot2D plot = (FillablePlot2D) getPlot();
    try {
      plot.setFill(FillablePlot2D.NO_FILL - 1);
      fail("accepted bad argument");
    } catch (final IllegalArgumentException iae) {
      ; // should get here
    }
    try {
      plot.setFill(FillablePlot2D.PATTERN_FILL + 1);
      fail("accepted bad argument");
    } catch (final IllegalArgumentException iae) {
      ; // should get here
    }
  }
}
