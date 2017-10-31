/**************************************************************************
 * Copyright (c) 2014 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - Chris Ellsworth
 * Project: Code Dx
 * SubSystem: com.secdec.codedx.util
 * FileName: JsonUtil.java
 *************************************************************************/
package com.codedx.util;;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

	/**
	 * Coverts a JSON string into a class object using jackson.databind.ObjectMapper
	 *
	 * @param jsonString a JSON object as a string to be converted into a class
	 * @param cls        the class type to convert the JSON string into, and for the method to return
	 * @return the Java class object holding the JSON data
	 */
	public static <T> T jsonStringToObject(String jsonString, Class<T> cls) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		object = mapper.readValue(jsonString, cls);
		return object;
	}

	/**
	 * Coverts a Java object into a JSON string using jackson.databind.ObjectMapper
	 *
	 * @param obj a Java object to be converted into a JSON string
	 * @return the JSON string
	 */
	public static String objectToJsonString(Object obj) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		jsonString = mapper.writeValueAsString(obj);
		return jsonString;
	}

}
