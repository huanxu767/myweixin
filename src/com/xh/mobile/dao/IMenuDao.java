package com.xh.mobile.dao;


import java.util.List;

import com.xh.mobile.pojo.Menu;


/**
 * 菜单管理
 * @author xuhuan
 *
 */
public interface IMenuDao {
	/**
	 * 获取Menu
	 * @param eventKey
	 * @return
	 */
	public Menu getMenuById(String eventKey);
	/**
	 * 
	 * @param eventKey
	 * @return
	 */
	public List<Menu> getMenusByParentEventKey(String eventKey);
}
