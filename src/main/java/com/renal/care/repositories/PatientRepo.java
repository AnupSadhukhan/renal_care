package com.renal.care.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renal.care.models.Patient;  

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long>{
	
}
