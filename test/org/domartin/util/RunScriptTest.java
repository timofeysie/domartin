package org.domartin.util;

import org.apache.log4j.Logger;
import junit.framework.TestCase;

import java.io.File;
import java.nio.file.Files;

public class RunScriptTest extends TestCase 
{

    /* Class name*/
    static Logger log = Logger.getLogger(RunScriptTest.class);
    private static String PROJECT_DIR = "projects";


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

	public void testXAPIValidator() 
	{
		String method = "testXAPIValidator";
		File file = new File("/");
        String root_path = file.getAbsolutePath();
        String path_to_xapi = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xAPI-Validator-JS", "xapiValidator.js"));
        String path_to_package = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xAPI-Validator-JS", "package.json"));
        String path_to_spec = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xAPI-Validator-JS", "spec", "xapiValidator_spec.js"));
        String path_to_chai = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xAPI-Validator-JS",  "spec", "lib", "chai.js"));
        String path_to_mocha = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xAPI-Validator-JS",  "spec", "lib", "mocha.js"));
        String path_to_underscore = readFile(JacksonUtility.getPathToFolder(root_path, PROJECT_DIR, 
        	"domartin", "files", "xAPI-Validator-JS",  "spec", "lib", "underscore.js"));
		String [] args = {path_to_xapi, path_to_spec, path_to_chai, path_to_mocha, 
			path_to_underscore, path_to_package};
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