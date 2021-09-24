package com.renal.care.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renal.care.dto.TreatmentDTO;
import com.renal.care.exceptions.RenalCareException;
import com.renal.care.services.MyTreatmentService;
import com.renal.care.services.ReportService;

@CrossOrigin(origins = {"http://localhost:9000"})
@RestController
public class ReportController {
	
	@Autowired
	private ReportService RService;
	
	@GetMapping("/reports")
	public void generateReport(HttpServletResponse response, @RequestParam("id") long[] ids) throws RenalCareException {
		System.out.println(ids.length+" "+ids);
		RService.generateReportByIds(response, ids);
		
	}
	@GetMapping("/reports/all")
	public void generateReport(HttpServletResponse response) {
		RService.gererateReportForAllPatientService(response);
	}
	@GetMapping("/reports/treatment-summary/{patientId}")
	public void generateReport(HttpServletResponse response,@PathVariable("patientId") String patientId) throws IOException {
		
		RService.generateTreatmentSummaryOfLastSevenDaysReportForPatient(response,patientId);
	}
	
	
}
