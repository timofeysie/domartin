Work Log

2013-01-31 --- Day of the Horse ---

Getting rid of the casts in the iteration code is proving a bit of a problem.
That Object key doesnt sound like it could be a map.  This is what we are trying to get.  Oh, we can't paste it here from the hung google docs.


2014-01-30 --- Lunar New Year ---

Working on testJsonMap() in JsonTest of the Acknowledge project.  The test is giving an error:

java.util.LinkedHashMap cannot be cast to java.lang.String
java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to java.lang.String
at org.domartin.json.JsonTest.testJsonMap(Unknown Source)
But we are not using a LinkedMap, so we tried theses two:

        java.util.HashMap context_map = map1.get("context");
        //String context_map = (String)map1.get("context");
THen we realized it was talking about the argument.  THe "context" should be a hashmap?

The API says: V	get(Object key) - Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.

That Object key doesnt sound like it could be a map.  This is what we are trying to get.  Oh, we can't paste it here from the hung google docs.

Map.Entry pairs = (Map.Entry)it.next();

This is how it's gotten in the iteration.  So how do we use that?  Even if we remove all the error codes, the test still fails like this:

testJsonMap	Error	N/A

java.lang.NullPointerException
at com.fasterxml.jackson.core.JsonFactory.createJsonParser(JsonFactory.java:879)
at com.fasterxml.jackson.databi
nd.ObjectMapper.readValue(ObjectMapper.java:2012)
at org.domartin.json.JsonTest.testJsonMap(Unknown Source)

We finally got the test to pass with this code:

        String expected = "android";
        String actual = "";  
        java.util.Iterator it = map1.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry pairs = (Map.Entry)it.next();
            String key = (String)pairs.getKey();
            HashMap value_hash = (HashMap)pairs.getValue();
            if (key.equals("context"))
            {
                System.out.println(key+" = "+value_hash);
                actual = (String)value_hash.get("platform");
                break;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        assertEquals(expected, actual);

So each sub-object is a Map.  We should really try and practice our generics and not cast like we are used to.  But first, we would like to get the values we want without iteratng thru the entire map.  But this fails:

        HashMap values = (HashMap)map1.get("actor");
        String mbox = (String)values.get("mbox");
        System.out.println("mbox = "+mbox);

error: inconvertible types
required: HashMap
found: String        
And the ^ is pointing to the string arg in the get call.  But the API says object would be OK.

Now here is another should be string:

expected:<{ 
"id"[: 123, "name": "Pankaj", "permanent": true, "address": { "street": "Albany Dr", "city": "San Jose", "zipcode": 95129 }, "phoneNumbers": [ 123456, 987654 ], "role": "Manager", "cities": [ "Los Angeles", "New York" ], "properties": { "age": "29 years", "salary"]: "1000 USD" }}> 
but was:<{ 
"id"[ : 123, "name" : "Pankaj", "permanent" : true, "address" : { "street" : "Albany Dr", "city" : "San Jose", "zipcode" : 95129 }, "phoneNumbers" : [ 123456, 987654 ], "role" : "Manager", "cities" : [ "Los Angeles", "New York" ], "properties" : { "age" : "29 years", "salary" ]: "1000 USD" }}>
