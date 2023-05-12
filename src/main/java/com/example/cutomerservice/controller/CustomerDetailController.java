/**
 * 
 */
package com.example.cutomerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cutomerservice.dto.BaseDTO;
import com.example.cutomerservice.dto.CustomerDetailsRequestDTO;
import com.example.cutomerservice.services.CustomerDetailService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * @author Ramkumar
 *
 */
@RestController
@RequestMapping("/customerdetails")
@Log4j2
public class CustomerDetailController {

	@Autowired
	private CustomerDetailService customerDetailService;

	/**
	 * @return
	 */
	@PostMapping(value = "/save")
	@ApiOperation(value = "This api is used to save the customer details.")
	public BaseDTO saveCustomerDetails(@RequestBody CustomerDetailsRequestDTO customerDetailsRequestDTO) {

		log.info("Inside CustomerDetailController-saveCustomerDetails() - START");

		BaseDTO baseDTO = new BaseDTO();
		baseDTO = customerDetailService.saveCustomerDetails(customerDetailsRequestDTO);

		log.info("Inside CustomerDetailController-saveCustomerDetails() - END");

		return baseDTO;

	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/getcustomerdetailbyid/{id}")
	@ApiOperation(value = "This api is used to get single customer details using id.")
	public BaseDTO getCustomerDetailById(@PathVariable("id") Long id) {

		log.info("Inside CustomerDetailController-getCustomerDetailById() - START");

		BaseDTO baseDTO = new BaseDTO();
		baseDTO = customerDetailService.getCustomerDetailById(id);

		log.info("Inside CustomerDetailController-getCustomerDetailById() - END");

		return baseDTO;

	}

	/**
	 * @param id
	 * @param customerDetailsRequestDTO
	 * @return
	 */
	@PutMapping(value = "/updatecustomerdetail/{id}")
	@ApiOperation(value = "This api is used to update the customer details using id.")
	public BaseDTO upadteCustomerDetails(@PathVariable("id") Long id,
			@RequestBody CustomerDetailsRequestDTO customerDetailsRequestDTO) {
		
		log.info("Inside CustomerDetailController-saveCustomerDetails() - START");

		BaseDTO baseDTO = new BaseDTO();
		baseDTO = customerDetailService.upadteCustomerDetails(id, customerDetailsRequestDTO);

		log.info("Inside CustomerDetailController-saveCustomerDetails() - END");

		return baseDTO;

	}
	
	
	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/deletecustomerdetail/{id}")
	@ApiOperation(value = "This api is used to delete the customer details using id.")
	public BaseDTO deleteCustomerDetails(@PathVariable("id") Long id) {
		log.info("Inside CustomerDetailController-saveCustomerDetails() - START");

		BaseDTO baseDTO = new BaseDTO();
		baseDTO = customerDetailService.deleteCustomerDetails(id);

		log.info("Inside CustomerDetailController-saveCustomerDetails() - END");

		return baseDTO;
	}

}
