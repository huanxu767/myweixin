package com.xh.mobile.dao;

import java.util.List;

import com.xh.mobile.pojo.MajiangRecord;
import com.xh.mobile.pojo.PlayerRecord;


/**
 * 记录管理
 * @author xuhuan
 *
 */
public interface IRecordDao {
	
	public Long addRecord(MajiangRecord record);
	
	public boolean addPlayerRecord(List<PlayerRecord> list);
	
	public List queryRanking(String type);
	
	public List queryHistory(String playerId);

	public List queryAllHistory(String userId);
	
}
