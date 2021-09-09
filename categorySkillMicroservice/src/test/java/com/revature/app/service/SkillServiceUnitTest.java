package com.revature.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.app.dao.SkillDAO;
import com.revature.app.dto.SkillDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;


@ExtendWith(MockitoExtension.class)
class SkillServiceUnitTest {
	
	@Mock
	private SkillDAO mockSkillDAO;
	
	@InjectMocks
	private SkillService skillService;
	
	@BeforeAll
	public static void setUp() {
		
	}
	
	
	
	@BeforeEach
		public void beforeTest() {
		
		Skill skill1 =  new Skill(1, "",new Category(1, "TestCat", "testcatDescription", 4) ,4);

		Skill skill2 = new Skill(0, "", new Category(1, "TestCat", "testcatDescription", 4), 4);
		
		
		Skill skill4=  new Skill(1, "TestSkill",new Category(1, "TestCat", "testcatDescription", 4) ,4);
		SkillDTO skillDTO1 = new SkillDTO("TestSkill", new Category(1, "TestCat","testcatDescription",4),4);
		

		//SkillDTO skillDTO3 = new SkillDTO("", null,0);
	
	//	Skill skill4 = new Skill( "TestSkill", new Category(1, "TestCat", "testcatDescription", 4),4);
	//SkillDTO skillDTO1 =new SkillDTO("TestSkill", new Category( "TestCat", "testcatDescription",4),4);
	
		
		SkillDTO skillDTO2 = new SkillDTO("Testskill", new Category(1, "TestCat", null,4 ),4);

	
	
	//		SkillDTO skillDTO3 = new SkillDTO("Test", new Category(0, "BadCat", null));
	//SkillDTO skillDTO3 = new SkillDTO("TestSkill", new Category(1, "TestCat", "testcatDescription", 4),4);
		
		//Get
		lenient().when(mockSkillDAO.findById(eq(1))).thenReturn(skill1);
		//lenient().when(mockSkillDAO.findById(eq(0))).thenReturn(null);
		
		//Add
		lenient().when(mockSkillDAO.save(new Skill(skillDTO1))).thenReturn(skill4);
		//lenient().when(mockSkillDAO.save(new Skill(skillDTO2))).thenReturn(null);
		//lenient().when(mockSkillDAO.save(new Skill(skillDTO3))).thenReturn(null);
		
		//Update
		lenient().when(mockSkillDAO.save(new Skill(skillDTO1))).thenReturn(skill4);

	lenient().when(mockSkillDAO.findById(eq(3))).thenReturn(skill2);
		lenient().when(mockSkillDAO.findById(eq(4))).thenReturn(skill1);
		lenient().when(mockSkillDAO.save(skill4)).thenReturn(skill4);
		
		//Delete
		//lenient().when(mockSkillDAO.findById(4)).thenReturn(skill3).thenReturn(null);
	//	lenient().when(mockSkillDAO.findById(5)).thenReturn(skill3);
	}
	
	@Test
	void test_getAllSkills_happy() {
		SkillDTO  skillDTO = new SkillDTO("react",new Category(0,"",""),2);
		Skill skill1 = new Skill(skillDTO);
	
		List<Skill> expected = new ArrayList<Skill>();
		expected.add(skill1);

		when(mockSkillDAO.findAllByuserid(2)).thenReturn(expected);
		//this expected will be returned when gett allskills(2) run ; all skill runs the mock findAllByuserid
		//the mock skillDAO return the expected the  skillService.getAllSkills(2) return the expected which is the actual
		//so expected and actual is actually ecpected equal expected
		List<Skill> actual = skillService.getAllSkills(2);
		
		assertEquals(expected, actual);
	}
	
	
	
	@Test
	void test_updateSkill_happy() throws EmptyParameterException,  BadParameterException, SkillNotFoundException {
		SkillDTO upSkill = new SkillDTO("TestSkill", new Category( 1,"TestCat", "testcatDescription",4),4);
		Skill expected = new Skill(1, "TestSkill", new Category( 1,"TestCat", "testcatDescription",4),4);

		Skill actual = skillService.updateSkill("4", upSkill);
		
		assertEquals(expected, actual);
	}
	
}
