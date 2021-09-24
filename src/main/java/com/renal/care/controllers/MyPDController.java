package com.renal.care.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.renal.care.dto.ProgramDTO;
import com.renal.care.models.Program;
import com.renal.care.services.MyPDService;

@CrossOrigin(origins = {"http://localhost:9000"})
@RestController
public class MyPDController {
	
	@Autowired
	private MyPDService myPDService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/mypd/patients/{patientId}")
	public ResponseEntity<List<ProgramDTO>> findAll(HttpServletResponse response,   @PathVariable("patientId") Long patientId){
		System.out.println("called  -> /mypd/{patientId}");
			try {
				if(patientId==null) {
					response.sendError(400,"Patient Id Not found");
					return ResponseEntity.notFound().build();
				}
			} catch (IOException e) {
				return ResponseEntity.unprocessableEntity().build();
			}
			
			
			List<Program> programs = myPDService.findAllByPatientId(patientId);
			List<ProgramDTO> programDTO = mapList(programs, ProgramDTO.class);
			return new ResponseEntity<>(programDTO,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/mypd/patients/{patientId}/active")
	public ResponseEntity<ProgramDTO> findActiveAndPendingPDProgram(HttpServletResponse response,   @PathVariable("patientId") String patientId){
		
			try {
				if(patientId==null) {
					response.sendError(400,"Patient Id Not found");
					return ResponseEntity.notFound().build();
				}
			} catch (IOException e) {
				return ResponseEntity.unprocessableEntity().build();
			}
			
			Program program = myPDService.getActiveProgramForPatientByDateIfNotTake(patientId);
			if(program==null)
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(modelMapper.map(program, ProgramDTO.class),HttpStatus.ACCEPTED);
		
	}
	
	
	@PostMapping("/mypd")
	public ResponseEntity<ProgramDTO> create(HttpServletResponse response, @RequestBody ProgramDTO programDTO){
		try {
			if(programDTO.getPatientId()==0L) {
				response.sendError(400,"Please provide correct patient id");
				return ResponseEntity.notFound().build();
			}
		} catch (IOException e) {
			return ResponseEntity.unprocessableEntity().build();
		}
		System.out.println(programDTO.getIsActive());
		if(Boolean.valueOf(programDTO.getIsActive())) {
			System.out.println("set true");
		}
		else {
			System.out.println("set false");
		}
		ProgramDTO newProgramDTO = modelMapper.map(myPDService.createOrUpdate(programDTO), ProgramDTO.class);
		return new ResponseEntity<>(newProgramDTO,HttpStatus.CREATED);
	}
	
	@PutMapping("/mypd")
	public ResponseEntity<ProgramDTO> update(HttpServletResponse response, @RequestBody ProgramDTO programDTO){
		try {
			if(programDTO.getPatientId()==0L) {
				response.sendError(400,"Please provide correct patient id");
				return ResponseEntity.notFound().build();
			}
		} catch (IOException e) {
			return ResponseEntity.unprocessableEntity().build();
		}
		ProgramDTO newProgramDTO = modelMapper.map(myPDService.createOrUpdate(programDTO), ProgramDTO.class);
		return new ResponseEntity<>(newProgramDTO,HttpStatus.ACCEPTED);
		
	}
	@DeleteMapping("/mypd/{id}")
	public ResponseEntity<String> delete(HttpServletResponse response,   @PathVariable("id") Long id){
		try {
			if(id==null) {
				response.sendError(400,"Please provide correct program id");
				return ResponseEntity.notFound().build();
			}
			Optional<Program> findById = myPDService.findById(id);
			System.out.println("program found? "+findById.isPresent());
			if(!findById.isPresent()) {
				
				 response.sendError(400,"Program not found");
				 
				 return ResponseEntity.notFound().build();
			}
			Program program = findById.get();
			if(program.getIsActive() && myPDService.countProgramsByPatientId(program.getPatientId())>1) {
				response.sendError(500, "Please activate another program before deleting...");
				return ResponseEntity.internalServerError().build();
			}
			
		}
		catch(IOException e) {
			return ResponseEntity.unprocessableEntity().build();
		}
		myPDService.deleteById(id);
		return new ResponseEntity<>("Program Deleted successfully", HttpStatus.ACCEPTED);
	}
	
	
	private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	    return source
	    	      .stream()
	    	      .map(element -> modelMapper.map(element, targetClass))
	    	      .collect(Collectors.toList());
	}
	
}
