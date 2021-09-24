package com.renal.care.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.renal.care.models.Program;

@Repository
public interface MyPDRepo extends JpaRepository<Program, Long>{
	
	public Program findByPatientIdAndIsActive(Long patientId,boolean isActive);
	public List<Program> findAllByPatientId(Long patientId);
	
	@Query("select count(p) from Program p where p.patientId=?1")
	public Integer countProgramByPatientId(long patientId);
	
	@Query(value = "select * from program p where p.patient_id = ("
			+ "select id from patient where LOWER(patid) = LOWER(:patientId))"
			+ " and p.is_active=true;",
			nativeQuery = true)
	public Program findActiveProgramForPatientForToday(String patientId);
}
