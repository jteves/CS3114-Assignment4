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

    public MemMan(int bufsize) {
        freeSize = bufsize;
        start = 0;
        bufSize = bufsize;
    }
    
    
    public void add(int space) {
        freeSize = freeSize - (space + 2);
        start = start + space + 2;
        if (freeSize <= 0) {
            freeSize += bufSize;
        }
    }
    
    public void dump() {
        int x = start + freeSize;
        System.out.println("Freelist Blocks:");
        System.out.println("(" + start + ", " + x + ")");
    }
}
