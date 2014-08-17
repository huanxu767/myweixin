package com.xh.mobile.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.xh.mobile.dao.IRecordDao;
import com.xh.mobile.pojo.MajiangRecord;
import com.xh.mobile.pojo.PlayerRecord;
import com.xh.utils.BaseJdbcDAO;


/**
 * 麻将记录管理
 * @author xuhuan
 *
 */
@Repository(value = "recordDao")
public class RecordDaoImpl extends BaseJdbcDAO implements IRecordDao{
	
	private final static Logger logger = Logger.getLogger(RecordDaoImpl.class);

	public Long addRecord(final MajiangRecord record) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "insert into m_record(place,description,create_time,record_time,maintain_id) values(?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, record.getPlace());
                ps.setString(2, record.getDescription());
                ps.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                ps.setString(4, record.getRecordTime());
                ps.setString(5, record.getOpenId());
                return ps;  
	        }  
	    }, keyHolder);  
	    Long generatedId = keyHolder.getKey().longValue(); 
	    return generatedId;
	}

	public boolean addPlayerRecord(List<PlayerRecord> list) {
		boolean flag = false;
		try {
			for (int i = 0; i < list.size(); i++) {
				PlayerRecord playerRecord = list.get(i);
				String sql = "insert into m_player_record(player_id,is_win,money,record_id,direction) " +
					     "values(?,?,?,?,?)";
				this.getJdbcTemplate().update(
						sql,
						new Object[] { playerRecord.getPlayerId(),
								playerRecord.getIsWin(), playerRecord.getMoney(),
								playerRecord.getRecordId(),
								playerRecord.getDirection() });
			}
			flag = true;
		} catch (Exception e) {
			logger.error(e);
		}
		return flag;
	}
	
	public List queryRanking(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  us.id, us.NAME,us.image_url,IFNULL(winMoney.times,0) wintimes,IFNULL(loseMoney.times,0) losetimes,");
		sql.append("   IFNULL(winMoney.money,0) winmoney,IFNULL(loseMoney.money,0) losemoney,");
		sql.append("   IFNULL(winMoney.money,0)-IFNULL(loseMoney.money,0) totalmoney,");
		sql.append("   IFNULL(FORMAT(( IFNULL(winMoney.times,0)/(IFNULL(winMoney.times,0)+IFNULL(loseMoney.times,0)))*100,2),0.00) winper,");
		sql.append("   IFNULL(FORMAT(( IFNULL(winMoney.times,0)/(IFNULL(winMoney.times,0)+IFNULL(loseMoney.times,0))),2),0.00) winperDesc");
		sql.append(" FROM m_user us   ");
		sql.append("	LEFT JOIN (SELECT player_id,SUM(money) money,is_win,COUNT(*) times FROM m_player_record  WHERE is_win = 1 GROUP BY player_id ) winMoney ON  winMoney.player_id = us.id");
		sql.append("    LEFT JOIN (SELECT player_id,SUM(money) money,is_win,COUNT(*) times FROM m_player_record  WHERE is_win = 0 GROUP BY player_id ) loseMoney ON  loseMoney.player_id = us.id");
		sql.append(" WHERE us.NAME IS NOT NULL  ");
		sql.append(" ORDER BY ");
		if("1".equals(type)){
			//赢钱
			sql.append(" totalmoney DESC");
		}
		if("2".equals(type)){
			//输钱
			sql.append("  totalmoney ");
		}
		if("3".equals(type)){
			//胜率
			sql.append("  winperDesc DESC");
		}
		return this.getJdbcTemplate().queryForList(sql.toString());
	}
}
