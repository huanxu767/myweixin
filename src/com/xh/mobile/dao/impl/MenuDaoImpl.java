package com.xh.mobile.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xh.mobile.dao.IMenuDao;
import com.xh.mobile.pojo.Menu;
import com.xh.utils.BaseJdbcDAO;

@Repository(value = "menuDao")
public class MenuDaoImpl extends BaseJdbcDAO implements IMenuDao{
	
	private final static Logger logger = Logger.getLogger(MenuDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	public Menu getMenuById(String eventKey) {
		String sql = "select * from m_menu where EventKey = ? and is_active = 1 ";  
  	  	Object[] o = {eventKey};   
  	  	return  (Menu)this.getJdbcTemplate().queryForObject(sql,o,new menuMapper());  
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> getMenusByParentEventKey(String eventKey) {
		String sql = "SELECT * FROM m_menu  "+
					 "WHERE parent_id  = ("+
					 "   SELECT id FROM m_menu WHERE EventKey=? "+
					 ")";
		Object[] o = {eventKey};   
  	  	return  this.getJdbcTemplate().query(sql,o,new menuMapper());  
	}
	
    private static final class menuMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Menu menu = new Menu();
            menu.setId(rs.getLong("id"));
            menu.setParentId(rs.getLong("parent_id"));
            menu.setName(rs.getString("name"));
            menu.setDescription(rs.getString("description"));
            menu.setPageUrl(rs.getString("page_url"));
            menu.setImageUrl(rs.getString("image_url"));
            menu.setType(rs.getString("type"));
            menu.setCreateTime(rs.getDate("create_time"));
            menu.setIsActive(rs.getShort("is_active"));
            menu.setEventKey(rs.getString("eventkey"));
            return menu;
        }
    }





	
}
