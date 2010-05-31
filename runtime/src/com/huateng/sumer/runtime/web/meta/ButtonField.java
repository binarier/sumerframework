package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;

/**
 * 用于存放操作行为定义数据,包括:
 * TODO 做属性校验，比如path里不能有值等
 * @author chenjun.li
 * @version $Id$
 */
public class ButtonField extends AbstractField implements Serializable {

	private static final long serialVersionUID = -5692978010840186778L;
    /**
     * 操作发生时产生的event
     */
    private String event;
    
    private String label;
    /**
     * 是否做客户端校验，默认为true。
     */
    private boolean validation = true;
    
    /**
     * 是否使用AJAX方式的按钮
     */
    private boolean ajax = false;

    public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

	public boolean isValidation() {
		return validation;
	}

	public void setValidation(boolean validation) {
		this.validation = validation;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
