package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class VDRConfiguration {

	private String type;

	@JsonIgnore
	private String properties;
}
