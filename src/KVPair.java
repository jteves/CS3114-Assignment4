import java.io.Serializable;

/**
 * 
 * @author Drew Williams, Jacob Teves
 *
 * @param <K> is the key
 * @param <E> value
 * 
 * @version 2/3/16
 * 
 * The KVPair class holds a pair of records, a key and a value
 */
public class KVPair<K extends Comparable<K>, E>
               implements Comparable<KVPair<K, E>>, Serializable {
    private K theKey;
    private E theVal;
    /**
     * constructor
     * 
     * @param k the key
     * @param v the value
     */
    public KVPair(K k, E v) {
        theKey = k;
        theVal = v;
    }

    /**
     * @param it compares keys with it
     * @return the answer
     */
    public int compareTo(KVPair<K, E> it) {
        return theKey.compareTo(it.key());
    }

    /**
     * 
     * @return the key
     */
    public K key() {
        return theKey;
    }
    /**
     * 
     * @return the value
     */
    public E value() {
        return theVal;
    }
    
}
