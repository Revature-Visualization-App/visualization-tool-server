package com.revature.app.integration;

/*import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
//import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;
//import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;
import com.revature.app.service.CurriculumService;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)*/
class CurriculumControllerIntegrationTest {

	/*@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	CurriculumService service;

	@Autowired
	CurriculumDao dao;

	private MockMvc mockMvc;
	private ObjectMapper om;

	@Autowired
	EntityManagerFactory emf;
	private EntityManager em;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.om = new ObjectMapper();
		em = emf.createEntityManager();
	}

//_________________SUPPORT_FUNCTIONS__________________//	
	public CurriculumDto generateTestDto() {
		//Session session = em.unwrap(Session.class);
		//Skill testSkill = session.get(Skill.class, 1);
		int testSkillId = 1;
		ArrayList<Integer> skills = new ArrayList<Integer>();
		skills.add(testSkillId);
		CurriculumDto testDto = new CurriculumDto("TestCurriculum", skills,0,0,0);
		return testDto;
	}

	public MockHttpServletRequestBuilder getHttpRequest(String path, CurriculumDto params)
			throws JsonProcessingException {
		String contentString = om.writeValueAsString(params);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(path).contentType(MediaType.APPLICATION_JSON)
				.content(contentString);

		return request;
	}

	public MockHttpServletRequestBuilder postHttpRequest(String path, CurriculumDto params)
			throws JsonProcessingException {
		String contentString = om.writeValueAsString(params);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
				.contentType(MediaType.APPLICATION_JSON).content(contentString);

		return request;
	}

	public MockHttpServletRequestBuilder putHttpRequest(String path, CurriculumDto params)
			throws JsonProcessingException {
		String contentString = om.writeValueAsString(params);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON)
				.content(contentString);

		return request;
	}

	public MockHttpServletRequestBuilder deleteHttpRequest(String path, Curriculum params)
			throws JsonProcessingException {
		String contentString = om.writeValueAsString(params);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(path)
				.contentType(MediaType.APPLICATION_JSON).content(contentString);

		return request;
	}

	public void performTest(MockHttpServletRequestBuilder actual, int status, CurriculumDto expected) throws Exception {
		String expectedDto = om.writeValueAsString(expected);

		Curriculum expectedOb = new Curriculum(expected);
		expectedOb.setCurriculumId(1);
		String expectedAsJson = om.writeValueAsString(expectedOb);

		this.mockMvc.perform(actual.contentType(MediaType.APPLICATION_JSON).content(expectedDto))
				.andExpect(MockMvcResultMatchers.status().is(status))
				.andExpect(MockMvcResultMatchers.content().json(expectedAsJson)).andReturn();
	}

	public ResultActions performTest(MockHttpServletRequestBuilder actual, int status, CurriculumDto expected, int flag)
			throws Exception {
		System.out.println("expected: " + expected);
		Curriculum expectedOb = new Curriculum(expected);
		expectedOb.setCurriculumId(1);
		String expectedAsJson = om.writeValueAsString(expectedOb);
		System.out.println("expectedAsJson: " + expectedAsJson);

		return this.mockMvc.perform(actual).andExpect(MockMvcResultMatchers.status().is(status))
				.andExpect(MockMvcResultMatchers.content().json(expectedAsJson));
	}

	public MvcResult performTest(MockHttpServletRequestBuilder actual, int status, List<Curriculum> expected)
			throws Exception {
		String expectedAsJson = om.writeValueAsString(expected);

		return this.mockMvc.perform(actual).andExpect(MockMvcResultMatchers.status().is(status))
				.andExpect(MockMvcResultMatchers.content().json(expectedAsJson)).andReturn();
	}

	// _________________END_SUPPORT_FUNCTIONS__________________//

	@Test
	@Order(0)
	@Transactional
	@Commit
	void test_addCurriculum_success() throws Exception {

		//Session session = em.unwrap(Session.class);
//		int testSkillId = 1;
//		ArrayList<Integer> skills = new ArrayList<Integer>();
//		skills.add(testSkillId);
//
//		em.getTransaction().begin();
//		em.persist(skills);
//		em.getTransaction().commit();

		CurriculumDto expected = generateTestDto();
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/curriculum");
		// postHttpRequest("/curriculum", expected);
		performTest(request, 200, expected);
	}

	@Test
	@Order(1)
	@Transactional
	void test_getCurriculumById_success() throws Exception {

		Session session = em.unwrap(Session.class);
		if (session.get(Curriculum.class, 1) == null) {
			fail("nothing is committed");
		}

		CurriculumDto expected = generateTestDto();
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/curriculum/1");
		// getHttpRequest("/curriculum/1", expected);
		performTest(request, 200, expected);
	}


	@Test
	@Order(2)
	@Transactional
	void test_getAllCurriculum_success() throws Exception {
		CurriculumDto secondDto = generateTestDto();
		secondDto.setName("AnotherOne");
		// temporarily save a second Curriculum to the db
		em.getTransaction().begin();
		em.persist(new Curriculum(secondDto));
		em.getTransaction().commit();

		ArrayList<Curriculum> expected = new ArrayList<Curriculum>();
		Session session = em.unwrap(Session.class);
		expected.add(session.get(Curriculum.class, 1));
		expected.add(session.get(Curriculum.class, 2));

		if (expected.size() < 2) {
			fail("Not properly persisted");
		}

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/curriculum");

		performTest(request, 200, expected);
	}

	@Test
	@Order(3)
	@Transactional
	void test_updateCurriculumById_success() throws Exception {
		CurriculumDto expected = generateTestDto();
		expected.setName("updateSuccess");
		MockHttpServletRequestBuilder request = putHttpRequest("/curriculum/1", expected);
		performTest(request, 200, expected);
	}

	@Test
	@Order(4)
	@Commit
	void test_deleteCurriculumById_success() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.delete("/curriculum/1");
		
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
//	@Test
//	@Order(50)
//	void test_deleteSkill_foreignKeyFailure() throws Exception {
//		Session session = em.unwrap(Session.class);
//		
//		//Add a new curriculum to the database directly that will fail to be deleted in the test
//		ArrayList<Integer> skillList = new ArrayList<Integer>();
//		skillList.add(1);
//		Curriculum testCurr = new Curriculum("TestCurr", skillList);
//		em.getTransaction().begin();
//		em.persist(testCurr);
//		em.getTransaction().commit();
//		
//		//Add a Visualization the Curriculum is a part of
//		ArrayList<Curriculum> currList = new ArrayList<Curriculum>();
//		currList.add(session.get(Curriculum.class, 3));
//		Visualization testVis = new Visualization("TestVis", currList);
//		em.getTransaction().begin();
//		em.persist(testVis);
//		em.getTransaction().commit();
//		
//		//Print out the category and skill as a sanity check
//		//System.out.println(session.get(Skill.class, 1));
//		System.out.println(session.get(Curriculum.class, 3));
//		System.out.println(session.get(Visualization.class, 1));
//		
//		//Now to test the method
//		this.mockMvc.perform(delete("/curriculum/3")).andExpect(MockMvcResultMatchers.status().is(400));
//
//	}*/
	
	
}
