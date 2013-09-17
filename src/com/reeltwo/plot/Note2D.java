package com.reeltwo.plot;

/**
 * Structure to hold attributes of a group of notes on a 2D graph.
 *
 * @author Jonathan Purvis
 */
public class Note2D extends Box2D {

  private Arrow2D mArrow;
  private String mText;

  /**
   * Creates a note.
   *
   * @param text text to display in the box
   * @param left left edge of box
   * @param top top edge of box
   * @param right right edge of box
   * @param bottom bottom edge of box
   * @param arrowX end x co-ordinate of the arrow
   * @param arrowY end y co-ordinate of the arrow
   */
  public Note2D(String text, float left, float bottom, float right, float top, float arrowX, float arrowY) {
    super(left, bottom, right, top);
    mText = text;
    mArrow = new Arrow2D(left + (right - left) / 2, bottom, arrowX, arrowY);
  }

  public String getText() {
    return mText;
  }
  public Box2D getBox() {
    return this;
  }
  public Arrow2D getArrow() {
    return mArrow;
  }
}

