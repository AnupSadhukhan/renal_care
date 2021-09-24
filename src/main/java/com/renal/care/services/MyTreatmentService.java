package com.renal.care.services;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateError;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.renal.care.dto.TreatmentDTO;
import com.renal.care.exceptions.ExceptionStrings;
import com.renal.care.exceptions.RenalCareException;
import com.renal.care.models.Treatment;
import com.renal.care.repositories.MyTreatmentRepo;

@Service
public class MyTreatmentService {

	ModelMapper modelMapper;
	MyTreatmentRepo myTreatmentRepo;
	
	public MyTreatmentService(ModelMapper modelMapper,MyTreatmentRepo myTreatmentRepo) {
		this.modelMapper = modelMapper;
		this.myTreatmentRepo = myTreatmentRepo;
	}
	public TreatmentDTO saveData(TreatmentDTO data) throws RenalCareException,HibernateError {
		if(data.getPatientPatID()==null ||  data.getPatientPatID().equals("")) 
			throw new RenalCareException(ExceptionStrings.E1);
		if(data.getDate()==null) 
			throw new RenalCareException(ExceptionStrings.E2);
		if(data.getStartTime()==null)
			 throw new RenalCareException(ExceptionStrings.E3);
		if(data.getEndTime()==null)
			throw new RenalCareException(ExceptionStrings.E4);
		if(data.getFillAmount()<=0f)
			throw new RenalCareException(ExceptionStrings.E5);
		if(data.getDrainAmount()<=0f)
			throw new RenalCareException(ExceptionStrings.E6);
		float uf = data.getDrainAmount() - data.getFillAmount();
		data.setUf(uf);
		Treatment treatmentData = modelMapper.map(data, Treatment.class);
		Treatment treatment = myTreatmentRepo.save(treatmentData);
		data = modelMapper.map(treatment, TreatmentDTO.class);
			
		return data;
	}
	
	public List<TreatmentDTO> getAllDataForPatient(String patientId) throws RenalCareException{
		if(patientId==null || patientId.equals("")) 
			throw new RenalCareException(ExceptionStrings.E7);
		List<TreatmentDTO> dataList = mapList(myTreatmentRepo.findAllByPatientPatID(patientId),TreatmentDTO.class);
		return dataList;
	}
	
	
	public List<TreatmentDTO> getLastSevenDaysDataForPatient(String patientId) throws RenalCareException{
		if(patientId==null || patientId.equals("")) 
			throw new RenalCareException(ExceptionStrings.E8);
		return mapList(myTreatmentRepo.findLastSevenDaysForPatient(patientId),TreatmentDTO.class);
		
	}
	
	public List<TreatmentDTO> getAllData(){
		List<TreatmentDTO> dataList = mapList(myTreatmentRepo.findAll(),TreatmentDTO.class);
		return dataList;
	}
	

	
	private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	    return source
	    	      .stream()
	    	      .map(element -> modelMapper.map(element, targetClass))
	    	      .collect(Collectors.toList());
	}
	
	
}
