package com.huateng.sumer.runtime.service.api;

import java.util.Map;

/**
 * 数据字典通用接口
 * @author chenjun.li
 *
 */
public interface Dictionary {
	Map<Object, Object> asMap(Object context);
	void refresh();
}
