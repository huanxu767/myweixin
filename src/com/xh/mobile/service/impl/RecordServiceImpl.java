package com.xh.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xh.mobile.dao.IRecordDao;
import com.xh.mobile.pojo.MajiangRecord;
import com.xh.mobile.pojo.PlayerRecord;
import com.xh.mobile.service.IRecordService;

@Service(value = "recordService")
public class RecordServiceImpl implements IRecordService{
	
	@Resource
	private IRecordDao recordDao;


	public boolean executeAddRecord(MajiangRecord record) {
		Long recordId = recordDao.addRecord(record);
		String[] playerIds = record.getPlayerId();
		List playerRecordList = new ArrayList();
//		东
		PlayerRecord earstplayerRecord = new PlayerRecord();
		earstplayerRecord.setPlayerId(playerIds[0]);
		earstplayerRecord.setIsWin(record.getEastPlayerIsWin());
		earstplayerRecord.setDirection(1);
		earstplayerRecord.setRecordId(recordId);
		earstplayerRecord.setMoney(record.getEastPlayerMoney());
		playerRecordList.add(earstplayerRecord);
//		南
		PlayerRecord southplayerRecord = new PlayerRecord();
		southplayerRecord.setPlayerId(playerIds[1]);
		southplayerRecord.setIsWin(record.getSouthPlayerIsWin());
		southplayerRecord.setDirection(2);
		southplayerRecord.setRecordId(recordId);
		southplayerRecord.setMoney(record.getSouthPlayerMoney());
		playerRecordList.add(southplayerRecord);

//		西
		PlayerRecord westplayerRecord = new PlayerRecord();
		westplayerRecord.setPlayerId(playerIds[2]);
		westplayerRecord.setIsWin(record.getWestPlayerIsWin());
		westplayerRecord.setDirection(3);
		westplayerRecord.setRecordId(recordId);
		westplayerRecord.setMoney(record.getWestPlayerMoney());
		playerRecordList.add(westplayerRecord);

//		北
		PlayerRecord northplayerRecord = new PlayerRecord();
		northplayerRecord.setPlayerId(playerIds[3]);
		northplayerRecord.setIsWin(record.getNorthPlayerIsWin());
		northplayerRecord.setDirection(4);
		northplayerRecord.setRecordId(recordId);
		northplayerRecord.setMoney(record.getNorthPlayerMoney());
		playerRecordList.add(northplayerRecord);
		recordDao.addPlayerRecord(playerRecordList);
		return false;
	}


	public List<Map> getRanking(String type) {
		return recordDao.queryRanking(type);
	}


	public List queryHistory(String playerId) {
		return recordDao.queryHistory(playerId);
	}


	public List queryAllHistory(String openid) {
		List list = recordDao.queryAllHistory(openid);
		return list;
	}

	public List queryAllHistory() {
		List list = recordDao.queryAllHistory(null);
		return list;
	}


	public Map queryMyRecordsChart(String playerId, int currentPage, int pageSize) {
//		http://127.0.0.1:8080/record/getMyRecordsChart.do?playerId=110006&currentPage=0&pageSize=5
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<String> xList = new ArrayList<String>();
		List<Map<String,String>> yList = new ArrayList<Map<String,String>>();
		resultMap.put("counts", recordDao.queryMyrecordsNum(playerId));
		List list = recordDao.queryMyrecordsChart(playerId, currentPage, pageSize);
		for (int i = 0;list != null && i < list.size(); i++) {
			Map tempMap = (Map)list.get(i);//X轴数据
			Map seriesMap = new HashMap();//y轴数据
			xList.add(tempMap.get("date")+"");
			seriesMap.put("y", Integer.parseInt(tempMap.get("mymoney")+""));
			seriesMap.put("extra", tempMap.get("tooltip"));
			yList.add(seriesMap);
		}
		resultMap.put("xList",xList );
		resultMap.put("yList", yList);
		return resultMap;
	}


	public Map queryEveryOne() {
		Map map = new HashMap();
		List list = recordDao.queryEveryOne();
		map.put("list", list);
		int recordNum = recordDao.queryRecordNum();
		map.put("recordNum", recordNum);
		int recordMoney = recordDao.queryRecordMoney();
		map.put("recordMoney", recordMoney);
		return map;
	}
	

	@Override
	public List queryAllHistory(String index, String size) {
		//最终List
		List resultList = new ArrayList();
		List list = recordDao.queryAllHistory(index,size);
		//section组List
		List sectionList = new ArrayList();
		List cellList = new ArrayList();
		Map sectionTitleMap = new HashMap();
		//记录当前的
		String tempYDFlag = "" ;
		for (int i = 0; i < list.size(); i++) {
			Map tempMap = (Map)list.get(i);
			//这条记录与上一条记录 年月日相同 则添加到    tempContentList  否则 重置 tempContentList 
			String tempYearDay = tempMap.get("date_yd") +"";
			if(!tempYearDay.equals(tempYDFlag)){
				//重置判断位
				tempYDFlag = tempYearDay;
				//这次记录与上次日期记录不同
				//记录
				if(i != 0){
					sectionList.add(sectionTitleMap);
					sectionList.add(cellList);
					resultList.add(sectionList);
					//重置cell List
					sectionList = new ArrayList();
					cellList = new ArrayList();
					sectionTitleMap = new HashMap();
				}
				//数据
				sectionTitleMap.put("title",tempYearDay);
				cellList.add(tempMap);
				
			}else{
				//这次记录与上次相同
				cellList.add(tempMap);
			}
		}
		return resultList;
	}
}


