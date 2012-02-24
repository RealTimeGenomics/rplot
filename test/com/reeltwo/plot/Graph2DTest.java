package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit tests for the Graph2D class.
 *
 * @author Richard Littin
 */
public class Graph2DTest extends TestCase {

  private static final float PRECISION = 0.0001f;

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public Graph2DTest(String name) {
    super(name);
  }


  @Override
  public void setUp() {
  }


  @Override
  public void tearDown() {
  }


  public void test1() {
    final Graph2D graph = new Graph2D();
    // check default/assummed values
    assertTrue(graph.getTitle().equals(""));
    assertTrue(graph.getKeyTitle().equals(""));
    assertTrue(graph.isBorder());

    for (AxisSide i : AxisSide.values()) {
      for (Axis2D axis : Axis2D.values()) {
        assertTrue(graph.getLabel(axis, i).equals(""));
        assertTrue(!graph.isGrid(axis, i));
      }

      assertEquals(-1.0f, graph.getLo(Axis2D.X, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis2D.X, i), PRECISION);
      assertEquals(-1.0f, graph.getLo(Axis2D.Y, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis2D.Y, i), PRECISION);

      assertEquals(0.2f, graph.getTic(Axis2D.X, i), PRECISION);
      assertEquals(0.2f, graph.getTic(Axis2D.Y, i), PRECISION);

      assertTrue(graph.isShowTics(Axis2D.X, i));
      assertTrue(graph.isShowTics(Axis2D.Y, i));

      assertNotNull(graph.getTicLabelFormatter(Axis2D.X, i));

      assertTrue(!graph.uses(Axis2D.X, i));
      assertTrue(!graph.uses(Axis2D.Y, i));
    }

    // check sets/gets
    graph.setTitle(null);
    assertTrue(graph.getTitle().equals(""));
    graph.setTitle("title");
    assertTrue(graph.getTitle().equals("title"));

    graph.setKeyTitle(null);
    assertTrue(graph.getKeyTitle().equals(""));
    graph.setKeyTitle("key title");
    assertTrue(graph.getKeyTitle().equals("key title"));

    for (AxisSide i : AxisSide.values()) {
      graph.setLabel(Axis2D.X, i, null);
      assertTrue(graph.getLabel(Axis2D.X, i).equals(""));
      graph.setLabel(Axis2D.X, i, "xlabel");
      assertTrue(graph.getLabel(Axis2D.X, i).equals("xlabel"));

      graph.setLabel(Axis2D.Y, i, null);
      assertTrue(graph.getLabel(Axis2D.Y, i).equals(""));
      graph.setLabel(Axis2D.Y, i, "ylabel");
      assertTrue(graph.getLabel(Axis2D.Y, i).equals("ylabel"));

      graph.setGrid(Axis2D.X, i, true);
      assertTrue(graph.isGrid(Axis2D.X, i));
      graph.setGrid(Axis2D.X, i, false);
      assertTrue(!graph.isGrid(Axis2D.X, i));

      graph.setGrid(Axis2D.X, i, true);
      assertTrue(graph.isGrid(Axis2D.X, i));
      graph.setGrid(Axis2D.X, i, false);
      assertTrue(!graph.isGrid(Axis2D.X, i));

      graph.setLo(Axis2D.X, i, 0.0f);
      assertEquals(0.0f, graph.getLo(Axis2D.X, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis2D.X, i), PRECISION);
      graph.setAutoScale(Axis2D.X, i);
      graph.setHi(Axis2D.X, i, 0.0f);
      assertEquals(-1.0f, graph.getLo(Axis2D.X, i), PRECISION);
      assertEquals(0.0f, graph.getHi(Axis2D.X, i), PRECISION);
      graph.setRange(Axis2D.X, i, 1.0f, 2.0f);
      assertEquals(1.0f, graph.getLo(Axis2D.X, i), PRECISION);
      assertEquals(2.0f, graph.getHi(Axis2D.X, i), PRECISION);
      graph.setAutoScale(Axis2D.X, i);
      assertEquals(-1.0f, graph.getLo(Axis2D.X, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis2D.X, i), PRECISION);

      graph.setLo(Axis2D.Y, i, 0.0f);
      assertEquals(0.0f, graph.getLo(Axis2D.Y, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis2D.Y, i), PRECISION);
      graph.setAutoScale(Axis2D.Y, i);
      graph.setHi(Axis2D.Y, i, 0.0f);
      assertEquals(-1.0f, graph.getLo(Axis2D.Y, i), PRECISION);
      assertEquals(0.0f, graph.getHi(Axis2D.Y, i), PRECISION);
      graph.setRange(Axis2D.Y, i, 1.0f, 2.0f);
      assertEquals(1.0f, graph.getLo(Axis2D.Y, i), PRECISION);
      assertEquals(2.0f, graph.getHi(Axis2D.Y, i), PRECISION);
      graph.setAutoScale(Axis2D.Y, i);
      assertEquals(-1.0f, graph.getLo(Axis2D.Y, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis2D.Y, i), PRECISION);

      graph.setTic(Axis2D.X, i, 0.34f);
      assertEquals(0.34f, graph.getTic(Axis2D.X, i), PRECISION);
      graph.setAutoScaleTic(Axis2D.X, i);
      assertEquals(0.2f, graph.getTic(Axis2D.X, i), PRECISION);

      graph.setTic(Axis2D.Y, i, 0.34f);
      assertEquals(0.34f, graph.getTic(Axis2D.Y, i), PRECISION);
      graph.setAutoScaleTic(Axis2D.Y, i);
      assertEquals(0.2f, graph.getTic(Axis2D.Y, i), PRECISION);

      graph.setShowTics(Axis2D.X, i, false);
      assertTrue(!graph.isShowTics(Axis2D.X, i));
      graph.setShowTics(Axis2D.X, i, true);
      assertTrue(graph.isShowTics(Axis2D.X, i));

      graph.setShowTics(Axis2D.Y, i, false);
      assertTrue(!graph.isShowTics(Axis2D.Y, i));
      graph.setShowTics(Axis2D.Y, i, true);
      assertTrue(graph.isShowTics(Axis2D.Y, i));
    }

    graph.setBorder(false);
    assertTrue(!graph.isBorder());
    graph.setBorder(true);
    assertTrue(graph.isBorder());

    final String[] labels = new String[]{"one", "two"};
    for (AxisSide i : AxisSide.values()) {
      for (Axis2D axis : Axis2D.values()) {
        graph.setTicLabelFormatter(axis, i, new StringFormatter(labels));
        assertTrue(graph.getTicLabelFormatter(axis, i) instanceof StringFormatter);
      }
    }
  }

