package com.xh.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.view.AbstractView;

public class JsonView extends AbstractView implements Ordered {

	private int order;

	public JsonView() {
		setContentType("application/json;charset=UTF-8");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object o = map.get(JsonModelAndView.EXCLUDE_PROPERTY);
		if (o != null) {
			JSONResponseUtil.print(response, map .get(JsonModelAndView.MODEL_KEY), (String[]) o);
		} else {
			JSONResponseUtil.print(response, map .get(JsonModelAndView.MODEL_KEY));
		}
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

}
