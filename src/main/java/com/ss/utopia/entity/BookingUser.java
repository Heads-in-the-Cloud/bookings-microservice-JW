package com.ss.utopia.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="booking_user")
public class BookingUser {
	@Id
	private Integer booking_id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public Integer getUserId() {
		return user.getId();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getBookingId() {
		return booking_id;
	}

	public void setBooking_Id(Integer booking_id) {
		this.booking_id = booking_id;
	}
}