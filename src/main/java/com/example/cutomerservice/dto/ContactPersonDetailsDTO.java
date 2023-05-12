/**
 * 
 */
package com.example.cutomerservice.dto;

import java.io.Serializable;
import java.util.Date;

import com.example.cutomerservice.entity.CustomerDetailsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author Ramkumar
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactPersonDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2156429810807354884L;
	
	private Long id;

	private String contactPersonName;

	private Long mobileNumber;

	private String district;

	private Long createdBy;

	private Date createdDate;

	private Date modifiedDate;

	private Integer version;

	private CustomerDetailsEntity customerDetailId;

}
