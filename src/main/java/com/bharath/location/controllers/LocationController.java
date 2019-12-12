package com.bharath.location.controllers;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bharath.location.model.Location;
import com.bharath.location.repos.LocationRepository;
import com.bharath.location.service.LocationService;
import com.bharath.location.util.EmailUtil;
import com.bharath.location.util.ReportUtil;

@Controller
@Component
public class LocationController {
	
	@Autowired
	LocationService service;
	
	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	LocationRepository repository;
	
	@Autowired
	ReportUtil reportUtil;
	
	@Autowired
	ServletContext sc;
	
	/*
	 * Controller method to show the URL after Create Location basically the homepage
	 */
	@RequestMapping("/showCreate")
	public String showCreate() {
		return "createLocation";
	}
	
	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("location") Location location, ModelMap modelMap ) {
		
		Location locationSaved = service.saveLocation(location); // new variable with the saved location
		String msg = "location saved with id: " + locationSaved.getId(); // success message
		modelMap.addAttribute("msg", msg);
		emailUtil.sendEmail("user@gmail.com", "Location Saved", "Location Saved Successfully"
				+ "and about to send a response");
		return "createLocation";
	}
	
	@RequestMapping("/displayLocations") 
	public String displaylocations(ModelMap modelMap) {
		
		List<Location> locations = service.getAlllocation();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	@RequestMapping("/deleteLocation")
	public String deleteLocation(@RequestParam("id") int id, ModelMap modelMap) {
		
		Location location = new Location();
		location.setId(id);
		service.deleteLocation(location);
		
		List<Location> locations = service.getAlllocation();
		modelMap.addAttribute("locations", locations);
		
		return "displayLocations";
	}
	
	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam("id") int id, ModelMap modelMap) {
		Location location = new Location();
		location.setId(id);
		service.getLocationById(id);
		modelMap.addAttribute("location", location);
		return "updateLocation";
	}

	@RequestMapping("/updateLoc")
	public String updateLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		service.updateLocation(location);
		List<Location> locations = service.getAlllocation();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	
	@RequestMapping("/generateReport")
	public String generateReport() {
		
		String path = sc.getRealPath("/");
		
		List<Object[]> data = repository.findTypeAndTypeCount();
		reportUtil.generatePieChart(path, data);
		return "report";
	}
}
