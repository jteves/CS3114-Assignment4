import java.io.FileNotFoundException;
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
     */
    public void testFileInput()
    {
        // A new Processor object with an existing text file name
        Processor pro = new Processor("test.txt", 0);

        String expectedFileName = "test.txt";

        assertEquals(expectedFileName, pro.getFile());
    }

    /**
     * Tests to match the processor results lines in the console to 
     * the lines in the given output file
     * @throws FileNotFoundException when the input file doesn't exist 
     */
    public void testMatchExampleOutput() throws FileNotFoundException
    {
//        Processor pro = new Processor("Test1.txt", 0);
//
//        try {
//          pro.process();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // String to be scanned
//        String output = systemOut().getHistory(); 
//        // File to be scanned
//        File correctFile = new File("Test1Out.txt"); 
//
//        System.out.println("\nThis is the new String: \n" + output);
//
//        Scanner fileScanner = new Scanner(correctFile);
//        Scanner consoleScanner = new Scanner(output);
//
//        String expectedLine;
//        String actualLine;
//
//        // Compares all lines from our results to the expected line 
//        while (consoleScanner.hasNextLine() && 
//                fileScanner.hasNextLine())
//        {
//            expectedLine = fileScanner.nextLine();
//            actualLine = consoleScanner.nextLine();
//
//            assertEquals(expectedLine, actualLine);
//        }
//
//        fileScanner.close();
//        consoleScanner.close();
    }    

//    /**
//     * Tests to have the correct number of lines from the processor 
//     * results as the given output file
//     */
//    public void testMatchExampleOutputNumLines()
//    {
//        Processor pro = new Processor("Test1.txt", 0);
//
//        //Outputs the lines onto the console
//        try {
//            pro.process();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String output = systemOut().getHistory();
//
//        Scanner consoleScanner = new Scanner(output);
//
//        int actualNumLines = 0; //numLines in the Test text file
//        int expectedNumLines = 0; // numLines in the Output text file
//
//        // Measures how many lines are in our results
//        while (consoleScanner.hasNextLine())
//        {
//            consoleScanner.nextLine();
//            actualNumLines++;
//        }
//        consoleScanner.close();
//        assertEquals(expectedNumLines, actualNumLines);
//    }
}
