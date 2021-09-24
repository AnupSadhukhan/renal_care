package com.renal.care.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.renal.care.utils.LocalDateAttributeConverter;
import com.renal.care.utils.LocalDateTimeAttributeConverter;

@Entity
public class Treatment implements Serializable{
		
			/**
		 * 
		 */
		private static final long serialVersionUID = -5336943512840570483L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		
		@JsonFormat(pattern ="yyyy-MM-dd")
		@Convert(converter = LocalDateConverter.class)
		private LocalDate date;
		
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		@Convert(converter = LocalDateTimeConverter.class)
		private LocalDateTime startTime;
		
		@Convert(converter = LocalDateTimeConverter.class)
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private LocalDateTime endTime;
		
		private float fillAmount;
		private float drainAmount;
		private float uf;
		private String patientPatID;
		private long programId;
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		
		
		public LocalDate getDate() {
			return date;
		}
		public void setDate(LocalDate date) {
			this.date = date;
		}
		public LocalDateTime getStartTime() {
			return startTime;
		}
		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}
		
		public LocalDateTime getEndTime() {
			return endTime;
		}
		public void setEndTime(LocalDateTime endTime) {
			this.endTime = endTime;
		}
		public float getFillAmount() {
			return fillAmount;
		}
		public void setFillAmount(float fillAmount) {
			this.fillAmount = fillAmount;
		}
		public float getDrainAmount() {
			return drainAmount;
		}
		public void setDrainAmount(float drainAmount) {
			this.drainAmount = drainAmount;
		}
		public float getUf() {
			return uf;
		}
		public void setUf(float uf) {
			this.uf = uf;
		}
		public String getPatientPatID() {
			return patientPatID;
		}
		public void setPatientPatID(String patientPatID) {
			this.patientPatID = patientPatID;
		}
		
		public long getProgramId() {
			return programId;
		}
		public void setProgramId(long programId) {
			this.programId = programId;
		}
		@Override
		public String toString() {
			return "TreatmentDTO [id=" + id + ", date=" + date + "]";
		}
		
	

}
