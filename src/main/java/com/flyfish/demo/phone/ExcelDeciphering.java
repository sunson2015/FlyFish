package com.flyfish.demo.phone;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 
 * <p>
 * Description: Excel文件解密
 * </p>
 * 
 * @author SuiGaopeng
 * @version 1.0 
 * Create Date: 2014-11-25 上午9:15:41 
 * Project Name: my-test
 * 
 * <pre>
 * Modification History: 
 *             Date                                Author                   Version          Description 
 * -----------------------------------------------------------------------------------------------------------  
 * LastChange: $Date::             $      $Author: $          $Rev: $
 * </pre>
 * 
 */
public class ExcelDeciphering {
	
	final static DecimalFormat DF = new DecimalFormat("0"); // 作于读取数据处理
	
	final static String readFileName = "C:\\Users\\Administrator.PC\\Desktop\\加密客户.xlsx"; // 解密源文件
	final static String writeFileName = "C:\\Users\\Administrator.PC\\Desktop\\dd.xlsx"; // 解密后文件(该文件可以不存在,若存在会覆盖原有文件)
	final static Integer[][] keyPosition = { 
		{ 1 }, // 解密行,从0开始,这个代表将第2行和第3行解密,如果想将所有行解密此处为{0},如果想将第二行以后所有解密此处为{3}
		{ 0, 0 } // 解密列,从0开始,这个代表将第3列和第4列解密,如果想将所有列解密此处为{0},如果想将第二列以后所有解密此处为{3}
	};
	
	public static void main(String[] args) {
		if (createDecExcelFile())
			System.out.println("<------------------ 解密成功 ------------------>");
		else
			System.out.println("<------------------ 解密失败 ------------------>");
	}
	
	/**
	 * <p>
	 * 读取加密后的Excel数据生成解密后的Excel文件
	 * </p>
	 * 
	 * @author SuiGaopeng
	 * @date 2014-11-25 上午9:17:01
	 * @return
	 */
	@SuppressWarnings("resource")
    public static boolean createDecExcelFile() {
		Workbook reWorkbook = null;
		Workbook wrWorkbook = null;
		boolean flag = false;
		
		// 判断源文件路径是否为空
		if (readFileName == null || readFileName.length() < 1)
			return flag;
		// 判断解密文件路径是否为空
		if (writeFileName == null || writeFileName.length() < 1)
			return flag;
		// 判断源文件是否存在
		File refile = new File(readFileName);
		if (!refile.exists())
			return flag;
		// 判断是否存在解密的位置,不存在返回失败
		if (keyPosition[0].length < 1 || keyPosition[1].length < 1)
			return flag;
		
		try {
			reWorkbook = WorkbookFactory.create(new File(readFileName));
		} catch (Exception e) {
			System.out.println("It cause Error on READING excel workbook: ");
			e.printStackTrace();
			return flag;
		}
		
		try {
			// XSSFWork used for .xslx (>= 2007), HSSWorkbook for 03 .xsl
			wrWorkbook = new XSSFWorkbook();// HSSFWorkbook();//WorkbookFactory.create(inputStream);
		} catch (Exception e) {
			System.out.println("It cause Error on CREATING excel workbook: ");
			e.printStackTrace();
			return flag;
		}
		
		if (wrWorkbook != null && reWorkbook != null) {
			Sheet reSheet = reWorkbook.getSheetAt(0);
			Sheet wrSheet = wrWorkbook.createSheet("testdata");
			Row reRow = null, wrkrow = null;
			boolean keyCol;
			DESPlus desPlus = null;
			
			try {
				desPlus = new DESPlus();
			} catch (Exception e) {
				e.printStackTrace();
				return flag;
			}
			
			for (int i = 0; i <= reSheet.getLastRowNum(); i++) {
				reRow = reSheet.getRow(i);
				wrkrow = wrSheet.createRow(i);
				
				// 判断该行是否需要解密
				keyCol = keyPosition[0].length == 1 ? i >= keyPosition[0][0] : Arrays.asList(keyPosition[0])
						.contains(i) ? true : false;
				
				for (int j = 0; j < reRow.getLastCellNum(); j++) {
					Cell cell = wrkrow.createCell(j, Cell.CELL_TYPE_STRING);
					if (reRow.getCell(j) != null) {
						// 判断是否需要解密
						if (keyCol
								&& (keyPosition[1].length == 1 ? j >= keyPosition[1][0] : Arrays.asList(keyPosition[1])
										.contains(j))) { // 判断是否是需要加密
							try {
								cell.setCellValue(desPlus.decrypt(reRow.getCell(j).getCellType() == 1 ? reRow
										.getCell(j).getStringCellValue().trim() : DF.format(reRow.getCell(j)
										.getNumericCellValue())));
							} catch (Exception e) {
								System.out.println("解密第"
										+ ++i
										+ "行,第"
										+ ++j
										+ "列出错,解密前内容为:\""
										+ (reRow.getCell(j).getCellType() == 1 ? reRow.getCell(j).getStringCellValue()
												.trim() : DF.format(reRow.getCell(j).getNumericCellValue())) + "错误如下:");
								e.printStackTrace();
								return flag;
							}
						} else {
							cell.setCellValue(reRow.getCell(j).getCellType() == 1 ? reRow.getCell(j)
									.getStringCellValue().trim() : DF.format(reRow.getCell(j).getNumericCellValue()));
						}
					}
				}
			}
			
			try {
				FileOutputStream outputStream = new FileOutputStream(writeFileName);
				wrWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				flag = true;
			} catch (Exception e) {
				System.out.println("It cause Error on WRITTING excel workbook: ");
				e.printStackTrace();
				return flag;
			}
		}
		
		return flag;
	}}
