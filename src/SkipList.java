 import java.io.IOException;
import java.util.Random;

/**
 * write more about this class later
 * @author Drew Williams, Jacob Teves
 * @version 2/2/16
 *
 *@param <K> is the key
 *@param <E> is the value
 */
public class SkipList<K extends Comparable<K>, E>  
{
    
    /**
     * head node of the list
     */
    private SkipNode<K, E> head;
    /**
     * iterator for the list
     */
    private SkipNode<K, E> ite;
    /**
     * random number generator for determining
     * the number of pointers a node will have
     */
    private int numEl;
    /**
     * Random number generator used for creating
     * nodes
     */
    private Random rnd;
    
    private MemMan mem;
//    private Serializer ser;
    //---------------------------------------------------

    /**
     * constructor for the skipList class
     *  
     * postcondition:
     * rnd  is initialized
     * head is initilaized with a size of 1
     * and a null KVPair
     * numEl is 0
     * ite points to the head node
     */
    public SkipList(MemMan m)
    {
        // random number generator used for nodes
        rnd   = new Random();
        // creates the head node
        head  = new SkipNode<K, E>(1, -1);
        // represents the lists size. initially is 0
        numEl = 0;

        iteToHead();
        mem = m;
//        ser = new Serializer();
    }

    /**
     * @return the number of elements in the
     * list 
     */
    public int getSize()
    {
        return numEl;
    }

    /**
     * 
     * postcondition: a random number has been chosen to
     * represent the number of pointers a node will have
     * 
     * @return the number of pointers a node will
     * have
     * 
     * had to modify the method due to webcat 
     * point deductions
     */
    private int randomLevel() 
    {
        int lev;
        //represents the level of pointers
        int num = 1;
        for (lev = 1; rnd.nextInt(2) == 0; lev++)
        {
            lev = lev + 1;
            num++;
        }
        return num;
    }

