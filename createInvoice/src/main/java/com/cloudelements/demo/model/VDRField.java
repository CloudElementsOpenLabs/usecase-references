package com.cloudelements.demo.model;

import lombok.Data;

@Data
public class VDRField implements Comparable<VDRField> {

	private String type, path,level, vendorPath, vendorType, displayName, id;

	@Override
	public int compareTo(VDRField o) {
		return this.path.compareTo(o.getPath());
	}

	
}
