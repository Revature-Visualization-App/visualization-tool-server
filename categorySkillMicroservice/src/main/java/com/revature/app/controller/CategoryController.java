package com.revature.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.app.dto.CategoryDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.exception.CategoryNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;
import com.revature.app.service.CategoryService;

@CrossOrigin(origins = "*")
@RequestMapping("category")
@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	private static Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	String goodLog = "User called the endpoint ";

	//@PostMapping(path = "category")
	@PostMapping(path = "/add")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Category addCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryBlankInputException {
		try {
			Category category = categoryService.addCategory(categoryDTO);
			String logString = String.format(goodLog, "to add a category to the database");
			logger.info(logString);
			return category;
		} catch (CategoryBlankInputException e) {
			logger.warn("User left a parameter blank while trying to add a category to the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	//this get all categogies for all users
	//if use this then go to category service and the method for this not taking user id
	//@GetMapping(path = "category")
//	@GetMapping(path = "/user/{id}")
//	public List<Category> getAllCategories() {
//		logger.info("User called the endpoint to get all categories from the database");
//		return categoryService.getAllCategories();
//	}
	
	
	
	//this get all categories based on user id
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Category>> getUserCategories(@PathVariable("id")int id){
		return new ResponseEntity<List<Category>>(  categoryService.getAllCategories(id), HttpStatus.OK);
	}	
	@GetMapping(path="/{id}")
	public Object getCategoryByID(@PathVariable("id") String categoryID) {

			int  categoryIDInt= Integer.parseInt(categoryID);
			Category cate = categoryService.findCategory(categoryIDInt);
			String logString = String.format(goodLog, "to get information about a category in the database with id %s");
			logString = String.format(logString, categoryID);
			logger.info(logString);
			return cate;
	}

	
	
	//@PutMapping(path = "category/{id}")

	@PutMapping(path = "/{id}")
	public Category updateCategory(@PathVariable("id") String id, @RequestBody CategoryDTO categoryDTO) {
		try {
			Category category = categoryService.updateCategory(id, categoryDTO);
			String logString = String.format(goodLog, "to update a category in the database with id %s");
			logString = String.format(logString, id);
			logger.info(logString);
			return category;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to update a category in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to update a category in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CategoryNotFoundException e) {
			logger.warn("User asked for information about a category in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public Object deleteCategory(@PathVariable("id") String id) {
		try {
			categoryService.deleteCategory(id);
			String logString = String.format(goodLog, "to delete a category from the database with id %s");
			logString = String.format(logString, id);
			logger.info(logString);
			return id;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to delete a category from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CategoryNotFoundException e) {
			logger.warn("User attempted to delete a category in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to delete a category from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (ForeignKeyConstraintException e) {
			logger.warn("User attempted to delete a category from the database but it was blocked because of a foreign key constraint");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
