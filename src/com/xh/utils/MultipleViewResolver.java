package com.xh.utils;

import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class MultipleViewResolver implements ViewResolver {
	
	private Map<String, ViewResolver> resolvers;

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		int index = viewName.indexOf('.');//first '.'
		ViewResolver resolver = null;
		
		if (index > -1) {
			int p = viewName.indexOf("?");//first '?'
			p = p == -1 ? viewName.length() : p;
			if (resolvers.containsKey(viewName.substring(index + 1, p))) {
				resolver = resolvers.get(viewName.substring(index+1, p));
			} else {
				resolver = resolvers.get(viewName.substring(index+1));
			}
		} else {
			resolver = resolvers.get(viewName);
		}

		if (resolver != null) {
			return resolver.resolveViewName(viewName, locale);
		}
		
		return null;
	}
}