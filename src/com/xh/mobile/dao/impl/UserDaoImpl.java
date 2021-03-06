package com.xh.mobile.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
        String sql="select email from m_user where email = ?";  
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
    	  String sql="select * from m_user where email = ? ";  
    	  Object[] o ={email};   
    	  return  (User)this.getJdbcTemplate().queryForObject(sql,o,new userMapper());  
    }

	public List<User> queryUsers() {
		String sql = "select * from m_user where 1=1 and name is not null ";
		return (List<User>) this.getJdbcTemplate().query(sql,new userMapper());
	}
	
	public int addUser(User user) {
		String sql = "insert into m_user(open_id,NAME,god_number,image_url,moblie,signature,create_time) " +
				     "values(?,?,?,?,?,?,?)";
		return this.getJdbcTemplate().update(
						sql,
						new Object[] { user.getOpenId(), user.getName(),
								user.getGod_number(),user.getImage_url(), user.getMoblie(),
								user.getSignature(),user.getCreateTime() });
	}
	public int addUser(String openid) {
		String sql = "insert into m_user(open_id) values(?) ";
		return this.getJdbcTemplate().update(sql,new Object[] {openid});
	}
	public User queryUserByOpenid(String openid) {
  	  String sql="select * from m_user where open_id = ? ";  
  	  Object[] o ={openid};   
  	  List bean = this.getJdbcTemplate().query(sql,o,new userMapper());
  	  if(bean.isEmpty() ){
  		  return null;
  	  }
  	  return  (User)bean.get(0);
	}
	
	public int updateUser(Map params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" update m_user set "+params.get("key")+" =?");
		sql.append(" where open_id=?");
		return this.getJdbcTemplate().update(sql.toString(),new Object[] {params.get("value"),params.get("openId") });
	}

    private static final class userMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setOpenId(rs.getString("open_id"));
			user.setImage_url(rs.getString("image_url"));
			user.setGod_number(rs.getString("god_number"));
			user.setName(rs.getString("name"));
			user.setMoblie(rs.getLong("moblie"));
			user.setSignature(rs.getString("signature"));
			user.setCreateTime(rs.getDate("create_time"));
			return user;
		}
    }

	public List queryUserRecord(Long id) {
		String sql="SELECT COUNT(*) counts,SUM(money) money,is_win isWin FROM m_player_record WHERE player_id = ? GROUP BY is_win";
		return this.getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	public Map queryUserById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  us.*,IFNULL(winMoney.times,0) wintimes,IFNULL(loseMoney.times,0) losetimes,");
		sql.append("   IFNULL(winMoney.money,0) winmoney,IFNULL(loseMoney.money,0) losemoney,");
		sql.append("   IFNULL(winMoney.money,0)-IFNULL(loseMoney.money,0) totalmoney,");
		sql.append("  IFNULL(FORMAT( IFNULL(winMoney.money,0)/(IFNULL(loseMoney.money,0)+IFNULL(winMoney.money,0)),2),0.00) winper");
		sql.append(" FROM m_user us   ");
		sql.append("	LEFT JOIN (SELECT player_id,SUM(money) money,is_win,COUNT(*) times FROM m_player_record  WHERE is_win = 1 GROUP BY player_id ) winMoney ON  winMoney.player_id = us.id");
		sql.append("    LEFT JOIN (SELECT player_id,SUM(money) money,is_win,COUNT(*) times FROM m_player_record  WHERE is_win = 0 GROUP BY player_id ) loseMoney ON  loseMoney.player_id = us.id");
		sql.append(" WHERE us.NAME IS NOT NULL  and  us.id = ? ");
		return this.getJdbcTemplate().queryForMap(sql.toString(),new Object[]{id});
	}
}
