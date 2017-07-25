package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUDataGridResult;
import com.taotao.util.pojo.ItemParamListResult;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public EUDataGridResult getItemParamList(Integer page, Integer rows) {
		List<ItemParamListResult> listResults = new ArrayList<>();
		TbItemParamExample example = new TbItemParamExample();
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = tbItemParamMapper
				.selectByExampleWithBLOBs(example);
		for (TbItemParam tbItemParam : list) {
			ItemParamListResult results = new ItemParamListResult();
			Long itemCatId = tbItemParam.getItemCatId();
			// 根据ID查询tb_item_cat表中的信息
			TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(itemCatId);
			results.setItemCatName(tbItemCat.getName());
			results.setId(tbItemParam.getId());
			results.setItemCatId(tbItemParam.getItemCatId());
			results.setParamData(tbItemParam.getParamData());
			results.setCreated(tbItemParam.getCreated());
			results.setUpdated(tbItemParam.getUpdated());
			listResults.add(results);
		}
		PageInfo<ItemParamListResult> pageInfo = new PageInfo<>(listResults);
		EUDataGridResult result = new EUDataGridResult();
		// 通过pageInfo的到总记录数
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		// 补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		// 插入到规格参数模板表中
		tbItemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = tbItemParamMapper
				.selectByExampleWithBLOBs(example);
		// 判断是否查询到结果
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}

}
