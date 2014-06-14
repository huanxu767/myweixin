package com.xh.mobile.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xh.mobile.dao.IMenuDao;
import com.xh.mobile.pojo.Menu;
import com.xh.mobile.pojo.PictureAndTextXmlMessage;
import com.xh.mobile.service.IMenuService;
import com.xh.utils.DateTime;

@Service(value = "menuService")
public class MenuServiceImpl  implements IMenuService {

	@Resource
	private IMenuDao menuDao;
	
	public Menu getMenuById(String eventKey) {
		return menuDao.getMenuById(eventKey);
	}

	public List<Menu> getMenusByParentEventKey(String eventKey) {
		return menuDao.getMenusByParentEventKey(eventKey);
	}

	public String responseNewsMessage(String toUserName, String fromUserName,String eventKey) throws IOException {
		List<Menu> menus = new ArrayList<Menu>();
		menus = menuDao.getMenusByParentEventKey(eventKey);
		if(menus.isEmpty()){
			//没有多个图文消息行,则推送本身
			Menu menu = menuDao.getMenuById(eventKey);
			menus.add(menu);
		}
		PictureAndTextXmlMessage messages = new PictureAndTextXmlMessage();
		messages.setArticlesMap(menus);
		messages.setCreateTime(DateTime.getLongTime()+"");
		messages.setFromUserName(fromUserName);
		messages.setToUserName(toUserName);
		messages.setMsgType("news");
		return messages.getXML();
	}

}
