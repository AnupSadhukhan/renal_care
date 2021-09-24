package com.renal.care.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renal.care.dto.TreatmentDTO;
import com.renal.care.exceptions.RenalCareException;
import com.renal.care.models.Patient;
import com.renal.care.utils.ExcelGeneratorUtil;
import com.renal.care.utils.PDFGenerationUtil;


@Service
public class ReportService {
	@Autowired
	private PDFGenerationUtil pdfService;
	
	@Autowired
	private ExcelGeneratorUtil excelService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MyTreatmentService myTreatmentService;
	
	
	
	public ReportService(PatientService patientService) {
		this.patientService = patientService;
	}
	
	public void generateReportByIds(HttpServletResponse response,long[] ids) throws RenalCareException {
		System.out.println("report service called");
		if(ids.length==1) {
			Optional<Patient> findById = patientService.findById(ids[0]);
			if(findById.isPresent()) {
				Patient patient = findById.get();
				List<TreatmentDTO> treatmentDataList = myTreatmentService.getLastSevenDaysDataForPatient(patient.getPatID());
				pdfService.generatePDFForPatient(response, patient,treatmentDataList);
			}
			else {
				System.out.println("user not presen");
				try {
					response.sendError(400, "Not found");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
		}
		else{
			List<Patient> patients = patientService.findByIds(ids);
			generateXlsReport(response, patients);
		}
		
		
	}
	public void generateTreatmentSummaryOfLastSevenDaysReportForPatient(HttpServletResponse response,String patientId) throws IOException {
		List<TreatmentDTO> treatmentDataList;
		try {
			treatmentDataList = myTreatmentService.getLastSevenDaysDataForPatient(patientId);
			pdfService.generatePDFTreatmentSummaryForPatient(response, treatmentDataList, patientId);
		} catch (RenalCareException e) {
			response.sendError(500, "Something went wrong");
			e.printStackTrace();
		}
	}
	public void gererateReportForAllPatientService(HttpServletResponse response) {
		List<Patient> patients = patientService.findAll();
		generateXlsReport(response, patients);
	}
	
	
	private void generateXlsReport(HttpServletResponse response,List<Patient> patients) {
		
		if(!patients.isEmpty()) {
			excelService.generateExcelsForPatients(response,patients);
		}
		else {
			try {
				response.sendError(400, "Not found");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}
