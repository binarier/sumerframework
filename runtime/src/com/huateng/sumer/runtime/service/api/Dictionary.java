package com.huateng.sumer.runtime.service.api;

import java.util.Map;

/**
 * �����ֵ�ͨ�ýӿ�
 * @author chenjun.li
 *
 */
public interface Dictionary {
	Map<Object, Object> asMap(Object context);
	void refresh();
}
