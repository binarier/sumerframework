package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;

/**
 * ���ڴ�Ų�����Ϊ��������,����:
 * TODO ������У�飬����path�ﲻ����ֵ��
 * @author chenjun.li
 * @version $Id$
 */
public class ButtonField extends AbstractField implements Serializable {

	private static final long serialVersionUID = -5692978010840186778L;
    /**
     * ��������ʱ������event
     */
    private String event;
    
    private String label;
    /**
     * �Ƿ����ͻ���У�飬Ĭ��Ϊtrue��
     */
    private boolean validation = true;
    
    /**
     * �Ƿ�ʹ��AJAX��ʽ�İ�ť
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
