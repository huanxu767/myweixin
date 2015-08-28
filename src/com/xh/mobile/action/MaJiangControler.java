package com.xh.mobile.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xh.mobile.pojo.MajiangRecord;
import com.xh.mobile.service.IRecordService;

@Controller
@RequestMapping("/record")
public class MaJiangControler extends BaseActionController {

	private final static Logger logger = Logger.getLogger(MaJiangControler.class);
	
	@Resource
	private IRecordService recordService;
	/**
	 * 新增牌局
	 * @param request
	 * @param response
	 * @param record
	 */
	@RequestMapping(value = "/addRecord.do")
	public void addRecord(HttpServletRequest request,HttpServletResponse response,
			MajiangRecord record) {
		Map map = new HashMap();
		boolean flag = recordService.executeAddRecord(record);
		map.put("flag", flag);
		outResult(response, map);
	}
	/**
	 * 排行榜
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRanking.do")
	public void getRanking(HttpServletRequest request,HttpServletResponse response) {
		Map map = new HashMap();
		List list = recordService.getRanking("1");
		List lostList = recordService.getRanking("2");
		List perList = recordService.getRanking("3");
		map.put("winlist", list);
		map.put("loseList", lostList);
		map.put("perList", perList);
		outResult(response, map);
	}
	/**
	 * 历史记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getHistory.do")
	public void getHistory(HttpServletRequest request,HttpServletResponse response,String playerId) {
		Map map = new HashMap();
		List list = recordService.queryHistory(playerId);
		map.put("list", list);
		outResult(response, map);
	}
	/**
	 * 全部历史记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllHistory.do")
	public void getAllHistory(HttpServletRequest request,HttpServletResponse response) {
		Map map = new HashMap();
		List list = recordService.queryAllHistory();
		map.put("list", list);
		outResult(response, map);
	}
	/**
	 * 全部历史记录分页
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllHistoryPager.do")
	public void getAllHistoryPager(HttpServletRequest request,HttpServletResponse response,String pagerSize,String pagerIndex) {
		Map map = new HashMap();
		List list = recordService.queryAllHistory(pagerIndex,pagerSize);
		map.put("list", list);
		outResult(response, map);
	}
	/**
	 * 个人历史记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getMyHistory.do")
	public void getMyHistory(HttpServletRequest request,HttpServletResponse response,String openid) {
		Map map = new HashMap();
		List list = recordService.queryAllHistory(openid);
		map.put("list", list);
		outResult(response, map);
	}
	/**
	 * 个人报表纪录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getMyRecordsChart.do")
	public void getMyRecordsChart(HttpServletRequest request,HttpServletResponse response,String id,
			int currentPage,int pageSize) {
		Map map = new HashMap();
		if(StringUtils.isEmpty(id)){
			map.put("flag", false);
		}
		map = recordService.queryMyRecordsChart(id, currentPage, pageSize);
		if(map == null || map.isEmpty()){
			map.put("flag", false);
		}else{
			map.put("flag", true);
		}
		outResult(response, map);
	}
	@RequestMapping(value = "/getEveryOne.do")
	public void getEveryOne(HttpServletRequest request,HttpServletResponse response) {
		Map map = new HashMap();
		map = recordService.queryEveryOne();
		if(map.isEmpty()){
			map.put("flag", false);
		}else{
			map.put("flag", true);
		}
		outResult(response, map);
	}
}
