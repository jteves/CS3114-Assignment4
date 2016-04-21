import java.io.IOException;

import student.TestCase;

/**
 *
 */

/**
 * @author CS3114 Staff
 * @version April 13, 2016
 */
public class SerializerTest extends TestCase {

    /**
     * This method sets up for the
     * tests that follow.
     */
    public void setUp() {
        // no op
    }

    /**
     * This method gets you credit for testing the Serializer initialization.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void testSerialize()
        throws IOException, ClassNotFoundException {

        Serializer serializer = new Serializer();
        assertNotNull(serializer);

        Integer pre = new Integer(10);
        byte[] serialized = Serializer.serialize(pre);
        Integer post = (Integer) Serializer.deserialize(serialized);

        assertEquals(pre.intValue(), post.intValue());
    }
}
