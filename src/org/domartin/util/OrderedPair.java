package org.domartin.util;

import org.apache.log4j.Logger;

/**
*/
public class OrderedPair<K, V> implements Pair<K, V> 
{

    private K key;
    private V value;

    /* Class name*/
    static Logger log = Logger.getLogger(OrderedPair.class);

    public OrderedPair(K key, V value) 
    {
		this.key = key;
		this.value = value;
    }

    public K getKey()	{ return key; }
    public V getValue() { return value; }

    public static void main(String[] args) 
    {
    	OrderedPair <String,String>op = new OrderedPair<String,String>("key1","val1");
    	System.out.println(op.getKey()+" "+op.getValue());
        log.debug("Debug message");
        log.info("Info message");
    }

}