package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Instance {

	private String id, name, createdDate, token,type, elementId;
	
	@JsonIgnore
	private String element, tags, provisionInteractions, valid, disabled, maxCacheSize, cacheTimeToLive, configuration, eventsEnabled, traceLoggingEnabled, cachingEnabled, externalAuthentication, user;
	// notice that type is manually set in InstanceService based by the 'instance name' json property
	
}
