package com.mexaco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

	@RequestMapping(method = RequestMethod.GET)
	public String asdf123123() {
		return "Love you babe!";
	}
	
	@RequestMapping(value = "/test/", method = RequestMethod.GET)
	public String asdf() {
		return "Love you babe!";
	}
	
	
	@RequestMapping(value = "/testosterone/", method = RequestMethod.GET)
	public String basdfasdf() {
		return "Hi Joane!!!";
	}

}
