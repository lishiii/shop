package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.util.JsonUtils;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping(value = "/itemcat/all", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) {
		CatResult itemCatList = itemCatService.getItemCatList();
		String json = JsonUtils.objectToJson(itemCatList);
		// 拼装返回值
		String result = callback + "(" + json + ");";
		return result;
	}
}
