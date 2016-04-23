/**
 * 
 * @author Drew, Jacob
 * @version 4/21/16
 *
 */

public class MemMan {
    
    private int freeSize;
    private int start;
    private int bufSize;

    /**
     * The Memory Manager that handles the data
     * @param bufsize the size of the buffer
     */
    public MemMan(int bufsize) {
        freeSize = bufsize;
        start = 0;
        bufSize = bufsize;
    }
    
    
    /**
     * Decreases the amount of free space, unless it needs to allocate
     * space
     * @param space The amount of space to take up
     */
    public void add(int space) {
        freeSize = freeSize - (space);
        start = start + space;
        if (freeSize <= 0) {
            freeSize += bufSize;
        }
    }
    
    /**
     * Dumps the amount of space in the buffer
     */
    public void dump() {
        System.out.println("Freelist Blocks:");
        System.out.println("(" + start + ", " + freeSize + ")");
    }
}
