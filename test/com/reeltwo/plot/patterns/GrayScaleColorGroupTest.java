package com.reeltwo.plot.patterns;

import java.awt.Paint;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the GrayScaleColorGroup class.
 *
 * @author Richard Littin
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


  @Override
  public void setUp() {
    super.setUp();
  }


  @Override
  public void tearDown() {
    super.tearDown();
  }

  @Override
  public PatternGroup getPatternGroup() {
    return new GrayScaleColorGroup();
  }

  public void testDefaults() {
    final PatternGroup pg = getPatternGroup();
    assertNotNull(pg);
    final Paint[] patterns = pg.getPatterns();
    assertNotNull(patterns);
    assertEquals(10, patterns.length);

    for (int i = 0; i < patterns.length; i++) {
      assertNotNull(i + " is null", patterns[i]);
    }

    assertEquals("Grayscale 10", pg.getName());
  }

  public void testConstructor() {
    for (int i = 1; i < 100; i *= 3) {
      final PatternGroup pg = new GrayScaleColorGroup(i);
      assertNotNull(pg);
      final Paint[] patterns = pg.getPatterns();
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
    } catch (final IllegalArgumentException iae) {
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
