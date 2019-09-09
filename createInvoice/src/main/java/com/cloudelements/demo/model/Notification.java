package com.cloudelements.demo.model;

import lombok.Data;

@Data
public class Notification {
	private String elementKey, accountId, eventId, companyId, instanceId, instance_id, instanceName;
	private String[] instanceTags;
}
