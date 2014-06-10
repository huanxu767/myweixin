package com.xh.mobile.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xh.mobile.dao.IMenuDao;
import com.xh.mobile.pojo.Menu;
import com.xh.mobile.service.IMenuService;

@Service(value = "menuService")
public class MenuServiceImpl  implements IMenuService {

	@Resource
	private IMenuDao menuDao;
	
	public Menu getMenuById(String eventKey) {
		return menuDao.getMenuById(eventKey);
	}

}
