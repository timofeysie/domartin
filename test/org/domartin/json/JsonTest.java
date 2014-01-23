package com.curchod.json;

import java.net.URI;
import java.net.URISyntaxException;

import junit.framework.TestCase;

import com.curchod.json.*;

public class JsonTest extends TestCase 
{

	public void testVocabularyLearningObject() 
	{
		VocabularyLearningObject vlo = new VocabularyLearningObject();
		URI id = null;
		try {
			id = new URI("http://en.wiktionary.org/wiki/cat");
		} catch (URISyntaxException e) {
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
        String expected = buffer.toString();
        System.err.println(actual);
        
        assertEquals(expected,actual);
	}
	
}
