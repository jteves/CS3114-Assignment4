import java.io.IOException;

import student.TestCase;


/**
 * 
 * @author Jacob, Drew
 * @version 3/15/2016
 *
 */
public class SkipListTest extends TestCase {
    
    // SkipList to test most methods with
    private SkipList<String, String>     defaultList;

    private KVPair<String, String>         pair1;
    
    private KVPair<String, String>         pair2;
    
    /**
     * Called at the beginning of each test
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void setUp() throws ClassNotFoundException, IOException {
    	BufferPool bp = new BufferPool("filename.txt", 4, 20);
		MemMan m = new MemMan(512, bp);
        defaultList = new SkipList<String, String>(m);

        pair1    = new KVPair<String, String>("a", "data");
        pair2    = new KVPair<String, String>("b", "more data");
    }    
    
    /**
     * Makes sure the text file was input properly to the processor  
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testConstructor() throws ClassNotFoundException, IOException
    {   
        int expectedListSize = 0;

        assertEquals(expectedListSize, defaultList.getSize());

        defaultList.currentNodeDepth();
    }
    
    /**
     * Makes sure the text file was input properly to the processor  
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testInsertIntoEmpty() throws ClassNotFoundException, IOException
    {
        int expectedListSize = 1;
        
        defaultList.insert(pair1);
        
        //Note: iterator on head node
        assertEquals(expectedListSize, defaultList.getSize());
    }    
    
    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testInsert2Pairs() throws ClassNotFoundException, IOException
    {
        defaultList.insert(pair1);
        defaultList.insert(pair2);

        defaultList.iteNext();
        assertEquals(null, defaultList.currentPair());
        defaultList.iteToHead();
        defaultList.iteNext();
        defaultList.iteNext();
        assertEquals(pair2, defaultList.currentPair());
    }
    
    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testInsertSamePair() throws ClassNotFoundException, IOException
    {
        pair1    = new KVPair<String, String>("a", "data");
        String     expectedKey         = "a";
        String     expectedValue         = "data";
        int     expectedListSize    = 2;

        defaultList.insert(pair1);
        defaultList.insert(pair1);

        defaultList.iteNext();

        assertEquals(expectedListSize, defaultList.getSize());
    }    

    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testSearch() throws ClassNotFoundException, IOException
    {
        KVPair<String, String> pair3 = 
                new KVPair<String, String>("c", "most");
        KVPair<String, String> pair4 = 
                new KVPair<String, String>("d", "moster");
        
        
        defaultList.insert(pair1);
        defaultList.insert(pair2);
        defaultList.insert(pair3);
        defaultList.insert(pair4);
        defaultList.insert(pair1);

        defaultList.search(pair2);
        assertEquals(pair1, defaultList.currentPair());

        defaultList.search(pair3);
        defaultList.search(pair4);
        defaultList.search(pair1);
        assertEquals(pair1, defaultList.currentPair());
    }

    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testRemove() throws ClassNotFoundException, IOException
    {
        KVPair<String, String> pair3 = 
                new KVPair<String, String>("c", "most");
        KVPair<String, String> pair4 = 
                new KVPair<String, String>("d", "moster");

        defaultList.insert(pair1);
        defaultList.insert(pair1);
        defaultList.insert(pair2);
        defaultList.insert(pair3);
        defaultList.insert(pair4);
        
        defaultList.insert(pair3);
        defaultList.insert(pair4);
        defaultList.insert(pair4);

        assertEquals(pair2, defaultList.remove(pair2));
        assertEquals(null, defaultList.remove(pair2));

        defaultList.remove(pair1);
        defaultList.remove(pair4);
        defaultList.remove(pair3);
        assertEquals(null, defaultList.currentPair());

        defaultList.remove(pair4);
        defaultList.remove(pair1);
        defaultList.remove(pair3);
        defaultList.remove(pair4);
        assertEquals(null, defaultList.remove(pair4));
    }
    
    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testRemove2() throws ClassNotFoundException, IOException
    {
        KVPair<String, String> pair3 = 
                new KVPair<String, String>("c", "most");
        KVPair<String, String> pair4 = 
                new KVPair<String, String>("d", "moster");
        
        defaultList.insert(pair1);
        defaultList.remove2(pair1);

        defaultList.insert(pair2);
        defaultList.remove2(pair2);
        
        defaultList.insert(pair1);
        defaultList.insert(pair2);
        defaultList.insert(pair3);
        defaultList.insert(pair4);
        
        defaultList.insert(pair3);
        defaultList.insert(pair4);
        defaultList.insert(pair4);

        assertEquals(pair2, defaultList.remove2(pair2));
        assertEquals(null, defaultList.remove2(pair2));

        defaultList.remove2(pair1);
        defaultList.remove2(pair4);
        defaultList.remove2(pair3);
        assertEquals(null, defaultList.currentPair());

        defaultList.remove2(pair4);
        defaultList.remove2(pair2);
        defaultList.remove2(pair3);
        defaultList.remove2(pair4);
        defaultList.remove2(pair4);
        assertEquals(null, defaultList.remove2(pair4));
    }
}




