package com.xh.mobile.service;


import java.io.IOException;
import java.util.List;

import com.xh.mobile.pojo.Menu;

/**
 * 微信菜单管理接口
 * @author xuhuan
 *
 */
public interface IMenuService {
	/**
	 * 查询Menu
	 * @param eventKey
	 * @return
	 */
	public Menu getMenuById(String eventKey) ;
	/**
	 * 根据父菜单eventKey查询子菜单列表
	 * @param parentId
	 * @return
	 */
	public List<Menu> getMenusByParentEventKey(String eventKey);
	/**
	 * 组织图文信息返回XML报文
	 * @param toUserName
	 * @param fromUserName
	 * @param eventKey
	 * @return
	 */
	public String responseNewsMessage(String toUserName,String fromUserName,String eventKey)throws IOException;
	
}
