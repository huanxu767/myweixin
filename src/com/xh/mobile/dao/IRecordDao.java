package com.xh.mobile.dao;

import java.util.List;
import java.util.Map;

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

	public List queryAllHistory(String openid);

	public List queryAllHistory(String index, String size);

	/**
	 * 查询我的纪录总数
	 * @param playerId
	 * @return
	 */
	public int queryMyrecordsNum(String playerId);
	/**
	 * 查询纪录报表
	 * @param playerId
	 * @return
	 */
	public List queryMyrecordsChart(String playerId,int currentPage,int pageSize);
	/**
	 * 查询每一个人 
	 * @return
	 */
	public List queryEveryOne();
	/**
	 * 查询总局数
	 */
	public int queryRecordNum();
	/**
	 * 总输赢
	 */
	public int queryRecordMoney();

	
}
