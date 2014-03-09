package org.domartin.util;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

import java.io.File;
import java.nio.file.Files;

public class RunScriptTest extends TestCase 
{

    /* Class name*/
    static Logger log = Logger.getLogger(RunScriptTest.class);
    //private static String PROJECT_DIR = "projects";
    private static String PROJECT_DIR = "java";


    public void testRunSimpleScript() 
	{
		String method = "testRunSimpleScript";
		String java_script = "var firstname;firstname=\"Test\";";
		String [] args = {java_script};
		String actual = RunScript.execute(args);
		log.info(method+" result "+actual);
		String expected = "Test";
		assertEquals(expected,actual);
	}

    /**
    * This test tries to load all the JavaScript .js files from the
    * xAPI-Validate-JS project by Zack Pierce on GitHub.
    * Currently it gives an "missing } after property list (<cmd>#13131)"
    * or some such number.  The <cmd> is the base tag in the RunScript class.
    *
    */
	public void testXAPIValidator() 
	{
		String method = "testXAPIValidator";
		File file = new File("/");
        String root_path = file.getAbsolutePath();
        String path_to_xapi = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xapi", "xapiValidator.js"));
        String path_to_spec = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xapi", "spec", "xapiValidator_spec.js"));
        String path_to_chai = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xapi",  "spec", "lib", "chai.js"));
        String path_to_mocha = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xapi",  "spec", "lib", "mocha.js"));
        String path_to_underscore = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xapi",  "spec", "lib", "underscore.js"));
        String test_statement = "{\"actor\":{\"objectType\":\"Agent\",\"mbox\":\"mailto:info@tincanapi.com\"},\"verb\":{\"id\":\"http://adlnet.gov/expapi/verbs/answered\",\"object\":{\"objectType\":\"Activity\",\"id\":\"http://en.wiktionary.org/wiki/cat\",\"definition\":{\"description\":{\"en-US\":\"cat\"},\"extensions\":{\"http://www.curchod.com/testing_type\":\"{type:READING}\"}}}}";
        String xapi = "{var report = xapiValidator.validateStatement("+test_statement+");}";
        log.info(method+" test statement: "+test_statement);
        String [] args1 = {path_to_xapi, path_to_spec, path_to_chai, path_to_mocha, 
            path_to_underscore, xapi};
        String [] args = {path_to_xapi, xapi};    
		String actual = RunScript.execute(args);
		log.info(method+" result "+actual);
		String expected = "Test";
		assertEquals(expected,actual);
	}

	private String readFile(String filename) 
	{
        File f = new File(filename);
        try 
        {
            byte[] bytes = Files.readAllBytes(f.toPath());
            log.info("readFile: "+bytes.length+" for "+filename);
            return new String(bytes,"UTF-8");
        } catch (java.io.FileNotFoundException e) 
        {
            e.printStackTrace();
        } catch (java.io.IOException e) 
        {
            e.printStackTrace();
        }
        return "";
    }

}