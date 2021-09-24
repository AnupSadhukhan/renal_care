package com.renal.care.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renal.care.dto.ProgramDTO;
import com.renal.care.models.Program;
import com.renal.care.repositories.MyPDRepo;
import com.renal.care.repositories.MyTreatmentRepo;

@Service
public class MyPDService {

	@Autowired
	private MyPDRepo myPDRepo;
	@Autowired
	private MyTreatmentRepo myTreatmentRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	public List<Program> findAllByPatientId(Long patientId) throws HibernateException{
		return myPDRepo.findAllByPatientId(patientId);
	}
	
	public Program createOrUpdate(ProgramDTO programDTO) throws HibernateException{
		Program program = modelMapper.map(programDTO,Program.class);
		if(program.getIsActive()) {
			inActivateAnotherProgram(program.getPatientId());
		}
		
		return myPDRepo.save(program);
		
	}
	public Optional<Program> findById(Long id) {
		
		return myPDRepo.findById(id);
	}
	public Integer countProgramsByPatientId(Long patientId) {
		return myPDRepo.countProgramByPatientId(patientId);
	}
	public void deleteById(Long id) {
		myPDRepo.deleteById(id);
	}
	
	public Program getActiveProgramForPatientByDateIfNotTake(String patientId) {
		Integer count = myTreatmentRepo.countTreatmentTakenByPatientToday(patientId);
		if(count>=1) return null;
		return myPDRepo.findActiveProgramForPatientForToday(patientId);
	}
	
	/*public Program update(ProgramDTO programDTO) throws HibernateException{
		Program program = modelMapper.map(programDTO,Program.class);
		if(program.isActive()) {
			inActivateAnotherProgram(program.getPatientId());
		}
		return myPDRepo.save(program);
	}*/
	
	private void inActivateAnotherProgram(Long patientId) throws HibernateException{
		Program currentActiveProgram = myPDRepo.findByPatientIdAndIsActive(patientId, true);
		if(currentActiveProgram!=null) {
			currentActiveProgram.setIsActive(false);
			myPDRepo.save(currentActiveProgram);
		}
	}
	private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	    return source
	    	      .stream()
	    	      .map(element -> modelMapper.map(element, targetClass))
	    	      .collect(Collectors.toList());
	}
}
