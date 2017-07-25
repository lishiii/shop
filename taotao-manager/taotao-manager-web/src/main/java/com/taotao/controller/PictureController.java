package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictureService;
import com.taotao.util.JsonUtils;

@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) throws Exception {
		// 调用service上传图片
		Map result = pictureService.uploadPicture(uploadFile);
		// 为了保证浏览器的兼容性，需要把result转化为json格式
		String json = JsonUtils.objectToJson(result);
		return json;
	}
}
