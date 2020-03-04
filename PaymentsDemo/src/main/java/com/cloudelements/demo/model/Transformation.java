package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Transformation implements Comparable<Transformation> {

	private String id, objectName, vendorName, startDate, level, elementKey, elementId;
	
	private TransformationField[] fields;
	  
	@JsonIgnore
	private String isLegacy, configuration, script;
	
	@Override
	public int compareTo(Transformation o) {
		return this.objectName.compareTo(o.getObjectName());
	}
	
}
