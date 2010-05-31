package com.huateng.sumer.runtime.web.meta;


/**
 * @author chenjun.li
 *
 */
public class TextField extends AbstractInputField {

	private static final long serialVersionUID = -8875589317525213488L;
	private int maxLength = Integer.MAX_VALUE;
	
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

}
