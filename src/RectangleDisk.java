import java.io.IOException;

/**
 * { your description of the project here }
 */

/**
 * The class containing the main method, the entry point
 * of the application.
 *
 * @author { your name here }
 * @version { put something here }
 */
public class RectangleDisk {

    /**
     * The entry point for the application.
     *
     * @param args The command line arguments.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.out.println("Usage: RectangleDisk <commandfile> "
                + "<diskFile> <numBuffs> <buffSize>");
        }
        else {
            Processor p = new Processor(args[0], 
                    Integer.parseInt(args[3]));
            p.process();
        }
    }
}
