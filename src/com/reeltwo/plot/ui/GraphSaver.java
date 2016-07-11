package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.patterns.DefaultColorGroup;
import com.reeltwo.plot.renderer.GraphicsRenderer;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * Handy utility class for saving graphs as images.
 *
 * @author Richard Littin
 */
public class GraphSaver {
  private static final String PNG = "png";
  private static final String SVG = "svg";
  private Color[] mColors = (Color[]) new DefaultColorGroup().getPatterns();
  private Paint[] mPatterns = null;

  private int mFontSize = -1; // use default font size
  private int mWidth = 800;
  private int mHeight = 600;

  private final JFileChooser mChooser;

  /**
   * Creates a new <code>GraphSaver</code>.
   */
  public GraphSaver() {
    mChooser = new JFileChooser();
    final FileExtensionFilter pff = new FileExtensionFilter(PNG);
    final FileExtensionFilter svg = new FileExtensionFilter(SVG);
    mChooser.addChoosableFileFilter(pff);
    mChooser.addChoosableFileFilter(svg);
    mChooser.setFileFilter(pff);
  }

  /**
   * Sets the font size to use.
   *
   * @param size font size
   */
  public void setFontSize(int size) {
    mFontSize = size;
  }

  /**
   * Returns the font size.
   *
   * @return font size
   */
  public int getFontSize() {
    return mFontSize;
  }

  /**
   * Sets the size, in pixels, of the image.
   *
   * @param width image width in pixels
   * @param height image height in pixels
   */
  public void setSize(int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("width and height must be >= 0: " + width + " : " + height);
    }
    mWidth = width;
    mHeight = height;
  }

  /**
   * Sets the colors to render plots with.
   *
   * @param colors an array of colors
   */
  public void setColors(Color[] colors) {
    if (colors == null) {
      throw new NullPointerException("no colors given");
    }
    mColors = colors;
  }

  /**
   * Sets the patterns to use when rendering plots.
   *
   * @param patterns an array of patterns
   */
  public void setPatterns(Paint[] patterns) {
    mPatterns = patterns;
  }

  private File adjustFileName(File file, FileFilter filter, FileFilter[] possibleFilters) {
    File f = file;
    if (f == null) {
      return f;
    }
    for (FileFilter possibleFilter : possibleFilters) {
      if (possibleFilter instanceof FileExtensionFilter && possibleFilter.accept(f)) {
        return f;
      }
    }
    if (filter != null && filter instanceof FileExtensionFilter) {
      f = new File(file.getPath() + "." + ((FileExtensionFilter) filter).getExtension());
    }
    return f;
  }

  /**
   * Saves the given <code>graph</code> to a file as determine via
   * interactive dialogs.
   *
   * @param graph a <code>Graph2D</code>
   */
  public void saveGraph(Graph2D graph) {
    if (graph != null) {
      boolean ok = false;
      while (!ok) {
        final int returnVal = mChooser.showSaveDialog(null);
        ok = true;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          final FileFilter[] fileFilters = mChooser.getChoosableFileFilters();
          final FileFilter fileFilter = mChooser.getFileFilter();
          final File file = adjustFileName(mChooser.getSelectedFile(), fileFilter, fileFilters);
          if (file.exists()) {
            final int ret = JOptionPane.showConfirmDialog(null, "Do you want to overwrite " + file.getName() + "?", "File Exists", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.NO_OPTION) {
              ok = false;
            }
          }
          if (ok) {
            final String extension;
            final int dotIndex = file.getName().lastIndexOf(".");
            if (dotIndex != -1) {
              extension = file.getName().substring(dotIndex + 1);
            } else {
              extension = PNG;
            }
            writeImage(file, graph, extension);
          }
        }
      }
    }
  }

  private void writeImage(File file, Graph2D graph, String extension) {
    try {
      final GraphicsRenderer gr = new GraphicsRenderer(mColors, mPatterns);
      final ImageWriter iw = new ImageWriter(gr);
      if (extension.equals(SVG)) {
        iw.toSVG(file, graph, mWidth, mHeight, null);
      } else {
          iw.toPNG(file, graph, mWidth, mHeight, null);
      }
    } catch (final Exception ioe) {
      System.err.println("Failed to write file " + ioe.getMessage());
    }

  }

  private static class FileExtensionFilter extends FileFilter {
    private final String mExtension;

    private FileExtensionFilter(String extension) {
      mExtension = extension;
    }

    @Override
    public String getDescription() {
      return mExtension.toUpperCase(Locale.getDefault()) + " Files (*." + mExtension + ")";
    }

    @Override
    public boolean accept(File f) {
      return f.isDirectory() || f.getName().toLowerCase().endsWith("." + mExtension);
    }

    public String getExtension() {
      return mExtension;
    }

  }
}
