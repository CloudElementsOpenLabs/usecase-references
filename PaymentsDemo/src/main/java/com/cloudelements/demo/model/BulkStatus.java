
package com.cloudelements.demo.model;

import lombok.Data;

@Data
public class BulkStatus {
	public String recordsCount, metadata, 
	recordsFailedCount, instanceId, 
	vendorJobId, 
	fileSize, object_name, 
	job_direction, id, 
	bulk_start_time, fileFormat, 
	status, infoMessage,bulk_finish_time, requestId;
	
	public String message, error;
}
