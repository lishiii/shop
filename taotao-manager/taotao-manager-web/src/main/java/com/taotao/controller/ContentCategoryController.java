package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.ContentCategoryService;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUTreeNode;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(
			@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EUTreeNode> list = contentCategoryService
				.getCategoryList(parentId);
		return list;
	}

	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(
				parentId, name);
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long parentId, Long id) {
		// TODO 服务层删除功能实现
		return null;
	}

	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long parentId, String name) {
		// TODO 服务层更新名称功能实现
		return null;
	}
}
