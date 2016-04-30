import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Utilizes a SkipList that will handle Rectangle 
 * Objects and outputs various messages for each
 * command as a helper class for Processor
 * 
 * @author Drew Williams, Jacob Teves
 * @version 2/06/16
 *
 */
public class DataBase {

    // Contains and handles the Rectangle objects
    private SkipList<String, Rectangle> list;
    private MemMan m;
    /**
     * Default Constructor containing a SkipList of Rectangle obj
     * 
     * Postcondition: A new SkipList exists
     * @param x is size of buffers
     */
    public DataBase(int size, int num, String store)
    {
        
        //TODO change constructor to take numbuffers and filename
        BufferPool bp = new BufferPool(store, num, size);
        bp.read(bp.getFile(), 0);
        m = new MemMan(size, bp);
        list = new SkipList<String, Rectangle>(m);
    }

    /**
     * Determines if the parameters of a rectangle are appropriate
     * for inserting a rectangle into the SkipList and outputs the 
     * results
     * 
     * Precondition: the parameters are not null.
     * Postcondition: The SkipList will have been updated with a 
     * new Rectangle if allowed. The console outputs the results.
     * 
     * @param recName   name of the rectangle
     * @param x         x coordinate
     * @param y         y coordinate
     * @param w         width
     * @param h         height
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void insert(String recName, int x, int y, int w, int h) throws ClassNotFoundException, IOException 
    {
        // The rectangle to be inserted into the SkipList
        Rectangle rec = new Rectangle(recName, x, y, w, h);
        
        if (rec.isLegal())
        {
            // The KVPair inserted into the SkipList
            KVPair<String, Rectangle> pair = new 
                    KVPair<String, Rectangle>(rec.getName(), rec);           
            list.insert(pair);
            // output: "Rectangle inserted: (name, x, y, w, h)"
            System.out.println("Rectangle inserted: (" + recName + 
                    ", " + x + ", " + y + ", " + w + ", " + h + ")");
        }
        else
        {
            // output: "Rectangle rejected: (name, x, y, w, h)"
            System.out.println("Rectangle rejected: (" + recName + 
                    ", " + x + ", " + y + ", " + w + ", " + h + ")");
        }
    }

//    /**
//     * Removes a Rectangle object with the provided name if allowed
//     * 
//     * Precondition: recName is not null
//     * Postcondition: The SkipList will have been updated to delete 
//     * a Rectangle with the provided name if allowed. The console 
//     * outputs the results.
//     * 
//     * @param recName the name of a Rectangle object
//     */
//    public void remove(String recName)
//    {
//        // KVPair containing the Rectangle that is being removed
//        KVPair<String, Rectangle> pair = 
//                new KVPair<String, Rectangle>(recName, null);
//        
//        pair = list.remove(pair);
//        
//        // The rectangle doesn't exist in the list
//        if (pair == null)
//        {
//            // output: "Rectangle not found: (name)"
//            System.out.println("Rectangle not found: (" + recName + 
//                    ")");
//        }
//        else
//        {
//            Rectangle rec = pair.value();
//            // output: "Rectangle removed: (name, x, y, w, h)"
//            System.out.println("Rectangle removed: (" + rec.getName()
//                    + ", " + rec.getX() + ", " + rec.getY() + ", "
//                    + rec.getWidth() + ", " + rec.getHeight() +
//                    ")");
//        }
//    }
//    
//    /**
//     * Removes a Rectangle object with the provided coordinates if 
//     * they are appropriate 
//     * 
//     * Precondition: the parameters are not null
//     * Postcondition: The SkipList will have been updated to delete 
//     * a Rectangle with the provided coordinates if allowed. The 
//     * console outputs the results.
//     * 
//     * @param x the x coordinate
//     * @param y the y coordinate
//     * @param w the width
//     * @param h the height
//     */
//    public void remove(int x, int y, int w, int h)
//    {
//        // Set up as "x y w h"
//        String      coordinates = x + " " + y + " " + w + " " + h;
//        // The rectangle that is supposed to be removed
//        Rectangle   rec         = new Rectangle("a", x, y, w, h);
//        // For outputting
//        boolean     recExists   = false;
//        
//        if (rec.isLegal())
//        {
//            list.iteToHead();
//            list.iteNext(); 
//            
//            // Traverse through the entire SkipList instance
//            while (list.currentPair() != null)
//            {
//                // The rectangle is at the list's iterator position  
//                if (list.currentPair().value().toString()
//                        .equals(coordinates))
//                {
//                    rec         = list.currentPair().value();
//                    recExists   = true;
//                    
//                    list.remove2(list.currentPair(), rec.toString());
//                    
//                    // output: "Rectangle removed: (name, x, y, w, h)"
//                    System.out.println("Rectangle removed: (" + 
//                            rec.getName()   + ", " + rec.getX()
//                            + ", " + rec.getY() + ", " + 
//                            rec.getWidth() + ", " + rec.getHeight() + 
//                            ")");
//                    break; // End the traversal of the SkipList
//                }
//                list.iteNext();
//            }
//            if (!recExists)
//            {
//                // output: "Rectangle not found: (x, y, w, h)"
//                System.out.println("Rectangle not found: (" +
//                        x + ", " + y + ", " + w + ", " + 
//                        h + ")");
//            }
//        }
//        else // Rectangle coordinates are illegal
//        {
//            // output: "Rectangle rejected: (name, x, y, w, h)"
//            System.out.println("Rectangle rejected: (" +
//                    x + ", " + y + ", " + w + ", " + 
//                    h + ")");
//        }
//    }
//
    /**
     * This method searches for a rectangle matching 
     * the key value, recName
     * 
     * Precondition: the parameter is not null
     * Postcondition: Outputs all the Rectangles in the SkipList with 
     * the specified name to the console if they exist 
     * 
     * @param recName the name of the rectangle being searched for
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void search(String recName) throws ClassNotFoundException, IOException
    {
        // Pair with the recName parameter as the key
        KVPair<String, Rectangle> key = new 
                KVPair<String, Rectangle>(recName, null);  
        list.search(key);
        
        // The pair located at the current iterator of the SkipList
        KVPair<String, Rectangle> ans = list.currentPair();

        // The rectangle doesn't exist in the list
        if (ans == null)
        {
            System.out.println("Rectangle not found: " + recName);
        }
        else
        {
            // Rectangle that was searched for
            Rectangle foundRec;
            
            // Used to compare the rectangles in the SkipList
            int x = 0;
            // Used for the SkipList logic 
            int y = list.currentNodeDepth();

            // Traverses through the list
            do 
            {
                x = list.currentPair().compareTo(key);
                if (x == 0)
                {
                    foundRec = list.currentPair().value();
                    // output: "Rectangle rejected: (name, x, y, w, h)"
                    System.out.println("(" + foundRec.getName() + ", " +
                        foundRec.getX() + ", " + foundRec.getY() + 
                        ", " + foundRec.getWidth() + ", " + 
                        foundRec.getHeight() + ")");
                }
                list.iteNext();
                
            } while (list.currentPair() != null 
                    && list.currentNodeDepth() < y);
            
        }
    }
//    
//    /**  
//     * Searches through the SkipList for Rectangles contained in 
//     * the specified region.
//     * 
//     * Precondition: the parameters are not null
//     * Postcondition: Outputs all the Rectangles in the SkipList 
//     * within the specified region to the console if they exist 
//     * 
//     * @param x the x coordinate
//     * @param y the y coordinate
//     * @param w the width
//     * @param h the height
//     */
//    public void regionSearch(int x, int y, int w, int h)
//    {
//        // output: "Rectangles intersecting region: (x, y, w, h)"
//        System.out.println("Rectangles intersecting region ("
//                + x + ", " + y + ", " + w + ", " + h + "):");
//        
//        // new Rectangle containing the input values
//        Rectangle rec = new Rectangle(null, x, y, w, h);
//        
//        // Rectangle used in traversing the SkipList as the iterator
//        Rectangle temp;
//        list.iteToHead();
//        list.iteNext();
//        while (list.currentPair() != null)
//        {
//            temp = list.currentPair().value();
//            if (temp.intersect(rec) || rec.intersect(temp))
//            {
//                // output: "(name, x, y, w, h)"
//                System.out.println("(" + temp.getName() + ", " +
//                        temp.getX() + ", " + temp.getY() + 
//                        ", " + temp.getWidth() + ", " + 
//                        temp.getHeight() + ")");
//            }
//            list.iteNext();
//        }
//    }
//    
    /**  
     * Searches through the SkipList for Rectangles that intersect
     * each other.
     * 
     * Precondition: The Rectangles in the SkipList have properties 
     * that aren't null.
     * Postcondition: Outputs all the Rectangles in the SkipList 
     * that intersect with each other to the console if they exist
     */
    public void intersections()
    {
        // The pair produced from the iterator
        KVPair<String, Rectangle> current;
        // The rectangle being compared to other rectangles
        Rectangle curRec;
        // The rectangles that aren't the curRec in the SkipList
        Rectangle curTemp;
        
        System.out.println("Intersection pairs: ");
        
        // Compares each rectangle to every other rectangle
        for (int i = 0; i < list.getSize(); i++)
        {
            list.iteToHead();
            list.iteNext();
            
            // Used for setting up the next pair in the SkipList
            for (int j = 0; j < i; j++)
            {
                list.iteNext();
            }
            current = list.currentPair();
            list.iteToHead();
            list.iteNext();
            
            // Compares the current pair to every other pair
            for (int j = 0; j < list.getSize(); j++)
            {
                if (i != j)
                {
                    curRec = current.value();
                    curTemp = list.currentPair().value();

                    // output: "(name, x, y, w, h | name, x, y, w, h)"
                    if (curRec.intersect(curTemp) || 
                            curTemp.intersect(curRec))
                    {
                        System.out.println("(" + curRec.getName() + 
                                ", " + curRec.getX() + ", " + 
                                curRec.getY() + ", " + 
                                curRec.getWidth() + ", " + 
                                curRec.getHeight() + " | " + 
                                curTemp.getName() + ", " + 
                                curTemp.getX() + ", " + 
                                curTemp.getY() + ", " + 
                                curTemp.getWidth() + ", " + 
                                curTemp.getHeight() + ")");
                    }
                }
                list.iteNext();
            }
        }
    }

