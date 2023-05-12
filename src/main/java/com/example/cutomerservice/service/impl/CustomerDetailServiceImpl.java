/**
 * 
 */
package com.example.cutomerservice.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cutomerservice.dto.BaseDTO;
import com.example.cutomerservice.dto.ContactPersonDetailsDTO;
import com.example.cutomerservice.dto.CustomerDetailsRequestDTO;
import com.example.cutomerservice.entity.ContactPersonDetailsEntity;
import com.example.cutomerservice.entity.CustomerDetailsEntity;
import com.example.cutomerservice.exception.CustomerServiceException;
import com.example.cutomerservice.repository.ContactPersonDetailsRepository;
import com.example.cutomerservice.repository.CustomerDetailRepository;
import com.example.cutomerservice.services.CustomerDetailService;

import lombok.extern.log4j.Log4j2;

/**
 * @author Ramkumar
 *
 */
@Service
@Log4j2
public class CustomerDetailServiceImpl implements CustomerDetailService {

	@Autowired
	private CustomerDetailRepository customerDetailRepository;

	@Autowired
	private ContactPersonDetailsRepository contactPersonDetailsRepository;

	/**
	 *
	 */
	@Override
	public BaseDTO saveCustomerDetails(CustomerDetailsRequestDTO customerDetailsRequestDTO) {

		log.info("Inside saveCustomerDetails() - START");
		log.info("customerDetailsRequestDTO: {}", customerDetailsRequestDTO);

		String errorMessage = null;
		BaseDTO baseDTO = null;
		try {
			baseDTO = validateAndSaveCustomerDetails(customerDetailsRequestDTO);
		} catch (CustomerServiceException customerServiceException) {
			errorMessage = customerServiceException.getMessage();
		} catch (Exception ex) {
			errorMessage = "Something went wrong. Unable to save customer details.";
			log.error("Exception at saveCustomerDetails()", ex);
		}

		if (StringUtils.isEmpty(errorMessage) && baseDTO == null) {
			errorMessage = "Unknown error. Unable to save customer details.";
		}

		if (StringUtils.isNotEmpty(errorMessage)) {
			log.error("saveCustomerDetails()-Error: {}", errorMessage);
			baseDTO = new BaseDTO();
			baseDTO.setStatusCode(1);
			baseDTO.setErrorDescription(errorMessage);
		}

		log.info("Inside saveCustomerDetails() - END");

		return baseDTO;
	}

