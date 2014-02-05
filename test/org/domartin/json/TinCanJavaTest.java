package org.domartin.json;

import org.apache.log4j.Logger;
import junit.framework.TestCase;

//import com.rusticisoftware.tincan;
import com.rusticisoftware.tincan.*;
import java.util.UUID;

public class TinCanJavaTest extends TestCase 
{

    /* Class name*/
    static Logger log = Logger.getLogger(JsonTest.class);

    public void testStatementSend() 
	{
		String endpoint1 = "https://cloud.scorm.com/tc/public/";
		String endpoint2 = "https://cloud.scorm.com/ScormEngineInterface/TCAPI/public/";
		RemoteLRS lrs = new RemoteLRS();
		try
		{
			lrs.setEndpoint(endpoint2);
		} catch (java.net.MalformedURLException mue)
		{
			log.error("mue");
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
			log.error("use");
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
			log.error("e");
		}
		assertEquals(true,false);
	}

	public void testGetLastQuery() 
	{
		StatementsQuery query = new StatementsQuery();
		query.setSince(new DateTime("2013-09-30T13:15:00.000Z"));
		StatementsResult result = obj.queryStatements(query);
		log.info("Statement Result "+result.toString());
		assertEquals(true,false);
	}

}