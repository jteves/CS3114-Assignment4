import java.io.IOException;

import student.TestCase;

/**
 * Test for the buffer pool
 * 
 * @author Drew Williams, Jacob Teves
 * @version 4/05/2016
 */
public class BufferPoolTest extends TestCase {
    
    private BufferPool pool;
    /** 
     * set up
     */
    public void setUp() {
        pool = new BufferPool("filename.txt", 6, 1);
    }
    
    /**
     * Tests to make sure the generated file gets sorted
     */
    public void test() {
    	pool.isFull();
    }
}