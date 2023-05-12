/**
 * 
 */
package com.example.cutomerservice.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ramkumar
 *
 */
@Data
public class BaseDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 572852843920207102L;

	Integer statusCode;

	String message;

	String errorDescription;

	Object responseContent;

}
