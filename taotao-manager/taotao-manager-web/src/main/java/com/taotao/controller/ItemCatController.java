package com.taotao.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemCat;
import com.taotao.service.ItemCatService;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List categoryList(
			@RequestParam(value = "id", defaultValue = "0") long parentId) {
		List catList = new ArrayList();
		// 调用service获取数据
		List<TbItemCat> itemCatList = itemCatService.getItemCatList(parentId);
		for (TbItemCat itemCat : itemCatList) {
			// 创建一个node节点
			HashMap<Object, Object> node = new HashMap<>();
			node.put("id", itemCat.getId());
			node.put("text", itemCat.getName());
			node.put("state", itemCat.getIsParent() ? "closed" : "open");
			// 将节点添加入list集合中
			catList.add(node);
		}
		return catList;

	}

}
