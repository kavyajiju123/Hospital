package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Hospital;

public interface HospitalService {
	List<Hospital> getAllHospital();
	void saveHospital(Hospital hospital);
	Hospital getHospitalById(long id);
	void deleteHospitalById(long id);
	Page<Hospital> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}