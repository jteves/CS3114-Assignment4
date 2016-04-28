import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class for processing all the commands found in the text
 * file for each Rectangle objects
 * 
 * @author Drew Williams, Jacob Teves
 * @version 2/06/16
 *
 */
public class Processor {

    // Used to set up processing the commands 
    private String fileName;
    private File file;
    private DataBase db;
//    private int bufSize;
    
    //The existing commands
    private static final String INSERT = "insert";
//    private static final String REMOVE = "remove";
//    private static final String REGIONSEARCH = "regionsearch";
//    private static final String INTERSECTIONS = "intersections";
//    private static final String SEARCH = "search";
    private static final String DUMP = "dump";
    
//    /** 
//     * Constructor that converts a string to a File and 
//     * contains a DataBase object that contains the SkipList
//     * 
//     * Precondition: the input is a valid file in the project folder
//     * Postcondition: a new File and Database object is made
//     *  
//     * @param input the file name
//     */
//    public Processor(String input)
//    {
//        db          = new DataBase(0); 
//        
//        fileName    = input;
//        file        = new File(fileName);
//        bufSize = 0;
//    }
    
    /** 
     * Constructor that converts a string to a File and 
     * contains a DataBase object that contains the SkipList
     * 
     * Precondition: the input is a valid file in the project folder
     * Postcondition: a new File and Database object is made
     *  
     * @param input the file name
     * @param size is the buffer size
     */
    public Processor(String input, int size, int num, String disk)
    {
        db          = new DataBase(size, num, disk); 
        
        fileName    = input;
        file        = new File(fileName);
//        bufSize = size;
    }

    /**
     * Processes the commands for handling rectangles in a file
     * 
     * Precondition: the file name is a valid text file containing
     * properly formatted commands and parameters
     * Postcondition: the console will contain an output of the 
     * different commands. The SkipList will have been updated 
     * depending on the file commands.
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void process() throws IOException, ClassNotFoundException
    {
        // The properties for a rectangle
        String recName;
        int x;
        int y;
        int w;
        int h;

        try
        {
            Scanner lines = new Scanner(file);
            
            // Reads through the entire file
            while (lines.hasNextLine() && lines.hasNext())
            {
                // The first word of each line of the text file
                String command = lines.next();
                
                if (command.equals(INSERT))
                {
                    recName = lines.next();
                    
                    // string conversion to int of coordinates
                    x       = Integer.parseInt(lines.next());
                    y       = Integer.parseInt(lines.next());
                    w       = Integer.parseInt(lines.next());
                    h       = Integer.parseInt(lines.next());
                    
                    db.insert(recName, x, y, w, h);
                }
//                else if (command.equals(REMOVE))
//                {
//                    // The next word following the remove command
//                    String next = lines.next();
//                    
//                    // Removal by specified [xywh] coordinates
//                    if (Character.isDigit(next.charAt(0)) ||
//                            next.charAt(0) == '-')
//                    {
//                        // string conversion to int of coordinates
//                        x = Integer.parseInt(next);
//                        y = Integer.parseInt(lines.next());
//                        w = Integer.parseInt(lines.next());
//                        h = Integer.parseInt(lines.next());
//                        
//                       // db.remove(x, y, w, h);
//                    }
//                    else // Removal by the rectangle name
//                    {
//                       // db.remove(next);
//                    }
//                }
//                else if (command.equals(REGIONSEARCH))
//                {
//                    // string conversion to int of coordinates
//                    x = Integer.parseInt(lines.next());
//                    y = Integer.parseInt(lines.next());
//                    w = Integer.parseInt(lines.next());
//                    h = Integer.parseInt(lines.next());
//                    
//                    // Rectangle width and height are valid (positive)
//                    if (w > 0 && h > 0) // 
//                    {
//                        db.regionSearch(x, y, w, h);
//                    }
//                    else 
//                    {
//                        // output: "Rectangle rejected: (x, y, w, h)"
//                        System.out.println("Rectangle rejected: " +
//                                "(" + x + ", " + y + ", " + w + ", " +
//                                h + ")");
//                    }
//                }
//                else if (command.equals(INTERSECTIONS))
//                {
//                   // db.intersections();
//                }
//                else if (command.equals(SEARCH))
//                {
//                    recName = lines.next();
//                    db.search(recName);
//                }
                else if (command.equals(DUMP))
                {
                    
                    db.dump();
                    
                }
            }
            lines.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Getter for testing purposes
     * @return the name of the file
     */
    public String getFile()
    {
        return fileName;
    }


}