	/**
	 * @param customerDetailsRequestDTO
	 * @return
	 * @throws Exception
	 */
	private BaseDTO validateAndSaveCustomerDetails(CustomerDetailsRequestDTO customerDetailsRequestDTO)
			throws Exception {

		log.info("Inside validateAndsaveCustomerDetails() - START");

		if (StringUtils.isEmpty(customerDetailsRequestDTO.getCustomerName())) {
			throw new CustomerServiceException("Please enter valid name.");
		}

		if (StringUtils.isEmpty(customerDetailsRequestDTO.getGender())) {
			throw new CustomerServiceException("Please enter gender detail.");
		}

		if (StringUtils.isEmpty(customerDetailsRequestDTO.getVillage())) {
			throw new CustomerServiceException("Please enter village details.");
		}

		if (StringUtils.isEmpty(customerDetailsRequestDTO.getDistrict())) {
			throw new CustomerServiceException("Please enter district details.");
		}

		if (customerDetailsRequestDTO.getDateOfBirth() == null) {
			throw new CustomerServiceException("Please select the Date of Birth.");
		}

		int length = Long.toString(customerDetailsRequestDTO.getMobileNumber()).length();

		if (customerDetailsRequestDTO.getMobileNumber() == null || length > 10 || length < 10) {
			throw new CustomerServiceException("Please enter the valid mobile number");
		}

		if (customerDetailsRequestDTO.getContactPersonDetailsEntity() == null) {
			throw new CustomerServiceException("Please enter Contact Person details.");
		}

		String registerNumber = generateRegisterNumber(customerDetailsRequestDTO.getCustomerName());

		customerDetailsRequestDTO.setRegisterNumber(registerNumber);
		customerDetailsRequestDTO.setCreatedDate(new Date());
		customerDetailsRequestDTO.setVersion(0);

		CustomerDetailsEntity customerDetailsEntity = new CustomerDetailsEntity();

		BeanUtils.copyProperties(customerDetailsRequestDTO, customerDetailsEntity);

		List<ContactPersonDetailsDTO> contactPersonDetailsDTOList = new ArrayList<ContactPersonDetailsDTO>();
		contactPersonDetailsDTOList = customerDetailsRequestDTO.getContactPersonDetailsEntity();
		List<ContactPersonDetailsEntity> contactPersonDetailsEntitieList = new ArrayList<ContactPersonDetailsEntity>();

		for (ContactPersonDetailsDTO contactPersonDetailsDTO : contactPersonDetailsDTOList) {
			ContactPersonDetailsEntity contactPersonDetailsEntity = new ContactPersonDetailsEntity();
			contactPersonDetailsEntity.setContactPersonName(contactPersonDetailsDTO.getContactPersonName());
			contactPersonDetailsEntity.setCustomerDetailId(customerDetailsEntity);
			contactPersonDetailsEntity.setDistrict(contactPersonDetailsDTO.getDistrict());
			contactPersonDetailsEntity.setMobileNumber(contactPersonDetailsDTO.getMobileNumber());
			contactPersonDetailsEntity.setCreatedDate(new Date());
			contactPersonDetailsEntity.setVersion(0);

			contactPersonDetailsEntitieList.add(contactPersonDetailsEntity);
		}

		customerDetailsEntity.setContactPersonDetailsEntity(contactPersonDetailsEntitieList);

		customerDetailsEntity = customerDetailRepository.save(customerDetailsEntity);

		log.info("customerDetailsEntity: {}", customerDetailsEntity);

		BaseDTO baseDTO = new BaseDTO();

		baseDTO.setStatusCode(0);
		baseDTO.setMessage("Customer details saved successfully.");

		log.info("Inside validateAndsaveCustomerDetails() - END");
		return baseDTO;
	}

	private static final String DATE_FORMAT = "MMMddyyyy";

	/**
	 * @param customerName
	 * @return
	 */
	private String generateRegisterNumber(String customerName) {

		log.info("Inside generateRegisterNumber() - START");

		String dateAndYear = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		String randomNumber = StringUtils.leftPad(String.valueOf(new Random().nextInt(99999999)), 8, '0');

		String registerNumber = customerName.substring(0, 3).concat("/").concat(dateAndYear).concat("/")
				.concat(randomNumber);
		registerNumber = registerNumber.toUpperCase();

		log.info("Inside generateRegisterNumber() - END");

		return registerNumber;
	}

