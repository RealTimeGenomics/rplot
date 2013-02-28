package com.reeltwo.plot.demo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * A dialog displaying memory use over time.
 *
 * @author Richard Littin
 */
public class MemoryUsage extends JDialog {
  private static final long serialVersionUID = -6222507196514885587L;
  private final MemoryPanel mMemoryPanel = new MemoryPanel();

  MemoryUsage() {
    super();
    setTitle("Memory Usage");

    final JPanel mainPanel = new JPanel(new BorderLayout());


    mainPanel.add(mMemoryPanel, BorderLayout.CENTER);

    final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

    buttonPanel.add(new JButton(
        new AbstractAction("GC") {
          private static final long serialVersionUID = 3660591807128148259L;

          public void actionPerformed(ActionEvent e) {
            mMemoryPanel.refresh();
          }
        }));
    buttonPanel.add(new JButton(
        new AbstractAction("Close") {
          private static final long serialVersionUID = 7740156196494085688L;

          public void actionPerformed(ActionEvent e) {
            close();
          }
        }));
    // make all buttons same width and height
    final Component[] components = buttonPanel.getComponents();
    int maxX = 0;
    int maxY = 0;
    for (int i = 0; i < components.length; i++) {
      final Dimension d = components[i].getPreferredSize();
      if (d.width > maxX) {
        maxX = d.width;
      }
      if (d.height > maxY) {
        maxY = d.height;
      }
    }
    final Dimension d = new Dimension(maxX, maxY);
    for (int i = 0; i < components.length; i++) {
      ((JButton) components[i]).setPreferredSize(d);
    }

    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    setContentPane(mainPanel);

    setSize(new Dimension(500, 200));
    setLocationRelativeTo(getOwner());

    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            close();
          }
        });
  }


  void close() {
    mMemoryPanel.stop();
    dispose();
  }


  /**
   * If 1 or more arguments are given, will attempt
   * to invoke the first argument's main method with the remaining
   * arguments as parameters. It will exit if any exceptions are
   * thrown.
   *
   * @param args The command line arguments.
   * @exception Exception if an error occurs.
   */
  public static void main(String[] args) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    final MemoryUsage mud = new MemoryUsage() {
      private static final long serialVersionUID = 5057017502018317041L;

      @Override
      public void close() {
        super.close();
        System.exit(0);
      }
    };
    mud.setVisible(true);

    //args[0] => class name
    //args[1]..args[n-1] => command line parameters
    if (args.length >= 1) {
      final Class<?> c = ClassLoader.getSystemClassLoader().loadClass(args[0]);
      final Method m = c.getMethod("main", new Class[]{String[].class});
      final String[] a = new String[args.length - 1];
      System.arraycopy(args, 1, a, 0, a.length);
      System.out.println("Invoking: " + c.getName() + ".main()");
      m.invoke(c, new Object[]{a});
    }
  }



}
