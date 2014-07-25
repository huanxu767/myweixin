package com.xh.mobile.dao;

import java.util.List;

import com.xh.mobile.pojo.MajiangRecord;
import com.xh.mobile.pojo.PlayerRecord;


/**
 * 马静记录管理
 * @author xuhuan
 *
 */
public interface IRecordDao {
	
	public Long addRecord(MajiangRecord record);
	
	public boolean addPlayerRecord(List<PlayerRecord> list);
	
}
