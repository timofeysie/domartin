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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.joda.time.DateTime;

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
	    definition.setName("ko-KR", "고양이");
	    definition.setDescription("Reading");
	    definition.setType("http://ko.wiktionary.org/wiki/고양이");
	    vlo.setDefintion(definition);
	    String actual = vlo.toJSON();
	    System.err.println(actual);
	    
	    StringBuffer buffer = new StringBuffer();
		buffer.append("\"object\": {");
        buffer.append("\"id\": \"http://en.wiktionary.org/wiki/cat\",");
        buffer.append("\"objectType\": \"Activity\",");
        buffer.append("\"definition\": {");
        buffer.append("\"name\": {\"ko-KR\": \"고양이\"},");
        buffer.append("\"description\": \"Reading\",");
        buffer.append("\"type\": \"http://ko.wiktionary.org/wiki/고양이\"");
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
}
