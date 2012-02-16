package com.reeltwo.plot.renderer;

import junit.framework.Test;

import junit.framework.TestCase;
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
 * Revision 1.2  2003/04/24 04:06:00  len
 * Run through the pretty printer.
 *
 * Revision 1.1  2001/10/08 22:04:26  richard
 * Initial coding of tests, bug fixes to others.
 *
 */
/**
 * JUnit tests for the ImageWriter class.
 *
 * @author Richard Littin
 */

public class ImageWriterTest extends TestCase {

  /**
   * Constructor (needed for JUnit)
   *
   * @param name A string which names the object.
   */
  public ImageWriterTest(String name) {
    super(name);
  }


  @Override
  public void setUp() {
  }


  @Override
  public void tearDown() {
  }


  public void test1() {
  }


  public static Test suite() {
    return new TestSuite(ImageWriterTest.class);
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
