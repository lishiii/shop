package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import com.taotao.util.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public List<TbContent> getContentList(long contentCid) {
		// 从redis缓存中取
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY,
					contentCid + "");
			if (!StringUtils.isBlank(result)) {
				// 把字符串转换为list
				List<TbContent> resultList = JsonUtils.jsonToList(result,
						TbContent.class);
				return resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		// 执行查询
		List<TbContent> list = contentMapper.selectByExample(example);

		// 向redis中添加内容
		try {
			// 先把list转化为字符串
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset("INDEX_CONTENT_REDIS_KEY", contentCid + "",
					cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
