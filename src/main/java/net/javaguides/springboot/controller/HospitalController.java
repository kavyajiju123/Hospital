package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Hospital;
import net.javaguides.springboot.service.HospitalService;

@Controller
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;
	
	// display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewHospitalForm")
	public String showNewHospitalForm(Model model) {
		// create model attribute to bind form data
		Hospital hospital = new Hospital();
		model.addAttribute("hospital", hospital);
		return "new_hospital";
	}
	
	@PostMapping("/saveHospital")
	public String saveHospital(@ModelAttribute("hospital") Hospital hospital) {
		
		hospitalService.saveHospital(hospital);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		
		Hospital hospital = hospitalService.getHospitalById(id);
		
		
		model.addAttribute("hospital", hospital);
		return "update_hospital";
	}
	
	@GetMapping("/deleteHospital/{id}")
	public String deleteHospital(@PathVariable (value = "id") long id) {
		
		 
		this.hospitalService.deleteHospitalById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Hospital> page = hospitalService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Hospital> listHospital = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listHospital", listHospital);
		return "index";
	}
}

