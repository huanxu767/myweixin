package com.xh.utils;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class JsonViewResolver implements ViewResolver {

	private View viewClass;

	public void setViewClass(View viewClass) {
		this.viewClass = viewClass;
	}

	
	public View resolveViewName(String s, Locale locale) throws Exception {
		return viewClass;
	}

}
