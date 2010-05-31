package com.huateng.sumer.runtime.web.meta;

import java.util.List;
import java.util.Map;


/**
 * @author chenjun.li
 */
public interface OptionsService<T extends Comparable<T>> {
    List<OptionDefinition<T>> getAsList(Object context);
    Map<Object, OptionDefinition<T>> getAsMap(Object context);
} 
