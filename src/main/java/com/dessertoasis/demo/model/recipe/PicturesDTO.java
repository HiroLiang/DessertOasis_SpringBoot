package com.dessertoasis.demo.model.recipe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PicturesDTO {
	private String fileName;
	private String base64Content;
	
	public PicturesDTO() {
		super();
	}
	
	
	public PicturesDTO(String json) throws JsonMappingException, JsonProcessingException {
		super();
		ObjectMapper om = new ObjectMapper();
		JsonNode readTree = om.readTree(json);
		this.fileName = readTree.get("fileName").asText();
		this.base64Content = readTree.get("base64Content").asText();
	}


	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBase64Content() {
		return base64Content;
	}
	public void setBase64Content(String base64Content) {
		this.base64Content = base64Content;
	}
	
	
}
