import java.io.FileNotFoundException;
import java.io.IOException;

import student.TestCase;


/**
 * Tests the Processor class to make sure it processes the file
 * in its intended way
 * 
 * @author Jacob Teves, Andrew Williams
 * @version 3/14/2016
 *
 */
public class ProcessorTest extends TestCase {

    /**
     * Makes sure the text file was input properly to the processor  
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testFileInput() throws ClassNotFoundException, IOException
    {
        
        // A new Processor object with an existing text file name
        Processor pro;
			pro = new Processor("test.txt", 0, 0, null);

        String expectedFileName = "test.txt";

        assertEquals(expectedFileName, pro.getFile());
    }
}
