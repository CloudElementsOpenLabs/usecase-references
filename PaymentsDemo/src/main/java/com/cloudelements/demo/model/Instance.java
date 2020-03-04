package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Instance implements Comparable<Instance> {

	private String id, name, createdDate, token,type, elementId;
	
	@JsonIgnore
	private String element, tags, provisionInteractions, valid, disabled, maxCacheSize, cacheTimeToLive, configuration, eventsEnabled, traceLoggingEnabled, cachingEnabled, externalAuthentication, user;
	// notice that type is manually set in InstanceService based by the 'instance name' json property

	@Override
	public int compareTo(Instance o) {
		return this.element.compareTo(o.getElement()) +
				this.id.compareTo(o.getId());
	}
	
}
