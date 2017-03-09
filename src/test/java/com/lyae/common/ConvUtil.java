package com.lyae.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;

@Log4j
public class ConvUtil {
	
	final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	
	/** 익셉션을 자세한 스트링으로 치환합니다. */
	public static String toString(Exception e) {
		return toString(null, e);
	}
	
	public static String toString(String preMessage, Exception e) {
		StringBuilder sb = new StringBuilder();
		if (preMessage != null) {
			sb.append(preMessage).append('\n');
		}
		sb.append(e.toString()).append('\n');
		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append(ste).append('\n');
		}
		return sb.toString();
	}
	
	/** jsonNode 필드 불러오기 */
	public static String toString(JsonNode jsonNode, String key) {
		return jsonNode.get(key).asText();
	}
	
	/** jsonNode 필드 불러오기 */
	public static String toString(JsonNode jsonNode, String key, String defualtValue, boolean includeEmpty) {
		JsonNode inNode = jsonNode.get(key);
		if (inNode != null) {
			String value = inNode.asText();
			if (value != null && (!includeEmpty || !value.trim().isEmpty())) {
				return value;
			}
		}
		return defualtValue;
	}
	
	public static String toString(Map<String, String> map) {
		StringBuilder sb = new StringBuilder(1024);
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(", ").append(entry.getKey()).append(" : ").append(entry.getValue());
		}
		return map.size() > 0 ? sb.substring(2) : "";
	}
	
	public static String toParamString(Map<String, String> map) {
		try {
			boolean isAnd = false;
			StringBuilder sb = new StringBuilder(1024);
			
			for (Entry<String, String> entry : map.entrySet()) {
				if (isAnd) { sb.append('&'); } else { isAnd = true; }
				sb.append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			}
			
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			// 사실 일어날 수 없는 오류이다.
			log.error(toString("변환중 오류 발생 : ", e));
		}
		return null;
	}
	
	/** getParameterMap를 값이 1개인 맵으로 변경한다. */
	public static Map<String, String> toParamMap(HttpServletRequest req) {
		Map<String, String[]> map = req.getParameterMap();
		Map<String, String> result = new HashMap<String, String>();
		
		for (Entry<String, String[]> entry : map.entrySet()) {
			String[] val = entry.getValue();
			if (val != null && val.length > 0) {
				result.put(entry.getKey(), val[0]);
			}
		}
		
		return result;
	}
	
	public static String toJsonObjectByClass(Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	public static Object toClassByJsonObject(Class clazz, String json) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, clazz);
	}
	
	public static Map<String, String> toMapByJsonObject(String json) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(json, HashMap.class);
	}
	
	/** 컨버팅 : yyyy-MM-dd HH:mm:ss */
	public static String toY_M_D_H_M_S(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/** 컨버팅 : yyyyMMddHHmmss */
	public static String toYMDHMS(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	/** 컨버팅 : yyyyMMddHHmmss */
	public static String toYMD(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	/** 컨버팅 : yyyyMMddHHmmss */
	public static String toHMS(Date date) {
		return new SimpleDateFormat("HHmmss").format(date);
	}
	
	/** 현재 시간으로부터 밀리세컨을 계산하여 Date를 반환한다. */
	public static Date addTime(long time) {
		return new Date(System.currentTimeMillis() + time);
	}
	
	/** 현재 시간으로부터 날짜를 계산하여 Date를 반환한다. */
	public static Date addDay(long day) {
		return new Date(System.currentTimeMillis() + (day * 86400000L));
	}
	
	/** 숫자 0을 채운 스트링 만들기 */
	public static String toStringZeroFill(int unit, int val) {
		return String.format("%0"+unit+"d", val);
	}
	
	/** hex 스트링을 만든다. */
	public static String toHexString(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	/** 바이트를 만든다 */
	public static byte[] toBytesByHexString(String hex) {
		return new BigInteger(hex, 16).toByteArray();
	}
	
	
//	
//	
//	
//	@Test
//	public void test() {
//		try {
//			RequestMap aaa = new RequestMap();
//			RequestMap bbb = new RequestMap();
//			aaa.put("aaa", "1dgfdgdfg");
//			aaa.put("aaa32", "132223sdfsdaf1");
//			aaa.put("aaa132", "12sadfsadf232");
//			System.out.println("준비");
//			String json = toJsonObjectByClass(aaa);
//			System.out.println(json);
//			bbb = (RequestMap)toClassByJsonObject(RequestMap.class, json);
//			System.out.println(bbb.get("aaa32"));
//		}
//		catch (Exception e) {
//			System.out.println(toString(e));
//		}
//	}
}
