package com.xh.mobile.pojo;

/**
 * 牌局参与玩家记录
 * @author xuhuan
 *
 */
public class PlayerRecord {
	private Long id;/**编号*/
	private String playerId;/**玩家编号*/
	private int isWin;/**0 赢钱  1输钱*/
	private int money;/** 输赢数目*/
	private Long recordId;/**对应记录编号*/
	private int direction;/**方位 1东 2南 3西 4北*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public int getIsWin() {
		return isWin;
	}
	public void setIsWin(int isWin) {
		this.isWin = isWin;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}
