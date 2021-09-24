package com.renal.care.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.renal.care.dto.TreatmentDTO;
import com.renal.care.models.Patient;
import com.renal.care.models.Treatment;

import ch.qos.logback.classic.Logger;
@Component
public class PDFGenerationUtil {
	
	public void generatePDFForPatient(HttpServletResponse response,Patient patient,List<TreatmentDTO> treatmentDataList) {
		System.out.println("pdf util");
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=PatientReport_" + patient.getPatID() + ".pdf";
        response.setHeader(headerKey, headerValue);
        Document document = new Document(PageSize.A4);
		try {
			
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	        
	        BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);
	        HeaderFooter header = new HeaderFooter(
                    new Phrase("This is a header without a page number", new Font(bf_symbol)), false);
	        header.setAlignment(Element.ALIGN_CENTER);
	        document.setHeader(header);
        
        
	        Paragraph p = new Paragraph("Patient Report", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	        p.setSpacingBefore(20);
	        document.add(p);
	        
	       
//	        Paragraph p2 = new Paragraph("Test : "+ patient.getName());
//	        document.add(p2);
	        
	        PdfPTable table = new PdfPTable(2);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {2f, 3.5f});
	        table.setSpacingBefore(20);
	         
	        table.addCell("Patient ID");
	        table.addCell(patient.getPatID());
	        table.addCell("Name");
	        table.addCell(patient.getName());
	        table.addCell("Mobile");
	        table.addCell(patient.getMobile()+"");
	        table.addCell("Email");
	        table.addCell(patient.getEmail());
	        table.addCell("Glucose Level");
	        table.addCell(patient.getGlucoseLevel());
	        table.addCell("BP");
	        table.addCell(patient.getBp());
	        table.addCell("Pulse Rate");
	        table.addCell(patient.getPulseRate());
	         
	        document.add(table);
	        
	        Paragraph p1 = new Paragraph("Treatment Summary", font);
	        p1.setAlignment(Paragraph.ALIGN_CENTER);
	        p1.setSpacingBefore(10);
	        document.add(p1);
	        
	        document.add(getTableWithData(treatmentDataList));
	        
	        System.out.println("document added");
	         
		}
		catch(DocumentException d) {
			System.out.println("Document Exp");
		}
		catch (IOException e) {
			System.out.println("IO excp");
		}
		finally {
			 document.close();
			 System.out.println("document clased");
		}
		
       
	}
	public void generatePDFTreatmentSummaryForPatient(HttpServletResponse response,List<TreatmentDTO> treatmentDataList,String patient_id) {
		System.out.println("pdf util");
		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Treatment_summary_" +patient_id + ".pdf";
        response.setHeader(headerKey, headerValue);
        Document document = new Document(PageSize.A4);
		try {
			
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	        
	        BaseFont bf_symbol = BaseFont.createFont(BaseFont.SYMBOL, "Cp1252", false);
	        HeaderFooter header = new HeaderFooter(
                    new Phrase("This is a header without a page number", new Font(bf_symbol)), false);
	        header.setAlignment(Element.ALIGN_CENTER);
	        document.setHeader(header);
        
        
	        Paragraph p = new Paragraph("Treatment Summary", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	        p.setSpacingBefore(20);
	        document.add(p);
	        
	       
//	        Paragraph p2 = new Paragraph("Test : "+ patient.getName());
//	        document.add(p2);
	        
	       
	         
	        document.add(getTableWithData(treatmentDataList));
	        
	        
	        System.out.println("document added");
	         
		}
		catch(DocumentException d) {
			System.out.println("Document Exp");
		}
		catch (IOException e) {
			System.out.println("IO excp");
		}
		finally {
			 document.close();
			 System.out.println("document closed");
		}
		
       
	}
	private PdfPTable getTableWithData(List<TreatmentDTO> treatmentDataList) {
		
		PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2f, 2f, 2f, 2f, 2f, 2f});
        table.setSpacingBefore(20);
        addHeader(table);
        for(TreatmentDTO data : treatmentDataList) {
        	table.addCell(data.getDate()+"");
        	table.addCell(data.getStartTime()+"");
        	table.addCell(data.getEndTime()+"");
        	table.addCell(data.getFillAmount()+"");
        	table.addCell(data.getDrainAmount()+"");
        	table.addCell(data.getUf()+"");
        	
        }
        return table;
         
       
	}
	private void addHeader(PdfPTable table) {
		table.addCell("Treatment Date");
		table.addCell("Start Time");
		table.addCell("End Time");
		table.addCell("Fill Amount (ml)");
		table.addCell("Drain Amount (ml)");
		table.addCell("Ultra Filtration (ml)");
	}
	
	
}
