package org.domartin.json;

import org.apache.log4j.Logger;
import junit.framework.TestCase;

//import com.rusticisoftware.tincan;
import org.joda.time.DateTime;
import com.rusticisoftware.tincan.v10x.StatementsQuery;
import com.rusticisoftware.tincan.*;
import java.util.UUID;

// for the vocabulary object test
import java.net.URI;
import com.rusticisoftware.tincan.Context;
import com.rusticisoftware.tincan.Activity;
import com.rusticisoftware.tincan.Extensions;
import com.rusticisoftware.tincan.LanguageMap;
import com.rusticisoftware.tincan.ActivityDefinition;
import com.fasterxml.jackson.databind.node.ObjectNode;
// import com.rusticisoftware.tincan.json.StringOfJSON;

public class TinCanJavaTest extends TestCase 
{

    /* Class name*/
    static Logger log = Logger.getLogger(JsonTest.class);

    public void testStatementSend() 
	{
		String method = "testStatementSend";
		String endpoint1 = "https://cloud.scorm.com/tc/public/";
		String endpoint2 = "https://cloud.scorm.com/ScormEngineInterface/TCAPI/public/";
		RemoteLRS lrs = new RemoteLRS();
		try
		{
			lrs.setEndpoint(endpoint2);
		} catch (java.net.MalformedURLException mue)
		{
			log.error(method+" MalformedURLException");
		}
		lrs.setVersion(TCAPIVersion.V100);
		lrs.setUsername("Test");
		lrs.setPassword("https://cloud.scorm.com/tc/public/");
		Agent agent = new Agent();
		agent.setMbox("mailto:info@tincanapi.com");
		Verb verb = null;
		Activity activity = null;
		try
		{
			verb = new Verb("http://adlnet.gov/expapi/verbs/attempted");
			activity = new Activity("http://rusticisoftware.github.com/TinCanJava");
		} catch (java.net.URISyntaxException use)
		{
			log.error(method+" URISyntaxException");
		}
		Statement st = new Statement();
		st.setActor(agent);
		st.setVerb(verb);
		st.setObject(activity);
		UUID uuid = null;
		try
		{
			uuid = lrs.saveStatement(st);
			log.info("uuid "+uuid.toString());
		} catch (java.lang.Exception e)
		{
			log.error(method+" Exception");
		}
		boolean actual = uuid.toString().length()>0 ? true : false;
		boolean expected = true;
		assertEquals(expected,actual);
	}

	public void testGetLastQuery() 
	{
		String method = "testGetLastQuery";
		// given a configured query statement like testStatementSend:
		String endpoint1 = "https://cloud.scorm.com/tc/public/";
		String endpoint2 = "https://cloud.scorm.com/ScormEngineInterface/TCAPI/public/";
		RemoteLRS lrs = new RemoteLRS();
		try
		{
			lrs.setEndpoint(endpoint2);
		} catch (java.net.MalformedURLException mue)
		{
			log.error(method+" MalformedURLException");
		}
		lrs.setVersion(TCAPIVersion.V100);
		lrs.setUsername("Test");
		lrs.setPassword("https://cloud.scorm.com/tc/public/");
		Agent agent = new Agent();
		agent.setMbox("mailto:info@tincanapi.com");
		Verb verb = null;
		Activity activity = null;
		try
		{
			verb = new Verb("http://adlnet.gov/expapi/verbs/attempted");
			activity = new Activity("http://rusticisoftware.github.com/TinCanJava");
		} catch (java.net.URISyntaxException use)
		{
			log.error(method+" URISyntaxException");
		}
		Statement st = new Statement();
		st.setActor(agent);
		st.setVerb(verb);
		st.setObject(activity);
		try
		{
			UUID uuid = lrs.saveStatement(st);
			log.info("uuid "+uuid.toString());
		} catch (java.lang.Exception e)
		{
			log.error(method+" Exception from saveStatement(st)");
		}

		// we should get the last record here.
		StatementsQuery query = new StatementsQuery();
		// sample format: "2013-09-30T13:15:00.000Z"
		query.setSince(getPrevoisTime());
		//StatementsResult result = obj.queryStatements(query); // obj is the RemoteLRS object.
		StatementsResult result = null;
		try
		{
			result = lrs.queryStatements(query);
		} catch (java.lang.Exception e)
		{
			log.error(method+" Exception from queryStatements(query)");
		}
		log.info("Statement Result "+result.toString());
		boolean actual = result.toString().length()>0 ? true : false;
		boolean expected = true;
		assertEquals(expected,actual);
	}

