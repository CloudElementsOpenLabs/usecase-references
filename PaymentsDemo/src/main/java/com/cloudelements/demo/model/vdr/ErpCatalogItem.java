package com.cloudelements.demo.model.vdr;

import com.cloudelements.demo.model.business.ItemData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ErpCatalogItem {

	private String erp_item_id, present_at_all_locations, 
	type, vatLiable, sku, active;
	private String[] present_at_location_ids, catalog_v1_ids, absent_at_location_ids, modifier_list_info;
	
	private ItemData item_data;
	
}
