package org.domartin.json;

import org.apache.log4j.Logger;
import junit.framework.TestCase;

//import com.rusticisoftware.tincan;
import org.joda.time.DateTime;
import com.rusticisoftware.tincan.v10x.StatementsQuery;
import com.rusticisoftware.tincan.*;
import java.util.UUID;


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
		try
		{
			verb = new Verb("http://adlnet.gov/expapi/verbs/attempted");
			activity = new Activity("http://rusticisoftware.github.com/TinCanJava");
		} catch (java.net.URISyntaxException use)
		{
			log.error(method+": URISyntaxException");
		}
		Statement st = new Statement();
		st.setActor(agent);
		st.setVerb(verb);
		st.setObject(activity);
		
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