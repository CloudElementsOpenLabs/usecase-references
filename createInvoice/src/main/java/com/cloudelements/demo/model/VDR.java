package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class VDR {

	private String id, objectName, level, elementId, vendorName, startDate, isLegacy;
	private VDRField[] fields;
	
	@JsonIgnore
	private String script;
	
	private VDRConfiguration[] configuration;
	
	
	public VDRField getField (String key) {
		for (VDRField field : this.fields) {
			if (key.equals(field.getPath())) {
				return field;
			}
		}
		
		return null;
	}
}
