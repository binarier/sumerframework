package com.huateng.sumer.runtime.service.general;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.FastTreeMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.huateng.sumer.runtime.web.meta.OptionDefinition;
import com.huateng.sumer.runtime.web.meta.OptionsService;

/**
 * @author chenjun.li
 *
 * @param <T> 选项对应取值的类型
 */
public class SimpleOptionsService<T extends Comparable<T>> implements
		OptionsService<T>, InitializingBean {

	private List<OptionDefinition<T>> options;
	private FastTreeMap map = new FastTreeMap();

	@Override
	public List<OptionDefinition<T>> getAsList(Object context) {
		return options;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, OptionDefinition<T>> getAsMap(Object context) {
		return (Map<Object, OptionDefinition<T>>) map;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		Assert.notNull(options, "必需设置options属性");

		for (OptionDefinition<T> od : options) {
			map.put(od.getValue(), od.getLabel());
		}

		map.setFast(true);
	}

	@Required
	public void setOptions(List<OptionDefinition<T>> options) {
		this.options = options;
	}
}
