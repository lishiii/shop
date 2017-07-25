package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUDataGridResult;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;

	/**
	 * 规格参数列表展示
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getItemParamList(Integer page, Integer rows) {
		EUDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}

	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}

	/**
	 * 保存参数数据
	 * 
	 * @param cid
	 * @param paramData
	 * @return
	 */
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable long cid, String paramData) {
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		TaotaoResult result = itemParamService.insertItemParam(itemParam);
		return result;

	}

}
