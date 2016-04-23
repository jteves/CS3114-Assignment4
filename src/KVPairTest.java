import student.TestCase;

/**
 * The test case for the KVPair class
 * @author Jacob Teves, Drew Williams
 * @version 3/14/2016
 */
public class KVPairTest extends TestCase {

    /**
     * Test method for 
     * {@link KVPair#KVPair}.
     * Tests the initial values of the KVPair.
     */
    public void testKVPair() {
        KVPair<Integer, String> k1 = 
                new KVPair<Integer, String>(1, "data");

        assertEquals(0, k1.compareTo(new 
                KVPair<Integer, String>(1, "data")));
        assertEquals(-1, k1.compareTo(new 
                KVPair<Integer, String>(2, "data")));
        assertEquals(1, k1.compareTo(new 
                KVPair<Integer, String>(0, "data")));

        assertEquals(1, (int)k1.key());
        assertEquals("data", k1.value());
    }
}
