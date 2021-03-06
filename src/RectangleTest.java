import org.junit.Test;

import student.TestCase;

/**
 * @author Jacob Teves, Drew Williams
 * @version 5/02/2016
 *
 */
public class RectangleTest extends TestCase {

    /**
     * Test method for 
     * {@link Rectangle#Rectangle(java.lang.String, int, int, int, int)}
     * for testing that the Rectangle properties are initialized 
     * properly and is legal
     */
    @Test
    public void testRectangle() {
        Rectangle dum = new Rectangle("name", 1, 2, 3, 4);
        String expectedName = "name";
        int expectedX = 1;
        int expectedY = 2;
        int expectedWidth = 3;
        int expectedHeight = 4;

        assertEquals(expectedName, dum.getName());
        assertEquals(expectedX, dum.getX());
        assertEquals(expectedY, dum.getY());
        assertEquals(expectedWidth, dum.getWidth());
        assertEquals(expectedHeight, dum.getHeight());
    }

    /**
     * Test method for {@link Rectangle#intersect(Rectangle)}
     * when two Rectangles intersect
     */
    @Test
    public void testIntersect() {
        Rectangle dum = new Rectangle("name", 1, 2, 3, 4);
        Rectangle dum2 = new Rectangle("name", 1, 2, 3, 4);
        
        assertTrue(dum.intersect(dum2));
    }

    /**
     * Test method for {@link Rectangle#intersect(Rectangle)}
     * when two Rectangles touch each other
     */
    @Test
    public void testRectanglesTouch() {
        Rectangle dum = new Rectangle("name", 1, 2, 1, 1);
        Rectangle dum2 = new Rectangle("name", 1, 1, 1, 1);
        
        assertFalse(dum.intersect(dum2));
        assertFalse(dum.sameCords(dum2));
        assertFalse(dum2.sameCords(dum));
    }

    /**
     * Test method for {@link Rectangle#intersect(Rectangle)}
     * when two Rectangles don't intersect
     */
    @Test
    public void testNoIntersectLeft() {
        Rectangle dum = new Rectangle("name", 2, 2, 1, 1);
        Rectangle dum2 = new Rectangle("name", 0, 2, 1, 1);
        
        assertFalse(dum.intersect(dum2));
        assertFalse(dum.sameCords(dum2));
        assertFalse(dum2.sameCords(dum));
    }

    /**
     * Test method for {@link Rectangle#intersect(Rectangle)}
     * when two Rectangles don't intersect
     */
    @Test
    public void testNoIntersectRight() {
        Rectangle dum = new Rectangle("name", 0, 2, 1, 1);
        Rectangle dum2 = new Rectangle("name", 2, 2, 1, 1);
        
        assertFalse(dum.intersect(dum2));
        assertFalse(dum.sameCords(dum2));
        assertFalse(dum2.sameCords(dum));
    }
    
    /**
     * Test method for {@link Rectangle#intersect(Rectangle)}
     * when two Rectangles don't intersect
     */
    @Test
    public void testNoIntersectTop() {
        Rectangle dum = new Rectangle("name", 2, 0, 1, 1);
        Rectangle dum2 = new Rectangle("name", 2, 2, 1, 1);
        
        assertFalse(dum.intersect(dum2));
        assertFalse(dum.sameCords(dum2));
        assertFalse(dum2.sameCords(dum));
    }

    /**
     * Test method for {@link Rectangle#intersect(Rectangle)}
     * when two Rectangles don't intersect
     */
    @Test
    public void testNoIntersectBottom() {
        Rectangle dum = new Rectangle("name", 2, 2, 1, 1);
        Rectangle dum2 = new Rectangle("name", 2, 0, 1, 1);
        
        assertFalse(dum.intersect(dum2));
        assertFalse(dum.sameCords(dum2));
        assertFalse(dum2.sameCords(dum));
    }

    /**
     * Test method for {@link Rectangle#isLegal()} when the Rectangle 
     * is legal
     */
    @Test
    public void testIsLegal() {
        Rectangle dum = new Rectangle("name", 1, 2, 3, 4);
        
        assertTrue(dum.isLegal());
    }
    
    /**
     * Test method for {@link Rectangle#isLegal()} when the Rectangle 
     * is illegal
     */
    @Test
    public void testIllegalName() {
        Rectangle dum = new Rectangle("1", 1, 2, 3, 4);
        
        assertFalse(dum.isLegal());
    }
    
    /**
     * Test method for {@link Rectangle#isLegal()} when the Rectangle 
     * is illegal
     */
    @Test
    public void testIllegalWidth() {
        Rectangle dum = new Rectangle("name", 1, 2, 0, 4);
        
        assertFalse(dum.isLegal());
    }
    
    /**
     * Test method for {@link Rectangle#isLegal()} when the Rectangle 
     * is illegal
     */
    @Test
    public void testIllegalHeight() {
        Rectangle dum = new Rectangle("name", 1, 2, 3, 0);
        
        assertFalse(dum.isLegal());
    }    
    
    /**
     * Test method for {@link Rectangle#isLegal()} when the Rectangle 
     * is illegal
     */
    @Test
    public void testIllegalXPosition() {
        Rectangle dum = new Rectangle("name", 1024, 2, 3, 4);
        
        assertFalse(dum.isLegal());
    }
    
    /**
     * Test method for {@link Rectangle#isLegal()} when the Rectangle 
     * is illegal
     */
    @Test
    public void testIllegalYPosition() {
        Rectangle dum = new Rectangle("name", 1, 1021     , 3, 4);
        
        assertFalse(dum.isLegal());
    }

    /**
     * Test method for {@link Rectangle#toString()}.
     */
    @Test
    public void testToString() {
        Rectangle dum = new Rectangle("name", 1, 2, 3, 4);
        
        assertNotNull(dum.toString());
    }

    /**
     * Test to determine if the rectangles have matching coordinates
     * or names or not.
     */
    public void testEquals() {

        Rectangle dum = new Rectangle("name", 1, 2, 3, 4);
        Rectangle dum2 = new Rectangle("name", 1, 2, 3, 4);
        Rectangle fail3 = new Rectangle("fail", 1, 2, 3, 4);
        Rectangle fail4 = new Rectangle("name", 1, 1, 3, 4);

        assertTrue(dum.sameCords(dum2));
        assertTrue(dum2.sameCords(dum));
        assertTrue(dum.equals(dum2));
        assertFalse(dum.equals(fail3));
        assertFalse(dum.equals(fail4));
    }
}
