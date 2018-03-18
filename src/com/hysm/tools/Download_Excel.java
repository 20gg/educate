/**
 * 
 */
package com.hysm.tools;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.bson.Document;

 

/**
 * XXXXXXXX
 * @author cgb
 * @date 2017年5月27日
 */

public class Download_Excel {
	// 输出所有订单信息
		public static HSSFWorkbook export(List<Document> list) {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("订单记录");

			 HSSFCellStyle style = wb.createCellStyle();
			 style.setAlignment(HSSFCellStyle.BORDER_THIN);//ALIGN_CENTER
			 style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			 style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			 style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			 style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			 HSSFCellStyle style2 = wb.createCellStyle();
			 style2.setAlignment(HSSFCellStyle.BORDER_THIN);
			 style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			 style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			 style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			 style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			 HSSFFont font = wb.createFont();
			 font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			 font.setFontHeightInPoints((short) 5);
			 style2.setFont(font);
			// //设置style 文本靠左 全部有边框
			for (int i = 0; i < 1 + list.size(); i++) {
				HSSFRow tempRow = sheet.createRow(i);
				for (int j = 0; j < 6; j++) {
					HSSFCell cell_temp = tempRow.createCell((short) j);
					if(j==0){
						cell_temp.setCellStyle(style2);
						
					}else{		
						cell_temp.setCellStyle(style);
					}
				}
			}
			//
			 sheet.setColumnWidth((short)0, (short)5000);
			 sheet.setColumnWidth((short)1, (short)5000);
			 sheet.setColumnWidth((short)2, (short)5000);
			 sheet.setColumnWidth((short)3, (short)5000);
			 sheet.setColumnWidth((short)4, (short)5500);
			 sheet.setColumnWidth((short)5, (short)5500);
			/* sheet.setColumnWidth((short)6, (short)4000);
			 sheet.setColumnWidth((short)7, (short)6000);
			 sheet.setColumnWidth((short) 8, (short) 7000);
			 sheet.setColumnWidth((short) 9, (short)7000);*/
//		     sheet.setColumnWidth((short) 10, (short)7000);
			 
			HSSFRow row = sheet.getRow(0);
			row.getCell((short) 0).setCellValue("序号");
			row.getCell((short) 1).setCellValue("昵称");
			row.getCell((short) 2).setCellValue("订单课程");
			row.getCell((short) 3).setCellValue("会员等级");
 
			row.getCell((short) 4).setCellValue("付费");
			row.getCell((short) 5).setCellValue("支付日期");
			 
			row.setHeight((short) (500));
			int num = 1;
			for (int i = 0; i < list.size(); i++) {
		
				row = sheet.getRow(i + 1);
				Document kpreq = list.get(i);
				row.getCell((short) 0).setCellValue(num);
				row.getCell((short) 1).setCellValue(kpreq.getString("name"));
			 
				row.getCell((short) 2).setCellValue(kpreq.getString("c_name"));
				
				int temp = kpreq.getInteger("role");
				if(0== temp){
					row.getCell((short) 3).setCellValue("普通用户");
				}else if(1== temp){
					row.getCell((short) 3).setCellValue("会员用户");
				}
				row.getCell((short) 4).setCellValue(kpreq.getInteger("pay_money"));
			 
				row.getCell((short) 5).setCellValue(kpreq.getString("date_1"));
				 
				num = num + 1;
				row.setHeight((short) (500));
			}
			return wb;
		}

}
