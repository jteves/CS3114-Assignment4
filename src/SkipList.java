 import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

/**
 * write more about this class later
 * @author Drew Williams, Jacob Teves
 * @version 2/2/16
 *
 *@param <K> is the key
 *@param <E> is the value
 */
public class SkipList<K extends Comparable<K>, E> implements Serializable 
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
    public SkipList()
    {
        // random number generator used for nodes
        rnd   = new Random();
        // creates the head node
        head  = new SkipNode<K, E>(1, null);
        // represents the lists size. initially is 0
        numEl = 0;

        iteToHead();
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
     * @throws IOException 
     */
    public int insert(KVPair<K, E> pair) throws IOException
    {
        int total;
        
        // increments the size of the list
        numEl++;
        //creates a node to hold the KVPair
        SkipNode<K, E> node = new SkipNode<K, E>(randomLevel(), pair);
        //total = Serializer.serialize(pair.key()).length; 
        //total += Serializer.serialize(pair.value()).length; 
        //total = Serializer.serialize(pair).length;
        total = Serializer.serialize(node).length;
        //checks to see if the head node needs to adjust its size
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
        while (i >= 0)
        {    
            if (temp.getPointer(i) == null)
            {
                if (node.size() > i)
                {
                    node.setPointer(i, null);
                    temp.setPointer(i, node);
                }
                i--;
            }
            else 
            {
                x = node.getPair().key().compareTo(
                       temp.getPointer(i).getPair().key());
//                // ensures that when keys are equal the largest one
//                // will be first
//                if (x == 0 && temp.getPointer(i).size() > node.size())
//                {
//                    x++;
//                }
                if (x > 0)
                {
                    temp = temp.getPointer(i);
                }
                else
                {
                    if (node.size() > i)
                    {
                        node.setPointer(i, temp.getPointer(i));
                        temp.setPointer(i, node);
                    }
                    i--;
                }
                    
            }
        }
        return total;
    }

//    /**
//     * precondition: look is a not null kvpair
//     * postcondition: the KVPair that has a matching
//     * key with look will be pointed to by the iterator
//     * 
//     * if look does not have a match then the iterator 
//     * will be set to null
//     * 
//     * @param look is the pair that has the key that
//     * We wish to find 
//     * sets the iterator to 
//     * the first instance of a kvpair with a key 
//     * that matches look
//     */
//    public void search(KVPair<K, E> look)
//    {
//        //will be used to compare keys in the list with look
//        SkipNode<K, E> temp = head;
//        //look is inserted into be a skipnode to help compare values
//        SkipNode<K, E> node = new SkipNode<K, E>(1, look);
//        // keeps track of which level of pointer is being looked
//        // at and compared to
//        int i               = head.size() - 1;
//        // represents the value of the compareTo() function
//        int x;
//        //iterator is set to null
//        ite = null;
//        while (i >= 0)
//        {    
//            if (temp.getPointer(i) == null)
//            {
//                i--;
//            }
//            else 
//            {
//                x = node.getPair().key().compareTo(
//                       temp.getPointer(i).getPair().key());
//                if (x > 0)
//                {
//                    temp = temp.getPointer(i);
//                }
//                else
//                {
//                    if (x == 0 && i == 0)
//                    {
//                        ite = temp.getPointer(i);
//                        // exits the loop once a match is found
//                        break;
//                    }
//                    i--;
//                }
//                    
//            }
//        }
//    }
//
//    /**
//     * 
//     * precondition: look is a not null kvpair
//     * postcondition: the node that matches keys with
//     * look has been found, removed, and returned
//     * 
//     * @param look is the pair we wish to remove
//     * 
//     * @return the kv pair that was removed from the list
//     * or null if there was no match
//     * 
//     * removes values based on the k value
//     */
//    public KVPair<K, E> remove(KVPair<K, E> look)
//    {
//        // will be returned
//        KVPair<K, E> ans    = null;
//        // will be used to compare items in the list
//        SkipNode<K, E> temp = head;
//        // look is made into  a skip node to make comparisons
//        // easier
//        SkipNode<K, E> node = new SkipNode<K, E>(1, look);
//        // keeps track of which level of pointer is being looked
//        // at and compared to
//        int i = head.size() - 1;
//        // holds the value of the compare to method
//        int x;
//        int _switch = 1;
//        int sizeMatch = 0;
//        while (i >= 0)
//        {    
//            if (temp.getPointer(i) == null)
//            {
//                i--;
//            }
//            else 
//            {
//                x = node.getPair().key().compareTo(
//                       temp.getPointer(i).getPair().key());
//                if (x > 0)
//                {
//                    temp = temp.getPointer(i);
//                }
//                else
//                {
//                    if (x == 0)
//                    {
//                        if (_switch == 1) {
//                            if (i == 0)
//                            {
//                                ans = temp.getPointer(0).getPair();
//                            }
//                            sizeMatch = temp.getPointer(i).size();
//                            temp.setPointer(i, temp.getPointer(i).
//                            		getPointer(i));
//                            _switch = 0;
//                        }
//                        else {
//                            if (sizeMatch == temp.getPointer(i).size()) {
//                                
//                                if (i == 0)
//                                {
//                                    ans = temp.getPointer(0).getPair();
//                                }
//                                temp.setPointer(i, temp.getPointer(i).
//                                		getPointer(i));
//                            }
//                            else {
//                                temp = temp.getPointer(i);
//                                i++;
//                            } 
//                        }
//                    }
//                    i--;
//                }
//                    
//            }
//        }
//        // decrements the size if an item has been removed
//        if (ans != null)
//        {
//            numEl--;
//        }
//        return ans;
//    }
//    
//    /**
//     * precondition: look is not null
//     * postcondition: look has been removed from the list
//     * and returned.  if look is not found then the function
//     * returns null
//     * 
//     * @param look is the parameter being removed
//     * @param s is the key being compared to
//     * 
//     * @return the kv pair that has been removed from
//     * the list
//     * 
//     * this method removes values based on the E value
//     */
//    public KVPair<K, E> remove2(KVPair<K, E> look)
//    {
//     // will be returned
//        KVPair<K, E> ans    = null;
//        // will be used to compare items in the list
//        SkipNode<K, E> temp = head;
//        // look is made into  a skip node to make comparisons
//        // easier
//        SkipNode<K, E> node = new SkipNode<K, E>(1, look);
//        // keeps track of which level of pointer is being looked
//        // at and compared to
//        int i = head.size() - 1;
//        // holds the value of the compare to method
//        int x;
//        int _switch = 1;
//        int sizeMatch = 0;
//        while (i >= 0)
//        {    
//            if (temp.getPointer(i) == null)
//            {
//                i--;
//            }
//            else 
//            {
//                x = node.getPair().key().compareTo(
//                       temp.getPointer(i).getPair().key());
//                if (x > 0)
//                {
//                    temp = temp.getPointer(i);
//                }
//                else
//                {
//                    if (x == 0 && temp.getPointer(i).getPair()
//                            .value().equals(look.value()))
//                    {
//                        if (_switch == 1) {
//                            if (i == 0)
//                            {
//                                ans = temp.getPointer(0).getPair();
//                            }
//                            sizeMatch = temp.getPointer(i).size();
//                            temp.setPointer(i, temp.getPointer(i).getPointer(i));
//                            _switch = 0;
//                        }
//                        else {
//                            if (sizeMatch == temp.getPointer(i).size()) {
//                                
//                                if (i == 0)
//                                {
//                                    ans = temp.getPointer(0).getPair();
//                                }
//                                temp.setPointer(i, temp.getPointer(i).getPointer(i));
//                            }
//                            else {
//                                temp = temp.getPointer(i);
//                                i++;
//                            } 
//                        }
//                    }
//                    else if (x == 0 && i == 0) {
//                        temp = temp.getPointer(i);
//                        i++;
//                    }
//                    i--;
//                }
//                    
//            }
//        }
//        // decrements the size if an item has been removed
//        if (ans != null)
//        {
//            numEl--;
//        }
//        return ans;
//    }
    
    //-------------------------------------------
    // Iterator methods

    /**
     * precondition: iterator ite is not null
     * postcondition: iterator now points to the
     * next node
     */
    public void iteNext()
    {
        ite = nextNode(ite);
    }
    /**
     * precondition: ite is not null
     * postcondition: the current KVpair is returned
     * 
     * @return the pair that the iterator
     * points to
     */
    public KVPair<K, E> currentPair()
    {
        if (ite == null)
        {
            return null;
        }
        return ite.getPair();
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
     */
    private SkipNode<K, E> nextNode(SkipNode<K, E> node)
    {
        return node.getPointer(0);
    }


    //------------------------------------------------------

    
}





