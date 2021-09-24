package com.renal.care.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateError;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.renal.care.dto.PatientDTO;
import com.renal.care.dto.TreatmentDTO;
import com.renal.care.exceptions.ExceptionStrings;
import com.renal.care.exceptions.RenalCareException;
import com.renal.care.models.Treatment;
import com.renal.care.repositories.MyTreatmentRepo;
import com.renal.care.services.MyTreatmentService;

@CrossOrigin(origins = {"http://localhost:9000"})
@RestController
public class MyTreatmentController {
	
	 
	 ModelMapper modelMapper;
	 MyTreatmentService myTreatmentService;
	 
	 public MyTreatmentController(MyTreatmentService myTreatmentService, ModelMapper modelMapper) {
		this.myTreatmentService = myTreatmentService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping("/my-treatments")
	public ResponseEntity<TreatmentDTO> saveTreatmentData(HttpServletResponse response, @RequestBody TreatmentDTO data) {
		
		try {
			data = myTreatmentService.saveData(data);
		} catch (HibernateError e) {
			
			e.printStackTrace();
			try {
				response.sendError(500, "Something went wrong");
				return ResponseEntity.unprocessableEntity().build();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
		} catch (RenalCareException e) {
			
			e.printStackTrace();
			try {
				response.sendError(500, ExceptionStrings.E);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		return new ResponseEntity<>(data,HttpStatus.CREATED);
	}
	
	@GetMapping("/my-treatments")
	public List<TreatmentDTO> getAllTreatmentData(){
		return myTreatmentService.getAllData();
	}
	
	@GetMapping("/my-treatments/{patientId}")
	public ResponseEntity<List<TreatmentDTO>> getAllTreatmentDataForPatient(HttpServletResponse response,@PathVariable("patientId") String patientId){
		System.out.println("all for patient");
		try {
			return new ResponseEntity<>(myTreatmentService.getAllDataForPatient(patientId),HttpStatus.OK);
		} catch (RenalCareException e) {
			try {
				response.sendError(500,"Something went wrong");
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}
		return ResponseEntity.unprocessableEntity().build();
	}
	@GetMapping("/my-treatments/filter/{patientId}")
	public ResponseEntity<List<TreatmentDTO>> getLastSevenDaysTreatmentDataForPatient(HttpServletResponse response,@PathVariable("patientId") String patientId){
		System.out.println("filter");
		try {
			return new ResponseEntity<>(myTreatmentService.getLastSevenDaysDataForPatient(patientId),HttpStatus.OK);
		} catch (RenalCareException e) {
			try {
				response.sendError(500,"Something went wrong");
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}
		return ResponseEntity.unprocessableEntity().build();
	}


}
