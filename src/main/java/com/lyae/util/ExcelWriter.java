package com.lyae.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelWriter {
	
	public static void makeFile(File file, List<Map<String, Object>> list, boolean isHashKeyTitle) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("출력 파일이 파일이 아닌 디렉토리 [" + file.getAbsolutePath() + "] 처리 요망.");
			}
			if (!file.delete()) {
				throw new IOException("중복파일 삭제 실패 [" + file.getAbsolutePath() + "] 처리 요망.");
			}
		}
		
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		
		
		
		if (list.size() > 0) {
			
			SXSSFRow row;
			int rownum = 0;
			Set<String> keyset = list.get(0).keySet();
			final int keySize = keyset.size();
			String[] keys = keyset.toArray(new String[keySize]);
			
			SXSSFSheet sheet = null;
			
			for (Map<String, Object> node : list) {
				
				if (rownum == 0) {
					sheet = wb.createSheet();
					if (isHashKeyTitle) {
						row = sheet.createRow(rownum++);
						for (int i = 0 ; i < keySize ; i++) {
							SXSSFCell cell = row.createCell(i);
							cell.setCellValue(keys[i]);
						}
					}
				}
				
				row = sheet.createRow(rownum++);
				for (int i = 0 ; i < keySize ; i++) {
					SXSSFCell cell = row.createCell(i);
					Object val = node.get(keys[i]);
					if (val != null) {
						cell.setCellValue(val.toString());
					} else {
						cell.setCellValue("");
					}
				}
				
				if (rownum >= 1000000) {
					rownum = 0;
				}
			}
		}
		
		FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
        wb.dispose();
        wb.close();
        file.setReadable(true, false);
		file.setWritable(true, false);
		file.setExecutable(true, false);
	}
}
