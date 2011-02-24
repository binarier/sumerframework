package com.huateng.frame.gwt.shared;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class DomainSupport<T>{
	
	private LinkedHashMap<T, String> map = new LinkedHashMap<T, String>();
	
	protected abstract void fill(Map<T, String> map);
	
	public DomainSupport()
	{
		fill(map);
	}
	
	public LinkedHashMap<T, String> asLinkedHashMap()
	{
		return map;
	}
	
	public Map<T, String> asMap()
	{
		return map;
	}
	
	public Map<T, String> asSortedMap()
	{
		return new TreeMap<T, String>(map);
	}
}
