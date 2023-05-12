package com.example.cutomerservice.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author Ramkumar
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDetailsRequestDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4620140234071267129L;
	
	private Long id;

	private String registerNumber;

	private String customerName;

	private String gender;

	private Date dateOfBirth;

	private Long mobileNumber;

	private String village;

	private String district;

	private Date createdDate;

	private Date modifiedDate;

	private Integer version;

	private List<ContactPersonDetailsDTO> contactPersonDetailsEntity = new ArrayList<>();

}
