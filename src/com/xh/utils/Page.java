package com.xh.utils;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;
import java.util.List;

/**
 * 分页pojo
 *
 * <p>Description: </p>
 * <p>Copyright	 : Copyright (c) 2012 </p>
 * <p>Company	 : xwtec.com </p>
 * <p>Version	 : 1.0 </p>
 * @author	     : pengpeng
 * @date 	     : 2012-10-11 下午02:59:30
 */
public class Page {

	// 这个值在列表的时候作为iterator标签的fromRow属性值调用，例如request.setAttribute("offset",fromRow);
	private int fromRow = 1; // 查询起始记录

	private int toRow = 1; // 查询结束记录

	private int totalRow = 0; // 总记录数

	private int currentPage = 1; // 当前页面数

	private int totalPage = 0; // 总页面数

	// 这个值在列表的时候作为iterator标签的length属性值调用，例如request.setAttribute("length",rowsPerpage);
	private int rowsPerPage = 20; // 每页显示记录数

	private boolean flag = false;

	// private boolean showPage; //判断是否需要翻页
	public Page() {
		this.currentPage = 1;
		this.totalPage = 1;
	}

	/**
	 * 构造器 初始化当前页面数
	 *
	 * @param curPage
	 *            当前页面数
	 */
	public Page(int curPage) {
		this.currentPage = curPage;
		// this.count();
	}

	/**
	 * 从集合类中取出当前页所需要的记录
	 *
	 * @param col
	 *            全部记录的集合
	 * @return Collection 当前页的记录集合
	 */
	public Collection getCurrentPageecord(Collection col) {
		// 设置总记录数
		setTotalRow(col.size());
		Vector v = new Vector();
		Iterator iterator = col.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			i++;
			if (i < getFromRow()) {
				iterator.next();
				continue;
			} else if (i > getToRow()) {
				iterator.next();
				continue;
			} else {
				v.add(iterator.next());
			}
		}
		return v;
	}

	public Page(int curPage, int totalRow) {
		this.currentPage = curPage;
		this.totalRow = totalRow;
		this.count();
	}

	private void count() {
		if ((totalRow != 0) && (this.totalRow % this.rowsPerPage == 0)) {
			this.totalPage = this.totalRow / this.rowsPerPage;
		} else {
			this.totalPage = this.totalRow / this.rowsPerPage + 1;
		}

		if (this.totalRow == 0) {
			this.flag = true;
			this.fromRow = 0;
		} else {
			this.fromRow = (this.currentPage - 1) * this.rowsPerPage + 1;
		}

		this.toRow = this.currentPage * this.rowsPerPage;

		if (this.toRow > this.totalRow) {
			this.toRow = this.totalRow;
		}
	}

	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}

	public void setToRow(int toRow) {
		this.toRow = toRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalPage(int totalPage) {

		this.totalPage = totalPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * 根据当前页面数和每页显示记录数，可以得到查询的起始记录数
	 *
	 * @return int 查询起始记录数
	 */
	public int getFromRow() {
		int rows = (currentPage - 1) * rowsPerPage;
		return rows;
	}

	/**
	 * 根据当前页面数和每页显示记录数得到中止记录数
	 *
	 * @return int 查询中止记录数
	 */
	public int getToRow() {
		int rows = currentPage * rowsPerPage;
		return rows;
	}

	public int getTotalRow() {
		return (this.totalRow);
	}

	public int getCurrentPage() {
		return (this.currentPage);
	}

	/**
	 * 根据总记录数和每页显示记录数可以得到总页数
	 *
	 * @return 总页数
	 */
	public int getTotalPage() {
		if ((totalRow != 0) && (this.totalRow % this.rowsPerPage == 0)) {
			this.totalPage = this.totalRow / this.rowsPerPage;
		} else {
			this.totalPage = this.totalRow / this.rowsPerPage + 1;
		}
		return (this.totalPage);
	}

	public int getRowsPerPage() {
		return (this.rowsPerPage);
	}

	public boolean getFlag() {
		return (this.flag);
	}

	/**
	 * 判断是否需要分页
	 *
	 * @return 如果需要翻页则返回true，否则返回false。
	 */
	public boolean hasNextPage() {
		if (totalRow < rowsPerPage || totalRow == rowsPerPage) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "Page [fromRow=" + this.getFromRow() + ", toRow="
				+ this.getToRow() + ", totalRow=" + this.getTotalRow()
				+ ", currentPage=" + this.getCurrentPage() + ", totalPage="
				+ this.getTotalPage() + ", rowsPerPage="
				+ this.getRowsPerPage() + ", flag=" + flag + "]";
	}

	public static List<?> getObjecrList4Page(
			List<?> objectList, Page page) {
		if (objectList == null || objectList.isEmpty()) {
			return null;
		}
		page.setTotalRow(objectList.size());
		return objectList.subList(page.getFromRow(), page.getToRow());
	}
}