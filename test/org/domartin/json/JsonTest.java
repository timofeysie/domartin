package org.domartin.json;

import java.net.URI;
import java.net.URISyntaxException;
import junit.framework.TestCase;
import org.domartin.json.*;

//import com.rusticisoftware.tincan.TestUtils.assertSerializeDeserialize;

//import com.rusticisoftware.tincan.TestUtils.getAgent;
import com.rusticisoftware.tincan.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rusticisoftware.tincan.State;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.joda.time.DateTime;

// tutorial impots
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.domartin.json.Address;
import org.domartin.json.Employee;

public class JsonTest extends TestCase 
{

	public void testVocabularyLearningObject() 
	{
		VocabularyLearningObject vlo = new VocabularyLearningObject();
		URI id = null;
		try 
		{
			id = new URI("http://en.wiktionary.org/wiki/cat");
		} catch (URISyntaxException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    vlo.setId(id);
	    vlo.setObjectType("Activity");
	    VocabularyDefinition definition = new VocabularyDefinition();
	    definition.setName("ko-KR", "째챠타챌�횑");
	    definition.setDescription("Reading");
	    definition.setType("http://ko.wiktionary.org/wiki/째챠타챌�횑");
	    vlo.setDefintion(definition);
	    String actual = vlo.toJSON();
	    System.err.println(actual);
	    
	    StringBuffer buffer = new StringBuffer();
		buffer.append("\"object\": {");
        buffer.append("\"id\": \"http://en.wiktionary.org/wiki/cat\",");
        buffer.append("\"objectType\": \"Activity\",");
        buffer.append("\"definition\": {");
        buffer.append("\"name\": {\"ko-KR\": \"째챠타챌�횑\"},");
        buffer.append("\"description\": \"Reading\",");
        buffer.append("\"type\": \"http://ko.wiktionary.org/wiki/째챠타챌�횑\"");
        buffer.append("}}");
        String expected = new String(buffer);
        //System.err.println(actual);
        assertEquals(expected,actual);
	}

    public void testTinCanJava() throws Exception 
    {    
        Agent agent = new Agent();
        agent.setName("TestName");
        agent.setMbox("timofeyc@test.com");

        List<StatementTarget> statementTargets = new ArrayList<StatementTarget>();
        statementTargets.add(new Activity("http://example.com/activity"));
        statementTargets.add(agent);
        statementTargets.add(new StatementRef(UUID.randomUUID()));
        
        SubStatement sub = new SubStatement();
        sub.setActor(agent);
        sub.setVerb(new Verb("http://example.com/verb"));
        sub.setObject(new Activity("http://example.com/sub-activity"));
        statementTargets.add(sub);
        
        
        Statement st = new Statement();
        st.setActor(agent);

        st.setAttachments(new ArrayList<Attachment>());
        Attachment att = new Attachment();
        att.setSha2("abc");
        st.getAttachments().add(att);   
        st.setAuthority(agent);
        st.setContext(new Context());
        st.getContext().setLanguage("en-US");
        // universally unique identifier
        UUID uuido = UUID.randomUUID();
        String uuid = uuido.toString();
        st.setId(uuido);
        st.setResult(new Result());
        st.getResult().setCompletion(true);
        st.setStored(new DateTime());
        st.setTimestamp(new DateTime());
        st.setVerb(new Verb("http://example.com/verb"));
        /*
        for (StatementTarget target : statementTargets) 
        {
            st.setObject(target);
            assertSerializeDeserialize(st);
        */
        //TCAPIVersion [] version = new TCAPIVersion(); 
        // enum types may not be instatiated.
        // com.fasterxml.jackson.databind.node.
        // com.fasterxml.jackson.databind.
        //ObjectNode object_node = st.toObjectNode();
		//uuid = uuid.substring(3,uuid.length());
		//uuid = uuid.substring(0,uuid.length()); 
        String expected = "Statement(id="+uuid
        	+", authority=Agent(objectType=Agent, name=TestName, mbox=timofeyc@test.com, mboxSHA1Sum=null, openID=null, account=null), version=null, voided=null)";
		String actual = st.toString();
        System.err.println("---");
        System.err.println(st.toString());
        System.err.println("---");
        System.err.println(uuid);      
        assertEquals(expected, actual);
    }
    
    public void testStatement() throws Exception 
    {    
    	Agent target_agent = new Agent();
    	target_agent.setName("TargetName");
    	target_agent.setMbox("target@test.com");
        
        List<StatementTarget> statementTargets = new ArrayList<StatementTarget>();
        statementTargets.add(new Activity("http://example.com/activity"));
        //statementTargets.add(State.getAgent("Target", "mbox", "mailto:target@example.com"));
        statementTargets.add(target_agent);
        statementTargets.add(new StatementRef(UUID.randomUUID()));
        
        Agent sub_agent = new Agent();
    	sub_agent.setName("SubName");
    	sub_agent.setMbox("sub@test.com");
    	
        SubStatement sub = new SubStatement();
        //sub.setActor(State.getAgent("Sub", "mbox", "mailto:sub@example.com"));
        sub.setActor(sub_agent);
        sub.setVerb(new Verb("http://example.com/verb"));
        sub.setObject(new Activity("http://example.com/sub-activity"));
        statementTargets.add(sub);
        
        Agent joe_agent = new Agent();
    	joe_agent.setName("JoeName");
    	joe_agent.setMbox("joe@test.com");
        Statement st = new Statement();
        //st.setActor(State.getAgent("Joe", "mbox", "mailto:joe@example.com"));
        st.setActor(joe_agent);

        st.setAttachments(new ArrayList<Attachment>());
        Attachment att = new Attachment();
        att.setSha2("abc");
        st.getAttachments().add(att);
        
        Agent auth_agent = new Agent();
        auth_agent.setName("AuthName");
        auth_agent.setMbox("auth@test.com");
        st.setAuthority(auth_agent);
        
        st.setContext(new Context());
        st.getContext().setLanguage("en-US");
        
        st.setId(UUID.randomUUID());
        
        st.setResult(new Result());
        st.getResult().setCompletion(true);
        
        st.setStored(new DateTime());
        st.setTimestamp(new DateTime());
        st.setVerb(new Verb("http://example.com/verb"));
        
        System.err.println("--- statementTest() ---");
        //TCAPIVersion version = new TCAPIVersion();
        //com.fasterxml.jackson.databind.node.ObjectNode node = st.toJSONNode(version); 
        System.err.println(st.toString());
        System.err.println("-----------------------");
        
        assertEquals(true, false);
    }

    public void testEmployee() throws Exception
    {
        //read json file data to String
        File file = new File("/");
        String path = file.getAbsolutePath();
        System.out.println("path "+path);
        byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        //convert json string to object
        Employee emp = objectMapper.readValue(jsonData, Employee.class);
        System.out.println("Employee Object\n"+emp);
        //convert Object to json string
        Employee emp1 = createEmployee();
        //configure Object mapper for pretty print
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //writing to console, can write to any output stream such as file
        StringWriter stringEmp = new StringWriter();
        objectMapper.writeValue(stringEmp, emp1);
        System.out.println("Employee JSON is\n"+stringEmp);
        assertEquals(true, false);
    }
 

    public static Employee createEmployee() 
    {
 
        Employee emp = new Employee();
        emp.setId(100);
        emp.setName("David");
        emp.setPermanent(false);
        emp.setPhoneNumbers(new long[] { 123456, 987654 });
        emp.setRole("Manager");
 
        Address add = new Address();
        add.setCity("Bangalore");
        add.setStreet("BTM 1st Stage");
        add.setZipcode(560100);
        emp.setAddress(add);
 
        List<String> cities = new ArrayList<String>();
        cities.add("Los Angeles");
        cities.add("New York");
        emp.setCities(cities);
 
        Map<String, String> props = new HashMap<String, String>();
        props.put("salary", "1000 Rs");
        props.put("age", "28 years");
        emp.setProperties(props);
 
        return emp;
    }   
}