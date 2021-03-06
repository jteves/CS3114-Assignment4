import java.io.IOException;

import student.TestCase;

/**
 * @author CS 3114 Staff
 * @version April 13, 2016
 */
public class RectangleDiskTest extends TestCase {

    /**
     * This method sets up the tests that follow.
     */
    public void setUp() {
        // no op
    }

    /**
     * This method gets you credit for testing a bad
     * set of parameters and for initializing the
     * Driver class.
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testBadParams() 
        throws IOException, ClassNotFoundException {
        RectangleDisk rd = new RectangleDisk();
        String[] params = { "DrewTest.txt", "filename.txt", "4", "20"};
        RectangleDisk.main(params);
        assertNotNull(rd);
    }
}
