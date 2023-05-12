/**
 * 
 */
package com.example.cutomerservice.services;

import com.example.cutomerservice.dto.BaseDTO;
import com.example.cutomerservice.dto.CustomerDetailsRequestDTO;

/**
 * @author Ramkumar
 *
 */
public interface CustomerDetailService {

	/**
	 * @return
	 */
	public BaseDTO saveCustomerDetails(CustomerDetailsRequestDTO customerDetailsRequestDTO);

	/**
	 * @param id
	 * @return
	 */
	public BaseDTO getCustomerDetailById(Long id);

	/**
	 * @param id
	 * @param customerDetailsRequestDTO
	 * @return
	 */
	public BaseDTO upadteCustomerDetails(Long id, CustomerDetailsRequestDTO customerDetailsRequestDTO);

	/**
	 * @param id
	 * @return
	 */
	public BaseDTO deleteCustomerDetails(Long id);

}
