package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.taotao.pojo.TbUser;
import com.taotao.util.TaotaoResult;

public interface UserService {
	TaotaoResult checkData(String content, Integer type);
	TaotaoResult createUser(TbUser user);
	TaotaoResult userLogin(String username, String password,HttpServletRequest request, HttpServletResponse response);
	TaotaoResult getUserByToken(String token);
	TaotaoResult logoutUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session);
}
