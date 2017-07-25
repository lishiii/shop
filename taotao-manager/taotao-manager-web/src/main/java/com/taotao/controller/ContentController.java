package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUDataGridResult;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}
	
	@RequestMapping(value="/query/list")
	@ResponseBody
	public EUDataGridResult listContent(Long categoryId,Integer page,Integer rows) {
		EUDataGridResult result = contentService.listContent(categoryId,page,rows);
		return result;
	}
	
	
}
