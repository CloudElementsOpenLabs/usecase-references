package com.cloudelements.demo.model.business;

import lombok.Data;

@Data
public class ItemVariation {
	private String type, present_at_all_locations;
	
	private String[] present_at_location_ids, catalog_v1_ids, absent_at_location_ids;
	
	private ItemVariationData item_variation_data;
}
