package org.domartin.util;

import junit.framework.TestCase;
import org.apache.log4j.Logger;

public class UtilTest extends TestCase 
{

    /* Class name*/
    static Logger log = Logger.getLogger(UtilTest.class);


	public void testOrderedPair()
    {
        String expected = "value";
        OrderedPair op = new OrderedPair("key","value");
        String[] arguments = new String[] {"unused"};
        op.main(arguments);
        String actual = (String)op.getValue();
        assertEquals(expected,actual);
    }

}