package com.cloudelements.demo.util;

import org.springframework.stereotype.Service;

import lombok.Data;


/*
 * Acts as a singleton throughout the entire app.
 * Upon element selection, the element String is set
 * Upon retrieval of the token response via the exposed /authListener webhook, the sessionToken is set 
 */
@Service
@Data
public class CustomSessionTokenService {
	public String token = "";
	public String element = "";
}
