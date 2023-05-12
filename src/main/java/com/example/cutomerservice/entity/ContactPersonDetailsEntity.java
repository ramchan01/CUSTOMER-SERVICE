/**
 * 
 */
package com.example.cutomerservice.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

/**
 * @author Ramkumar
 *
 */
@Entity
@Table(name = "contact_person_details")
@Data
public class ContactPersonDetailsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1890005607247418508L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "contact_person_name")
	private String contactPersonName;

	@Column(name = "mobile_number")
	private Long mobileNumber;

	@Column(name = "district")
	private String district;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "version")
	private Integer version;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "customer_details_id")
	@ToString.Exclude
	private CustomerDetailsEntity customerDetailId;

}
