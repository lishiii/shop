package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.service.ItemParamItemService;

@Controller
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;

	/**
	 * 根据商品id查询规格参数
	 * 
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("itemList/{itemId}")
	public String getItemParamByItemId(@PathVariable long itemId, Model model) {
		String result = itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("itemParam", result);
		return "item";
	}
}
