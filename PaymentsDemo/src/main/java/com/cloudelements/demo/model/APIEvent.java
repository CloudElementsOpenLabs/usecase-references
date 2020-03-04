package com.cloudelements.demo.model;

import lombok.Data;

@Data
public class APIEvent {
	private String date, elementKey, pollDate, eventType, hubKey, objectId, objectType, otherId;
}
