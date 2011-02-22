package com.huateng.frame.gwt.client.ui;

import org.synthful.smartgwt.client.LazyCanvas;

import com.google.gwt.place.shared.Place;
import com.smartgwt.client.widgets.Canvas;

/**
 * @author chenjun.li
 *
 * @param <C> {@link Canvas}类型，表示整个View的顶层元素
 * @param <P> 表示该view对应的Place类型
 */
public abstract class BasicView<C extends Canvas> extends LazyCanvas<C>
{
	/**
	 * 由子类实现，用于将数据状态反映到显示界面上
	 * @param clientSide 用于提示函数是否需要从服务器端刷新数据，以优化显示过程
	 * @param hint 自定义更新提示信息，以优化操作
	 */
	public abstract void updateView(boolean clientSideOnly, Object hint);
	
	/**
	 * 在每次Activity开始执行时调用，目的是把Place里的状态参数传递进来。
	 * 由于涉及函数调用原型，这里只能用通用的 {@link Place}类型做参数，而无法加入泛型参数，不然没法统一调用。
	 * @param place 
	 */
	public abstract void setupView(Place place);
}
