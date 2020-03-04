package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ResourceField {

	// Fields based on
	// https://staging.cloud-elements.com/elements/api-v2/hubs/helpdesk/objects/tickets/metadata
	private String type, path, vendorPath, displayName, vendorDisplayName, vendorNativeType, vendorRequired, vendorReadOnly, mask, createable, updateable;
	
	@JsonIgnore
	private String[] method;
	
	@JsonIgnore
	private String[] choices;
	
	
	@JsonIgnore
	private String picklistValues,filterable, restrictedPicklist;
}
