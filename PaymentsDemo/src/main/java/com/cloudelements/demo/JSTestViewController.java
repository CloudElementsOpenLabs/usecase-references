package com.cloudelements.demo;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudelements.demo.model.Element;
import com.cloudelements.demo.util.AuthenticationUtil;
import com.cloudelements.demo.util.HTTPUtil;
import com.fasterxml.jackson.annotation.JsonView;


@Controller
public class JSTestViewController {

	@RequestMapping ("/js")
	public String handleBasicAuthentication (Element el, HashMap<String, String> configMap) {
		return "jstest/jstest";
	}
	
	
}
