package com.huateng.sumer.runtime.web.meta;

/**
 * <p>�б�ѡ���</p>
 * <ul>
 * <li><b>label</b> ��ʾ��ǩ</li>
 * <li><b>value</b> ѡ��ȡֵ</li>
 * <li><b>tag</b> �Զ����ֶ�</li>
 *
 * @author chenjun.li
 * @version $Id$
 */
public class OptionDefinition<T extends Comparable<T>> implements Comparable<OptionDefinition<T>> {
    private String label;
	private T value;
	private Object tag;
    
    public int compareTo(OptionDefinition<T> o) {
        if (value == null || o.getValue() == null)    //nullΪ��С
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
