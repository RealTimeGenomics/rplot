package com.reeltwo.plot;

/**
 * Text to display at a Point2D.
 *
 * @author Richard Littin
 */

public class TextPoint2D extends ObjectPoint2D {

  /**
   * Sets text and co-ordinates to display it at.
   *
   * @param x x co-ordinate.
   * @param y y co-ordinate.
   * @param text text to display.
   */
  public TextPoint2D(float x, float y, String text) {
    super(x, y, text);
  }


  /**
   * Sets the text to display.
   *
   * @param text some text.
   */
  public void setText(String text) {
    setObject(text);
  }


  /**
   * Returns the text to display.
   *
   * @return some text.
   */
  public String getText() {
    final Object obj = getObject();
    if (obj == null) {
      return "";
    }
    return (String) obj;
  }

}
