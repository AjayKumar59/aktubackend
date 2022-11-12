package com.study.aktu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.study.aktu.model.Paper;
import com.study.aktu.model.Quantum;
import com.study.aktu.repo.QuantumRepository;


 

@RestController
@RestControllerAdvice
public class QuantumController {

	@Autowired
	QuantumRepository quantumRepository;

	@PostMapping("/quantum")
	public ResponseEntity<?> addQuantum(@RequestBody Quantum quantum) {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("message", "quantum add successfully !!");
		quantumRepository.save(quantum);
		return new ResponseEntity<HashMap<String, String>>(data, HttpStatus.OK);
		
	

	}

	@PostMapping("/quantums/posts")
	public String addQuantum(@RequestBody List<Quantum> quantum) {
		quantumRepository.saveAll(quantum);
		return "aLL quantums add successfully !!";

	}

	@GetMapping("/quantum")
	public ResponseEntity<?> getAllQuantum() {
		List<Quantum> quantums = this.quantumRepository.findAll();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("count", quantums.size());
		data.put("quantums", quantums);
		Object datanotfound = "data not found";
		if (quantums.size() == 0) {
			data.put("message", datanotfound);
		}
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);

	}


	@GetMapping("/quantum/search/{keyword}")
	public ResponseEntity<?> search(@PathVariable("keyword") String keyword) {
		List<Quantum> quantums = new ArrayList<>();
		this.quantumRepository
				.findAllByNameContainingIgnoreCaseOrCourseContainingIgnoreCaseOrBranchContainingIgnoreCase(
						keyword, keyword,keyword)
				.forEach(quantums::add);

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("count", quantums.size());
		data.put("quantums", quantums);
		Object datanotfound = "paper not found";
		if (quantums.size() == 0) {
			data.put("message", datanotfound);
		}
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);
	}
	
	@GetMapping("/quantum/{id}")
	public ResponseEntity<?> getByIdQuantum(@PathVariable("id") Long id) {
		Optional<Quantum> quantums = this.quantumRepository.findById(id);
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("quantums", quantums);
		Object datanotfound = "this item is no more !!";
		if (quantums.isEmpty() == true) {
			data.put("message", datanotfound);
		}
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);

	}


	@PutMapping("/quantum/{id}")
	public ResponseEntity<?> updateQuantum(@RequestBody Quantum newQuantum, @PathVariable("id") Long id) {
		
		Optional<Quantum> q = this.quantumRepository.findById(id);
		HashMap<String, String> data = new HashMap<String, String>();
		if (q.isEmpty() == true) {
			data.put("message", "id not found with id:- " + id);
		} else {
			data.put("message", "quantum updated successfully !!");

			Quantum quantum= quantumRepository.findById(id).get();
			quantum.setBranch(newQuantum.getBranch());
			quantum.setCourse(newQuantum.getCourse());
			quantum.setImage(newQuantum.getImage());
			quantum.setName(newQuantum.getName());
			quantum.setPrice(newQuantum.getPrice());
			quantum.setSem(newQuantum.getSem());
			quantum.setSubcode(newQuantum.getSubcode());
			quantum.setUrl(newQuantum.getUrl());
			quantumRepository.save(quantum);
		}
		return new ResponseEntity<HashMap<String, String>>(data, HttpStatus.OK);
		
	

	}

	@DeleteMapping("/quantum/{id}")
	public ResponseEntity<?> deleteByIdQuantum(@PathVariable("id") Long id) {
		Optional<Quantum> quantum = this.quantumRepository.findById(id);
		HashMap<String, String> data = new HashMap<String, String>();

		if (quantum.isEmpty() == true) {
			data.put("message", "id not found with id:- " + id);
		} else {
			data.put("message", "Quantum deleted successfully !!");
			quantumRepository.deleteById(id);

		}
		return new ResponseEntity<HashMap<String, String>>(data, HttpStatus.OK);

		

	}
	

	}
