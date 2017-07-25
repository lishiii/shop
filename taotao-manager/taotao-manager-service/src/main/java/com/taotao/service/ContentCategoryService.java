package com.taotao.service;

import java.util.List;

import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUTreeNode;

public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(long parentId);
	TaotaoResult insertContentCategory(long parentId, String name);
}
