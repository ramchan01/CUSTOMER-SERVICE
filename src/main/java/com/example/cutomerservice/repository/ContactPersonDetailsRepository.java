/**
 * 
 */
package com.example.cutomerservice.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.cutomerservice.entity.ContactPersonDetailsEntity;

/**
 * @author Ramkumar
 *
 */
@Repository
public interface ContactPersonDetailsRepository extends JpaRepository<ContactPersonDetailsEntity, Long>{

}
