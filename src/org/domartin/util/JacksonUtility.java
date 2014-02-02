package org.domartin.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonUtility
{

	public static String getPathToFolder(String ... folders)
	{
		StringBuffer buffer = new StringBuffer();
		for (String n : folders) 
          {
              buffer.append(n+File.separator);
          }
        return new String(buffer);
	}

	/**
	* Read data from a file in the folder name passed in and return a byte array.
	*/
	public static byte[] getJsonData(String file_name, String folder, String root_path)
	{
		//read json file data to String
        File file = new File("/");
        //String to = folder+File.separator;
        byte[] json_data = null;
        try
        {
            json_data = Files.readAllBytes(Paths.get(root_path+folder+file_name));
        } catch (java.io.IOException ioe)
        {
            System.err.println("getJsonData IOException "+root_path+folder+file_name);
            ioe.printStackTrace();
        }
        return json_data;
	}
	
	public static String findNodeValue(byte[] jsonData, String search_node)
	{
        //create ObjectMapper instance
        ObjectMapper object_mapper = new ObjectMapper();
        //read JSON like DOM Parser
        JsonNode root_node = null;
        try
        {
            root_node = object_mapper.readTree(jsonData);
            } catch (java.io.IOException ioe)
        {
            System.err.println("findNodeValue IOException");
        }
        JsonNode id_node = root_node.path("id");
        //System.out.println("id = "+idNode.asInt());
        JsonNode phoneNosNode = root_node.path("phoneNumbers");
        Iterator<JsonNode> elements = phoneNosNode.elements();
        while(elements.hasNext())
        {
            JsonNode node = elements.next();
            String node_name = node.asText();
            System.out.println("node_name "+node_name);;
            if (node_name.equals(search_node))
            	return node.toString();
            //System.out.println("Phone No = "+phone.asLong());
        }
        int found_value = id_node.asInt();
        return null;
    }

}