    /**
     * Gets and prints all the contents of the SkipList
     * 
     * Precondition: the Skiplist is initialized and the Rectangle
     * objects within it have properties that aren't null.
     * Postcondition: Outputs each of the Rectangles in the SkipList 
     * to the console with the format: 
     *      Node has depth #, Value (x, y, w, h)
     * and the size of the SkipList with this format:
     *      SkipList size is: #
     * @throws IOException 
     * @throws ClassNotFoundException 
     *      
     */
    public void dump() throws ClassNotFoundException, IOException
    {
        // Gets the Rectangle at the current iterator
        Rectangle rec;

        list.iteToHead();

        System.out.println("SkipList dump:");

        if (list.getSize() == 0)
        {
            System.out.println("Node has depth 1, Value (null)");
            System.out.println("SkipList size is: 0");
        }
        else
        {
            System.out.println("Node has depth " + 
                    list.currentNodeDepth() + ", Value (null)");
            
            list.iteNext();

            while (list.currentPair() != null)
            {
                rec = (Rectangle) list.currentPair().value();

                // output: "Node has depth #, Value (x, y, w, h)"
                System.out.println("Node has depth " + 
                        list.currentNodeDepth() + ", Value " + "(" + 
                        rec.getName() + ", " + rec.getX() + ", " + 
                        rec.getY() + ", " + rec.getWidth() + ", " + 
                        rec.getHeight() + ")");

                list.iteNext();
            }
            System.out.println("SkipList size is: " + list.getSize());
        }
        m.dump();
    }
    
//    /**
//     * @return the size of the list
//     */
//    public int getSize()
//    {
//        return list.getSize();
//    }
   

}


