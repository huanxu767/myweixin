package com.xh.mobile.pojo;

import java.util.Date;

/**
 * 麻将记录
 * @author xuhuan
 *
 */
public class MajiangRecord {
	private long id;
	private long placeId;
	private String description;
	private Date startTime;
	private long eastPlayerId;//东边 玩家编号
	private int eastPlayerIsWin;//0输 1赢
	private int eastPlayerMoney;//东边 赢的钱
	
	private long southPlayerId;//南边 玩家编号
	private int southPlayerIsWin;//0输 1赢
	private int southPlayerMoney;//南边 赢的钱
	
	private long westPlayerId;//西边 玩家编号
	private int westPlayerIsWin;//0输 1赢
	private int westPlayerMoney;//西边 赢的钱
	
	private long northPlayerId;//北边 玩家编号
	private int northPlayerIsWin;//0输 1赢
	private int northPlayerMoney;//北边 赢的钱
	
	//非数据库对应 
	private String[] userName;
	
	public String[] getUserName() {
		return userName;
	}
	public void setUserName(String[] userName) {
		this.userName = userName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(long placeId) {
		this.placeId = placeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public long getEastPlayerId() {
		return eastPlayerId;
	}
	public void setEastPlayerId(long eastPlayerId) {
		this.eastPlayerId = eastPlayerId;
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
	public long getSouthPlayerId() {
		return southPlayerId;
	}
	public void setSouthPlayerId(long southPlayerId) {
		this.southPlayerId = southPlayerId;
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
	public long getWestPlayerId() {
		return westPlayerId;
	}
	public void setWestPlayerId(long westPlayerId) {
		this.westPlayerId = westPlayerId;
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
	public long getNorthPlayerId() {
		return northPlayerId;
	}
	public void setNorthPlayerId(long northPlayerId) {
		this.northPlayerId = northPlayerId;
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
	
}
