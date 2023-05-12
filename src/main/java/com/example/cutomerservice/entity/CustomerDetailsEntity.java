/**
 * 
 */
package com.example.cutomerservice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Ramkumar
 *
 */
@Entity
@Table(name = "customer_details")
@Data
public class CustomerDetailsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -60785029133824483L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "register_number")
	private String registerNumber;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "gender")
	private String gender;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "mobile_number")
	private Long mobileNumber;

	@Column(name = "village")
	private String village;

	@Column(name = "district")
	private String district;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "version")
	private Integer version;

	@JsonManagedReference
	@OneToMany(mappedBy = "customerDetailId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ContactPersonDetailsEntity> contactPersonDetailsEntity = new ArrayList<>();

}
