package com.taotao.service;

import com.taotao.pojo.TbContent;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUDataGridResult;

public interface ContentService {
	TaotaoResult insertContent(TbContent content);
	EUDataGridResult listContent(Long categoryId, Integer page, Integer rows);
}
