package com.huateng.sumer.runtime.web.meta;

import java.text.DateFormat;

public class DateField extends AbstractInputField {

	private static final long	serialVersionUID	= 1L;

	public enum FormatType {
		/**
		 * ������
		 */
		DATE,
				/**
		 * ��ʱ��
		 */
		TIME,
				/**
		 * ʱ�����ڶ���Ч
		 */
		BOTH
	};

	/**
	 * �������ͣ��� {@link FormatType}��
	 */
	private FormatType	formatType;

	/**
	 * ���ڸ�ʽ�����ͣ���&lt;fmt:formatDate/&gt;��dateStyle���Զ�Ӧ��Ĭ��Ϊ"long"��ѡ���
	 * {@link DateFormat}��
	 */
	private String		dateStyle	= "long";
	/**
	 * ���ڸ�ʽ�����ͣ���&lt;fmt:formatDate/&gt;��timeStyle���Զ�Ӧ��Ĭ��Ϊ"long"��ѡ���
	 * {@link DateFormat}��
	 */
	private String		timeStyle	= "long";


	public FormatType getFormatType() {
		return formatType;
	}

	public void setFormatType(FormatType formatType) {
		this.formatType = formatType;
	}

	public String getDateStyle() {
		return dateStyle;
	}

	public void setDateStyle(String dateStyle) {
		this.dateStyle = dateStyle;
	}

	public String getTimeStyle() {
		return timeStyle;
	}

	public void setTimeStyle(String timeStyle) {
		this.timeStyle = timeStyle;
	}

}
