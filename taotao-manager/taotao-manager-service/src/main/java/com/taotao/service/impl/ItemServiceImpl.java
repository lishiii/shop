package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemDescExample.Criteria;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import com.taotao.util.IDUtils;
import com.taotao.util.TaotaoResult;
import com.taotao.util.pojo.EUDataGridResult;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;

	/**
	 * 查询所有商品信息
	 */
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		TbItemExample itemExample = new TbItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		// 执行查询条件
		List<TbItem> list = tbItemMapper.selectByExample(itemExample);
		// 将得到的结果封装到PageInfo中去
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EUDataGridResult result = new EUDataGridResult();
		// 通过pageInfo的到总记录数
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	/**
	 * 添加商品信息，以及商品描述
	 */
	@Override
	public TaotaoResult saveItem(TbItem item, String desc, String itemParam)
			throws Exception {
		// item内容补全
		// 生产商品ID
		Long itemId = IDUtils.getItemId();
		item.setId(itemId);
		// 设置 商品的状态status
		item.setStatus((byte) 1);
		// 设置商品的创建时间和更新时间
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 插入到数据库中
		tbItemMapper.insert(item);
		TaotaoResult result = saveItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		result = saveItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}

	/**
	 * 商品添加
	 * 
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private TaotaoResult saveItemDesc(long itemId, String desc) {
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(tbItemDesc);
		return TaotaoResult.ok();
	}

	/**
	 * 商品规格参数添加
	 * 
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult saveItemParamItem(long itemId, String itemParam) {

		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		tbItemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getItemDesc(long itemId) {
		TbItemDescExample example = new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = tbItemDescMapper
				.selectByExampleWithBLOBs(example);
		String itemDesc = null;
		TaotaoResult msg = null;
		for (TbItemDesc tbItemDesc : list) {
			itemDesc = tbItemDesc.getItemDesc();
		}
		if (itemDesc != null && itemDesc.length() >= 0) {
			msg = TaotaoResult.ok(itemDesc);
		} else {
			msg = TaotaoResult.build(404, "not found");
		}
		return msg;
	}

	@Override
	public TaotaoResult updateItem(TbItem item, String desc, String itemParams)
			throws Exception {
		// 设置商品的更新时间
		item.setUpdated(new Date());
		// 插入到数据库中
		int updateByExample = tbItemMapper.updateByPrimaryKey(item);
		if (updateByExample < 0) {
			throw new Exception();
		}
		TaotaoResult result = null;
		result = updateItemDesc(item.getId(), desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		result = updateItemParamItem(item.getId(), itemParams);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}

	/**
	 * 更新描述信息
	 * 
	 * @param itemId
	 * @param desc
	 * @return
	 */
	private TaotaoResult updateItemDesc(long itemId, String desc) {
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemDesc(desc);
		tbItemDescMapper.updateByPrimaryKeyWithBLOBs(tbItemDesc);
		return TaotaoResult.ok();
	}

	private TaotaoResult updateItemParamItem(long itemId, String itemParam) {
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setUpdated(new Date());
		tbItemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);
		return TaotaoResult.ok();
	}
}
