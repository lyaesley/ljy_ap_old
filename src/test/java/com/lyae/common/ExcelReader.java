package com.lyae.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 엑셀 유틸입니다.<br>
 * 사용법은 소스아래 [사용 테스트] 를 참고
 */
public class ExcelReader {
	
	private final static Logger logger = Logger.getLogger(ExcelReader.class);
	
	/** 타입오류 */
	public final static int TYPE_ERROR = -1;
	/** XLS 타입 */
	public final static int TYPE_XLS = 1;
	/** XLSX 타입 */
	public final static int TYPE_XLSX = 2;
	
	/** 데이터 포멧터 */
	private DataFormatter formatter = new DataFormatter();
	
	/** 데이트 포맷터 */
	private SimpleDateFormat dateFormat = null;
	
	/** 시트 */
	private final Sheet sheet;
	/** 컬럼(열) 갯수 */
	private final int columnCount;
	/** 전체 행 */
	private final int rowCount;
	/** 현재 행 */
	private int rowIndex = 0;
	/** 현재 행 값 */
	private String[] rowData;
	/** 빈 행 무시 */
	private boolean ignoreEmptyRow = true;
	/** 마지막 nextState 상태 */
	private boolean lastNextState = false;
	
	/** 날짜형 자료에 대한 포멧을 정의합니다. */
	public void setDateFormat(SimpleDateFormat simpleDateFormat) {
		this.dateFormat = simpleDateFormat;
	}
	
	/**
	 * 엑셀유틸
	 * @Deprecated 호환성을 위해 살아 있지만 다른 생정자나 static 함수를 이용하시기 바랍니다.<br>
	 * 주의 : 이 생성자로 생성한 경우. parserXls, parserXlsx 만 사용할 수 있습니다.
	 */
	@Deprecated
	public ExcelReader() {
		sheet = null;
		columnCount = 0;
		rowCount = 0;
		rowData = null;
	}
	
	/** 엑셀유틸 */
	public ExcelReader(FileInputStream fileInputStream, int TYPE, int startRowIndex, int columnCount) throws IOException {
		switch (TYPE) {
			case TYPE_XLS: sheet = new HSSFWorkbook(fileInputStream).getSheetAt(0); break;
			case TYPE_XLSX: sheet = new XSSFWorkbook(fileInputStream).getSheetAt(0); break;
			default:
				throw new IllegalArgumentException("TYPE valid of TYPE_XLS or TYPE_XLSX ");
		}
		this.columnCount =columnCount;
		this.rowCount = sheet.getPhysicalNumberOfRows();
		this.rowIndex = startRowIndex - 1;
		this.rowData = new String[columnCount];
	}
	
	/** 엑셀유틸 */
	public ExcelReader(String url, int TYPE, int startRowIndex, int columnCount) throws IOException {
		this(new FileInputStream(url), TYPE, startRowIndex, columnCount);
	}
	
	/** 비어있는 행 무시 : 기본값 true */
	public void setIgnoreEmptyRow(boolean ignoreEmptyRow) {
		this.ignoreEmptyRow = ignoreEmptyRow;
	}
	
	/** 현재 행 */
	public int getRowIndex() {
		return rowIndex;
	}
	
	/** 다음 행으로 이동 */
	public boolean nextRow() {
		// 포인터를 다음으로 옴김
		int i = (rowIndex + 1);
		
		// 빈행 무시 옵션을 활용하기위해 마지막까지 루프를 돈다.
		for ( ; i < rowCount ; i++) {
			Row row = sheet.getRow(i);
			int emptyCellCount = 0;
			for (int j = 0 ; j < columnCount ; j++) {
				Cell cell = row.getCell(j);
				
				if (cell == null) {
					// null : null의 경우 빈 스트링으로 바꿔준다.
					rowData[j] = "";
					emptyCellCount++;
				} else if (dateFormat != null && HSSFDateUtil.isInternalDateFormat(cell.getCellStyle().getDataFormat())) {
					// 날짜형 + dateFormat 이 정의된 경우 : 해당포멧으로 바꿔준다.
					rowData[j] = dateFormat.format(cell.getDateCellValue());
				} else if ((rowData[j] = formatter.formatCellValue(cell).trim()).length() == 0) {
					// 기타값
					emptyCellCount++;
				}
			}
			// 빈행 무시 옵션이 켜짐 && 행전체가 비어 있을 경우.
			if (ignoreEmptyRow && columnCount == emptyCellCount) {
				continue;
			} else {
				// 다음행을 찾아 적용
				rowIndex = i;
				return (lastNextState = true);
			}
		}
		
		// 다음행 적용
		rowIndex = i;
		return (lastNextState = false);
	}
	
	/** 행의 데이터 가져오기 */
	public String[] getRowData() {
		return lastNextState ? rowData.clone() : null;
	}
	
	/** 파일 이름으로 TYPE 를 구해옵니다. */
	public static int getFileType(String filename) {
		int dot;
		if (filename != null && (dot = filename.lastIndexOf('.')) != -1) {
			String fileExt = filename.substring(dot + 1).toLowerCase();
			if ("xlsx".equals(fileExt)) {
				return TYPE_XLSX;
			} else if ("xls".equals(fileExt)) {
				return TYPE_XLS;
			}
		}
		return TYPE_ERROR;
	}
	
