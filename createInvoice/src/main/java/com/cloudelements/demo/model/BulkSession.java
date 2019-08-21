package com.cloudelements.demo.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class BulkSession {
	private String bulkId;
	private ArrayList<String> objectList;
	private ArrayList<ResourceField> fieldList;
	private BulkStatus bulkStatus;
}
