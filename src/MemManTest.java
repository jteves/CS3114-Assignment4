import student.TestCase;

/**
 * The test case for the MemMan class
 * @author Jacob Teves, Drew Williams
 * @version 3/14/2016
 */
public class MemManTest extends TestCase {

    /**
     * Test for MemMan
     */
    public void testMemMan() {
        MemMan m = new MemMan(1000, null);
        
        
        m.dump();
        
        String expectedOutput = "Freelist Blocks:\n(0, 1000)\n";
        assertEquals(expectedOutput, systemOut().getHistory());
    }
}