  public void test2() {
    final Graph2D graph = new Graph2D();
    final PointPlot2D pplot = new PointPlot2D();
    pplot.setData(new Point2D[]{new Point2D(1.3f, 3.1f), new Point2D(-1.2f, 5.1f)});
    graph.addPlot(pplot);
    final PointPlot2D pplot2 = new PointPlot2D(AxisSide.TWO, AxisSide.TWO);
    pplot2.setData(new Point2D[]{new Point2D(2.0f, 4.0f), new Point2D(-4.4f, 7.3f)});
    graph.addPlot(pplot2);

    assertNotNull(graph.getPlots());
    assertTrue(graph.getPlots().length == 2);
    for (Axis2D axis : Axis2D.values()) {
      for (AxisSide side : AxisSide.values()) {
        assertTrue(graph.uses(axis, side));
      }
    }

    assertEquals(-1.5f, graph.getLo(Axis2D.X, AxisSide.ONE), PRECISION);
    assertEquals(1.5f, graph.getHi(Axis2D.X, AxisSide.ONE), PRECISION);
    assertEquals(0.5f, graph.getTic(Axis2D.X, AxisSide.ONE), PRECISION);

    assertEquals(3.0f, graph.getLo(Axis2D.Y, AxisSide.ONE), PRECISION);
    assertEquals(5.2f, graph.getHi(Axis2D.Y, AxisSide.ONE), PRECISION);
    assertEquals(0.5f, graph.getTic(Axis2D.Y, AxisSide.ONE), PRECISION);

    assertEquals(-5.0f, graph.getLo(Axis2D.X, AxisSide.TWO), PRECISION);
    assertEquals(2.0f, graph.getHi(Axis2D.X, AxisSide.TWO), PRECISION);
    assertEquals(1.0f, graph.getTic(Axis2D.X, AxisSide.TWO), PRECISION);

    assertEquals(4.0f, graph.getLo(Axis2D.Y, AxisSide.TWO), PRECISION);
    assertEquals(7.5f, graph.getHi(Axis2D.Y, AxisSide.TWO), PRECISION);
    assertEquals(0.5f, graph.getTic(Axis2D.Y, AxisSide.TWO), PRECISION);
  }


  public static Test suite() {
    return new TestSuite(Graph2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
