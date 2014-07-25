package com.xh.mobile.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
		
		return false;
	}
	
	
	
}
