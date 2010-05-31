package com.huateng.sumer.runtime.service.general;

import java.util.Map;

import com.huateng.sumer.runtime.service.api.Dictionary;

/**
 * �򵥵�ע��ʽ�����ֵ�
 * @author chenjun.li
 *
 */
public class SimpleDictionary implements Dictionary {
	
	private Map<Object, Object> table;

	@Override
	public Map<Object, Object> asMap(Object context) {
		return table;
	}

	@Override
	public void refresh() {
	}

	public Map<Object, Object> getTable() {
		return table;
	}

	public void setTable(Map<Object, Object> table) {
		this.table = table;
	}

}
