package com.huateng.frame.gwt.client.ui;

import org.synthful.smartgwt.client.LazyCanvas;

import com.smartgwt.client.widgets.Canvas;

/**
 * @author chenjun.li
 *
 * @param <C> {@link Canvas}类型，表示整个View的顶层元素
 */
public abstract class BasicView<C extends Canvas> extends LazyCanvas<C>
{
	public abstract void updateView(Object hint);
	
	public abstract void setupView();
}
