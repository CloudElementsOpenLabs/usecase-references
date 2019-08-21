package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Element {

	private String id, name, key, description, image, logo, active, deleted, typeOauth, trialAccount;
	
	private ElementConfiguration[] configuration;
	
	private String Private; // nasty hack as this is a reserved keyword in Java but seems to work by capitalizing it for the mapper
	
	
	@JsonIgnore
	private String
	transformationsEnabled,
    bulkDownloadEnabled,
    bulkUploadEnabled,
    cloneable,
    extendable,
    beta,
    authentication,
    extended,
    useModelsForMetadata,
    hub,
    protocolType, hookName, configDescription, defaultTransformations,signupURL, objectMetadata, existingAccountDescription, 
    requestId, message, modelType;
	
	@JsonIgnore
	private String objects;
	@JsonIgnore
	private String models;
	@JsonIgnore
	private String hooks;
	@JsonIgnore
	private String parameters, resources;
	
	public ElementConfiguration getConfig (String key) {
		for (ElementConfiguration config : configuration) {
			if (config.getKey().equals(key)) {
				return config;
			}
		}
		return null;
	}
}
