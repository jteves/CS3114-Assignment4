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
     */
    public void setUp() {
        defaultList = new SkipList<String, String>();

        pair1    = new KVPair<String, String>("a", "data");
        pair2    = new KVPair<String, String>("b", "more data");
    }    
    
    /**
     * Makes sure the text file was input properly to the processor  
     */
    public void testConstructor()
    {   
        int expectedListSize = 0;

        assertEquals(expectedListSize, defaultList.getSize());
        assertNull(defaultList.currentPair());

        defaultList.currentNodeDepth();
    }
    
    /**
     * Makes sure the text file was input properly to the processor  
     */
    public void testInsertIntoEmpty()
    {
        int expectedListSize = 1;
        
        try {
			defaultList.insert(pair1);
		} catch (IOException e) {
			e.printStackTrace();
		}

        //Note: iterator on head node
        assertNull(defaultList.currentPair()); 
        assertEquals(expectedListSize, defaultList.getSize());
    }    
    
    /**
     * Makes sure the text file was input properly to the processor  
     */
    public void testInsertIntoEmptyGetKeyValueData()
    {
        String expectedKey         = "a";
        String expectedValue     = "data";
        
        try {
			defaultList.insert(pair1);
		} catch (IOException e) {
			e.printStackTrace();
		}        
        defaultList.iteNext();

        assertEquals(expectedKey,     defaultList.currentPair().key());
        assertEquals(expectedValue, defaultList.currentPair().value());  
        
        defaultList.iteToHead();
        
        //Confirm iteToHead method brings the iterator back to head
        assertNull(defaultList.currentPair()); 
    }
    
    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     */
    public void testInsert2Pairs()
    {
        try {
			defaultList.insert(pair1);
	        defaultList.insert(pair2);
		} catch (IOException e) {
			e.printStackTrace();
		}

        defaultList.iteNext();
        assertEquals(pair1, defaultList.currentPair());
        defaultList.iteToHead();
        defaultList.iteNext();
        defaultList.iteNext();
        assertEquals(pair2, defaultList.currentPair());
    }
    
    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     */
    public void testInsertSamePair()
    {
        pair1    = new KVPair<String, String>("a", "data");
        String     expectedKey         = "a";
        String     expectedValue         = "data";
        int     expectedListSize    = 2;

        try {
			defaultList.insert(pair1);
	        defaultList.insert(pair1);
		} catch (IOException e) {
			e.printStackTrace();
		}

        defaultList.iteNext();

        assertEquals(expectedKey,     defaultList.currentPair().key());
        assertEquals(expectedValue, defaultList.currentPair().value());
        
        defaultList.iteNext();

        assertEquals(expectedKey,     defaultList.currentPair().key());
        assertEquals(expectedValue, defaultList.currentPair().value());
        assertEquals(expectedListSize, defaultList.getSize());
    }    

    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     */
    public void testSearch()
    {
        KVPair<String, String> pair3 = 
                new KVPair<String, String>("c", "most");
        KVPair<String, String> pair4 = 
                new KVPair<String, String>("d", "moster");
        
        try {
			defaultList.insert(pair1);
	        defaultList.insert(pair2);
	        defaultList.insert(pair3);
	        defaultList.insert(pair4);
	        defaultList.insert(pair1);
		} catch (IOException e) {
			e.printStackTrace();
		}

//        defaultList.search(pair2);
//        assertEquals(pair2, defaultList.currentPair());
//
//        defaultList.search(pair3);
//        defaultList.search(pair4);
//        defaultList.search(pair1);
//        assertEquals(pair1, defaultList.currentPair());
    }

    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     */
    public void testRemove()
    {
        KVPair<String, String> pair3 = 
                new KVPair<String, String>("c", "most");
        KVPair<String, String> pair4 = 
                new KVPair<String, String>("d", "moster");

        try {
            defaultList.insert(pair1);
            defaultList.insert(pair1);
			defaultList.insert(pair2);
	        defaultList.insert(pair3);
	        defaultList.insert(pair4);
	        
	        defaultList.insert(pair3);
	        defaultList.insert(pair4);
	        defaultList.insert(pair4);
		} catch (IOException e) {
			e.printStackTrace();
		}

//        assertEquals(pair2, defaultList.remove(pair2));
//        assertEquals(null, defaultList.remove(pair2));
//
//        defaultList.remove(pair1);
//        defaultList.remove(pair4);
//        defaultList.remove(pair3);
        assertEquals(null, defaultList.currentPair());

//        defaultList.remove(pair4);
//        defaultList.remove(pair1);
//        defaultList.remove(pair3);
//        defaultList.remove(pair4);
//        assertEquals(null, defaultList.remove(pair4));
    }
    /**
     * Tests to have the correct number of lines from the processor results
     * as the given output file
     */
    public void testRemove2()
    {
//        KVPair<String, String> pair3 = 
//                new KVPair<String, String>("c", "most");
//        KVPair<String, String> pair4 = 
//                new KVPair<String, String>("d", "moster");
//        
//        defaultList.insert(pair1);
//        defaultList.remove2(pair1);
//
//        defaultList.insert(pair2);
//        defaultList.remove2(pair2);
//        
//        defaultList.insert(pair1);
//        defaultList.insert(pair2);
//        defaultList.insert(pair3);
//        defaultList.insert(pair4);
//        
//        defaultList.insert(pair3);
//        defaultList.insert(pair4);
//        defaultList.insert(pair4);
//
//        assertEquals(pair2, defaultList.remove2(pair2));
//        assertEquals(null, defaultList.remove2(pair2));
//
//        defaultList.remove2(pair1);
//        defaultList.remove2(pair4);
//        defaultList.remove2(pair3);
//        assertEquals(null, defaultList.currentPair());
//
//        defaultList.remove2(pair4);
//        defaultList.remove2(pair2);
//        defaultList.remove2(pair3);
//        defaultList.remove2(pair4);
//        defaultList.remove2(pair4);
//        assertEquals(null, defaultList.remove2(pair4));
    }
}




