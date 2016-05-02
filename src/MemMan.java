import java.io.IOException;


/**
 * 
 * @author Drew, Jacob
 * @version 4/21/16
 *
 * The MemMan class is a memory manager
 * it keeps track of all of the free locations on the file
 * for the buffer pool to write to
 * 
 * it is implemented like a doubly linked list
 */
public class MemMan {
    /**
     * starting size of the memory blocks
     */
    private int bufSize;
    /**
     * head block
     */
    private FreeBlock head;
    /**
     * tail block
     */
    private FreeBlock tail;
    /**
     * current end value of the free memory
     */
    private int end;
    /**
     * buffer pool for writing to the storage file
     */
    private BufferPool bp;
    /**
     * length of the key values in bytes that will be inserted into the byte 
     * arrays
     */
    static int KEY = 2;

    /**
     * The constructor for the
     * Memory Manager that handles the data
     * 
     * @param bufsize the size of the buffer
     * @param pool is the buffer pool
     */
    public MemMan(int bufsize, BufferPool pool) {
        
        bufSize = bufsize;
        head = new FreeBlock(-1, -1);
        tail = new FreeBlock(-1, -1);
        //adds the original free block
        FreeBlock block = new FreeBlock(0, bufsize);
        //setting pointers for head and tail
        head.setNext(block);
        block.setNext(tail);
        tail.setPrev(block);
        block.setPrev(head);
        //current end of the list is the buffer size
        end = bufsize;
        bp = pool;
    }
    
    /**
     * Dumps the amount of space in the buffer
     */
    public void dump() {
        System.out.println("Freelist Blocks:");
        FreeBlock block = head.next();
        while (block != tail) {
            System.out.println("(" + block.getBeg() + ", " + 
                    block.getSpace() + ")");
            block = block.next();
        }
        
    }
    
    /**
     * 
     * @param obj is the object being inserted into the bufferpool
     * @return the size of the object and key value when serialized
     * @throws IOException
     */
    public int insert(Object obj) throws IOException {
        
        byte[] arr = Serializer.serialize(obj);
        int space = -1;
        int loc = -1;
        FreeBlock block;
        int length = arr.length + KEY;
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
    
    public void remove(int loc) {
        byte[] key = bp.sendToMerge(loc, loc + KEY);
        int len = key[0];
        len = len & 0xff;
        int t = key[1] & 0xff;
        len = len + (t << 8);
        FreeBlock block = new FreeBlock(loc, len + KEY);
        FreeBlock temp = head;
        while (temp.next() != tail) {
            if (temp.next().getBeg() > block.getBeg()) {
                block.setNext(temp.next());
                temp.setNext(block);
                block.setPrev(temp);
                block.next().setPrev(block);
                break;
            }
            temp = temp.next();
        }
        joinBlocks();
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
