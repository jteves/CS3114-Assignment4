import student.TestCase;

/**
 * The test case for the KVPair class
 * @author Jacob Teves, Drew Williams
 * @version 3/14/2016
 */
public class MemManTest extends TestCase {

	public void testMemMan() {
		MemMan m = new MemMan(1000);
		
		m.add(10);
		
		m.dump();
		
		String expectedOutput = "Freelist Blocks:\n(10, 990)";
		assertEquals(expectedOutput, systemOut().getHistory());
	}
}
