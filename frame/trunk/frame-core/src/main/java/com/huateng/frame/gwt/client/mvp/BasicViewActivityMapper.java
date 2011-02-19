package com.huateng.frame.gwt.client.mvp;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.huateng.frame.gwt.client.ui.BasicView;

public abstract class BasicViewActivityMapper implements ActivityMapper
{
	private Map<Class<?>, BasicView<?>> viewMap = new HashMap<Class<?>, BasicView<?>>();
	
	/**
	 * 由实现类来初始化Place与View实例的映射
	 * @param map
	 */
	protected abstract void setupPlaceViewMap(Map<Class<?>, BasicView<?>> map);

	public Activity getActivity(Place place)
	{
		if (viewMap.isEmpty())
		{
			//在首次调用时初始化
			setupPlaceViewMap(viewMap);
		}
		
		BasicView<?> targetView = viewMap.get(place.getClass());
		
		if (targetView == null)
			return null;
		return new BasicViewActivity(targetView);
	}

}
