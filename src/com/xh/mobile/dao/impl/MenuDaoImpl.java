package com.xh.mobile.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xh.mobile.dao.IMenuDao;
import com.xh.mobile.pojo.Menu;
import com.xh.utils.BaseJdbcDAO;

@Repository(value = "menuDao")
public class MenuDaoImpl extends BaseJdbcDAO implements IMenuDao{
	
	private final static Logger logger = Logger.getLogger(MenuDaoImpl.class);
	
	public Menu getMenuById(String eventKey) {
		String sql="select * from m_menu where EventKey = ? and is_active = 1 ";  
  	  	Object[] o ={eventKey};   
  	  	return  (Menu)this.getJdbcTemplate().queryForObject(sql,o,new menuMapper());  
	}
	

    private static final class menuMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Menu menu = new Menu();
            menu.setId(rs.getLong("id"));
            menu.setPageUrl(rs.getString("page_url"));
            menu.setCreateTime(rs.getDate("create_time"));
            return menu;
        }
    }



	
}
