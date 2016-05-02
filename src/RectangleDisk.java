import java.io.IOException;

/**
 * { your description of the project here }
 */

/**
 * The class containing the main method, the entry point
 * of the application.
 *
 * @author Drew Williams, Jacob Teves
 * @version 4/22/2016
 */
public class RectangleDisk {

    /**
     * The entry point for the application.
     *
     * @param args The command line arguments.
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) 
    		throws IOException, ClassNotFoundException {
        Processor p = new Processor(args[0], Integer.parseInt
        		(args[3]), Integer.parseInt(args[2]), args[1]);
        p.process();
    }
}
