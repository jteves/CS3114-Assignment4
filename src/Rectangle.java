import java.io.Serializable;

/**
 * The Rectangle class will hold values given
 * to it, and have a few getter methods for the fields
 * 
 * @author Drew Williams, Jacob Teves
 * @version 2/06/16
 *
 */
public class Rectangle implements Serializable
{
    // The properties of a rectangle
    private int x;
    private int y;
    private int w;
    private int h;
    private String name;

    /**
     * Constructor for the class. Initializes the 
     * properties of the Rectangle
     * 
     * Precondition: The parameters (Rectangle properties) aren't null
     * Postcondition: A rectangle with name and specified coordinates
     * 
     * @param s         name of the rectangle
     * @param xCord     x coordinate
     * @param yCord     y coordinate
     * @param width     width
     * @param height    height
     */
    public Rectangle(String s, int xCord, int yCord, 
            int width, int height)
    {
        name = s;
        x    = xCord;
        y    = yCord;
        w    = width;
        h    = height;
    }

    /**
     * @return the x coordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return the y coordinate
     */
    public int getY()
    {
        return y;
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return w;
    }

    /**
     * @return the height
     */
    public int getHeight()
    {
        return h;
    }

    /**
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Determines if this Rectangle intersects with the provided
     * Rectangle object.
     * 
     * Precondition: this Rectangle and the provided Rectangle 
     * x, y, w, h properties aren't null and exist 
     * Postcondition: The Rectangles do or don't intersect
     * 
     * @param   rec the rectangle which will be compared
     *          against
     * @return  whether or not the rectangles intersect
     */
    public boolean intersect(Rectangle rec)
    {
        // The edges of this rectangle
        int left        = getX();
        int right       = getX() + getWidth();
        int top         = getY();
        int bottom      = getY() + getHeight();
        
        // The edges of the rectangle being compared to
        int recLeft     = rec.getX();
        int recRight    = rec.getX() + rec.getWidth();
        int recTop      = rec.getY();
        int recBottom   = rec.getY() + rec.getHeight();
 
        // True if the rectangles don't intersect
        boolean notIntersecting = left >= recRight || 
                right <= recLeft || top >= recBottom || 
                bottom <= recTop;
        
        return !notIntersecting;
    }

    /**
     * Determines if the Rectangle has an appropriate
     * name, x and y coordinate, width, and height.
     * 
     * Precondition: The rectangle is initialized with x, y, w, h 
     * properties that aren't null.
     * Postcondition: The rectangle is or isn't legal
     * 
     * @return if the rectangle is a legal rectangle
     */
    public boolean isLegal()
    {
        return (getX() >= 0 && getY() >= 0 &&
                getWidth() > 0 && getHeight() > 0 &&
                getX() + getWidth() <= 1024 && 
                getY() + getHeight() <= 1024) && 
                Character.isLetter(getName().charAt(0));
    }
    
    
    public boolean sameCords(Rectangle rec) {
        return rec.getX() == this.getX() 
                && rec.getY() == this.getX()
                && rec.getHeight() == this.getHeight() 
                && rec.getWidth() == this.getWidth();
    }
    
    public boolean equals(Object o) {
        Rectangle rec = (Rectangle) o;
        return sameCords(rec) && rec.getName().equals(name);
    }
      
//    /**
//     * Provides a human readable version of the coordinates
//     * 
//     * Precondition: the Rectangle has x, y, w, h properties that 
//     * aren't null.
//     * Postcondition: the String will be formatted as "x y w h"
//     * 
//     * @return a human readable version of the coordinates
//     */
//    public String toString()
//    {
//        String s;
//        s = getX() + " " + getY() + " " + getWidth() +
//                " " + getHeight();
//        return s;
//    }

}





