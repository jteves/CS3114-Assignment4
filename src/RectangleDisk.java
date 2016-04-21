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
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: RectangleDisk <commandfile> "
                + "<diskFile> <numBuffs> <buffSize>");
        }
        else {
            System.out.println("Found expected parameter list.");
        }
    }
}
