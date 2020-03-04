package com.cloudelements.demo.model.business;

import lombok.Data;

@Data
public class ItemVariationData {
	private String name, pricing_type, track_inventory, present_at_all_locations;
	
	private String[] present_at_location_ids;
	
	
	private LocationOverride[] location_overrides; 
}
