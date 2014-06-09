package com.xh.mobile.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xh.mobile.dao.IUserDao;
import com.xh.mobile.pojo.User;
import com.xh.utils.BaseJdbcDAO;

@Repository(value = "userDao")
public class UserDaoImpl extends BaseJdbcDAO implements IUserDao
{
	
	private final static Logger logger = Logger.getLogger(UserDaoImpl.class);
	/**
	 * 查询是否存在该账户
	 * @param email
     * @return
	 */
    public String queryUser(String email){  
        String currentPhase = "";  
        String sql="select email from welcome_customer where email = ?";  
        Object[] o ={email};   
        try {  
            currentPhase = (String)this.getJdbcTemplate().queryForObject(sql,o, String.class);  
        } catch (Exception e) {  
        	logger.error("查询用户信息失败 : " + e);
            currentPhase = "";  
        }  
        return currentPhase;  
    }  
    
    public User  queryUserByEmail(String email){
    	  String sql="select * from welcome_customer where email = ? ";  
    	  Object[] o ={email};   
    	  return  (User)this.getJdbcTemplate().queryForObject(sql,o,new userMapper());  
    }

	public List<User> queryUsers() {
		String sql = "select * from m_user where 1=1 ";
		return (List<User>) this.getJdbcTemplate().query(sql,new userMapper());
	}
	
	public int addUser(User user) {
		String sql = "insert into m_user(open_id,NAME,mark,moblie,signature,create_time) " +
				     "values(?,?,?,?,?,?)";
		return this.getJdbcTemplate().update(
						sql,
						new Object[] { user.getOpenId(), user.getName(),
								user.getMark(), user.getMoblie(),
								user.getSignature(),user.getCreateTime() });
	}

    private static final class userMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setOpenId(rs.getString("open_id"));
            user.setName(rs.getString("name"));
            user.setMoblie(rs.getLong("moblie"));
            user.setSignature(rs.getString("signature"));
            user.setCreateTime(rs.getDate("create_time"));
            return user;
        }
    }

	
}