    /**
     * 
     * precondition: pair is not null KVPair
     * postcondition: pair has been inserted into
     * the list in order numerically or alphabetically
     * 
     * in a case where two nodes have the same key
     * the one of greatest size will be the leading node
     * 
     * inserts a kvpair into the list
     * @param pair the  kvpair to be inserted
     * @return the integer handler
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, E> pair) throws IOException, ClassNotFoundException
    { 
        // increments the size of the list
        numEl++;
        //creates a node to hold the KVPair
        int loc = mem.insert(pair);
        SkipNode<K, E> node = new SkipNode<K, E>(randomLevel(), loc);
        loc = mem.insert(node);
        
        KVPair<K, E> nPair = (KVPair<K, E>) mem.getObj(node.getPair());
        
        if (node.size() > head.size())
        {
            head.changeSize(node.size());
        }
        //used for later comparisons to help place the
        //new node in the proper spot
        SkipNode<K, E> temp = head;
        // will represent the result of compareTo()
        int x;
        // keeps track of which level of pointer is being looked
        // at and compared to
        int i = head.size() - 1;
        int upLoc = head.getPointer(i);
        while (i >= 0)
        {    
            if (temp.getPointerInt(i) == -1)
            {
                if (node.size() > i)
                {
                    node.setPointer(i, -1);
                  
                  temp.setPointer(i, loc);
                  mem.update(loc, node);
                  if (temp != head) {
                     mem.update(upLoc, temp);
                  }
                  else {  
                      upLoc = head.getPointer(i);
                      
                  }
                  
                }
                i--;
            }
            else 
            {
                SkipNode<K, E> tNode = (SkipNode<K, E>)mem.getObj(temp.getPointer(i));
                KVPair<K, E> tPair = (KVPair<K, E>) mem.getObj(tNode.getPair());
                x = nPair.key().compareTo(tPair.key());
                // ensures that when keys are equal the largest one
                // will be first
                if (x == 0 && tNode.size() > node.size())
                {
                    x++;
                }
                if (x > 0)
                {
                    upLoc = temp.getPointer(i);
                    temp = (SkipNode<K, E>) mem.getObj(temp.getPointer(i)); 
                    
                }
                else
                {
                    if (node.size() > i)
                    {
                        node.setPointer(i, temp.getPointerInt(i));
                        mem.update(loc, node);
                        temp.setPointer(i, loc);
                        if (temp != head){
                            mem.update(upLoc, temp);
                        }
                    }
                    i--;
                }
                    
            }
        }
        
    }

    /**
     * precondition: look is a not null kvpair
     * postcondition: the KVPair that has a matching
     * key with look will be pointed to by the iterator
     * 
     * if look does not have a match then the iterator 
     * will be set to null
     * 
     * @param look is the pair that has the key that
     * We wish to find 
     * sets the iterator to 
     * the first instance of a kvpair with a key 
     * that matches look
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    public void search(KVPair<K, E> look) throws ClassNotFoundException, IOException
    {
        //will be used to compare keys in the list with look
        SkipNode<K, E> temp = head;
        //look is inserted into be a skipnode to help compare values
        // keeps track of which level of pointer is being looked
        // at and compared to
        int i               = head.size() - 1;
        // represents the value of the compareTo() function
        int x;
        //iterator is set to null
        ite = null;
        while (i >= 0)
        {    
            if (temp.getPointer(i) == -1)
            {
                i--;
            }
            else 
            {
                SkipNode<K, E> tNode = (SkipNode<K, E>)mem.getObj(temp.getPointer(i));
                KVPair<K, E> tPair = (KVPair<K, E>) mem.getObj(tNode.getPair());
                x = look.key().compareTo(tPair.key());
                if (x > 0)
                {
                    temp = (SkipNode<K, E>) mem.getObj(temp.getPointer(i));
                }
                else
                {
                    if (x == 0 && i == 0)
                    {
                        ite = (SkipNode<K, E>) mem.getObj(temp.getPointer(i));
                        // exits the loop once a match is found
                        break;
                    }
                    i--;
                }
                    
            }
        }
    }

    /**
     * 
     * precondition: look is a not null kvpair
     * postcondition: the node that matches keys with
     * look has been found, removed, and returned
     * 
     * @param look is the pair we wish to remove
     * 
     * @return the kv pair that was removed from the list
     * or null if there was no match
     * 
     * removes values based on the k value
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, E> remove(KVPair<K, E> look) throws ClassNotFoundException, IOException
    {
        // will be returned
        KVPair<K, E> ans    = null;
        // will be used to compare items in the list
        SkipNode<K, E> temp = head;
        // look is made into  a skip node to make comparisons
        // easier
        
        // keeps track of which level of pointer is being looked
        // at and compared to
        int i = head.size() - 1;
        // holds the value of the compare to method
        int x;
        int _switch = 1;
        int sizeMatch = 0;
        int upLoc = head.getPointer(i);
        while (i >= 0)
        {    
            if (temp.getPointer(i) == -1)
            {
                i--;
            }
            else 
            {
                SkipNode<K, E> tNode = (SkipNode<K, E>)mem.getObj(temp.getPointer(i));
                KVPair<K, E> tPair = (KVPair<K, E>) mem.getObj(tNode.getPair());
                x = look.key().compareTo(tPair.key());
                if (x > 0)
                {
                    upLoc = temp.getPointer(i);
                    temp = (SkipNode<K, E>) mem.getObj(temp.getPointer(i));
                }
                else
                {
                    if (x == 0)
                    {
                        if (_switch == 1) {
                            if (i == 0)
                            {
                                SkipNode<K, E> tNode2 = (SkipNode<K, E>)mem.getObj(temp.getPointer(0));
                                ans = (KVPair<K, E>) mem.getObj(tNode2.getPair());
                            }
                            sizeMatch = tNode.size();
                            temp.setPointer(i, tNode.getPointer(i));
                            if (temp != head) {
                                mem.update(upLoc, temp);
                            }
                            _switch = 0;
                        }
                        else {
                            if (sizeMatch == tNode.size()) {
                                
                                if (i == 0)
                                {
                                    SkipNode<K, E> tNode2 = (SkipNode<K, E>)mem.getObj(temp.getPointer(0));
                                    ans = (KVPair<K, E>) mem.getObj(tNode2.getPair());
                                }
                                temp.setPointer(i, tNode.getPointer(i));
                                if (temp != head) {
                                    mem.update(upLoc, temp);
                                }
                                
                            }
                            else {
                                upLoc = temp.getPointer(i);
                                temp = tNode;
                                i++;
                            } 
                        }
                    }
                    i--;
                }
                    
            }
        }
        // decrements the size if an item has been removed
        if (ans != null)
        {
            numEl--;
        }
        return ans;
    }
    
    /**
     * precondition: look is not null
     * postcondition: look has been removed from the list
     * and returned.  if look is not found then the function
     * returns null
     * 
     * @param look is the parameter being removed
     * @param s is the key being compared to
     * 
     * @return the kv pair that has been removed from
     * the list
     * 
     * this method removes values based on the E value
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public KVPair<K, E> remove2(KVPair<K, E> look) throws ClassNotFoundException, IOException
    {
     // will be returned
        KVPair<K, E> ans    = null;
        // will be used to compare items in the list
        SkipNode<K, E> temp = head;
        // look is made into  a skip node to make comparisons
        // easier
        // keeps track of which level of pointer is being looked
        // at and compared to
        int i = head.size() - 1;
        // holds the value of the compare to method
        int x;
        int _switch = 1;
        int sizeMatch = 0;
        int upLoc = head.getPointer(i);
        while (i >= 0)
        {    
            if (temp.getPointer(i) == -1)
            {
                i--;
            }
            else 
            {
                @SuppressWarnings("unchecked")
                SkipNode<K, E> tNode = (SkipNode<K, E>)mem.getObj(temp.getPointer(i));
                @SuppressWarnings("unchecked")
                KVPair<K, E> tPair = (KVPair<K, E>) mem.getObj(tNode.getPair());
                x = look.key().compareTo(tPair.key());
                if (x > 0)
                {
                    upLoc = temp.getPointer(i);
                    temp = tNode;
                }
                else
                {
                    if (x == 0 && tPair.value().equals(look.value()))
                    {
                        if (_switch == 1) {
                            if (i == 0)
                            {
                                ans = tPair;
                            }
                            sizeMatch = tNode.size();
                            temp.setPointer(i, tNode.getPointer(i));
                            if (temp != head) {
                                mem.update(upLoc, temp);
                            }
                            _switch = 0;
                        }
                        else {
                            if (sizeMatch == tNode.size()) {
                                
                                if (i == 0)
                                {
                                    ans = tPair;
                                }
                                temp.setPointer(i, tNode.getPointer(i));
                                if (temp != head) {
                                    mem.update(upLoc, temp);
                                }
                            }
                            else {
                                upLoc = temp.getPointer(i);
                                temp = tNode;
                                i++;
                            } 
                        }
                    }
                    else if (x == 0 && i == 0) {
                        upLoc = temp.getPointer(i);
                        temp = tNode;
                        i++;
                    }
                    i--;
                }
                    
            }
        }
        // decrements the size if an item has been removed
        if (ans != null)
        {
            numEl--;
        }
        return ans;
    }
    
    @SuppressWarnings("unchecked")
    public KVPair<K, E> searchforR(KVPair<K, E> look) throws ClassNotFoundException, IOException {
        SkipNode<K, E> temp = (SkipNode<K, E>)
                mem.getObj(head.getPointer(0));
        int _switch = 1;
        Rectangle rec;
        KVPair<K, E> pair;
        while (_switch != 0) {
            if (temp.getPointer(0) == -1) {
                _switch = 0;
            }
            pair = (KVPair<K, E>) mem.getObj(temp.getPair());
            rec = (Rectangle) pair.value();
            if (rec.sameCords((Rectangle) look.value())) {
                return pair;
            }
            temp = (SkipNode<K, E>) mem.getObj(temp.getPointer(0));
        }
        return null;
    }
    
    //-------------------------------------------
    // Iterator methods

    /**
     * precondition: iterator ite is not null
     * postcondition: iterator now points to the
     * next node
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void iteNext() throws ClassNotFoundException, IOException
    {
        ite = nextNode(ite);
    }
    /**
     * precondition: ite is not null
     * postcondition: the current KVpair is returned
     * 
     * @return the pair that the iterator
     * points to
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, E> currentPair() 
            throws ClassNotFoundException, IOException
    {
        if (ite == null)
        {
            return null;
        }
        return (KVPair<K, E>) mem.getObj(ite.getPair());
    }

    /**
     * postcondition: ite points to the head node
     */
    public void iteToHead()
    {
        ite = head;
    }

    /**
     * precondition: ite is not null
     * postcondition: the node depth of ite is
     * returned
     * 
     * @return the size of ite
     */
    public int currentNodeDepth()
    {
        return ite.size();
    }

    /**
     * precondition: node is not null
     * postcondition: returns the next node
     * 
     * @param node that preceeds the node being
     * returned
     * @return the node after node
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    @SuppressWarnings("unchecked")
    private SkipNode<K, E> nextNode(SkipNode<K, E> node) 
            throws ClassNotFoundException, IOException
    {
        if (node.getPointer(0) == -1) {
            return null;
        }
        return (SkipNode<K, E>) mem.getObj(node.getPointer(0));
    }


    //------------------------------------------------------

    
}





