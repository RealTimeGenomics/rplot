package com.reeltwo.plot.patterns;

import java.awt.Paint;
import junit.framework.TestCase;

/**
 * JUnit tests for the abstract PatternGroup class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
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

  public void setUp() {
  }


  public void tearDown() {
  }

  public void testBasics() {
    PatternGroup pg = getPatternGroup();
    assertNotNull(pg);
    Paint [] patterns = pg.getPatterns();
    assertNotNull(patterns);
    final int count = pg.getPatternCount();
    assertTrue("PatternGroup must return at least one pattern.", count > 0);
    assertEquals(count, patterns.length);
    for (int i = 0; i < count; i++) {
      Paint pattern = pg.getPattern(i);
      assertNotNull(i + " is null", pattern);
      assertEquals(i + " not equal", pattern, patterns[i]);
    }
  }

  public void testOutOfBounds() {
    PatternGroup pg = getPatternGroup();
    try {
      pg.getPattern(-1);
      fail("accepted index of -1");
    } catch (Exception e) {
      ; // should get here
    }

    try {
      pg.getPattern(pg.getPatternCount() + 1);
      fail("accepted index > than number of patterns");
    } catch (Exception e) {
      ; // should get here
    }
  }
}
