package com.reeltwo.plot.patterns;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import java.awt.image.BufferedImage;

/**
 * JUnit tests for the Pattern class.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class PatternTest extends TestCase {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public PatternTest(String name) {
    super(name);
  }


  public void setUp() {
  }


  public void tearDown() {
  }

  public void testLoading() {
    BufferedImage [] patterns = Pattern.getPatterns();
    assertNotNull("Null patterns", patterns);
    assertEquals("Wrong number of patterns", 48, patterns.length);
    for (int i = 0; i < patterns.length; i++) {
      assertEquals("Wrong width for pattern " + i, 8, patterns[i].getWidth());
      assertEquals("Wrong height for pattern " + i, 8, patterns[i].getHeight());
    }
  }


  public static Test suite() {
    return new TestSuite(PatternTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
