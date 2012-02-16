package com.reeltwo.plot.patterns;

import java.awt.Paint;
import junit.framework.TestCase;

/**
 * JUnit tests for the abstract PatternGroup class.
 *
 * @author Richard Littin
 */

public abstract class AbstractPatternGroupTest extends TestCase {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public AbstractPatternGroupTest(String name) {
    super(name);
  }

  abstract PatternGroup getPatternGroup();

  @Override
  public void setUp() {
  }


  @Override
  public void tearDown() {
  }

  public void testBasics() {
    final PatternGroup pg = getPatternGroup();
    assertNotNull(pg);
    final Paint[] patterns = pg.getPatterns();
    assertNotNull(patterns);
    assertTrue("PatternGroup must return at least one pattern.", patterns.length > 0);
    for (int i = 0; i < patterns.length; i++) {
      assertNotNull(i + " is null", patterns[i]);
    }

    assertNotNull(pg.getName());
    assertNotNull(pg.getDescription());
  }
}
