package com.reeltwo.plot;

import junit.framework.TestCase;

/*
 * Created: Mon May 21 16:35:49 2001
 *
 * $Log$
 * Revision 1.1  2004/05/09 22:37:30  richard
 * Initial revision
 *
 * Revision 1.3  2003/04/24 04:06:00  len
 * Run through the pretty printer.
 *
 * Revision 1.2  2002/04/15 22:54:34  sean
 * Removed extra imports
 *
 * Revision 1.1  2001/10/10 19:30:46  richard
 * Renamed Plot & Datum abstract tests.
 *
 * Revision 1.1  2001/10/08 22:04:26  richard
 * Initial coding of tests, bug fixes to others.
 *
 */
/**
 * Abstract JUnit tests for the Datum2D classes.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public abstract class AbstractDatum2DTest extends TestCase {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public AbstractDatum2DTest(String name) {
    super(name);
  }


  public abstract Datum2D getDatum();


  protected void setUp() {
  }


  protected void tearDown() {
  }


  public void testBasics() {
    Datum2D datum = getDatum();
    assertTrue(datum.getXLo() <= datum.getXHi());
    assertTrue(datum.getYLo() <= datum.getYHi());
  }
}

