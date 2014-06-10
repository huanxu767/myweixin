package com.xh.mobile.service;


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
}
