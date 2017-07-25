package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.TaotaoResult;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String query, int page) {
		// 调用taotoa-search服务
		// 查询参数
		Map<String, String> param = new HashMap<>();
		param.put("q", query);
		param.put("page", page + "");
		try {
			// 调用服务
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			// 把字符串转化为java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json,
					SearchResult.class);
			if (taotaoResult.getStatus() == 200) {
				SearchResult result = (SearchResult) taotaoResult.getData();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
