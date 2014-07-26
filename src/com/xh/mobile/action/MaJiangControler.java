package com.xh.mobile.action;


import java.util.HashMap;
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
	
	@RequestMapping(value = "/addRecord.do")
	public void addRecord(HttpServletRequest request,HttpServletResponse response,
			MajiangRecord record) {
		Map map = new HashMap();
		boolean flag = recordService.addRecord(record);
		System.out.println("dddd");
		map.put("flag", flag);
		outResult(response, map);
	}
	
}
