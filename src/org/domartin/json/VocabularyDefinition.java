package org.domartin.json;

import org.domartin.util.OrderedPair;
/**
 * Create a definition JSON node based on the following structure:
 * "definition":
*            "name": "ko-KR": "°íŸçÀÌ"
*            "description":  Reading ,Writing, Speaking or Listening
 *           "type": "http://ko.wiktionary.org/wiki/°íŸçÀÌ"           
 * @author user
 *
 */
public class VocabularyDefinition 
{
	
	private OrderedPair name;
	private String description;
	private String type;
	 
	public void setName(String locale, String word)
	{
		this.name = new OrderedPair(locale, word);
	}
	
	public OrderedPair getName()
	{
		return this.name;
	}
	
	public Object getNameLocale()
	{
		return this.name.getKey();
	}
	
	public Object getNameValue()
	{
		return this.name.getValue();
	}

	public void setDescription(String _description)
	{
		this.description = _description;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setType(String _type)
	{
		this.type = _type;
	}
	
	public String getType()
	{
		return this.type;
	}

}
