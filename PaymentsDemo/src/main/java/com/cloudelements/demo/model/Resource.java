package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Resource {

	// Fields based on
	// https://staging.cloud-elements.com/elements/api-v2/hubs/helpdesk/objects/tickets/metadata
	private ResourceField[] fields;
	
	@JsonIgnore
	private String requestId, message, providerMessage;
}
