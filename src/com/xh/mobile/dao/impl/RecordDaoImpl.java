package com.xh.mobile.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.xh.mobile.dao.IRecordDao;
import com.xh.mobile.pojo.MajiangRecord;
import com.xh.mobile.pojo.PlayerRecord;
import com.xh.utils.BaseJdbcDAO;
import com.xh.utils.WebAppConfig;


/**
 * 麻将记录管理
 * @author xuhuan
 *
 */
@Repository(value = "recordDao")
public class RecordDaoImpl extends BaseJdbcDAO implements IRecordDao{
	
	private final static Logger logger = Logger.getLogger(RecordDaoImpl.class);
	/** 进入排行榜最低要求，参与场次要达到一定要求*/
	private final static int rankMinLimite = Integer.parseInt(WebAppConfig.GLOBAL_CONFIG_PROPERTIES.getProperty("majiang.rank.mintime"));
	private final static String PAGER_SIZE = WebAppConfig.GLOBAL_CONFIG_PROPERTIES.getProperty("majiang.rank.pagerSize");

	

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
		sql.append(" and (IFNULL(winMoney.times, 0) + IFNULL(loseMoney.times, 0))>"+rankMinLimite);
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
	
	public List queryHistory(String playerId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT record_id,MAX(c.create_time) time,MAX(c.place), GROUP_CONCAT(u.name) NAMES, ");
		sql.append(" 	    GROUP_CONCAT(CONCAT(LEFT(u.name,5),(CASE r.is_win WHEN 0 THEN '-' WHEN 1 THEN '+' END),r.money)) record");
		sql.append(" FROM m_player_record r");
		sql.append(" LEFT JOIN m_user u ON   r.player_id=u.id ");
		sql.append(" LEFT JOIN m_record c ON   c.id = r.record_id ");
		if(!StringUtils.isEmpty(playerId)){
			sql.append(" r.player_id = ? ");
			Object args = new Object[]{playerId};
			return this.getJdbcTemplate().queryForList(sql.toString(),args);
		}
		sql.append("GROUP BY record_id DESC");
		return this.getJdbcTemplate().queryForList(sql.toString());
	}

	public List queryAllHistory(String openid) {
		StringBuffer sql = new StringBuffer();
		ArrayList list = new ArrayList();
		sql.append(" SELECT rc.place,DATE_FORMAT(rc.record_time,'%y-%m-%d %h:%i %p %w') date, ");
		sql.append(" GROUP_CONCAT(CONCAT(us.real_name,CASE is_win WHEN '1' THEN '+' WHEN '0' THEN '-' ELSE '+' END ,CONVERT(money,CHAR)) ) content");
		sql.append(" FROM m_player_record m");
		sql.append(" LEFT JOIN m_user us ON us.id = m.player_id ");
		sql.append(" LEFT JOIN m_record rc ON rc.id = m.record_id ");
		sql.append(" GROUP BY m.record_id DESC , rc.record_time DESC ");
		if (!StringUtils.isEmpty(openid)) {
			if(openid.length()>10){
				sql.append(" HAVING GROUP_CONCAT(us.open_id) LIKE ? ");
			}else{
				sql.append(" HAVING GROUP_CONCAT(us.id) like ? ");
			}
			list.add("%"+openid+"%");
		}
		return this.getJdbcTemplate().queryForList(sql.toString(), list.toArray());
	}

	public int queryMyrecordsNum(String playerId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) nums from m_player_record pr where pr.player_id = ? ");
		Map map = this.getJdbcTemplate().queryForMap(sql.toString(), new Object[]{playerId});
		return Integer.parseInt(map.get("nums")+"");
	}

	public List queryMyrecordsChart(String playerId, int currentPage,
			int pageSize) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  r.id,r.place,r.create_time,DATE_FORMAT(r.record_time,'%y-%m-%d') date,pr.is_win,pr.money,");
		sql.append(" CONCAT(CASE pr.is_win WHEN '1' THEN '' WHEN '0' THEN '-' ELSE '+' END ,CONVERT(pr.money,CHAR)) mymoney,");
		sql.append(" (");
		sql.append("  	select GROUP_CONCAT( ");
		sql.append("  		CONCAT(us.real_name, ");
		sql.append("  		CASE is_win WHEN '1' THEN '+' WHEN '0' THEN '-' ELSE '+' END , ");
		sql.append("  		CONVERT(money,CHAR),'<br>' ");
		sql.append("  	)");
		sql.append(" ) content");
		sql.append(" from m_player_record m ");
		sql.append(" LEFT JOIN m_user us ON us.id = m.player_id ");
		sql.append(" where m.record_id = r.id ");
		sql.append(" ) tooltip");
		sql.append(" from m_record r ,m_player_record pr");
		sql.append(" where r.id = pr.record_id and pr.player_id = ?");
		sql.append(" order by r.id desc ");
		sql.append(" limit ?,?");
		return this.getJdbcTemplate().queryForList(sql.toString(), new Object[]{playerId,(currentPage-1)*pageSize,pageSize});
	}

	public List queryEveryOne() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id,u.image_url,u.real_name,r.counts from m_user u ");
		sql.append(" left join (select count(*) counts,player_id from m_player_record group by player_id) r");
		sql.append(" on r.player_id = u.id");
		sql.append(" where u.real_name is not null");
		sql.append(" order by r.counts desc");
		return this.getJdbcTemplate().queryForList(sql.toString());
		
	}

	public int queryRecordNum() {
		String sql = "select count(*) from m_record";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	public int queryRecordMoney() {
		String sql = "select sum(money) from m_player_record where is_win = 1";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	@Override
	public List queryAllHistory(String index, String size) {
		StringBuffer sql = new StringBuffer();
		int beginIndex = Integer.parseInt(index) - 1;
		int pagerSize = Integer.parseInt(size);
		if(StringUtils.isEmpty(size)){
			size = PAGER_SIZE;
		}
		sql.append(" SELECT rc.place,DATE_FORMAT(rc.create_time,'%y-%m-%d %r %w') date,  DATE_FORMAT(rc.create_time,'%y-%m-%d'  ) DATE_YD, ");
		sql.append(" GROUP_CONCAT(CONCAT(us.real_name,CASE is_win WHEN '1' THEN '+' WHEN '0' THEN '-' ELSE '+' END ,CONVERT(money,CHAR)) ) content");
		sql.append(" FROM m_player_record m");
		sql.append(" LEFT JOIN m_user us ON us.id = m.player_id ");
		sql.append(" LEFT JOIN m_record rc ON rc.id = m.record_id ");
		sql.append(" GROUP BY m.record_id desc  LIMIT "+beginIndex*pagerSize+" , "+size);
		return this.getJdbcTemplate().queryForList(sql.toString());
	}
}
