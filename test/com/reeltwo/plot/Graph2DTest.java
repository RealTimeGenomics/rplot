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

  public void test1() {
    final Graph2D graph = new Graph2D();
    // check default/assummed values
    assertTrue(graph.getTitle().equals(""));
    assertTrue(graph.getKeyTitle().equals(""));
    assertTrue(graph.isBorder());

    for (Edge i : Edge.values()) {
      for (Axis axis : Axis.values()) {
        assertTrue(graph.getLabel(axis, i).equals(""));
        assertTrue(!graph.isGrid(axis, i));
      }

      assertEquals(-1.0f, graph.getLo(Axis.X, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis.X, i), PRECISION);
      assertEquals(-1.0f, graph.getLo(Axis.Y, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis.Y, i), PRECISION);

      assertEquals(0.2f, graph.getTic(Axis.X, i), PRECISION);
      assertEquals(0.2f, graph.getTic(Axis.Y, i), PRECISION);

      assertTrue(graph.isShowTics(Axis.X, i));
      assertTrue(graph.isShowTics(Axis.Y, i));

      assertNotNull(graph.getTicLabelFormatter(Axis.X, i));

      assertTrue(!graph.uses(Axis.X, i));
      assertTrue(!graph.uses(Axis.Y, i));
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

    for (Edge i : Edge.values()) {
      graph.setLabel(Axis.X, i, null);
      assertTrue(graph.getLabel(Axis.X, i).equals(""));
      graph.setLabel(Axis.X, i, "xlabel");
      assertTrue(graph.getLabel(Axis.X, i).equals("xlabel"));

      graph.setLabel(Axis.Y, i, null);
      assertTrue(graph.getLabel(Axis.Y, i).equals(""));
      graph.setLabel(Axis.Y, i, "ylabel");
      assertTrue(graph.getLabel(Axis.Y, i).equals("ylabel"));

      graph.setGrid(Axis.X, i, true);
      assertTrue(graph.isGrid(Axis.X, i));
      graph.setGrid(Axis.X, i, false);
      assertTrue(!graph.isGrid(Axis.X, i));

      graph.setGrid(Axis.X, i, true);
      assertTrue(graph.isGrid(Axis.X, i));
      graph.setGrid(Axis.X, i, false);
      assertTrue(!graph.isGrid(Axis.X, i));

      graph.setLo(Axis.X, i, 0.0f);
      assertEquals(0.0f, graph.getLo(Axis.X, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis.X, i), PRECISION);
      graph.setAutoScale(Axis.X, i);
      graph.setHi(Axis.X, i, 0.0f);
      assertEquals(-1.0f, graph.getLo(Axis.X, i), PRECISION);
      assertEquals(0.0f, graph.getHi(Axis.X, i), PRECISION);
      graph.setRange(Axis.X, i, 1.0f, 2.0f);
      assertEquals(1.0f, graph.getLo(Axis.X, i), PRECISION);
      assertEquals(2.0f, graph.getHi(Axis.X, i), PRECISION);
      graph.setAutoScale(Axis.X, i);
      assertEquals(-1.0f, graph.getLo(Axis.X, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis.X, i), PRECISION);

      graph.setLo(Axis.Y, i, 0.0f);
      assertEquals(0.0f, graph.getLo(Axis.Y, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis.Y, i), PRECISION);
      graph.setAutoScale(Axis.Y, i);
      graph.setHi(Axis.Y, i, 0.0f);
      assertEquals(-1.0f, graph.getLo(Axis.Y, i), PRECISION);
      assertEquals(0.0f, graph.getHi(Axis.Y, i), PRECISION);
      graph.setRange(Axis.Y, i, 1.0f, 2.0f);
      assertEquals(1.0f, graph.getLo(Axis.Y, i), PRECISION);
      assertEquals(2.0f, graph.getHi(Axis.Y, i), PRECISION);
      graph.setAutoScale(Axis.Y, i);
      assertEquals(-1.0f, graph.getLo(Axis.Y, i), PRECISION);
      assertEquals(1.0f, graph.getHi(Axis.Y, i), PRECISION);

      graph.setTic(Axis.X, i, 0.34f);
      assertEquals(0.34f, graph.getTic(Axis.X, i), PRECISION);
      graph.setAutoScaleTic(Axis.X, i);
      assertEquals(0.2f, graph.getTic(Axis.X, i), PRECISION);

      graph.setTic(Axis.Y, i, 0.34f);
      assertEquals(0.34f, graph.getTic(Axis.Y, i), PRECISION);
      graph.setAutoScaleTic(Axis.Y, i);
      assertEquals(0.2f, graph.getTic(Axis.Y, i), PRECISION);

      graph.setShowTics(Axis.X, i, false);
      assertTrue(!graph.isShowTics(Axis.X, i));
      graph.setShowTics(Axis.X, i, true);
      assertTrue(graph.isShowTics(Axis.X, i));

      graph.setShowTics(Axis.Y, i, false);
      assertTrue(!graph.isShowTics(Axis.Y, i));
      graph.setShowTics(Axis.Y, i, true);
      assertTrue(graph.isShowTics(Axis.Y, i));
    }

    graph.setBorder(false);
    assertTrue(!graph.isBorder());
    graph.setBorder(true);
    assertTrue(graph.isBorder());

    final String[] labels = new String[]{"one", "two"};
    for (Edge i : Edge.values()) {
      for (Axis axis : Axis.values()) {
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
    final PointPlot2D pplot2 = new PointPlot2D(Edge.ALTERNATE, Edge.ALTERNATE);
    pplot2.setData(new Point2D[]{new Point2D(2.0f, 4.0f), new Point2D(-4.4f, 7.3f)});
    graph.addPlot(pplot2);

    assertNotNull(graph.getPlots());
    assertTrue(graph.getPlots().length == 2);
    for (Axis axis : Axis.values()) {
      for (Edge side : Edge.values()) {
        assertTrue(graph.uses(axis, side));
      }
    }

    assertEquals(-1.5f, graph.getLo(Axis.X, Edge.MAIN), PRECISION);
    assertEquals(1.5f, graph.getHi(Axis.X, Edge.MAIN), PRECISION);
    assertEquals(0.5f, graph.getTic(Axis.X, Edge.MAIN), PRECISION);

    assertEquals(3.0f, graph.getLo(Axis.Y, Edge.MAIN), PRECISION);
    assertEquals(5.2f, graph.getHi(Axis.Y, Edge.MAIN), PRECISION);
    assertEquals(0.5f, graph.getTic(Axis.Y, Edge.MAIN), PRECISION);

    assertEquals(-5.0f, graph.getLo(Axis.X, Edge.ALTERNATE), PRECISION);
    assertEquals(2.0f, graph.getHi(Axis.X, Edge.ALTERNATE), PRECISION);
    assertEquals(1.0f, graph.getTic(Axis.X, Edge.ALTERNATE), PRECISION);

    assertEquals(4.0f, graph.getLo(Axis.Y, Edge.ALTERNATE), PRECISION);
    assertEquals(7.5f, graph.getHi(Axis.Y, Edge.ALTERNATE), PRECISION);
    assertEquals(0.5f, graph.getTic(Axis.Y, Edge.ALTERNATE), PRECISION);
  }


  public void testConstants() {
    assertEquals(Edge.MAIN, Graph2D.ONE);
    assertEquals(Edge.ALTERNATE, Graph2D.TWO);
    assertEquals(Axis.X, Graph2D.X);
    assertEquals(Axis.Y, Graph2D.Y);
  }

  public static Test suite() {
    return new TestSuite(Graph2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
