package com.reeltwo.plot.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;


/**
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class ZoomListener extends MouseInputAdapter {


  private ZoomPlotPanel mGlassPane;
  private Container mContainer;


  public ZoomListener(ZoomPlotPanel glassPane, Container container) {
    mGlassPane = glassPane;
    mContainer = container;
  }


  public void mouseMoved(MouseEvent e) {
    //System.err.println("Mouse moved");
    redispatchMouseEvent(e);
  }


  public void mouseDragged(MouseEvent e) {
    //System.err.println("Mouse Dragged");
    if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
      mGlassPane.setPointTwo(e.getPoint());
      mGlassPane.repaint();
    }
    redispatchMouseEvent(e);
  }


  public void mouseClicked(MouseEvent e) {
    //System.err.println("Mouse Clicked");
    redispatchMouseEvent(e);
  }


  public void mouseEntered(MouseEvent e) {
    redispatchMouseEvent(e);
  }


  public void mouseExited(MouseEvent e) {
    redispatchMouseEvent(e);
  }


  public void mousePressed(MouseEvent e) {
    //System.err.println("Mouse Pressed");
    if ((e.getModifiers() & InputEvent.BUTTON1_MASK)
      == InputEvent.BUTTON1_MASK) {
      mGlassPane.setPointOne(e.getPoint());
      mGlassPane.setPointTwo(e.getPoint());
      mGlassPane.repaint();
    }
    redispatchMouseEvent(e);
  }


  public void mouseReleased(MouseEvent e) {
    //System.err.println("Mouse Released");
    if ((e.getModifiers() & InputEvent.BUTTON1_MASK)
      == InputEvent.BUTTON1_MASK) {
      mGlassPane.setPointTwo(e.getPoint());
      mGlassPane.zoomIn();
      mGlassPane.repaint();
    }
    redispatchMouseEvent(e);
  }


  private void redispatchMouseEvent(MouseEvent e) {

    Point glassPanePoint = e.getPoint();
    Component component = null;
    Container container = mContainer;
    Point containerPoint = SwingUtilities.convertPoint(mGlassPane, glassPanePoint, mContainer);
    int eventID = e.getID();

    component = SwingUtilities.getDeepestComponentAt(container, containerPoint.x, containerPoint.y);

    //System.err.println("Component = " + component);
    if (component == null) {
      return;
    }
    Point componentPoint = SwingUtilities.convertPoint(mGlassPane, glassPanePoint, component);
    component.dispatchEvent(new MouseEvent(component, eventID, e.getWhen(), e.getModifiers(),
                                           componentPoint.x, componentPoint.y,
                                           e.getClickCount(), e.isPopupTrigger()));

  }
}
