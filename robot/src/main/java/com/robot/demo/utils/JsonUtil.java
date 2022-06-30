package com.robot.demo.utils;


import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

public class JsonUtil {

    public static ObjectMapper om = new ObjectMapper();
    static {
    	om.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;  
    	om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    /** 
     * json化
      * 
     * @param data
     * @return
     * @throws IOException
      */
    public static String object2String(Object data) throws IOException {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().setFailOnUnknownId(false);
        om.setFilters(filterProvider);
        return om.writeValueAsString(data);
    }

//    public static List<LinkedHashMap<String, String>> string2ListMap(String json) throws IOException {
//        return om.readValue(json, new TypeReference<List<LinkedHashMap<String, String>>> () {
//        });
//    }
    public static <T> List<T> string2ListMap(String json) throws IOException {
        return om.readValue(json, new TypeReference<List<T>> () {
        });
    }
    public static <T> List<T> string2ListObject(String json,Class<T> clazz) throws IOException {
        JavaType type = getCollectionType(ArrayList.class, clazz);
        return om.readValue(json, type);
    }
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
    	return om.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
    }   
    public static JavaType getMapCollectionType( Class<?> elementClasses) {   
        return om.getTypeFactory().constructParametricType(HashMap.class, String.class, elementClasses);   
    }
    public static <T>  Map<String,T> string2Map(String json,Class<T> clazz) throws IOException {
        JavaType type = getMapCollectionType(clazz);
        return om.readValue(json, type);
    }
    public static <T> T string2Obj(String json, Class<T> clazz) throws IOException {
        json = json.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
    	json = URLDecoder.decode(json, "utf-8").replaceAll("\\\\(?!\")", "\\\\\\\\");
        return om.readValue(json, clazz);
    }
    public static <T> T string2Obj(String json) throws IOException {
        json = json.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
    	json = URLDecoder.decode(json, "utf-8").replaceAll("\\\\(?!\")", "\\\\\\\\");
        return om.readValue(json, new TypeReference<T>() {
        });
    }
//    public static void main(String[] args) throws UnsupportedEncodingException {
//		String body = "%7B%22monitorData%22%3A%22%7B%5C%22id%5C%22%3A823910001628654869%2C%5C%22machineId%5C%22%3A82391000%2C%5C%22userId%5C%22%3Anull%2C%5C%22shotCounter%5C%22%3A1%2C%5C%22cycleTime%5C%22%3A16.0%2C%5C%22moldCloseTime%5C%22%3A0%2C%5C%22injHoldTime%5C%22%3A0%2C%5C%22workTime%5C%22%3A1628654885000%2C%5C%22recordDate%5C%22%3A1628654869000%2C%5C%22statDate%5C%22%3Anull%2C%5C%22companyId%5C%22%3Anull%2C%5C%22serverTime%5C%22%3A1628654877861%2C%5C%22data%5C%22%3A%5C%22%7B%5C%5C%5C%22StartTime%5C%5C%5C%22%3A%5C%5C%5C%222021-8-11+12%3A7%3A49%5C%5C%5C%22%2C%5C%5C%5C%22Duration%5C%5C%5C%22%3A%5C%5C%5C%22160%5C%5C%5C%22%2C%5C%5C%5C%22Number%5C%5C%5C%22%3A%5C%5C%5C%221%5C%5C%5C%22%7D%5C%22%7D%22%7D";
//		System.out.println(body.startsWith("%7B"));
//		System.out.println(URLDecoder.decode(body, "utf-8"));
//	}
    public static List<String> string2ListString(String json) throws IOException {
        return om.readValue(json, new TypeReference<List<String>>() {
        });
    }
    public static List<String> getListStrFromJson(String json, String key) throws IOException {
        if (json.contains(key)) {
            List<LinkedHashMap<String, String>> temp = string2ListMap(json);
            List<String> ids = new ArrayList<String> ();
            for (LinkedHashMap< String, String > t : temp) {
                ids.add(t.get(key));
            }
            return ids;
        } else {
            return string2ListString(json);
        }
    }
}
