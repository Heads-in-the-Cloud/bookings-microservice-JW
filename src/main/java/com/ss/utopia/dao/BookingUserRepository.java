package com.ss.utopia.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ss.utopia.entity.BookingUser;

public interface BookingUserRepository extends JpaRepository<BookingUser, Integer>{
	@Modifying
	@Transactional
	@Query(nativeQuery =true, value="insert into booking_user (booking_id,user_id) values (:booking_id, :id)")
	void insertVal(@Param("booking_id")Integer booking_id, @Param("id") Integer id);
}
