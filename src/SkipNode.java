import java.io.Serializable;

//import SkipList.SkipNode;

/**
 * 
 * @author Drew Williams, Jacob Teves
 * @version 2/2/16
 * 
 * @param <K>is the key
 * @param <E>value
 * 
 * The skipnode class will be used to hold the kVPairs and their
 * nodes that they point to in the skiplist
 */
public class SkipNode<K extends Comparable<K>, E> implements Serializable
{
    /**
     * the array of kvpair pointers the node will hold
     */
    private int[] pointers;
    /**
     * the number of pointers the node holds
     */
    private int size;
    /**
     * the kv pair held by the node
     */
    private int data;

    /**
     * precondition: pair and numpoint are not null
     * postcondition: a new skipnode has been created
     * with size numpoint and data value of kvpair
     * 
     * creates a new skipnode object
     * 
     * @param numpoint is the number of pointers
     * @param pair is the kv pair being held by the node
     * to give the skipnode
     */

    @SuppressWarnings("unchecked")
    public SkipNode(int numpoint, int pair)
    {
        // initializes the size field
        size     = numpoint;
        //initializes the pointers
        pointers = new int[size];
        //sets all pointers to null
        // initializes the data field
        data     = pair;
        for (int i = 0; i < size; i++)
        {
            pointers[i] = -1;
        }

    }

    /**
     * 
     * @return the KVPair, data
     */
    public int getPair()
    {
        return data;
    }

    /**
     * precondition: x is a 0 or positive
     * integer that is not bigger than size
     * postcondition: the pointer held in position
     * x is returned
     * 
     * @param x the location of the pointer in
     * pointers[]
     * @return the specific pointer in pointers[] at 
     * position x
     */
    public int getPointer(int x)
    {
        return pointers[x];
        
    }
    
    
    /**
     * precondition: x is a 0 or positive
     * integer that is not bigger than size
     * postcondition: the pointer held in position
     * x is returned
     * 
     * @param x the location of the pointer in
     * pointers[]
     * @return the specific pointer in pointers[] at 
     * position x
     */
    public int getPointerInt(int x)
    {
        return pointers[x];
    }

    /**
     * precondition: node is not null and x is an integer
     * between 0 and the size
     * postcondition: the pointer at position x is now
     * set to the value of node
     * 
     * sets the value of pointers[x] to be node
     * 
     * @param x the location of the pointer in
     * pointers[]
     * @param node the node that will now be contained
     * in pointers[x]
     */
    public void setPointer(int x, int node)
    {
        pointers[x] = node;
    }

    /**
     * 
     * @return the number of elements held in
     * pointers[]
     */
    public int size()
    {
        return size;
    }

    /**
     * 
     * precondition: x is a positive integer
     * postcondition: the size of the node has been
     * changed to x
     * 
     * changes the size of the array, pointers[],
     * of the head node
     * how ever many spots specified by:
     * x - pointers.length, will be added onto the
     * end of pointers[]
     * 
     * @param x is the size that pointers[] will be
     * changed to
     */
    public void changeSize(int x)
    {
        // new value for data
        @SuppressWarnings("unchecked")
        int[] arr = new int[x];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -1;
        }
        for (int i = 0; i < size; i++)
        {
            arr[i] = pointers[i];
        }
        // pointers and size are updated
        pointers  = arr;
        size      = x;
    }

}

