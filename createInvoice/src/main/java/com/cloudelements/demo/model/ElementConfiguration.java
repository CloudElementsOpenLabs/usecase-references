package com.cloudelements.demo.model;

import lombok.Data;

@Data
public class ElementConfiguration {

	private String id,
    name,
    key,
    description,
    defaultValue,
    resellerConfig,
    companyConfig,
    active,
    internal,
    groupControl,
    displayOrder,
    type,
    hideFromConsole,
    required, groupName, obj;
	
	private ElementConfigurationOption[] options ;
	
}
