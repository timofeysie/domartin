package org.domartin.util;

import org.apache.log4j.Logger;
import junit.framework.TestCase;

public class RunScriptTest extends TestCase 
{

    /* Class name*/
    static Logger log = Logger.getLogger(RunScriptTest.class);

    public void testRunSimpleScript() 
	{
		String method = "testStatementSend";
		String java_script = "var firstname;firstname=\"Test\";";
		String [] args = {java_script};
		String actual = RunScript.execute(args);
		log.info(method+" result "+actual);
		String expected = "Test";
		assertEquals(expected,actual);
	}

}