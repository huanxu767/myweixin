package com.xh.mobile.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		boolean flag = recordService.addRecord(record);
		System.out.println("dddd");
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
}
