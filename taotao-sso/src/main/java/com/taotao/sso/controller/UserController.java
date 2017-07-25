package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.util.ExceptionUtil;
import com.taotao.util.TaotaoResult;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 
	 * 用户注册前对username、phone、email是否存在进行校验
	 * 
	 * @param param
	 *            校验内容
	 * @param type
	 *            类型，可选参数1、2、3分别代表username、phone、email
	 * @param callback
	 *            jsonp回调函数
	 * @return
	 */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,
			@PathVariable Integer type, String callback) {
		TaotaoResult result = null;

		// 参数有效性校验
		if (StringUtils.isBlank(param)) {
			result = TaotaoResult.build(400, "校验内容不能为空");
		}
		if (type == null) {
			result = TaotaoResult.build(400, "校验内容类型不能为空");
		}
		if (type != 1 && type != 2 && type != 3) {
			result = TaotaoResult.build(400, "校验内容类型错误");
		}

		// 校验出错，返回Jsonp结果
		if (null != result) {
			if (null != callback) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
						result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			} else {
				return result;
			}
		}

		// 校验成功，调用服务
		try {
			result = userService.checkData(param, type);

		} catch (Exception e) {
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		if (null != callback) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

	/**
	 * 用户注册接口
	 * 
	 * @param userusername
	 *            //用户名 password //密码 phone //手机号 email //邮箱 参数。
	 *            接收参数调用mapper向user表中添加记录。
	 * @return 返回taotaoResult对象。如果成功200失败400异常500.
	 */
	@RequestMapping("/register")
	public TaotaoResult createUser(TbUser user) {
		try {
			TaotaoResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	// 用户登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult userLogin(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			TaotaoResult result = userService.userLogin(username, password,
					request, response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	/**
	 * 根据token查看用户登录是否过期
	 * 
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		// 判断是否为jsonp调用
		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}

	}

	/**
	 * 安全退出
	 */

	@RequestMapping("/logout")
	@ResponseBody
	public Object logoutUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		TaotaoResult result = null;
		try {
			result = userService.logoutUser(request,response,session);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return result;
	}

}
