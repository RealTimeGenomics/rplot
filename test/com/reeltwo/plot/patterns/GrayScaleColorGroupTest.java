package com.reeltwo.plot.patterns;

import java.awt.Paint;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the GrayScaleColorGroup class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class GrayScaleColorGroupTest extends AbstractPatternGroupTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public GrayScaleColorGroupTest(String name) {
    super(name);
  }


  public void setUp() {
    super.setUp();
  }


  public void tearDown() {
    super.tearDown();
  }

  public PatternGroup getPatternGroup() {
    return new GrayScaleColorGroup();
  }

  public void testDefaults() {
    PatternGroup pg = getPatternGroup();
    assertNotNull(pg);
    Paint [] patterns = pg.getPatterns();
    assertNotNull(patterns);
    assertEquals(10, patterns.length);

    for (int i = 0; i < patterns.length; i++) {
      assertNotNull(i + " is null", patterns[i]);
    }

    assertEquals("Grayscale 10", pg.getName());
  }

  public void testConstructor() {
    for (int i = 1; i < 100; i *= 3) {
      PatternGroup pg = new GrayScaleColorGroup(i);
      assertNotNull(pg);
      Paint [] patterns = pg.getPatterns();
      assertNotNull(patterns);
      assertEquals(i, patterns.length);

      for (int p = 0; p < patterns.length; p++) {
        assertNotNull(p + " is null", patterns[p]);
      }

      assertEquals("Grayscale " + i, pg.getName());
    }
  }

  public void testBadConstrutor() {
    try {
      new GrayScaleColorGroup(0);
      fail("constructor accepted bad parameter.");
    } catch (IllegalArgumentException iae) {
      ; // expected
    }
  }

  public static Test suite() {
    return new TestSuite(GrayScaleColorGroupTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
