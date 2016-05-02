import java.io.IOException;

import student.TestCase;

/**
 * Test for the buffer pool
 * 
 * @author Drew Williams, Jacob Teves
 * @version 4/05/2016
 */
public class BufferPoolTest extends TestCase {
	
    /**
     * Tests to make sure the bufferpool initializes properly
     */
    public void test() {
    	BufferPool pool = new BufferPool("filename.txt", 6, 1);
    	assertFalse(pool.isContained(1));
    	assertFalse(pool.isFull());
    }
}