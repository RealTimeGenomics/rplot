package com.reeltwo.plot.ui;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;

/**
 * Routines to write Graph2D's to graphics files of various formats.
 *
 * @author <a href=mailto:rlittin@reeltwo.com>Richard Littin</a>
 * @version $Revision$
 */
public class ImageWriter {

  private Color[] mColors = AWTDefaults.COLORS;

  private final GraphicsRenderer mGraphicsRenderer;

  /** TODO Description of the Field. */
  public static final int PNG_IMAGE = 0;
  /** TODO Description of the Field. */
  //public static final int GIF_IMAGE = 1;


  /**
   * Private to prevent instantiation.
   *
   * @param gr TODO Description.
   */
  public ImageWriter(GraphicsRenderer gr) {
    mGraphicsRenderer = gr;
  }


  /**
   * Sets the colors to use in each of the plots.
   *
   * @param colors an array of Color
   */
  public void setColors(Color[] colors) {
    if (colors == null || colors.length < 2) {
      throw new IllegalArgumentException("Must specify at least 2 colors");
    }
    mColors = colors;
  }


  /**
   * Writes the given graph out to a formatted file of the specified
   * <tt>type</tt> . The width and height parameters determine the
   * dimension of the image (in pixels). The mappings from world to
   * screen data points for each axis pair is returned.
   *
   * @param type type of image to produce.
   * @param file File to save graph to.
   * @param graph graph to save.
   * @param width width of image.
   * @param height height of image.
   * @param font font to use in graph.
   * @return an array of world to screen mappings.
   * @exception IOException if a file writing error occurs.
   */
  public Mapping[] toImage(int type, File file, Graph2D graph, int width, int height, Font font)
     throws IOException {

    if (file == null) {
      throw new NullPointerException("null file given.");
    }

    FileOutputStream fos = new FileOutputStream(file);
    try {
      return toImage(type, fos, graph, width, height, font);
    } finally {
      fos.close();
    }
  }


  /**
   * Writes the given graph out to a formatted output stream of the
   * specified <tt>type</tt> . The width and height parameters
   * determine the dimension of the image (in pixels). The mappings
   * from world to screen data points for each axis pair is returned.
   *
   * @param type type of image to produce.
   * @param os stream to write to.
   * @param graph graph to save.
   * @param width width of image.
   * @param height height of image.
   * @param font font to use in graph.
   * @return an array of world to screen mappings.
   * @exception IOException if a file writing error occurs.
   */
  public Mapping[] toImage(int type, OutputStream os, Graph2D graph, int width, int height, Font font)
     throws IOException {
    if (type < 0 || type > 2) {
      throw new IllegalArgumentException("Illegal image type '" + type + "' given.");
    }
    switch (type) {
      //case GIF_IMAGE:
      //return toGIF(os, graph, width, height, font);
    case PNG_IMAGE:
      return toPNG(os, graph, width, height, font);
    }
    return null;
  }


  /**
   * Writes the given graph out to a PNG formatted file. The width and
   * height parameters determine the dimension of the image (in
   * pixels). The mappings from world to screen data points for each
   * axis pair is returned.
   *
   * @param file File to save graph to.
   * @param graph graph to save.
   * @param width width of image.
   * @param height height of image.
   * @param font font to use in graph.
   * @return an array of world to screen mappings.
   * @exception IOException if a file writing error occurs.
   */
  public Mapping[] toPNG(File file, Graph2D graph, int width, int height, Font font)
     throws IOException {

    if (file == null) {
      throw new NullPointerException("null file given.");
    }

    FileOutputStream fos = new FileOutputStream(file);
    try {
      return toPNG(fos, graph, width, height, font);
    } finally {
      fos.close();
    }
  }


  /**
   * Writes the given graph out to a PNG formatted output stream. The
   * width and height parameters determine the dimension of the image
   * (in pixels). The mappings from world to screen data points for
   * each axis pair is returned.
   *
   * @param os stream to write to.
   * @param graph graph to save.
   * @param width width of image.
   * @param height height of image.
   * @param font font to use in graph.
   * @return an array of world to screen mappings.
   * @exception IOException if a file writing error occurs.
   */
  public Mapping[] toPNG(OutputStream os, Graph2D graph, int width, int height, Font font)
     throws IOException {

    if (os == null) {
      throw new NullPointerException("null output stream given.");
    }
    if (graph == null) {
      throw new NullPointerException("null graph given.");
    }
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("dimensions must be greater than 0");
    }

    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    Graphics2D g = bi.createGraphics();

    if (font != null) {
      g.setFont(font);
    }
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    mGraphicsRenderer.drawGraph(graph, g, 0, 0, width, height);
    Mapping[] mapping = mGraphicsRenderer.getMappings();
    for (Iterator it = ImageIO.getImageWritersBySuffix("png"); it.hasNext(); ) {
      javax.imageio.ImageWriter iw = (javax.imageio.ImageWriter) it.next();
      iw.setOutput(new MemoryCacheImageOutputStream(os));
      iw.write(bi);
      break;
    }
    return mapping;
  }


}
