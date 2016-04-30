import java.io.IOException;
import java.io.RandomAccessFile;


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
    BufferPool bp;
    static int PAD = 50;
    static int KEY = 2;

    /**
     * The Memory Manager that handles the data
     * @param bufsize the size of the buffer
     */
    public MemMan(int bufsize, BufferPool pool) {
        freeSize = bufsize;
        start = 0;
        bufSize = bufsize;
        head = new FreeBlock(-1, -1);
        tail = new FreeBlock(-1, -1);
        head.setNext(tail);
        tail.setPrev(head);
        end = 0;
        bp = pool;
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
        FreeBlock block = head.next();
        while (block != tail) {
            System.out.println("(" + block.getBeg() + ", " + block.getSpace() + ")");
            block = block.next();
        }
        
    }
    
    
    public int insert(Object obj) throws IOException {
        
        byte[] arr = Serializer.serialize(obj);
        int space = -1;
        int loc = -1;
        FreeBlock block;
        int length = arr.length + KEY + PAD;
        while (loc == -1) { 
            block = head.next();
            while (block != tail) {
                if (block.getSpace() >= length && space == -1) {
                    space = block.getSpace() - (length);
                    loc = block.getBeg();
                }
                else if (block.getSpace() >= length && 
                        block.getSpace() - (length ) < space) {
                    space = block.getSpace() - (length);
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
                end = end + bufSize;
            }
        }
        
        block = head.next();
        while (block.getBeg() != loc) {
            block = block.next();
        }
        
        block.insert(length);
        bp.recieveFromMerge(loc + KEY, arr);
        byte[] temp = new byte[KEY];
        temp[0] = (byte) (arr.length & 0xff);
        temp[1] = (byte) ((arr.length >> 8) & 0xff);
        //System.out.println(arr.length);
        //System.out.println(temp[0]);
        bp.recieveFromMerge(loc, temp);
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
    
    public Object getObj(int x) throws 
            ClassNotFoundException, IOException {
        byte[] key = bp.sendToMerge(x, x + KEY);
        int len = key[0];
        len = len & 0xff;
        int t = key[1] & 0xff;
        len = len + (t << 8);
        //System.out.println(len);
        byte[] arr = bp.sendToMerge(x + KEY, x + len + KEY);
        System.out.println(x + " " + len);
        return Serializer.deserialize(arr);
    }
    
    public void update(int loc, Object obj) throws IOException {
        byte[] temp = new byte[KEY];
        byte[] arr = Serializer.serialize(obj);
        temp[0] = (byte) (arr.length & 0xff);
        temp[1] = (byte) ((arr.length >> 8) & 0xff);
        bp.recieveFromMerge(loc, temp);
        bp.recieveFromMerge(loc + KEY, arr);
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
