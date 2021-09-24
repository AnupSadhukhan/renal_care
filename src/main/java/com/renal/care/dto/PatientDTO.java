package com.renal.care.dto;

import java.io.Serializable;
import java.security.SecureRandom;



import org.slf4j.LoggerFactory;

import com.renal.care.models.Patient;

import ch.qos.logback.classic.Logger;

public class PatientDTO implements Serializable{
	private static final Logger log = (Logger) LoggerFactory.getLogger(Patient.class);
	
	private Long id;
	private String patID;
	private String name;
	private long mobile;
	private String email;
	private String glucoseLevel;
	private String bp;
	private String pulseRate;
	
	public PatientDTO() {
		super();
		
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getGlucoseLevel() {
		return glucoseLevel;
	}


	public void setGlucoseLevel(String glucoseLevel) {
		this.glucoseLevel = glucoseLevel;
	}


	public String getPatID() {
		return patID;
	}


	public void setPatID(String patID) {
		this.patID = patID;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBp() {
		return bp;
	}
	public void setBp(String bp) {
		this.bp = bp;
	}
	public String getPulseRate() {
		return pulseRate;
	}
	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
	}

	@Override
	public String toString() {
		return "PatientDTO [name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", glucoseLevel=" + glucoseLevel + ", bp=" + bp + ", pulseRate=" + pulseRate + "]";
	}
	
	
}
