package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * Plots a graph in a Swing JPanel described by the data in a Plot data
 * object.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */

public class PlotPanel extends JPanel {
  protected Color[] mColors = AWTDefaults.COLORS;

  private Action mPrintAction =
    new AbstractAction("Print", null) {
      public void actionPerformed(ActionEvent e) {
        GraphPrinter gp = new GraphPrinter();
        gp.printGraph(mGraph);
      }
    };
  private Action mSaveAction =
    new AbstractAction("Save as PNG", null) {
      public void actionPerformed(ActionEvent e) {
        boolean ok = false;
        while (!ok) {
          JFileChooser mChooser = new JFileChooser();
          int returnVal = mChooser.showSaveDialog(null);
          ok = true;
          if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File file = mChooser.getSelectedFile();
            if (file.exists()) {
              JOptionPane op = new JOptionPane();
              int ret = op.showConfirmDialog(null,
                "Do you want to overwrite " + file.getName() + "?",
                "File Exists",
                JOptionPane.YES_NO_OPTION);
              if (ret == JOptionPane.NO_OPTION) {
                ok = false;
              }
            }
            if (ok) {
              String errorMessage = null;
              try {
                Dimension d = PlotPanel.this.getSize();
                final int screenWidth = (int) d.getWidth();
                final int screenHeight = (int) d.getHeight();

                Font font = null;
                if (PlotPanel.this.mScreenFontSize > 0) {
                  Font old = PlotPanel.this.getGraphics().getFont();
                  font = new Font(old.getName(), old.getStyle(), mScreenFontSize);
                }

                ImageWriter iw = new ImageWriter(mGraphicsRenderer);
                iw.toPNG(file.getCanonicalPath(), PlotPanel.this.mGraph, screenWidth, screenHeight, font);
              } catch (Exception ioe) {
                errorMessage = "Failed to write file " + ioe.getMessage();
              } finally {
                if (errorMessage != null) {
                  System.err.println(errorMessage);
                }
              }
            }
          }
        }

      }
    };
  private Action mSnapShotAction =
    new AbstractAction("Snap Shot", null) {
      public void actionPerformed(ActionEvent e) {
        try {
          SwingPlot sp = new SwingPlot("Snap Shot");
          sp.plot((Graph2D) mGraph.clone());
        } catch (CloneNotSupportedException cnse) {
          System.err.println("Failed to clone graph: " + cnse.getMessage());
        }
      }
    };


  public Action getPrintAction() {
    return mPrintAction;
  }

  public JPopupMenu getPopup() {
    return mPopup;
  }

  /** the data that is to be plotted */
  private Graph2D mGraph = null;
  private Mapping[] mMapping = null;

  private JPopupMenu mPopup = new JPopupMenu();
  private JMenuItem mPrintMenuItem = new JMenuItem(mPrintAction);
  private JMenuItem mSaveMenuItem = new JMenuItem(mSaveAction);
  private JMenuItem mSnapShotMenuItem = new JMenuItem(mSnapShotAction);

  private int mScreenFontSize = -1;
  private int mPrintFontSize = -1;

  private GraphicsRenderer mGraphicsRenderer = new GraphicsRenderer(mColors);

  private boolean mBufferGraphs = false;
  private BufferedImage mBI = null;

  /** Default constructor. */
  public PlotPanel() {
    super(true);
    setBackground(Color.WHITE);

    //mPopup.add(mPrintMenuItem);
    //mPopup.add(mSaveMenuItem);
    //mPopup.add(mSnapShotMenuItem);
    //mPopup.setLightWeightPopupEnabled(false);

    //Add listener to components that can bring up pop-up menus.
    //MouseListener popupListener = new PopupListener();
    //this.addMouseListener(popupListener);

    //mGraphicsRenderer.setGraphColor(new Color(0.78f,0.86f,0.94f));
    mGraphicsRenderer.setGraphShadowWidth(2);
  }


  /**
   * Sets the font point sizes for labels drawn on the screen and on
   * the printer.
   *
   * @param screenFontSize size of screen font
   * @param printFontSize size of printer font
   */
  public PlotPanel(int screenFontSize, int printFontSize) {
    this();
    mScreenFontSize = screenFontSize;
    mPrintFontSize = printFontSize;
  }


  /**
   * Sets the font point sizes for labels drawn on the screen and on
   * the printer, and whether to show a pop-up menu that contains extra
   * features.
   *
   * @param screenFontSize size of screen font
   * @param printFontSize size of printer font
   * @param showPopup whether to enable pop-up menu.
   */
  public PlotPanel(int screenFontSize, int printFontSize, boolean showPopup) {
    this(screenFontSize, printFontSize);

    if (showPopup) {
      showPopup();
    }
  }


  /**
   * Sets whether to show a pop-up menu that contains extra features.
   *
   * @param showPopup whether to enable pop-up menu.
   */
  public PlotPanel(boolean showPopup) {
    this();

    if (showPopup) {
      showPopup();
    }
  }


  public void setGraphBGColor(Color color) {
    mGraphicsRenderer.setGraphColor(color);
  }


  private void showPopup() {
    mPopup.add(mPrintMenuItem);
    mPopup.add(mSaveMenuItem);
    mPopup.add(mSnapShotMenuItem);
    mPopup.setLightWeightPopupEnabled(false);

    //Add listener to components that can bring up pop-up menus.
    MouseListener popupListener = new PopupListener();
    this.addMouseListener(popupListener);
  }


  /**
   * Sets the colors used for lines in plots. Note color[1] is the
   * first color used in graphs. color[0] is usually black.
   *
   * @param colors and array of colors
   */
  public void setColors(Color[] colors) {
    if (colors == null) {
      throw new NullPointerException("null color array given.");
    }
    if (colors.length == 0) {
      throw new IllegalArgumentException("Must be at least one color.");
    }
    for (int i = 0; i < colors.length; i++) {
      if (colors[i] == null) {
        throw new NullPointerException("null color given.");
      }
    }
    mColors = colors;
    mGraphicsRenderer = new GraphicsRenderer(mColors);
  }


  /**
   * Resets the colors used for lines in plots to system defaults.
   */
  public void resetColors() {
    setColors(AWTDefaults.COLORS);
  }


  /**
   * Draws a graph with the given plot data.
   *
   * @param graph TODO Description.
   */
  public void plot(Graph2D graph) {
    mBI = null;
    mGraph = graph;
    updateUI();
  }


  /**
   * return the plot data that the panel is holding
   *
   * @return The graph value.
   */
  public Graph2D getGraph() {
    return mGraph;
  }


  public Mapping[] getMapping() {
    return mMapping;
  }


  public int getScreenFontSize() {
    return mScreenFontSize;
  }

  public void setAntialiasing(boolean flag) {
    mGraphicsRenderer.setAntialiasing(flag);
  }

  public void setTextAntialiasing(boolean flag) {
    mGraphicsRenderer.setTextAntialiasing(flag);
  }

  /**
   * Sets whether to buffer graph images.  Speeds things up when it
   * comes to redrawing the same graph, at the tradeoff of extra
   * memory use. 
   *
   * @param flag whether to buffer graphs
   */
  public void setBufferGraphs(boolean flag) {
    mBufferGraphs = flag;
  }

  /**
   * Overrides JPanel.paintComponent and actually renders the graph in
   * the given Graphics object.
   *
   * @param g a Graphics component.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Dimension d = getSize();

    final int screenWidth = (int) d.getWidth();
    final int screenHeight = (int) d.getHeight();

    if (mBufferGraphs) {
      //long start = new Date().getTime();
      BufferedImage bi = mBI;
      if (bi == null || bi.getWidth() != screenWidth || bi.getHeight() != screenHeight) {
        bi = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        mBI = bi;
        //long imgend = new Date().getTime();
        Graphics g2 = bi.createGraphics();
        g2.setColor(getBackground());
        g2.fillRect(0, 0, screenWidth, screenHeight);

        if (mScreenFontSize > 0) {
          Font old = g2.getFont();
          g2.setFont(new Font(old.getName(), old.getStyle(), mScreenFontSize));
        }
      
        mGraphicsRenderer.drawGraph(mGraph, g2, 0, 0, screenWidth, screenHeight);
        mMapping = mGraphicsRenderer.getMappings();
        //long rendend = new Date().getTime();
        //System.err.print("(" + (imgend - start) + " " + (rendend - imgend) + ") ");
      }
      g.drawImage(bi, 0, 0, null);
      //long screenend = new Date().getTime();
      //System.err.println((screenend - start));
    } else {
      if (mScreenFontSize > 0) {
        Font old = g.getFont();
        g.setFont(new Font(old.getName(), old.getStyle(), mScreenFontSize));
      }
      
      mGraphicsRenderer.drawGraph(mGraph, g, 0, 0, screenWidth, screenHeight);
      mMapping = mGraphicsRenderer.getMappings();
    }
  }


  /**
   * A class required to listen for right-clicks
   *
   * @author <a href="mailto:len@reeltwo.com">len</a>
   * @version $Revision$
   */
  private class PopupListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      maybeShowPopup(e);
    }


    public void mouseReleased(MouseEvent e) {
      maybeShowPopup(e);
    }


    private void maybeShowPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
        mPopup.show(e.getComponent(), e.getX(), e.getY());
      }
    }
  }
}
