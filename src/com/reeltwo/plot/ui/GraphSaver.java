package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * Handy utility class for saving graphs as images.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class GraphSaver {
  private Color [] mColors = AWTDefaults.COLORS;
  private Paint [] mPatterns = null;

  private int mFontSize = -1; // use default font size
  private int mWidth = 800;
  private int mHeight = 600;

  private final JFileChooser mChooser;

  GraphSaver() {
    mChooser = new JFileChooser();
    PNGFileFilter pff = new PNGFileFilter();
    mChooser.addChoosableFileFilter(pff);
    mChooser.setFileFilter(pff);
  }

  public void setFontSize(int size) {
    mFontSize = size;
  }
  
  public int getFontSize() {
    return mFontSize;
  }

  public void setSize(int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("width and height must be >= 0: " + width + " : " + height);
    }
    mWidth = width;
    mHeight = height;
  }
  
  public void setColors(Color [] colors) {
    if (colors == null) {
      throw new NullPointerException("no colors given");
    }
    mColors = colors;
  }

  public void setPatterns(Paint [] patterns) {
    mPatterns = patterns;
  }

  private File adjustFileName(File file, FileFilter filter) {
    File f = file;
    if (file != null && filter != null && !filter.accept(f)) {
      if (filter instanceof PNGFileFilter) {
        f = new File(file.getPath() + ".png");
      }
    }
    return f;
  }

  public void saveGraph(Graph2D graph) {
    if (graph != null) {
      boolean ok = false;
      while (!ok) {
        final int returnVal = mChooser.showSaveDialog(null);
        ok = true;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          final File file = adjustFileName(mChooser.getSelectedFile(), mChooser.getFileFilter());
          if (file.exists()) {
            final int ret = JOptionPane.showConfirmDialog(null, "Do you want to overwrite " + file.getName() + "?", "File Exists", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.NO_OPTION) {
              ok = false;
            }
          }
          if (ok) {
            writeImage(file, graph);
          }
        }
      }
    }
  }

  private void writeImage(File file, Graph2D graph) {
    try {
      GraphicsRenderer gr = new GraphicsRenderer(mColors, mPatterns);
      ImageWriter iw = new ImageWriter(gr);
      iw.toPNG(file, graph, mWidth, mHeight, null);
    } catch (Exception ioe) {
      System.err.println("Failed to write file " + ioe.getMessage());
    }

  }

  private static class PNGFileFilter extends FileFilter {
    public String getDescription() {
      return "PNG Files (*.png)";
    }
    
    public boolean accept(File f) {
      return (f.isDirectory() || f.getName().toLowerCase().endsWith(".png"));
    }

  }
}
