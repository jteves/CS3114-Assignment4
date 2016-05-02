import java.io.IOException;

/**
 * This project processes a list of commands that manipulates 
 * Rectangle objects into a SkipList which is serialized into a disk
 * file through a MemoryManager and BufferPool. It incorporates a 
 * best-fit design storage.
 */

/**
 * The class containing the main method, the entry point
 * of the application.
 *
 * Compiler: javac 1.8.0_20
 * OS: Windows 10 
 * Date Completed: 05/02/2016
 * 
 * @author Drew Williams- dwill225, Jacob Teves- jteves
 * @version 5/2/2016
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
    	// args should look like: 
    	// 		{commandFile} {diskFile} {numBuffs} {buffSize}
        Processor p = new Processor(args[0], Integer.parseInt
        		(args[3]), Integer.parseInt(args[2]), args[1]);
        p.process();
    }
}
