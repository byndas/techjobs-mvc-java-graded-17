package org.launchcode.techjobsmvc.controllers;

import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.HashMap;

// super bonus:
public class TechJobsController {
	private static HashMap<String, String> actionChoices = new HashMap<>();
	private static HashMap<String, String> columnChoices = new HashMap<>();

	public TechJobsController() {
//	re-usable modular code
//  change once here to update nav bar & search options everywhere
		actionChoices.put("search", "Search");
		actionChoices.put("list", "List");

		columnChoices.put("all", "All");
		columnChoices.put("employer", "Employer");
		columnChoices.put("location", "Location");
		columnChoices.put("positionType", "Position Type");
		columnChoices.put("coreCompetency", "Skill");
	}

	@ModelAttribute("actions") // binds html actions variable to HashMap actionChoices
	public static HashMap getActionChoices() {
		return actionChoices;
	}

	@ModelAttribute("columns")
	public static HashMap getColumnChoices() {
		return columnChoices;
	}
}
