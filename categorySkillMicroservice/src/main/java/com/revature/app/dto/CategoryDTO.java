package com.revature.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryDTO {

	private String categoryName;
	private String categoryDescription;
	private int userid;
	
}
