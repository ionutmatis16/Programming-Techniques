import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class represents a Test Runner Class for the Test Class PolTest. It runs the test class using
 * JUnitCore.runClasses() method and displays the failure (if any). Finally, it displays whether the test was
 * successful and the number of failures.
 *
 * @author Ionut Matis, 30421
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PolTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("All operations work correctly: " + result.wasSuccessful());
        System.out.println("The number of failures: " + result.getFailureCount());
    }
}
