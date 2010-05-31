package com.huateng.sumer.runtime.web.meta;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单项定义，包括按钮、链接。
 * @author chenjun.li
 *
 */
public class MenuDefinition implements Serializable {

	private static final long serialVersionUID = 2767025804132704627L;

	private String label;

	private String url;
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private List<MenuDefinition> submenus;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<MenuDefinition> getSubmenus() {
		return submenus;
	}
	public void setSubmenus(List<MenuDefinition> submenus) {
		this.submenus = submenus;
	}
}
