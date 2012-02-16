
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs all rplot tests.
 *
 * @author <a href="mailto:rlittin@reeltwo.com">Richard Littin</a>
 */
public class AllTests extends TestSuite {

  public static Test suite() {
    final TestSuite suite = new TestSuite();

    suite.addTest(com.reeltwo.plot.AllTests.suite());

    return suite;
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
