package com.xh.mobile.pojo;

import java.util.Date;
import java.util.List;

/**
 * 麻将记录
 * @author xuhuan
 *
 */
public class MajiangRecord {
	private long id;
	private String place;/**地点*/
	private String description;/**备注*/
	private String recordTime;/**牌局时间*/
	private Date startTime;/**开始时间*/
	private String openId;/**维护人第三方编号*/
	
	private List<PlayerRecord> playersRecordList;
	
	/** 非数据库对应*/
	private String[] playerId;
	
	private int eastPlayerIsWin;//0输 1赢
	private int eastPlayerMoney;//东边 赢的钱
	
	private int southPlayerIsWin;//0输 1赢
	private int southPlayerMoney;//南边 赢的钱
	
	private int westPlayerIsWin;//0输 1赢
	private int westPlayerMoney;//西边 赢的钱
	
	private int northPlayerIsWin;//0输 1赢
	private int northPlayerMoney;//北边 赢的钱
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public List<PlayerRecord> getPlayersRecordList() {
		return playersRecordList;
	}
	public void setPlayersRecordList(List<PlayerRecord> playersRecordList) {
		this.playersRecordList = playersRecordList;
	}
	public String[] getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String[] playerId) {
		this.playerId = playerId;
	}
	public int getEastPlayerIsWin() {
		return eastPlayerIsWin;
	}
	public void setEastPlayerIsWin(int eastPlayerIsWin) {
		this.eastPlayerIsWin = eastPlayerIsWin;
	}
	public int getEastPlayerMoney() {
		return eastPlayerMoney;
	}
	public void setEastPlayerMoney(int eastPlayerMoney) {
		this.eastPlayerMoney = eastPlayerMoney;
	}
	public int getSouthPlayerIsWin() {
		return southPlayerIsWin;
	}
	public void setSouthPlayerIsWin(int southPlayerIsWin) {
		this.southPlayerIsWin = southPlayerIsWin;
	}
	public int getSouthPlayerMoney() {
		return southPlayerMoney;
	}
	public void setSouthPlayerMoney(int southPlayerMoney) {
		this.southPlayerMoney = southPlayerMoney;
	}
	public int getWestPlayerIsWin() {
		return westPlayerIsWin;
	}
	public void setWestPlayerIsWin(int westPlayerIsWin) {
		this.westPlayerIsWin = westPlayerIsWin;
	}
	public int getWestPlayerMoney() {
		return westPlayerMoney;
	}
	public void setWestPlayerMoney(int westPlayerMoney) {
		this.westPlayerMoney = westPlayerMoney;
	}
	public int getNorthPlayerIsWin() {
		return northPlayerIsWin;
	}
	public void setNorthPlayerIsWin(int northPlayerIsWin) {
		this.northPlayerIsWin = northPlayerIsWin;
	}
	public int getNorthPlayerMoney() {
		return northPlayerMoney;
	}
	public void setNorthPlayerMoney(int northPlayerMoney) {
		this.northPlayerMoney = northPlayerMoney;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
