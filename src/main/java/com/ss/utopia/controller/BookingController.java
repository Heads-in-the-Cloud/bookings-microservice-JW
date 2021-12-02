package com.ss.utopia.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

import com.ss.utopia.dao.BookingAgentRepository;
import com.ss.utopia.entity.Booking;
import com.ss.utopia.entity.BookingPayment;
import com.ss.utopia.entity.Flight;
import com.ss.utopia.entity.FlightBookings;
import com.ss.utopia.entity.Passenger;
import com.ss.utopia.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	BookingService bookingService;
	@Autowired 
	BookingAgentRepository bookingAgentRepo;
	@GetMapping
	public @ResponseBody Iterable<Booking> getBookings(){
		return bookingService.getBookings();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Booking> getBookingById(@PathVariable Integer id){
		return bookingService.getBookingById(id);
	}
	
	@GetMapping("/payment")
	public @ResponseBody Iterable<BookingPayment> getPayments(){
		return bookingService.getPayments();
	}
	
	@GetMapping("/flightbookings")
	public @ResponseBody Iterable<FlightBookings> getFlightBookings(){
		return bookingService.getFlightBookings();
	}
	@GetMapping("/passengers")
	public @ResponseBody Iterable<Passenger> getPassengers(){
		return bookingService.getPassengers();
	}
	@PostMapping
	public @ResponseBody ResponseEntity<String> addBooking(@RequestBody BookingInput input, @RequestAttribute("role") Integer role, @RequestAttribute("id") Integer id){
		return bookingService.createBooking(input.getPayment(),input.getPassengers(),input.getFlightIds(), role, id);
	}
	
	@PutMapping
	public @ResponseBody ResponseEntity<String> updateBooking(@RequestParam Integer id, @RequestParam Integer status){
		return bookingService.updateBooking(id, status);
	}
//	
//	@DeleteMapping("/{id}")
//	public @ResponseBody ResponseEntity<String> deleteBooking(@PathVariable Integer id) {
//		return bookingService.deleteBooking(id);
//	}
}