	/**
	*{
	*    "actor": 
	*    {
	*            "objectType": "Agent", 
	*            "mbox":"mailto:timofeyc@example.com" 
	*        },
	*        "verb" : 
	*    { 
	*            "id":"http://adlnet.gov/expapi/verbs/answered", 
	*            "display":{"en-US":"answered",
	*			 "result.success = true"}
	*        },
	*    "object": 
	*    {
	*            "id": "http://en.wiktionary.org/wiki/cat",
	*            "definition": 
	*        {
	*                "description": {"en-US": "cat" },
	*                "object": 
	*            {
	*                    "objectType": "SubStatement",
	*                    "object": 
	*                {
	*                            "id":"http://ko.wiktionary.org/wiki/goyanig",
	*                            "definition": 
	*                            {
	*                                "name" : {"ko-KR": "goyanig"}
	*                    }
	*                    }
	*                }
	*            }        
	*    },
	*    "context": 
	*    {
	*        "language" : "ko-KR",
	*        "platform" : "android",
	*        "extensions": 
	*        {
	*                "http://www.curchod.com/testing_type": {"type": "READING"}
	*                }
	*    }
	*}
*/
	public void testCreateVocabularyStatement() 
	{
		String method = "testCreateVocabularyStatement";
		String endpoint1 = "https://cloud.scorm.com/tc/public/";
		String endpoint2 = "https://cloud.scorm.com/ScormEngineInterface/TCAPI/public/";
		RemoteLRS lrs = new RemoteLRS();
		try
		{
			lrs.setEndpoint(endpoint2);
		} catch (java.net.MalformedURLException mue)
		{
			log.error(method+": MalformedURLException");
		}
		lrs.setVersion(TCAPIVersion.V100);
		lrs.setUsername("Test");
		lrs.setPassword("https://cloud.scorm.com/tc/public/");
		Agent agent = new Agent();
		agent.setMbox("mailto:info@tincanapi.com");
		Verb verb = null;
		Activity activity = null;
		ActivityDefinition activity_definition = null;
		Context context = null;
		Extensions extension = null;
		try
		{
			verb = new Verb("http://adlnet.gov/expapi/verbs/answered");
			activity = new Activity("http://rusticisoftware.github.com/TinCanJava");
			activity.setId("http://en.wiktionary.org/wiki/cat");
			LanguageMap description = new LanguageMap();
			description.put("en-US", "cat" );
			activity_definition = new ActivityDefinition();
			activity_definition.setDescription(description);
			activity.setDefinition(activity_definition);
			context = new Context();
			context.setLanguage("ko-KR");
			context.setPlatform("android");
			extension = new Extensions();
			// the following causes this exception:
			// No serializer found for class com.rusticisoftware.tincan.json.StringOfJSON and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationConfig.SerializationFeature.FAIL_ON_EMPTY_BEANS) )
			//StringOfJSON ext_string = new StringOfJSON("{\"type\": \"READING\"}"); 
			String ext_string = "{type:READING}"; 
			extension.put(new URI("http://www.curchod.com/testing_type"), ext_string);
			activity_definition.setExtensions(extension);
		} catch (java.net.URISyntaxException use)
		{
			log.error(method+": URISyntaxException");
		}
		Statement st = new Statement();
		st.setActor(agent);
		st.setVerb(verb);
		st.setObject(activity);
		ObjectNode object = st.toJSONNode(TCAPIVersion.V100);
		log.info(method+" statement: "+object.toString());
		assertEquals(true,false);
	}

	private DateTime getPrevoisTime()
	{
		DateTime date = new DateTime();
		int year = date.getYear();
		int monthOfYear = date.getMonthOfYear();
		int dayOfMonth = date.getDayOfMonth();
		int hourOfDay = date.hourOfDay().get();
		int minuteOfHour = date.minuteOfHour().get() - 1;
		int secondOfMinute = date.secondOfMinute().get();
		DateTime new_date = new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute); 
		return new_date;
	}

}