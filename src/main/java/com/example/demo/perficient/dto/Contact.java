package com.example.demo.perficient.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Contact {

	@Id
	@GeneratedValue
	Long id;
	
	@NotNull
	@NotBlank(message = "first name is required")
	@Size(min=3, max=30, message="El nombre debe tener entre {min} y {max} caracteres")
	String firstName;
	
	String lastName;
	
	@Pattern(regexp="^\\+[0-9]*$", message="El número de telefono sólo puede tener dígitos iniciando con el símbolo +")
	String phoneNumber;
	
	@Email(message ="Email inválido")
	String email;
	
	public Contact(){
		super();
		}

	public Contact(long l, String firstName, String lastName, String phoneNumber, String email) {
		setId(l);
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setEmail(email);
	}

	public Contact( String firstName, String lastName, String phoneNumber, String email) {
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setEmail(email);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
