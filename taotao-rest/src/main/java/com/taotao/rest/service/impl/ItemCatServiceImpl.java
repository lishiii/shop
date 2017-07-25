package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.util.JsonUtils;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_ITEM_CAT_REDIS_KEY}")
	private String INDEX_ITEM_CAT_REDIS_KEY;

	@Override
	public CatResult getItemCatList() {
		// 读取缓冲中的信息
		try {
			String result = jedisClient.get(INDEX_ITEM_CAT_REDIS_KEY);
			if (!StringUtils.isBlank(result)) {
				// 将数据转化为对象
				CatResult resultPojo = JsonUtils.jsonToPojo(result,
						CatResult.class);
				return resultPojo;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));

		// 将信息写入缓冲中
		try {
			// 将数据转化json格式
			String resultJson = JsonUtils.objectToJson(catResult);
			jedisClient.set(INDEX_ITEM_CAT_REDIS_KEY, resultJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return catResult;
	}

	/**
	 * 查询分类列表
	 * 
	 * @param parentId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<?> getCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 返回值list
		List resultList = new ArrayList<>();
		// 向list中添加节点,显示需要的结果数
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			if (tbItemCat.getIsParent()) {
				CatNode node = new CatNode();
				if (parentId == 0) {
					node.setName("<a href='/products/" + tbItemCat.getId()
							+ ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					node.setName(tbItemCat.getName());
				}
				node.setUrl("/products/" + tbItemCat.getId() + ".html");
				// 递归
				node.setItem(getCatList(tbItemCat.getId()));
				resultList.add(node);
				count++;
				// 当遍历个数为14，停止遍历
				if (parentId == 0 && count >= 14) {
					break;
				}
			} else {
				resultList.add("/products/" + tbItemCat.getId() + ".html|"
						+ tbItemCat.getName());
			}

		}

		return resultList;

	}
}
