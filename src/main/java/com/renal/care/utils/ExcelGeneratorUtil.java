package com.renal.care.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.renal.care.models.Patient;
import com.renal.care.models.Treatment;

@Component
public class ExcelGeneratorUtil {
	 private XSSFWorkbook workbook;
	    private XSSFSheet sheet;
	    private List<Patient> patients;
	    private List<Treatment> treatmentDataList;
	public void generateExcelsForPatients(HttpServletResponse response,List<Patient> patients) {
		response.setContentType("application/octet-stream");
        
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Patients_Report.xlsx";
        response.setHeader(headerKey, headerValue);
        
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Patient Data");
		this.patients = patients;
		try {
			this.export(response);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public void generateTreatmentSummaryOfLastSevenDaysForPatient(HttpServletResponse response,List<Treatment> treatmentDataList) {
		response.setContentType("application/octet-stream");
        
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Treatment_summary.xlsx";
        response.setHeader(headerKey, headerValue);
        
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Treatment Data");
		this.treatmentDataList = treatmentDataList;
		try {
			this.export(response);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	private void writeHeaderLine() {
		Row row = sheet.createRow(0);
		
		CellStyle style = workbook.createCellStyle();
		//style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		font.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		style.setFont(font);
		
		int columNum = 0;
		createCell(row, columNum++, "Patient ID", style);      
        createCell(row, columNum++, "Full Name", style);       
        createCell(row, columNum++, "E-mail", style);    
        createCell(row, columNum++, "Mobile", style);
        createCell(row, columNum++, "Glucose Level", style);
        createCell(row, columNum++, "Blood Pressure", style);
        createCell(row, columNum, "Pulse Rate", style);
		
	}
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue(new String(value+""));
        }
        cell.setCellStyle(style);
    }
	private void writeDataLines() {
	        int rowCount = 1;
	 
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setFontHeight(14);
	        style.setFont(font);
	                 
	        for (Patient patient : patients) {
	            Row row = sheet.createRow(rowCount++);
	            int columnCount = 0;
	             
	            createCell(row, columnCount++, patient.getPatID(), style);
	            createCell(row, columnCount++, patient.getName(), style);
	            createCell(row, columnCount++, patient.getEmail(), style);
	            createCell(row, columnCount++, patient.getMobile(), style);
	            createCell(row, columnCount++, patient.getGlucoseLevel(), style);
	            createCell(row, columnCount++, patient.getBp(), style);
	            createCell(row, columnCount, patient.getPulseRate(), style);
	        }
	 }
	     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
    private void exportTreatmentSummary(HttpServletResponse response) throws IOException {
        //writeTreatmentHeaderLine();
       // writeTreatmentDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}
