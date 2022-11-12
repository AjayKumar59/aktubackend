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
import com.study.aktu.repo.PaperRepository;

@RestController
@RestControllerAdvice
public class PaperController {

	@Autowired
	PaperRepository paperRepository;

	@PostMapping("/paper")
	public ResponseEntity <?> addPaper(@RequestBody Paper paper) {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("message", "paper add successfully !!");
		paperRepository.save(paper);
		return new ResponseEntity<HashMap<String, String>>(data, HttpStatus.OK);
		
		

	}

	@PostMapping("/papers/posts")
	public String addPapers(@RequestBody List<Paper> paper) {
		paperRepository.saveAll(paper);
		return "aLL paper add successfully !!";

	}

	@GetMapping("/paper")
	public ResponseEntity<?> getAllPapers() {
		List<Paper> paper = this.paperRepository.findAll();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("count", paper.size());
		data.put("papers", paper);
		Object datanotfound = "papers not found !!";
		if (paper.size() == 0) {
			data.put("message", datanotfound);
		}
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);

	}

	@GetMapping("/paper/search/{keyword}")
	public ResponseEntity<?> search(@PathVariable("keyword") String keyword) {
		List<Paper> paper = new ArrayList<>();
		this.paperRepository
				.findAllByPapernameContainingIgnoreCaseOrSemesterContainingIgnoreCaseOrBranchContainingIgnoreCaseOrSessionContainingIgnoreCase(
						keyword, keyword, keyword, keyword)
				.forEach(paper::add);

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("count", paper.size());
		data.put("papers", paper);
		Object datanotfound = "paper not found";
		if (paper.size() == 0) {
			data.put("message", datanotfound);
		}
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);
	}

	@GetMapping("/paper/{id}")
	public ResponseEntity<?> getByIdPaper(@PathVariable("id") Long id) {
		Optional<Paper> paper = this.paperRepository.findById(id);
		HashMap<String, Object> data = new HashMap<String, Object>();

		data.put("papers", paper);
		Object datanotfound = "this paper not exits  !!";
		if (paper.isEmpty() == true) {
			data.put("message", datanotfound);
		}
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);

	}

	@PutMapping("/paper/{id}")
	public ResponseEntity<?> updateQuantum(@RequestBody Paper newPaper, @PathVariable Long id) {
		Optional<Paper> p = this.paperRepository.findById(id);
		HashMap<String, String> data = new HashMap<String, String>();
		if (p.isEmpty() == true) {
			data.put("message", "id not found with id:- " + id);
		} else {
			data.put("message", "paper updated successfully !!");

			Paper paper = paperRepository.findById(id).get();

			paper.setBranch(newPaper.getBranch());
			paper.setCourse(newPaper.getCourse());
			paper.setImage(newPaper.getImage());
			paper.setPapername(newPaper.getPapername());
			paper.setSemester(newPaper.getSemester());
			paper.setSession(newPaper.getSession());
			paper.setSession(newPaper.getSubcode());
			paperRepository.save(paper);
		}
		return new ResponseEntity<HashMap<String, String>>(data, HttpStatus.OK);

	}

	@DeleteMapping("/paper/{id}")
	public ResponseEntity<?> deleteByIdQuantum(@PathVariable("id") Long id) {
		Optional<Paper> paper = this.paperRepository.findById(id);
		HashMap<String, String> data = new HashMap<String, String>();

		if (paper.isEmpty() == true) {
			data.put("message", "id not found with id:- " + id);
		} else {
			data.put("message", "Paper deleted successfully !!");
			paperRepository.deleteById(id);

		}
		return new ResponseEntity<HashMap<String, String>>(data, HttpStatus.OK);

	}


}
