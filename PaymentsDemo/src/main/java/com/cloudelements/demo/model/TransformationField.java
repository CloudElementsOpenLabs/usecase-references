package com.cloudelements.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class TransformationField implements Comparable<TransformationField> {

	private String type, path,level, vendorPath, vendorType, id, vdrFieldId;
	
	@JsonIgnore
	private String configuration;

	@Override
	public int compareTo(TransformationField o) {
		return this.path.compareTo(o.getPath());
	}
	
}
