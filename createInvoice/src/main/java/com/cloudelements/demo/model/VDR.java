package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class VDR implements Comparable<VDR> {

	private String id, objectName, level, elementId, vendorName, startDate, isLegacy, clonedFrom, description;

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


	@Override
	public int compareTo(VDR o) {
		return this.objectName.compareTo(o.objectName);
	}
}
