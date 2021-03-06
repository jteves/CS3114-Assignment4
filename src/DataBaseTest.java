import java.io.IOException;

import student.TestCase;

/**
 * The test case for the DataBase class
 * @author Jacob Teves, Drew Williams
 * @version 3/14/2016
 */
public class DataBaseTest extends TestCase {

    /**
     * Test method for {@link DataBase#DataBase()}.
     * to make sure its data structures are not null
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testDataBase() throws ClassNotFoundException, IOException {
        DataBase db = new DataBase(100, 5, "filename.txt");

        assertNotNull(db);
    }

    /**
     * Test method for {@link DataBase#insert} 
     * and {@link DataBase#search}
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testInsertSearch() throws ClassNotFoundException, IOException {
        DataBase db = new DataBase(100, 5, "filename.txt");

        db.insert("illegal", -1, 1, 0, 0);
        db.insert("legal", 1, 1, 1, 1);
        db.insert("legal2", 1, 1, 1, 2);
        db.insert("legal3", 1, 1, 100, 2);
        db.insert("legal4", 1, 1, 101, 2);
        assertEquals(db.getSize(), 4);
        db.search("illegal");
        
        db.search("legal2");
        db.search("legal");
        db.search("illegal");
        db.search("legal2");
        db.search("legal4");
        db.search("legal3");
        db.regionSearch(0, 0, 101, 2);
        db.regionSearch(0, 1, 1, 2);
        db.regionSearch(1, 0, 100, 2);

        assertNotNull(db);
    }    
    
    /**
     * Test method for {@link DataBase#dump}
     * and {@link DataBase#remove(String)}
     * and {@link DataBase#remove(int, int)}
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public void testDumpRemove() throws ClassNotFoundException, IOException {
        DataBase db = new DataBase(100, 5, "filename.txt");

        db.insert("illegal", -1, 1, 0, 0);
        db.insert("legal", 1, 1, 1, 1);
        db.insert("legal2", 1, 1, 1, 1);
        db.insert("legal3", 1, 1, 1, 1);

        db.dump();
        assertNotNull(db);

        db.remove("legal2");
        db.remove("Null Point");

        db.remove(0, 0, 0, 0);
        db.remove(1, 1, 1, 1);
        assertEquals(1, db.getSize());
    }
}
