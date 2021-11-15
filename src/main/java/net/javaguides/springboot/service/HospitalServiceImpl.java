package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Hospital;
import net.javaguides.springboot.repository.HospitalRepository;

@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;

	@Override
	public List<Hospital> getAllHospital() {
		return hospitalRepository.findAll();
	}

	@Override
	public void saveHospital(Hospital hospital) {
		this.hospitalRepository.save(hospital);
	}

	@Override
	public Hospital getHospitalById(long id) {
		Optional<Hospital> optional = hospitalRepository.findById(id);
		Hospital hospital = null;
		if (optional.isPresent()) {
			hospital = optional.get();
		} else {
			throw new RuntimeException(" Patient not found for id :: " + id);
		}
		return hospital;
	}

	@Override
	public void deleteHospitalById(long id) {
		this.hospitalRepository.deleteById(id);
	}

	@Override
	public Page<Hospital> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.hospitalRepository.findAll(pageable);
	}
}
