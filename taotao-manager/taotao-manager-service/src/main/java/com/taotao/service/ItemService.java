package com.taotao.service;

import com.taotao.pojo.TbItem;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUDataGridResult;

public interface ItemService {
	EUDataGridResult getItemList(int page, int rows);

	TaotaoResult saveItem(TbItem item, String desc, String itemParam)
			throws Exception;

	TaotaoResult getItemDesc(long itemId);

	TaotaoResult updateItem(TbItem item, String desc, String itemParams)
			throws Exception;
}
