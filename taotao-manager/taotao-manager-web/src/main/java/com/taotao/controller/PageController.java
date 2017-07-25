package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	/**
	 * 主页面展示
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	/**
	 * 其他页面展示
	 * 
	 * @param page
	 *            页面名称
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showpage(@PathVariable String page) {
		return page;
	}
}
