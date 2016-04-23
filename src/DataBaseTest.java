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
     */
    public void testDataBase() {
        DataBase db = new DataBase(100);

        assertNotNull(db);
    }

    /**
     * Test method for {@link DataBase#insert} 
     * and {@link DataBase#search}
     */
    public void testInsertSearch() {
        DataBase db = new DataBase(100);

        db.insert("illegal", -1, 1, 0, 0);
		db.insert("legal", 1, 1, 1, 1);
		db.insert("legal2", 1, 1, 1, 2);

		assertNotNull(db);
//        db.search("illegal");
//        
//        db.search("legal2");
//        db.search("legal");
    }    
    
    /**
     * Test method for {@link DataBase#dump}
     * and {@link DataBase#remove(String)}
     * and {@link DataBase#remove(int, int)}
     */
    public void testDumpRemove() {
        DataBase db = new DataBase(100);

        db.insert("illegal", -1, 1, 0, 0);
		db.insert("legal", 1, 1, 1, 1);
		db.insert("legal2", 1, 1, 1, 1);
		db.insert("legal3", 1, 1, 1, 1);

        db.dump();
        assertNotNull(db);

//        db.remove("legal2");
//        db.remove("Null Point");
//
//        db.remove(0, 0);
//        db.remove(1, 1);
    }
}
