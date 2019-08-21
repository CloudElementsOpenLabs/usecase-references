package com.cloudelements.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JSONGetController {

	@RequestMapping (value = "/getJSON", produces = "application/json")
	public String getJSON () throws MalformedURLException, IOException {
			return "{\n" + 
					"	\"data\": {\n" + 
					"		\"dayparts\": [{\n" + 
					"				\"daypart_id\": 2,\n" + 
					"				\"daypart_name\": \"Regular Menu\",\n" + 
					"				\"availability\": [{\n" + 
					"						\"enabled\": true,\n" + 
					"						\"time_periods\": [{\n" + 
					"							\"from\": \"00:00\",\n" + 
					"							\"to\": \"23:59\"\n" + 
					"						}],\n" + 
					"						\"dow\": \"sunday\"\n" + 
					"					},\n" + 
					"					{\n" + 
					"						\"enabled\": true,\n" + 
					"						\"time_periods\": [{\n" + 
					"							\"from\": \"00:00\",\n" + 
					"							\"to\": \"23:59\"\n" + 
					"						}],\n" + 
					"						\"dow\": \"monday\"\n" + 
					"					}\n" + 
					"				],\n" + 
					"				\"categories\": [{\n" + 
					"					\"category_id\": 197,\n" + 
					"					\"default_name\": \"Burgers\",\n" + 
					"					\"products\": [{\n" + 
					"						\"external_data\": \"1234\",\n" + 
					"						\"price\": 3.95,\n" + 
					"						\"attributes\": [{\n" + 
					"							\"min_qty\": 1,\n" + 
					"							\"max_qty\": 1,\n" + 
					"							\"collapse\": false,\n" + 
					"							\"attribute_options\": [{\n" + 
					"								\"external_data\": \"PLU-4\",\n" + 
					"								\"price\": 0.0,\n" + 
					"								\"pd_id\": 4\n" + 
					"							}],\n" + 
					"							\"pd_id\": 2\n" + 
					"						}],\n" + 
					"						\"pd_id\": 3\n" + 
					"					}]\n" + 
					"				}],\n" + 
					"				\"product_data\": [{\n" + 
					"					\"pd_id\": 2\n" + 
					"				}],\n" + 
					"\n" + 
					"				\"name_default\": \"Choose side\"\n" + 
					"			},\n" + 
					"			{\n" + 
					"				\"pd_id\": 3,\n" + 
					"\n" + 
					"				\"name_default\": \"Product 2\",\n" + 
					"				\"image_url\": \"https://path-to-image-2.png\"\n" + 
					"			},\n" + 
					"			{\n" + 
					"				\"pd_id\": 4,\n" + 
					"				\"name_default\": \"Side 1\"\n" + 
					"			}\n" + 
					"		],\n" + 
					"		\"global_settings\": {\n" + 
					"			\"default_lang_id\": \"es_ES\",\n" + 
					"			\"currency_code\": \"€\"\n" + 
					"		}\n" + 
					"	},\n" + 
					"	\"resultCode\": 1\n" + 
					"}";
	}
	
	
}
