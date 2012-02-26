/**
 * 
 */
package com.reeltwo.plot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests for DefaultFormatter class.
 * @author Richard Littin
 */
public class DefaultFormatterTest extends TestCase {

  /**
   * Test method for {@link com.reeltwo.plot.DefaultFormatter#setNumDecimalDigits(float)}.
   */
  public void testSetNumDecimalDigits() {
    final DefaultFormatter df = new DefaultFormatter();
    df.setNumDecimalDigits(1.0f);
    assertEquals("123", df.format(123.456f));
    df.setNumDecimalDigits(3.0f);
    assertEquals("123", df.format(123.456f));
    df.setNumDecimalDigits(10.0f);
    assertEquals("123", df.format(123.456f));
    df.setNumDecimalDigits(0.1f);
    assertEquals("123.5", df.format(123.456f));
    df.setNumDecimalDigits(0.001f);
    assertEquals("123.456", df.format(123.456f));
  }

  public static Test suite() {
    return new TestSuite(Graph2DTest.class);
  }

}
