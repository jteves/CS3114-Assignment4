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
     * Inserts an object into the bufferpool using the best fit method
     * 
     * @param obj is the object being inserted into the bufferpool
     * @return the size of the object and key value when serialized
     * @throws IOException
     */
    public int insert(Object obj) throws IOException {
        //byte array when object is serialized
        byte[] arr = Serializer.serialize(obj);
        //initializing a "null" for comparisons later on
        int space = -1;
        int loc = -1;
        //used for traversing list
        FreeBlock block;
        //length of the array plus key value
        int length = arr.length + KEY;
        //finds place to insert obj
        while (loc == -1) { 
            block = head.next();
            // finds best fit
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
            //if there is no fit, more space is added
            if (loc == -1) {
                //new block being added
                FreeBlock temp = new FreeBlock(end, bufSize);
                tail.previous().setNext(temp);
                temp.setPrev(tail.previous());
                tail.setPrev(temp);
                temp.setNext(tail);
                //merges blocks if needed
                joinBlocks();
                end = end + bufSize;
            }
        }
        //block to be inserted to
        block = head.next();
        //finds block
        while (block.getBeg() != loc) {
            block = block.next();
        }
        //inserts and sends bufferpool
        block.insert(length);
        bp.recieveFromMerge(loc + KEY, arr);
        byte[] temp = new byte[KEY];
        temp[0] = (byte) (arr.length & 0xff);
        temp[1] = (byte) ((arr.length >> 8) & 0xff);
        bp.recieveFromMerge(loc, temp);
        return loc;
    }
    
    /**
     * joins adjacent free blocks into one large block
     */
    public void joinBlocks() {
        //used for traversing list
        FreeBlock block = head.next();
        while (block != tail) {
            // merges blocks if they are adjacent
            if (block.previous().getBeg() + block.
                    previous().getSpace() == block.getBeg()) {
                block.previous().setNext(block.next());
                block.next().setPrev(block.previous());
                block.previous().addSpace(block.getSpace());
            }  
            block = block.next();
        }
    }
    
    /**
     * Gets the deserialized object at the x location
     * @param x location of the object
     * @return the object, deserialized
     * @throws ClassNotFoundException
     * @throws IOException 
     */
    public Object getObj(int x) throws 
            ClassNotFoundException, IOException {
    	// Gets the key of the object
        byte[] key = bp.sendToMerge(x, x + KEY);
        int len = key[0];
        len = len & 0xff;
        int t = key[1] & 0xff;
        len = len + (t << 8);
        // Retrieves the object from the bufferpool based off key
        byte[] arr = bp.sendToMerge(x + KEY, x + len + KEY);
       
        return Serializer.deserialize(arr);
    }
    
    /**
     * Updates the bufferpool object itself and its key. 
     * Takes place when inserting and removing from the skipList.
     * @param loc Location in the bufferpool
     * @param obj Object being updated
     * @throws IOException
     */
    public void update(int loc, Object obj) throws IOException {
    	
        byte[] temp = new byte[KEY];
        byte[] arr = Serializer.serialize(obj);
       // Starting position of object
        temp[0] = (byte) (arr.length & 0xff);
       // Length of object
        temp[1] = (byte) ((arr.length >> 8) & 0xff);
        bp.recieveFromMerge(loc, temp);
        bp.recieveFromMerge(loc + KEY, arr);
    }
    
    /**
     * Removes the block at the provided location of the free list
     * @param loc location of the value
     */
    public void remove(int loc) {
    	// Gets the key
        byte[] key = bp.sendToMerge(loc, loc + KEY);
        int len = key[0];
        len = len & 0xff;
        int t = key[1] & 0xff;
        len = len + (t << 8);
        FreeBlock block = new FreeBlock(loc, len + KEY);
        FreeBlock temp = head;
        // Traverses the list to locate the object block location      
        while (temp.next() != tail) {
            if (temp.next().getBeg() > block.getBeg()) {
            	// Empties the value in the located block
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
    
    
    
    
    
    
    
    
    /**
     * A double linked list that stores the objects location and 
     * length
     * @author Drew Williams, Jacob Teves
     * @version 5/02/2016
     */
    private class FreeBlock {
        
        private FreeBlock next;
        private FreeBlock prev;
        private int len;
        private int beg;
        
        /**
         * @param start starting free space
         * @param space amount of free space
         */
        private FreeBlock(int start, int space) {
            next = null;
            prev = null;
            len = space;
            beg = start;
        }
        
        /**
         * Sets the next block to the provided block
         * @param block the block that should be next
         */
        private void setNext(FreeBlock block) {
            next = block;
        }

        /**
         * Sets the previous block to the provided block
         * @param block the block that should be previous
         */
        private void setPrev(FreeBlock block) {
            prev = block;
        }
        
        /**
         * Gets the block that's next
         * @return the next FreeBlock node
         */
        private FreeBlock next() {
            return next;
        }
        
        /**
         * Gets the block that's previous
         * @return the previous FreeBlock node
         */
        private FreeBlock previous() {
            return prev;
        }
        
        /**
         * Gets the amount of free space of the block
         * @return the block's length
         */
        private int getSpace() {
            return len;
        }
        
        /**
         * Gets the start of the block
         * @return the block's beginning
         */
        private int getBeg() {
            return beg;
        }
        
        /**
         * Used when inserting an object to lower the amount of free 
         * space in this block
         * @param x amount of space to be removed
         */
        private void insert(int x) {
            beg = beg + x;
            len = len - x;
        }
        
        /**
         * Used in joinBlocks(). Adds space to this block
         * @param x the amount of space added
         */
        private void addSpace(int x) {
            len = len + x;
        }
    }
}
