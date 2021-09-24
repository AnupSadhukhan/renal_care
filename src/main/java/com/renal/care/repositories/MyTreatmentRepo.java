package com.renal.care.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.renal.care.dto.TreatmentDTO;
import com.renal.care.models.Treatment;

@Repository
public interface MyTreatmentRepo extends JpaRepository<Treatment, Long> {
	List<Treatment> findAllByPatientPatID(String patientId);
	
	@Query(value = "select * from treatment t where LOWER(t.patient_patid)=LOWER(:patientId)"
			+ " and t.date>current_date() - interval 7 day",
			nativeQuery = true)
	List<Treatment> findLastSevenDaysForPatient(String patientId);
	
	@Query(value="select count(*) from treatment t where t.program_id in ("
			+ "select id from program where patient_id = ("
			+ "select id from patient where LOWER(patid)=Lower(:patientId)))"
			+ "and t.date = current_date()",
			nativeQuery = true)
	Integer countTreatmentTakenByPatientToday(String patientId);
}
