package com.reeltwo.plot.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.Node;

import com.reeltwo.plot.Graph2D;
import com.reeltwo.plot.renderer.GraphicsRenderer;
import com.reeltwo.plot.renderer.Mapping;

import de.erichseifert.vectorgraphics2d.Document;
import de.erichseifert.vectorgraphics2d.VectorGraphics2D;
import de.erichseifert.vectorgraphics2d.svg.SVGProcessor;
import de.erichseifert.vectorgraphics2d.util.PageSize;

/**
 * Routines to write Graph2D's to graphics files of various formats.
 *
 * @author Richard Littin
 */
public class ImageWriter {

  // Java ImageWriter format name for PNG
  private static final String IW_NAME_PNG = "png";

  // Constants used in image metadata text insertion
  private static final String META_FORMAT_NAME = "javax_imageio_png_1.0";
  private static final String META_TEXT = "tEXt";
  private static final String META_TEXTENTRY = "tEXtEntry";
  private static final String META_KEYWORD = "keyword";
  private static final String META_VALUE = "value";

  /** the thing that does the graph rendering */
  private final GraphicsRenderer mGraphicsRenderer;

  private final Map<String, String> mMetaData = new TreeMap<>();

  /**
   * Supported image formats.
   */
  public enum ImageFormat {
    /** SVG file type Scalable vector graphics */
    SVG,
    /** Portable network graphics */
    PNG
  }

  /**
   * Constructor
   *
   * @param gr The graphics renderer.
   */
  public ImageWriter(GraphicsRenderer gr) {
    mGraphicsRenderer = gr;
  }

  /**
   * Set an item of metadata to be added to output files (where supported)
   * @param key the keyword
   * @param value the value
   */
  public void setMetaData(String key, String value) {
    mMetaData.put(key, value);
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
  public Mapping[] toImage(ImageFormat type, File file, Graph2D graph, int width, int height, Font font) throws IOException {
    if (file == null) {
      throw new NullPointerException("null file given.");
    }

    try (FileOutputStream fos = new FileOutputStream(file)) {
      return toImage(type, fos, graph, width, height, font);
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
  public Mapping[] toImage(ImageFormat type, OutputStream os, Graph2D graph, int width, int height, Font font) throws IOException {
    switch (type) {
      case PNG:
        return toPNG(os, graph, width, height, font);
      case SVG:
        return toSVG(os, graph, width, height, font);
      default:
        throw new IllegalArgumentException("Illegal image type '" + type + "' given.");
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
  public Mapping[] toPNG(OutputStream os, Graph2D graph, int width, int height, Font font) throws IOException {
    if (os == null) {
      throw new NullPointerException("null output stream given.");
    }
    if (graph == null) {
      throw new NullPointerException("null graph given.");
    }
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("dimensions must be greater than 0");
    }

    final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    final Graphics2D g = bi.createGraphics();
    if (font != null) {
      g.setFont(font);
    }
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    mGraphicsRenderer.drawGraph(graph, g, 5, 5, width - 10, height - 10);
    final Mapping[] mapping = mGraphicsRenderer.getMappings();

    final javax.imageio.ImageWriter writer = ImageIO.getImageWritersByFormatName(IW_NAME_PNG).next();
    final ImageWriteParam writeParam = writer.getDefaultWriteParam();
    final ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
    final IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);

    for (Map.Entry<String, String> e : mMetaData.entrySet()) {
      final IIOMetadataNode root = new IIOMetadataNode(META_FORMAT_NAME);
      final IIOMetadataNode text = new IIOMetadataNode(META_TEXT);
      final IIOMetadataNode textEntry = new IIOMetadataNode(META_TEXTENTRY);
      textEntry.setAttribute(META_KEYWORD, e.getKey());
      textEntry.setAttribute(META_VALUE, e.getValue());
      text.appendChild(textEntry);
      root.appendChild(text);
      metadata.mergeTree(META_FORMAT_NAME, root);
    }

    try (ImageOutputStream stream = ImageIO.createImageOutputStream(os)) {
      writer.setOutput(stream);
      writer.write(metadata, new IIOImage(bi, null, metadata), writeParam);
    }
    return mapping;
  }

  /**
   * Writes the given graph out to a SVG formatted output stream. The
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
  public Mapping[] toSVG(OutputStream os, Graph2D graph, int width, int height, Font font) throws IOException {
    if (os == null) {
      throw new NullPointerException("null output stream given.");
    }
    if (graph == null) {
      throw new NullPointerException("null graph given.");
    }
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("dimensions must be greater than 0");
    }

    final VectorGraphics2D g = new VectorGraphics2D();
    if (font != null) {
      g.setFont(font);
    } else {
      g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
    }
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    final int inset = 5;
    mGraphicsRenderer.drawGraph(graph, g, inset, inset, width - 2 * inset, height - 2 * inset);
    final Mapping[] mapping = mGraphicsRenderer.getMappings();
    final SVGProcessor proc = new SVGProcessor();
    final Document document = proc.getDocument(g.getCommands(), new PageSize(width, height));
    document.writeTo(os);
    return mapping;
  }

  private static List<Node> findNodesWithName(String name, Node root) {
    final List<Node> found = new ArrayList<>();
    Node n = root.getFirstChild();
    while (n != null) {
      if (n.getNodeName().equals(name)) {
        found.add(n);
      }
      found.addAll(findNodesWithName(name, n));
      n = n.getNextSibling();
    }
    return found;
  }

  /**
   * Return text metadata contained in the supplied image file
   * @param imageFile the image file
   * @return a map of text metadata in the image
   * @throws IOException if there is a problem reading the image file as PNG
   */
  public static Map<String, String> getPngTextMetaData(File imageFile) throws IOException {
    try (final InputStream is = new FileInputStream(imageFile)) {
      final ImageReader imageReader = ImageIO.getImageReadersByFormatName(IW_NAME_PNG).next();
      imageReader.setInput(ImageIO.createImageInputStream(is), true);
      final IIOMetadata metadata = imageReader.getImageMetadata(0);
      final List<Node> tEXtNodes = findNodesWithName(META_TEXTENTRY, metadata.getAsTree(metadata.getNativeMetadataFormatName()));
      final Map<String, String> textmeta = new TreeMap<>();
      for (final Node n : tEXtNodes) {
        textmeta.put(n.getAttributes().getNamedItem(META_KEYWORD).getNodeValue(),
          n.getAttributes().getNamedItem(META_VALUE).getNodeValue());
      }
      return textmeta;
    }
  }


  /**
   * Test metadata reading from command line
   * @param args command line arguments
   * @throws IOException if bad things
   */
  public static void main(String[] args) throws IOException {
    final Map<String, String> meta = getPngTextMetaData(new File(args[0]));
    for (Entry<String, String> e : meta.entrySet()) {
      System.out.println("keyword: " + e.getKey() + "; value: " + e.getValue());
    }
  }
}
