package com.reeltwo.plot.patterns;

import java.awt.Paint;
import junit.framework.TestCase;

/**
 * JUnit tests for the abstract PatternGroup class.
 *
 * @author Richard Littin (richard@reeltwo.com)
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
    assertTrue("PatternGroup must return at least one pattern.", patterns.length > 0);
    for (int i = 0; i < patterns.length; i++) {
      assertNotNull(i + " is null", patterns[i]);
    }

    assertNotNull(pg.getName());
    assertNotNull(pg.getDescription());
  }
}
