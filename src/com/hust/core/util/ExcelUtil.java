package com.hust.core.util;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

//import cn.itcast.nsfw.user.entity.User;

public class ExcelUtil {


	public static void exportUserExcel(List<Object> userList, ServletOutputStream outputStream) {
		try {
	
			HSSFWorkbook workbook = new HSSFWorkbook();

			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
			

			HSSFCellStyle style1 = createCellStyle(workbook, (short)16);
			

			HSSFCellStyle style2 = createCellStyle(workbook, (short)13);
			

			HSSFSheet sheet = workbook.createSheet("");

			sheet.addMergedRegion(cellRangeAddress);

			sheet.setDefaultColumnWidth(25);
			

			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);

			cell1.setCellStyle(style1);
			cell1.setCellValue("");
			

			HSSFRow row2 = sheet.createRow(1);
			String[] titles = {""};
			for(int i = 0; i < titles.length; i++){
				HSSFCell cell2 = row2.createCell(i);
				
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			
		
			if(userList != null){
				for(int j = 0; j < userList.size(); j++){
					HSSFRow row = sheet.createRow(j+2);
					HSSFCell cell11 = row.createCell(0);
				//	cell11.setCellValue(userList.get(j).getName());
					HSSFCell cell12 = row.createCell(1);
					//cell12.setCellValue(userList.get(j).getAccount());
					HSSFCell cell13 = row.createCell(2);
					//cell13.setCellValue(userList.get(j).getDept());
					HSSFCell cell14 = row.createCell(3);
					//cell14.setCellValue(userList.get(j).isGender()?"�?":"�?");
					HSSFCell cell15 = row.createCell(4);
					//cell15.setCellValue(userList.get(j).getEmail());
				}
			}
		
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints(fontSize);
		style.setFont(font);
		return style;
	}

}
