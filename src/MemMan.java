import java.io.IOException;

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
    private FreeBlock head;
    private FreeBlock tail;
    private int end;

    /**
     * The Memory Manager that handles the data
     * @param bufsize the size of the buffer
     */
    public MemMan(int bufsize) {
        freeSize = bufsize;
        start = 0;
        bufSize = bufsize;
        head.setNext(tail);
        tail.setPrev(head);
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
    
    
    public int insert(Object obj) throws IOException {
        
        byte[] arr = Serializer.serialize(obj);
        int space = -1;
        int loc = -1;
        FreeBlock block;
        while (loc == -1) { 
            block = head.next();
            while (block != tail) {
                if (block.getSpace() >= arr.length && space == -1) {
                    space = block.getSpace() - arr.length;
                    loc = block.getBeg();
                }
                else if (block.getSpace() >= arr.length && 
                        block.getSpace() - arr.length < space) {
                    space = block.getSpace() - arr.length;
                    loc = block.getBeg();
                }
                block = block.next();
            }
            if (loc == -1) {
                FreeBlock temp = new FreeBlock(end, bufSize);
                tail.previous().setNext(temp);
                temp.setPrev(tail.previous());
                tail.setPrev(temp);
                temp.setNext(tail);
                joinBlocks();
            }
        }
        
        block = head.next();
        while (block.getBeg() != loc) {
            block = block.next();
        }
        
        block.insert(arr.length);
        //TODO actually speak to the file
        return loc;
    }
    
    
    public void joinBlocks() {
        FreeBlock block = head.next();
        while (block != tail) {
            if (block.previous().getBeg() + block.
                    previous().getSpace() == block.getBeg()) {
                block.previous().setNext(block.next());
                block.next().setPrev(block.previous());
                block.previous().addSpace(block.getSpace());
            }  
            block = block.next();
        }
    }
    
    
    
    
    
    
    
    
    
    private class FreeBlock {
        
        private FreeBlock next;
        private FreeBlock prev;
        private int len;
        private int beg;
        
        
        private FreeBlock(int start, int space) {
            next = null;
            prev = null;
            len = space;
            beg = start;
        }
        
        private void setNext(FreeBlock block) {
            next = block;
        }
        
        private void setPrev(FreeBlock block) {
            prev = block;
        }
        
        private FreeBlock next() {
            return next;
        }
        
        private FreeBlock previous() {
            return prev;
        }
        
        private int getSpace() {
            return len;
        }
        
        private int getBeg() {
            return beg;
        }
        
        private void insert(int x) {
            beg = beg + x;
            len = len - x;
        }
        
        private void addSpace(int x) {
            len = len + x;
        }
        
    }
}
