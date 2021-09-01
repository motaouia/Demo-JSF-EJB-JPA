package org.medmota.demoapp.repositories;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.medmota.demoapp.models.School;

@Stateless
public class SchoolRepository {

	@PersistenceContext(name = "schoolDS")
	private EntityManager entityManager;
	
	Logger logger = Logger.getLogger(SchoolRepository.class.getName());

	/*
	 * @PostConstruct public void init() { addSchool(new
	 * School("Oxford. Sr. Sec. School")); addSchool(new School("ENCG"));
	 * addSchool(new School("ENSIAS")); addSchool(new School("ENSA")); addSchool(new
	 * School("INPT")); }
	 */

	public void addSchool(String nomSchool) {
		entityManager.persist(new School(nomSchool));
	}

	public void editSchool(Long schoolId, String updatedSchoolName) {
		//logger.log(Level.INFO, "Inside Repository updateSchoolDetails Method!!");
		if (isSchoolIdPresent(schoolId)) {
			Query queryObj = entityManager.createQuery("UPDATE School s SET s.name=:name WHERE s.id= :id");
			queryObj.setParameter("id", schoolId);
			queryObj.setParameter("name", updatedSchoolName);
			int updateCount = queryObj.executeUpdate();
			if (updateCount > 0) {
				logger.log(Level.INFO, "Record For Id: " + schoolId + " Is Updated");
			}
		}
	}

	public List<School> listAllSchools() {
		Query q = entityManager.createQuery("Select s from School s");
		return q.getResultList();
	}

	public void deleteSchoolDetails(int schoolId) {
		entityManager.remove(entityManager.find(School.class, schoolId));
	}

	// Helper Method 1 - Fetch Particular School Details On The Basis Of School Id
	// From The Database
	private boolean isSchoolIdPresent(Long schoolId) {
		boolean idResult = false;
		Query queryObj = entityManager.createQuery("SELECT s FROM School s WHERE s.id = :id");
		queryObj.setParameter("id", schoolId);
		School selectedSchoolId = (School) queryObj.getSingleResult();
		if (selectedSchoolId != null) {
			idResult = true;
		}
		return idResult;
	}
}
