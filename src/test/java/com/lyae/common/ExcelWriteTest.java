package com.lyae.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class ExcelWriteTest {

	public static ArrayList<HashMap<String, String>>  mapTest(){
		HashMap<String,String> one = new HashMap<String,String>();
		HashMap<String,String> two = new HashMap<String,String>();
		HashMap<String,String> three = new HashMap<String,String>();
		ArrayList<HashMap<String, String>> listOne = new ArrayList<HashMap<String,String>>();
		one.put("model", "a");
		one.put("cnt", "1");
		one.put("month", "201601");
		two.put("model", "b");
		two.put("cnt", "2");
		two.put("month", "201601");
		three.put("model", "c");
		three.put("cnt", "3");
		three.put("month", "201601");
		listOne.add(one);
		listOne.add(two);
		listOne.add(three);
		System.out.println(listOne);
		
		HashMap<String,String> four = new HashMap<String,String>();
		HashMap<String,String> five = new HashMap<String,String>();
		HashMap<String,String> six = new HashMap<String,String>();
		ArrayList<HashMap<String, String>> listTwo = new ArrayList<HashMap<String,String>>();
		four.put("model", "b");
		four.put("cnt", "4");
		four.put("month", "201602");
		five.put("model", "c");
		five.put("cnt", "5");
		five.put("month", "201602");
		six.put("model", "d");
		six.put("cnt", "6");
		six.put("month", "201602");
		listTwo.add(four);
		listTwo.add(five);
		listTwo.add(six);
		System.out.println(listTwo);
		
		HashMap<String,String> seven = new HashMap<String,String>();
		HashMap<String,String> eight = new HashMap<String,String>();
		HashMap<String,String> nine = new HashMap<String,String>();
		ArrayList<HashMap<String, String>> listThree = new ArrayList<HashMap<String,String>>();
		seven.put("model", "c");
		seven.put("cnt", "7");
		seven.put("month", "201603");
		eight.put("model", "d");
		eight.put("cnt", "8");
		eight.put("month", "201603");
		nine.put("model", "e");
		nine.put("cnt", "9");
		nine.put("month", "201603");
		listThree.add(seven);
		listThree.add(eight);
		listThree.add(nine);
		System.out.println(listThree);
		
		
		ArrayList<HashMap<String, String>> listadd = new ArrayList<HashMap<String,String>>();
		listadd.addAll(listOne);
		listadd.addAll(listTwo);
		listadd.addAll(listThree);
		System.out.println("listadd : " + listadd);
		
		ArrayList<ArrayList<HashMap<String, String>>> allStat = new ArrayList<ArrayList<HashMap<String, String>>>();
		allStat.add(listOne);
		allStat.add(listTwo);
		allStat.add(listThree);
		
		System.out.println("allStat " + allStat);
		
		ArrayList<HashMap<String, String>> all = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> allMap;
		
		for(HashMap<String,String> listNode : listadd){
			String model = listNode.get("model");
			String month = listNode.get("month");
			String cnt = listNode.get("cnt");
			
			//모델 유무 확인하기 위함
			boolean addYn = true;
			for (HashMap<String,String> workAllMap: all){
				if(workAllMap.containsValue(model)){
					workAllMap.put(month, cnt);
					addYn = false;
				}
			}
			//기존에 없는 모델 일 경우 추가
			if(addYn){
				allMap = new HashMap<String,String>();
				allMap.put("model", model);
				allMap.put(month, cnt);
				all.add(allMap);
			}
		}	
		
		/*
		for(ArrayList<HashMap<String,String>> listMonth : allStat){
			for(HashMap<String,String> listNode : listMonth){
				String model = listNode.get("model");
				String month = listNode.get("month");
				String cnt = listNode.get("cnt");
				
				//모델 유무 확인하기 위함
				boolean addYn = true;
				for (HashMap<String,String> workAllMap: all){
					if(workAllMap.containsValue(model)){
						workAllMap.put(month, cnt);
						addYn = false;
					}
				}
				//기존에 없는 모델 일 경우 추가
				if(addYn){
					allMap = new HashMap<String,String>();
					allMap.put("model", model);
					allMap.put(month, cnt);
					all.add(allMap);
				}
			}	
		}
		*/
		System.out.println("all : " + all);
		return all;
	}
	
	public static void statExcel(ArrayList<HashMap<String, String>> paramList) throws IOException{
		
		ArrayList<String> columnList = new ArrayList<String>();
		
		columnList.add("201601");
		columnList.add("201602");
		columnList.add("201603");
		
		//1차로 workbook을 생성
		XSSFWorkbook workbook=new XSSFWorkbook();
		//2차는 sheet생성
		XSSFSheet sheet=workbook.createSheet("시트명");
		//엑셀의 행
		XSSFRow row=null;
		//엑셀의 셀
		XSSFCell cell=null;
		
		//TITLE 작성
		row = sheet.createRow(0);
		if(columnList !=null &&columnList.size() >0){
	            for(int j=0;j<columnList.size();j++){
	                //생성된 row에 컬럼을 생성한다
	                cell=row.createCell(j+1);
	                //map에 담긴 데이터를 가져와 cell에 add한다
	                cell.setCellValue(String.valueOf(columnList.get(j)));
	            }
	    }
				
		//임의의 DB데이터 조회
		if(paramList !=null &&paramList.size() >0){
	    	int i=1;
		    for(HashMap<String,String>mapobject : paramList){
		        // 시트에 하나의 행을 생성한다(i 값이 0이면 첫번째 줄에 해당)
		        row=sheet.createRow((short)i);
		        i++;
		        cell=row.createCell(0);
		        cell.setCellValue(String.valueOf(mapobject.get("model")));
		        
		        for(int j=0;j<columnList.size();j++){
		        	//생성된 row에 컬럼을 생성한다
	                cell=row.createCell(j+1);
	                //map에 담긴 데이터를 가져와 cell에 add한다
	                if (mapobject.get(columnList.get(j)) != null ){
	                cell.setCellValue(String.valueOf(mapobject.get(columnList.get(j))));
	                }
	            }
		    }
		}
		File file = new File("C:\\Users\\lyaes\\Documents\\poiTest.xlsx");
		if ( !file.exists() ){
			file.createNewFile();
		}
		
		FileOutputStream fileoutputstream=new FileOutputStream(file);
		//파일을 쓴다
		workbook.write(fileoutputstream);
		//필수로 닫아주어야함
		workbook.close();
		fileoutputstream.close();
		System.out.println("엑셀파일생성성공");

	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 ArrayList<HashMap<String, String>> paramList;
		 paramList = mapTest();
		 statExcel(paramList);
		 
	}

}
