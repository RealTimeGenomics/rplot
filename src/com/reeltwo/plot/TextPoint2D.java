package com.reeltwo.plot;

/**
 * Text to display at a Point2D.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class TextPoint2D extends Point2D {
  /** text to be displayed */
  private String mText = "";


  /**
   * Sets text and co-ordinates to display it at.
   *
   * @param x x co-ordinate.
   * @param y y co-ordinate.
   * @param text text to display.
   */
  public TextPoint2D(float x, float y, String text) {
    super(x, y);
    setText(text);
  }


  /**
   * Sets the text to display.
   *
   * @param text some text.
   */
  public void setText(String text) {
    if (text == null) {
      mText = "";
    } else {
      mText = text;
    }
  }


  /**
   * Returns the text to display.
   *
   * @return some text.
   */
  public String getText() {
    return mText;
  }


  /**
   * Returns a string representation of this object.
   *
   * @return a co-ordinate string
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append('(').append(getX()).append(',').append(getY());
    if (getText() != null) {
      sb.append(',').append(getText());
    }
    sb.append(')');
    return sb.toString();
  }

}
