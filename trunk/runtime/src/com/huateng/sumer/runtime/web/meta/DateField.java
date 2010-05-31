package com.huateng.sumer.runtime.web.meta;

import java.text.DateFormat;

public class DateField extends AbstractInputField {

	private static final long	serialVersionUID	= 1L;

	public enum FormatType {
		/**
		 * 仅日期
		 */
		DATE,
				/**
		 * 仅时间
		 */
		TIME,
				/**
		 * 时间日期都有效
		 */
		BOTH
	};

	/**
	 * 日期类型，见 {@link FormatType}。
	 */
	private FormatType	formatType;

	/**
	 * 日期格式化类型，与&lt;fmt:formatDate/&gt;的dateStyle属性对应，默认为"long"，选项见
	 * {@link DateFormat}。
	 */
	private String		dateStyle	= "long";
	/**
	 * 日期格式化类型，与&lt;fmt:formatDate/&gt;的timeStyle属性对应，默认为"long"，选项见
	 * {@link DateFormat}。
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
