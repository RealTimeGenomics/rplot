package com.reeltwo.plot;

import junit.framework.Test;

import junit.framework.TestSuite;

/*
 * Created: Mon May 21 16:35:49 2001
 *
 * $Log$
 * Revision 1.2  2005/05/26 20:31:41  richard
 * fixes to author tag
 *
 * Revision 1.1.1.1  2004/05/09 22:37:30  richard
 * initial import
 *
 * Revision 1.4  2003/04/24 04:06:00  len
 * Run through the pretty printer.
 *
 * Revision 1.3  2002/04/15 22:54:34  sean
 * Removed extra imports
 *
 * Revision 1.2  2001/10/10 19:30:46  richard
 * Renamed Plot & Datum abstract tests.
 *
 * Revision 1.1  2001/10/08 22:04:26  richard
 * Initial coding of tests, bug fixes to others.
 *
 */
/**
 * JUnit tests for the TextPoint2D class.
 *
 * @author Richard Littin (richard@reeltwo.com)
 * @version $Revision$
 */

public class TextPoint2DTest extends AbstractDatum2DTest {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public TextPoint2DTest(String name) {
    super(name);
  }


  public Datum2D getDatum() {
    return new TextPoint2D(1, 2, "text");
  }


  public void test1() {
    TextPoint2D tpoint = new TextPoint2D(1, 2, "text");
    assertTrue(tpoint instanceof Point2D);
    assertTrue(tpoint.getText().equals("text"));

    tpoint.setText(null);
    assertTrue(tpoint.getText().equals(""));
    tpoint.setText("house");
    assertTrue(tpoint.getText().equals("house"));

    tpoint = new TextPoint2D(1, 2, null);
    assertTrue(tpoint.getText().equals(""));
  }


  public static Test suite() {
    return new TestSuite(TextPoint2DTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
