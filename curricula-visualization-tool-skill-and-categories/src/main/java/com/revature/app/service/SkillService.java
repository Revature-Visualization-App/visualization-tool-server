package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.revature.app.dao.SkillDAO;
import com.revature.app.dto.CategoryDTO;
import com.revature.app.dto.SkillDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;

@Service
public class SkillService {

	@Autowired
	private SkillDAO skillDAO;

	@Transactional
	public List<Skill> getAllSkills(){
		return skillDAO.findAll();
	}
	
	String badParam = "The skill ID provided must be of type int";
	String emptyParam = "The skill ID was left blank";
	
	@Transactional(rollbackOn = {SkillNotFoundException.class})
	public Skill getSkillByID(String skillID) throws BadParameterException, EmptyParameterException, SkillNotFoundException {
		Skill skill = null;
		try {
			if(skillID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(skillID);
			skill = skillDAO.findById(id);
			if(skill == null) {
				throw new SkillNotFoundException("The skill with ID " + skillID + " could not be found.");
			} else {
				return skill;
			}
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}
	
	@Transactional
	public Skill addSkill(SkillDTO skillDTO) throws EmptyParameterException {
		Skill skill = null;
		
		Category c = null;
		
		if (skillDTO.getCategory() == null) {
			skillDTO.setCategory(c);
		}
		
		
		if(skillDTO.getName().trim().equals("")) {
			throw new EmptyParameterException("The skill name was left blank");
		}
		skill = new Skill(skillDTO);
		skill = skillDAO.save(skill);
		return skill;
	}

	@Transactional(rollbackOn = {SkillNotFoundException.class})
	public Skill updateSkill(String skillID, SkillDTO upSkill) throws EmptyParameterException, BadParameterException, SkillNotFoundException{
		Skill skill = null;
		try {
			if(skillID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			if(upSkill.getName().trim().equals("")){
				throw new EmptyParameterException("The skill name was left blank");
			}
			int id = Integer.parseInt(skillID);
			skill = skillDAO.findById(id);
			if(skill == null) {
				throw new SkillNotFoundException("The skill could not be updated because it couldn't be found");
			} else {
				skill.updateFromDTO(upSkill);
				skill = skillDAO.save(skill);
			}
			return skill;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional(rollbackOn = {SkillNotFoundException.class, ForeignKeyConstraintException.class})
	public Skill deleteSkill(String skillID) throws EmptyParameterException, BadParameterException,  SkillNotFoundException, ForeignKeyConstraintException {
		Skill skill = null;
		try {
			if(skillID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(skillID);
			skill = skillDAO.findById(id);
			if(skill == null) {
				throw new SkillNotFoundException("The skill could not be deleted because it couldn't be found");
			} else {
				skillDAO.delete(skill);
				skillDAO.flush();
			}
			return skill;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		} catch (DataIntegrityViolationException e) {
			throw new ForeignKeyConstraintException("Please remove this skill from all curricula before attempting to delete this skill");
		}
	}
	
}
