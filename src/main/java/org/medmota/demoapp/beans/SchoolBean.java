package org.medmota.demoapp.beans;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.medmota.demoapp.models.School;
import org.medmota.demoapp.repositories.SchoolRepository;

@Named
@RequestScoped
public class SchoolBean {

	@Inject
	private SchoolRepository schoolRepository;
	Logger logger = Logger.getLogger(SchoolBean.class.getName());
	private Long id;
	private String name;
	private Long editSchoolId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getEditSchoolId() {
		return editSchoolId;
	}

	public void setEditSchoolId(Long editSchoolId) {
		this.editSchoolId = editSchoolId;
	}

	public String updateSchoolDetails(SchoolBean schoolBean) {
		  logger.log(Level.INFO, "Inside bean updateSchoolDetails Method!!");
		  schoolRepository.editSchool(schoolBean.editSchoolId, schoolBean.name);
		  logger.log(Level.INFO, "Outside bean updateSchoolDetails Method!!");
		  FacesContext.getCurrentInstance().addMessage("editSchoolForm:schoolId", new FacesMessage("School Record #" + schoolBean.editSchoolId + " Is Successfully Updated In Db")); 
		  return "schoolEdit.xhtml";
	}

	public String addNewSchool(SchoolBean schoolBean) {
		schoolRepository.addSchool(schoolBean.getName());
		return "schoolsList.xhtml?faces-redirect=true";
	}
	

	public List<School> schoolListFromDb() {
		return schoolRepository.listAllSchools();
	}

	// Method To Delete The School Details From The Database
	public String deleteSchoolById(int schoolId) {
		schoolRepository.deleteSchoolDetails(schoolId);
		return "schoolsList.xhtml?faces-redirect=true";
	}
	

	// Method To Navigate User To The Edit Details Page And Passing Selecting School
	// Id Variable As A Hidden Value
	public String editSchoolDetailsById() {
		editSchoolId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("selectedSchoolId"));
		return "schoolEdit.xhtml";
	}

}
