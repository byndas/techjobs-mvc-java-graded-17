package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;

@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

// TODO #3: create handler to process search request & render updated search view
    @PostMapping("/results") // must match form action
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
//        System.out.println(searchType);
//        System.out.println(searchTerm);
        ArrayList<Job> jobs;
        model.addAttribute("columns", columnChoices);
        if (searchType.equals("all")) {
            jobs = JobData.findAll();
            model.addAttribute("title", "Jobs With All: "+searchTerm);
        }
        else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);

            model.addAttribute(
                "title",
                "Jobs With "+searchType+": "+searchTerm
            );
        }
        model.addAttribute("jobs", jobs);
        return "search";
    }
}

