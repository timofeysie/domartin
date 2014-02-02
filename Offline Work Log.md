Work Log

2013-01-31 --- Day of the Horse ---

Getting rid of the casts in the iteration code is proving a bit of a problem.

        java.util.Iterator <HashMap> it = map1.entrySet().iterator();
incompatible types.
required: Iterator <HashMap>
found:	  Iterator<Entry<String,String>>

It seems like those two should be reversed.

Iterator <HashMap> it = map1.entrySet().iterator()<HashMap>;

THis is not valid.

One problem is that we don;t understand the EntrySet class, as it doesnt seem to be an actual class:

public Set<Map.Entry<K,V>> entrySet() Returns a Set view of the mappings contained in this map.

THere is no actual entry class, but the API says this: Nested classes/interfaces inherited from class java.util.AbstractMap
AbstractMap.SimpleEntry<K,V>, AbstractMap.SimpleImmutableEntry<K,V>
Interface Iterator<E>

We have to resist the temptation to put a @SupressWarning annotation on the code and understand exactly what is going on.  This will be a good lesson for us in generics, especially since we are planning on taking the Java certification test.  It is a shame that now generics are an integrated part of Java, and now understanding the API means understanding the generics used in it.  It would be nice to just be using Groovy now, and maybe Groovy will turn out to be the saviour of Java.  How could a newbie use a text editor and the API docs as we did ten years ago to get up and running?  The generics section of the Java trail is extensive and hard to read, yet their understanding of it is required to use so much of the API docs.
ok.  Enough of the philosophizing.  That is just an avoidence tactic.  Understant the situation where you want to use generics.

IF we use this:
Iterator <HashMap<String,String>> it = map1.entrySet().iterator();
The compiler says:
required: Iteractor<HashMap<String,String>> 
found Iterator<Entry<String,String>>
and points to the end of the line.  THis means that indirectly we should change the return type, since we can put generics in the 'power user' format used by calling the methods on the map like that.  Maybe we should split up the calls to understand this better.  The API says:

public Set<Map.Entry<K,V>> entrySet()

What is the Map.Entry format?

Iterator<E>	iterator()

That is what is being returned, so we definately should not have multiple generics on the left side, right?  No, not right, as if we try this:

Iterator <String> it = map1.entrySet().iterator();

The compile says again: incompatible types.
required: Iterator<String>
found:	  Iterator<Entry<String,String>>
The fact that the map can contain either an element or another map makes this complicated.
It's a Map<String,Map> contruct.  Or should it be bounded?
Map<String,? extends Map>  or Map<String,? super String>.  Both of these seem like they would not cover a situation where the values could be a string or another map.
We should wait until we are online again instead of running in circles like this.  We need to look at what other people do in this situation.  
SO it's back to casting:

            Map.Entry pairs = (Map.Entry)it.next();
            String key = (String)pairs.getKey();
            HashMap value_hash = (HashMap)pairs.getValue();

Interface Map.Entry<K,V>    
A Map.Entry is a key and its value combined into one class and allows you to iterate over Map.entrySet instead of having to iterate over Map.keySet(), Then getting the value for each key.
THis is what Paul (reputation 6,000+) said on StackOverflow.  How does a reputation get so high?  Does Paul spend his days trawling for questions on the site?  His answer was voted up to 10.

Anyhow, it's time to move on.  We need to make tests for the editing or a json object, and also for the creation of a json node programatically to finish of our Jackson tutorial.
We also need to extract some usable methods out of this 'test' class so they can be used in Wherewithal and Indoct and the future Indoct which will be a Grails webapp.
So as a first step we made the JacksonUtility class.  Since we're not sure how far we will rely on Jackson, we shouldn't spend to much time making interfaces and classes to hide the use of files from the caller.  At this point, we are working with files, but we will add methods for working with objects or whatever form we get the json in.
We are going to have to think about logging however.
But since we are offline we can't download something like commons logging.
Anyhow, we would have this type of code to use the utility to find one node value:

        String path_to_file = JacksonUtility.getPathToFolder("projects", "domartin", "files");
        json_data = JacksonUtility.getJsonData("updated_emp.txt", path_to_file);
        String expected = JacksonUtility.findNodeValue(json_data, "id");

Looks terrible really.  And when are we going to be looking for one value?  Json will just be a transport mechinism, like xml, and the real work will be in the db.  We should think about a real world example of how we will use json before we put work like this into creating utility methods.
Since we are using xml to send words and tests to the Android client.




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
