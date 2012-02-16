package com.reeltwo.plot.patterns;

import java.awt.Paint;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the DefaultColorGroup class.
 *
 * @author Richard Littin
 */
public class DefaultColorGroupTest extends AbstractPatternGroupTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public DefaultColorGroupTest(String name) {
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
    return new DefaultColorGroup();
  }

  public void testDefaults() {
    final PatternGroup pg = getPatternGroup();
    assertNotNull(pg);
    final Paint[] patterns = pg.getPatterns();
    assertNotNull(patterns);
    assertEquals(8, patterns.length);

    for (int i = 0; i < patterns.length; i++) {
      assertNotNull(i + " is null", patterns[i]);
    }

    assertEquals("Default Colors", pg.getName());
  }

  public static Test suite() {
    return new TestSuite(DefaultColorGroupTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
