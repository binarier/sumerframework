package com.huateng.sumer.runtime.web.meta;

/**
 * <p>列表选项定义</p>
 * <ul>
 * <li><b>label</b> 显示标签</li>
 * <li><b>value</b> 选项取值</li>
 * <li><b>tag</b> 自定义字段</li>
 *
 * @author chenjun.li
 * @version $Id$
 */
public class OptionDefinition<T extends Comparable<T>> implements Comparable<OptionDefinition<T>> {
    private String label;
	private T value;
	private Object tag;
    
    public int compareTo(OptionDefinition<T> o) {
        if (value == null || o.getValue() == null)    //null为最小
            return -1;
        return value.compareTo(o.getValue());
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}
}
