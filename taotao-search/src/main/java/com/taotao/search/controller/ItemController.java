package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.search.service.ItemsService;
import com.taotao.util.TaotaoResult;

@Controller
@RequestMapping("/manager")
public class ItemController {

	@Autowired
	private ItemsService itemService;

	/**
	 * 导入商品数据到索引库
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importAllItems() {
		TaotaoResult result = itemService.importAllItems();
		return result;
	}
}