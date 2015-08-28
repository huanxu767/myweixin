package com.xh.mobile.service;


import java.util.List;
import java.util.Map;

import com.xh.mobile.pojo.MajiangRecord;

/**
 * 微信麻将记录管理接口
 * @author xuhuan
 *
 */
public interface IRecordService {
	/**
	 * 新增牌局
	 * @param record
	 * @return
	 */
	public boolean executeAddRecord(MajiangRecord record);
	/**
	 * 获取排行榜
	 * @param type 1 赢钱榜 2输钱榜
	 * @return
	 */
	public List<Map> getRanking(String type);
	/**
	 * 查询历史记录
	 * @param playerId
	 * @return
	 */
	public List queryHistory(String playerId);
	/**
	 * 查询全部历史记录
	 * @return
	 */
	public List queryAllHistory();
	
	/**
	 * 查询个人历史记录
	 * @return
	 */
	public List queryAllHistory(String uuid);
	/**
	 * 查询全部历史记录分页
	 * @return
	 */
	public List queryAllHistory(String index,String size);
	/**
	 * 查询个人纪录图表
	 * @param playerId
	 * @return
	 */
	public Map queryMyRecordsChart(String playerId,int currentPage,int pageSize);
	/**
	 * 查询每一个人
	 * @return
	 */
	public Map queryEveryOne();
	
}
