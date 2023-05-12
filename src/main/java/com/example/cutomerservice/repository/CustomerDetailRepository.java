/**
 * 
 */
package com.example.cutomerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cutomerservice.entity.CustomerDetailsEntity;

/**
 * @author Ramkumar
 *
 */
@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetailsEntity, Long>{

}