	/**
	 *
	 */
	@Override
	public BaseDTO getCustomerDetailById(Long id) {

		log.info("Inside getCustomerDetailById() - START");
		log.info("id: {}", id);

		String errorMessage = null;
		BaseDTO baseDTO = null;
		try {
			baseDTO = validateAndGetCustomerDetailById(id);
		} catch (CustomerServiceException customerServiceException) {
			errorMessage = customerServiceException.getMessage();
		} catch (Exception ex) {
			errorMessage = "Something went wrong. Unable to get customer details.";
			log.error("Exception at getCustomerDetailById()", ex);
		}

		if (StringUtils.isEmpty(errorMessage) && baseDTO == null) {
			errorMessage = "Unknown error. Unable to get customer details.";
		}

		if (StringUtils.isNotEmpty(errorMessage)) {
			log.error("getCustomerDetailById()-Error: {}", errorMessage);
			baseDTO = new BaseDTO();
			baseDTO.setStatusCode(1);
			baseDTO.setErrorDescription(errorMessage);
		}

		log.info("Inside getCustomerDetailById() - END");

		return baseDTO;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private BaseDTO validateAndGetCustomerDetailById(Long id) throws Exception {

		log.info("Inside validateAndGetCustomerDetailById() - START");

		CustomerDetailsEntity customerDetailsEntity = new CustomerDetailsEntity();

		customerDetailsEntity = customerDetailRepository.findById(id).get();

		if (customerDetailsEntity == null) {
			throw new CustomerServiceException("Data not found.");
		}

		BaseDTO baseDTO = new BaseDTO();

		baseDTO.setStatusCode(0);
		baseDTO.setMessage("Data fetched ssuccessfully.");
		baseDTO.setResponseContent(customerDetailsEntity);

		log.info("Inside validateAndGetCustomerDetailById() - END");
		return baseDTO;
	}

	/**
	 *
	 */
	@Override
	public BaseDTO upadteCustomerDetails(Long id, CustomerDetailsRequestDTO customerDetailsRequestDTO) {

		log.info("Inside upadteCustomerDetails() - START");
		log.info("id: {}", id);

		String errorMessage = null;
		BaseDTO baseDTO = null;
		try {
			baseDTO = validateAndupadteCustomerDetails(id, customerDetailsRequestDTO);
		} catch (CustomerServiceException customerServiceException) {
			errorMessage = customerServiceException.getMessage();
		} catch (Exception ex) {
			errorMessage = "Something went wrong. Unable to update customer details.";
			log.error("Exception at upadteCustomerDetails()", ex);
		}

		if (StringUtils.isEmpty(errorMessage) && baseDTO == null) {
			errorMessage = "Unknown error. Unable to update customer details.";
		}

		if (StringUtils.isNotEmpty(errorMessage)) {
			log.error("upadteCustomerDetails()-Error: {}", errorMessage);
			baseDTO = new BaseDTO();
			baseDTO.setStatusCode(1);
			baseDTO.setErrorDescription(errorMessage);
		}

		log.info("Inside upadteCustomerDetails() - END");

		return baseDTO;
	}

	/**
	 * @param id
	 * @param customerDetailsRequestDTO
	 * @return
	 * @throws Exception
	 */
	private BaseDTO validateAndupadteCustomerDetails(Long id, CustomerDetailsRequestDTO customerDetailsRequestDTO)
			throws Exception {

		log.info("Inside validateAndupadteCustomerDetails() - START");

		CustomerDetailsEntity customerDetailsEntity = new CustomerDetailsEntity();

		customerDetailsEntity = customerDetailRepository.findById(id).get();

		if (customerDetailsEntity == null) {
			throw new CustomerServiceException("Data not found.");
		}

		customerDetailsEntity.getContactPersonDetailsEntity().clear();

		if (StringUtils.isEmpty(customerDetailsRequestDTO.getCustomerName())) {
			throw new CustomerServiceException("Please enter valid name.");
		}

		if (StringUtils.isEmpty(customerDetailsRequestDTO.getGender())) {
			throw new CustomerServiceException("Please enter gender detail.");
		}

		if (StringUtils.isEmpty(customerDetailsRequestDTO.getVillage())) {
			throw new CustomerServiceException("Please enter village details.");
		}

		if (StringUtils.isEmpty(customerDetailsRequestDTO.getDistrict())) {
			throw new CustomerServiceException("Please enter district details.");
		}

		if (customerDetailsRequestDTO.getDateOfBirth() == null) {
			throw new CustomerServiceException("Please select the Date of Birth.");
		}

		int length = Long.toString(customerDetailsRequestDTO.getMobileNumber()).length();

		if (customerDetailsRequestDTO.getMobileNumber() == null || length > 10 || length < 10) {
			throw new CustomerServiceException("Please enter the valid mobile number");
		}

		if (customerDetailsRequestDTO.getContactPersonDetailsEntity() == null) {
			throw new CustomerServiceException("Please enter Contact Person details.");
		}

		customerDetailsRequestDTO.setModifiedDate(new Date());
		customerDetailsRequestDTO.setRegisterNumber(customerDetailsEntity.getRegisterNumber());
		customerDetailsRequestDTO.setCreatedDate(customerDetailsEntity.getCreatedDate());

		BeanUtils.copyProperties(customerDetailsRequestDTO, customerDetailsEntity);

		List<ContactPersonDetailsDTO> contactPersonDetailsDTOList = new ArrayList<ContactPersonDetailsDTO>();
		contactPersonDetailsDTOList = customerDetailsRequestDTO.getContactPersonDetailsEntity();
		List<ContactPersonDetailsEntity> contactPersonDetailsEntitieList = new ArrayList<ContactPersonDetailsEntity>();

		for (ContactPersonDetailsDTO contactPersonDetailsDTO : contactPersonDetailsDTOList) {
			ContactPersonDetailsEntity contactPersonDetailsEntity = new ContactPersonDetailsEntity();

			if (contactPersonDetailsDTO.getId() == null) {
				contactPersonDetailsEntity.setContactPersonName(contactPersonDetailsDTO.getContactPersonName());
				contactPersonDetailsEntity.setCustomerDetailId(customerDetailsEntity);
				contactPersonDetailsEntity.setDistrict(contactPersonDetailsDTO.getDistrict());
				contactPersonDetailsEntity.setMobileNumber(contactPersonDetailsDTO.getMobileNumber());
				contactPersonDetailsEntity.setModifiedDate(null);
				contactPersonDetailsEntity.setVersion(0);

				contactPersonDetailsEntitieList.add(contactPersonDetailsEntity);
			} else {

				contactPersonDetailsEntity = contactPersonDetailsRepository.findById(contactPersonDetailsDTO.getId())
						.get();

				contactPersonDetailsEntity.setContactPersonName(contactPersonDetailsDTO.getContactPersonName());
				contactPersonDetailsEntity.setCustomerDetailId(customerDetailsEntity);
				contactPersonDetailsEntity.setDistrict(contactPersonDetailsDTO.getDistrict());
				contactPersonDetailsEntity.setMobileNumber(contactPersonDetailsDTO.getMobileNumber());
				contactPersonDetailsEntity.setModifiedDate(new Date());
				contactPersonDetailsEntity.setVersion(0);

				contactPersonDetailsEntitieList.add(contactPersonDetailsEntity);
			}
		}

		customerDetailsEntity.setContactPersonDetailsEntity(contactPersonDetailsEntitieList);

		customerDetailsEntity = customerDetailRepository.save(customerDetailsEntity);

		log.info("customerDetailsEntity: {}", customerDetailsEntity);

		BaseDTO baseDTO = new BaseDTO();

		baseDTO.setStatusCode(0);
		baseDTO.setMessage("Customer details updated successfully.");

		log.info("Inside validateAndupadteCustomerDetails() - END");

		return baseDTO;
	}

	/**
	 *
	 */
	@Override
	public BaseDTO deleteCustomerDetails(Long id) {

		log.info("Inside deleteCustomerDetails() - START");
		log.info("id: {}", id);

		String errorMessage = null;
		BaseDTO baseDTO = null;
		try {
			baseDTO = validateAndDeleteCustomerDetails(id);
		} catch (CustomerServiceException customerServiceException) {
			errorMessage = customerServiceException.getMessage();
		} catch (Exception ex) {
			errorMessage = "Something went wrong. Unable to delete customer details.";
			log.error("Exception at deleteCustomerDetails()", ex);
		}

		if (StringUtils.isEmpty(errorMessage) && baseDTO == null) {
			errorMessage = "Unknown error. Unable to delete customer details.";
		}

		if (StringUtils.isNotEmpty(errorMessage)) {
			log.error("deleteCustomerDetails()-Error: {}", errorMessage);
			baseDTO = new BaseDTO();
			baseDTO.setStatusCode(1);
			baseDTO.setErrorDescription(errorMessage);
		}

		log.info("Inside saveCustomerDetails() - END");

		return baseDTO;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private BaseDTO validateAndDeleteCustomerDetails(Long id) throws Exception {
		
		log.info("Inside validateAndDeleteCustomerDetails() - START");
		
		customerDetailRepository.deleteById(id);
		
		BaseDTO baseDTO = new BaseDTO();
		baseDTO.setStatusCode(0);
		baseDTO.setMessage("Customer Details Deleted.");
		
		log.info("Inside validateAndDeleteCustomerDetails() - END");
		return baseDTO;
	}

}
