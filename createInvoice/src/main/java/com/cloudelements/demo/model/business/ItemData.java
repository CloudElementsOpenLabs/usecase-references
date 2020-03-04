package com.cloudelements.demo.model.business;

import lombok.Data;

@Data
public class ItemData {
	private String description, erp_category_id, modifier_list_info, name;
	
	private ItemVariation[] variations;
}
