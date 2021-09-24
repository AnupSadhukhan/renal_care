package com.renal.care.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.renal.care.utils.DeserializeBooleanValue;
import com.renal.care.utils.SerializeBooleanValue;

public class ProgramDTO implements Serializable{
	private long id;
	private String name;
	private float fillAmount;
	private boolean isActive;
	private long patientId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getFillAmount() {
		return fillAmount;
	}
	public void setFillAmount(float fillAmount) {
		this.fillAmount = fillAmount;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	
}
