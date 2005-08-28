package com.reeltwo.plot.patterns;

import java.awt.Paint;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * JUnit tests for the BW8x8PatternGroup class.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */
public class BW8x8PatternGroupTest extends AbstractPatternGroupTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public BW8x8PatternGroupTest(String name) {
    super(name);
  }


  public void setUp() {
    super.setUp();
  }


  public void tearDown() {
    super.tearDown();
  }

  public PatternGroup getPatternGroup() {
    return new BW8x8PatternGroup();
  }


  public void testDefaults() {
    PatternGroup pg = getPatternGroup();
    assertNotNull(pg);
    Paint[] patterns = pg.getPatterns();
    assertNotNull(patterns);
    assertEquals(50, patterns.length);

    for (int i = 0; i < patterns.length; i++) {
      assertNotNull(i + " is null", patterns[i]);
    }

    assertEquals("Black and White 8x8", pg.getName());    
  }


  public static Test suite() {
    return new TestSuite(BW8x8PatternGroupTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
