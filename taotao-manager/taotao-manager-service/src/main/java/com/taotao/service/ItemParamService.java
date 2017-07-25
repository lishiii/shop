package com.taotao.service;

import com.taotao.pojo.TbItemParam;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUDataGridResult;

public interface ItemParamService {
	EUDataGridResult getItemParamList(Integer page,Integer rows);
	TaotaoResult insertItemParam(TbItemParam itemParam);
	TaotaoResult getItemParamByCid(long cid);
}
