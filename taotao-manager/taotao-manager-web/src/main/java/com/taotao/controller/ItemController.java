package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUDataGridResult;

/**
 * 对象商品的查看 编辑 删除 进行处理
 * 
 * @author Ink4T
 * 
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveItem(TbItem item, String desc, String itemParams)
			throws Exception {
		TaotaoResult result = itemService.saveItem(item, desc, itemParams);
		return result;
	}

	/**
	 * 商品编辑 回显商品描述
	 */
//	@RequestMapping("/rest/item/query/item/desc/{itemId}")
//	@ResponseBody
//	public TaotaoResult getItemDesc(@PathVariable long itemId) {
//
//		TaotaoResult result = itemService.getItemDesc(itemId);
//		return result;
//	}

	/**
	 * 加载商品规格 /rest/item/param/item/query/
	 */
	// @RequestMapping("/rest/item/param/item/query/{itemId}")
	// @ResponseBody
	// public TaotaoResult getItemParam(@PathVariable long itemId) {
	// // TODO 添加service的操作
	// TaotaoResult result = null;
	// return result;
	// }
	//
	/**
	 * 更新商品信息
	 */
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public TaotaoResult updateItem(TbItem item,String desc, String itemParams) throws Exception{
		TaotaoResult result = itemService.updateItem(item, desc, itemParams);
		return result;
	}

}
