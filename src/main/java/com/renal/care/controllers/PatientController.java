package com.renal.care.controllers;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.renal.care.dto.PatientDTO;

import com.renal.care.models.Patient;
import com.renal.care.services.PatientService;

import ch.qos.logback.classic.Logger;

/**
 * This is patient controller <i>class</i>
 * <p>this is new line</p>
 * @author Anup
 * @version 1.0
 * @since 2021
 *
 */
@CrossOrigin(origins = {"http://localhost:9000"})
@RestController
public class PatientController {
	 
	private static final Logger log = (Logger) LoggerFactory.getLogger(PatientController.class);
	private static final String PAT_PREFIX = "PAT";
	
	private PatientService patientService;
	private ModelMapper modelMapper;
	public PatientController(PatientService patientService, ModelMapper modelMapper) {
		this.patientService = patientService;
		this.modelMapper = modelMapper;
	}
	/**
	 * 
	 * @return
	 * 
	 * @param  
	 */
	@GetMapping
	public String test() {
		
		log.info("test working...");
		 
		
		return "working";
	}
	
	@PostMapping("/patients")
	public ResponseEntity<PatientDTO> create(@RequestBody PatientDTO patient) {
		
			Patient p = modelMapper.map(patient,Patient.class);
			if(p.getPatID() == null) {
				p.setPatID(this.generatePatientID());
			}
		
		
		
		return new ResponseEntity<>(modelMapper.map(patientService.addPatient(p), PatientDTO.class),HttpStatus.CREATED);
		
	}
	@PutMapping("/patients")
	public ResponseEntity<String> update(@RequestBody PatientDTO patient) {
		if(patient.getPatID() == null) return new ResponseEntity<>("Patient ID not found",HttpStatus.BAD_REQUEST);
		try {
			Patient p = modelMapper.map(patient, Patient.class);
			System.out.println(patient);
			patientService.updatePatient(p);
		}
		catch (Exception e) {
			
			return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Updated",HttpStatus.ACCEPTED);
	}
	@GetMapping("/patients")
	public ResponseEntity<List<PatientDTO>> getAllPatients(){
		List<Patient> patients = patientService.findAll();
		List<PatientDTO> patientDtoList = mapList(patients, PatientDTO.class);
		return new ResponseEntity<>(patientDtoList,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/patients/ids")
	public ResponseEntity<List<PatientDTO>> getAllPatinetByIds(@RequestParam("ids") long[] ids){
		List<Patient> patients = patientService.findByIds(ids);
		List<PatientDTO> patientDtoList = mapList(patients, PatientDTO.class);
		return new ResponseEntity<>(patientDtoList,HttpStatus.ACCEPTED);
	}
	@GetMapping("/patients/{id}")
	public ResponseEntity<PatientDTO> getPatientById(@PathVariable("id") long id){
		Patient patient = patientService.findById(id).get();
		PatientDTO patientDTO = modelMapper.map(patient,PatientDTO.class);
		return new ResponseEntity<>(patientDTO,HttpStatus.ACCEPTED);
	}
	private String generatePatientID() {
		SecureRandom random = new SecureRandom();
		int bound = 10000;
		return PAT_PREFIX + random.nextInt(bound);
	}
	private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	    return source
	    	      .stream()
	    	      .map(element -> modelMapper.map(element, targetClass))
	    	      .collect(Collectors.toList());
	}
	
	
}
