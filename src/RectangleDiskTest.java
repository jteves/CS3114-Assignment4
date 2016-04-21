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
     */
    public void testBadParams() {
        RectangleDisk rectDisk = new RectangleDisk();
        assertNotNull(rectDisk);
        String[] params = { "bad", "params" };
        RectangleDisk.main(params);
        assertFuzzyEquals(
            "Usage: RectangleDisk <commandfile> "
            + "<diskFile> <numBuffs> <buffSize>",
            systemOut().getHistory());
    }

    /**
     * This method gets you credit for testing a good
     * set of parameters.
     */
    public void testGoodParams() {
        String[] params = { "commands.txt", "dataFile.dat", "5", "4096" };
        RectangleDisk.main(params);
        assertFuzzyEquals("Found expected parameter list.", 
            systemOut().getHistory());
    }
}
