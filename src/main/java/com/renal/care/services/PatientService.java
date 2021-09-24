package com.renal.care.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import com.renal.care.models.Patient;
import com.renal.care.repositories.PatientRepo;

@Service
public class PatientService {
	private PatientRepo patientRepo;
	
	public PatientService(PatientRepo repo) {
		this.patientRepo = repo;
	}
	public Patient addPatient(Patient p) {
		return patientRepo.save(p);
	}
	public Patient updatePatient(Patient p) throws HibernateException{
		return patientRepo.save(p);
	}
	public List<Patient> findByIds(long[] ids){
		List<Long> patientIds = new ArrayList<>();
		for(long id : ids) {
			patientIds.add(id);
		}
		return patientRepo.findAllById(patientIds);
	}
	public List<Patient> findAll(){
		return patientRepo.findAll();
	}
	public Optional<Patient> findById(long id) {
		return patientRepo.findById(id);
	}
}
