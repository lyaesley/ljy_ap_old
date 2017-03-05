package com.lyae.common;

import java.util.ArrayList;
import java.util.List;

import com.lyae.util.ExcelReader;

public class ExcelReaderTest {
	
	public static void test() {
		
		try {
			List<String> urls = new ArrayList<String>();
			//urls.add("C:\\abc.xls");
			//urls.add("C:\\abc.xlsx");
			urls.add("C:\\dev\\test.xlsx");

			for (String url : urls) {
				ExcelReader excel = new ExcelReader(url, 2, 1, 5);

				while (excel.nextRow()) {
					for (String cell : excel.getRowData()) {
						System.out.print(cell);
						System.out.print(" | ");
					}
					System.out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test();
	}
	
}
