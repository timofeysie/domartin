package org.domartin.json;

import org.apache.log4j.Logger;
import junit.framework.TestCase;

//import com.rusticisoftware.tincan;
import com.rusticisoftware.tincan.*;

public class TinCanJavaTest extends TestCase 
{

    /* Class name*/
    static Logger log = Logger.getLogger(JsonTest.class);

    public void testStatementSend() 
	{
		RemoteLRS lrs = new RemoteLRS();
		try
		{
			lrs.setEndpoint("https://cloud.scorm.com/tc/public/");
		} catch (java.io.MalformedURLException mue)
		{
			log.error("mue");
		}
		lrs.setVersion(TCAPIVersion.V100);
		lrs.setUsername("<Test User>");
		lrs.setPassword("<Test User's Password>");
		Agent agent = new Agent();
		agent.setMbox("mailto:info@tincanapi.com");
		Verb = null;
		try
		{
			Verb verb = new Verb("http://adlnet.gov/expapi/verbs/attempted");
		} catch (java.io.URISyntaxException use)
		{
			log.error("use");
		}
		Activity activity = new Activity("http://rusticisoftware.github.com/TinCanJava");
		Statement st = new Statement();
		st.setActor(agent);
		st.setVerb(verb);
		st.setObject(activity);
		lrs.saveStatement(st);
		assertEquals(true,false);
	}

}