	/**
	 * xls 파서<br>
	 *<b>주의</b> :
	 * 엑셀의 텍스트 형식이 아닌 파일은 1234가 1234.0F 같은식으로 나올 수 있다.
	 * @deprecated 일반적인 생성 사용을 권장함.
	 */
	@Deprecated
	public List<String[]> parserXls( String url , int startRow , int columncnt) {
		
		List<String[]> result = new ArrayList<String[]>();
		try{
			//파일을 읽기위해 엑셀파일을 가져온다 
			FileInputStream fis=new FileInputStream(url);
			HSSFWorkbook workbook=new HSSFWorkbook(fis);
			int rowindex=0;
			int columnindex=0;
			//시트 수 (첫번째에만 존재하므로 0을 준다)
			//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			HSSFSheet sheet=workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();
			for(rowindex = startRow; rowindex <= rows ; rowindex++){
			    //행을 읽는다
			    HSSFRow row=sheet.getRow(rowindex);
			    if(row !=null){
			        //셀의 수
			        //int cells=row.getPhysicalNumberOfCells();
			        String data[] = new String[columncnt];
			        
			        for(columnindex=0; columnindex < columncnt; columnindex++){
			            //셀값을 읽는다
			            HSSFCell cell = row.getCell(columnindex);

			            //셀이 빈값일경우를 위한 널체크
			            if(cell==null){
			                continue;
			            }else{
			                //타입별로 내용 읽기
			                switch (cell.getCellType()){
			                case HSSFCell.CELL_TYPE_FORMULA:
			                	data[columnindex] = cell.getCellFormula();
			                    break;
			                case HSSFCell.CELL_TYPE_NUMERIC:
			                	data[columnindex] = cell.getNumericCellValue()+"";
			                    break;
			                case HSSFCell.CELL_TYPE_STRING:
			                	data[columnindex] = cell.getStringCellValue()+"";
			                    break;
			                case HSSFCell.CELL_TYPE_BLANK:
			                	data[columnindex] = cell.getBooleanCellValue()+"";
			                    break;
			                case HSSFCell.CELL_TYPE_ERROR:
			                	data[columnindex] = cell.getErrorCellValue()+"";
			                    break;
			                }
			            }
			           
		            }
			        result.add(data);
			        
		        }
			}
		} catch (Exception e){
			logger.error(e.getLocalizedMessage(),e);
		}
		
		return result;
	}
	
	/**
	 * xlsx 파서<br>
	 *<b>주의</b> :
	 * 엑셀의 텍스트 형식이 아닌 파일은 1234가 1234.0F 같은식으로 나올 수 있다.
	 * @deprecated 일반적인 생성 사용을 권장함.
	 */
	@Deprecated
	public List<String[]> parserXlsx( String url , int startRow , int columncnt) {
		
		List<String[]> result = new ArrayList<String[]>();
		
		try{
			FileInputStream fis=new FileInputStream(url);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			int rowindex=0;
			int columnindex=0;
			
			//시트 수 (첫번째에만 존재하므로 0을 준다)
			//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();
			
			for( rowindex = startRow; rowindex <= rows; rowindex++){
			    //행을읽는다
			    XSSFRow row=sheet.getRow(rowindex);
			    if(row !=null){
			        
			    	//셀의 수
			        //int cells = row.getPhysicalNumberOfCells();
			        String data[] = new String[columncnt];
			        
			        for(columnindex=0; columnindex < columncnt; columnindex++ ){
			            
			        	//셀값을 읽는다
			            XSSFCell cell = row.getCell(columnindex);
			           
			            //셀이 빈값일경우를 위한 널체크
			            if(cell != null){
			            	if( cell.getCellType() == XSSFCell.CELL_TYPE_STRING ) {
			            		if( !cell.toString().isEmpty() ){
			            			data[columnindex] = cell.getStringCellValue()+"";
			            		} else {
			            			data[columnindex] = "";
			            		}
			            	} else if ( cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC ) {
			            		data[columnindex] =  cell.getNumericCellValue()+"";
			            	} else if( cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA ){
			            		if( !cell.toString().isEmpty() ){
			            			data[columnindex] = cell.getCellFormula();
			            		} else {
			            			data[columnindex] = "";
			            		}
			            	} else if ( cell.getCellType() == XSSFCell.CELL_TYPE_BLANK ) {
			            		data[columnindex] ="";
			            	} else if ( cell.getCellType() == XSSFCell.CELL_TYPE_ERROR ) {
			            		data[columnindex] = cell.getErrorCellValue()+"";
			            	} else {
			            		data[columnindex] = "";
			            	}
			            }
			        }
			        result.add(data);
			    }
			}
		} catch (Exception e){
			logger.error(e.getLocalizedMessage(),e);
		}
		
		return result;
	}

	// 사용 테스트
//	public static class TestClass {
//		@Test
//		public void test() {
//
//			try {
//				List<String> urls = new ArrayList<String>();
//				//urls.add("C:\\abc.xls");
//				//urls.add("C:\\abc.xlsx");
//				urls.add("C:\\abcd.xlsx");
//
//				for (String url : urls) {
//					ExcelUtil excel = new ExcelUtil(url, getFileType(url), 1, 9);
//
//					while (excel.nextRow()) {
//						for (String cell : excel.getRowData()) {
//							System.out.print(cell);
//							System.out.print(" || ");
//						}
//						System.out.println();
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
}