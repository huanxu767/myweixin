package com.xh.utils;

import org.springframework.web.servlet.ModelAndView;

public class JsonModelAndView extends ModelAndView {

	public static final String MODEL_KEY = "MODEL_KEY";
	
	public static final String EXCLUDE_PROPERTY = "EXCLUDE_PROPERTY";

	private static final String VIEW_NAME = "json";


	public JsonModelAndView() {
		setViewName(VIEW_NAME);
	}

	public JsonModelAndView(Object object) {
		addObject(MODEL_KEY, object);
		setViewName(VIEW_NAME);
	}
	
	public JsonModelAndView(Object object, String[] excludeProperty) {
		addObject(MODEL_KEY, object);
		addObject(EXCLUDE_PROPERTY, excludeProperty);
		setViewName(VIEW_NAME);
	}

	public void setModel(Object object) {
		addObject(MODEL_KEY, object);
	}

	public void setExcludeProperty(String[] excludeProperty) {
		addObject(EXCLUDE_PROPERTY, excludeProperty);
	}
}