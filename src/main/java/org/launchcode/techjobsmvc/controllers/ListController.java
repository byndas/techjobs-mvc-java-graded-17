package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;


@Controller
@RequestMapping(value = "list")
public class ListController extends TechJobsController {

    static HashMap<String, Object> tableChoices = new HashMap<>();

    public ListController () {
        tableChoices.put("employer", JobData.getAllEmployers());
        tableChoices.put("location", JobData.getAllLocations());
        tableChoices.put("positionType", JobData.getAllPositionTypes());
        tableChoices.put("coreCompetency", JobData.getAllCoreCompetency());
    }

    @GetMapping(value = "")
    public String list(Model model) {
        model.addAttribute("tableChoices", tableChoices);
        model.addAttribute("employers", JobData.getAllEmployers());
        model.addAttribute("locations", JobData.getAllLocations());
        model.addAttribute("positions", JobData.getAllPositionTypes());
        model.addAttribute("skills", JobData.getAllCoreCompetency());

        return "list";
    }

    @GetMapping(value = "jobs")
    public String listJobsByColumnAndValue(
        Model model,
        @RequestParam String column,
        @RequestParam(required = false) String value,
        @RequestParam(required = false) String filterColumn,
        @RequestParam(required = false) String filterValue
    ) {
        ArrayList<Job> jobs;
//      THIRD BONUS:
        if (column.equals("all")) {
            // if listing "view all"
            // user arrives from "/list" page since listing lacks filterValue
            if (filterValue == null || filterValue.isEmpty()) {
                // null or isEmpty() covers how Java treats param value
                jobs = JobData.findAll();
                model.addAttribute("title", "All Jobs");
            }
            else { // user arrives from "/search" page
                // double filter
                // // covers search all plus clicking search link value
                // column is "all"
                // value is empty string or keyword input
                // filterColumn is search results key
                // filterValue is search results value
                jobs = JobData.findByFilter(column, value, filterColumn, filterValue);
                model.addAttribute(
                    "title", // list results html tag dynamic title
                    value+" Jobs with "+getColumnChoices().get(filterColumn)+": "+filterValue
                );
            }
        }
        else { // user arrives from neither "view all" nor search "all"
            if (filterValue == null || filterValue.isEmpty()) {
                // user arrives from "/list" page since listing lacks filterValue
                jobs = JobData.findByColumnAndValue(column, value);

                model.addAttribute(
                    "title", // list results html tag dynamic title
                    "Jobs with "+getColumnChoices().get(column)+": "+value
                );
            }
            else { // user arrives from "/search" page
                // each parameter has a value
                // double filter
                jobs = JobData.findByFilter(column, value, filterColumn, filterValue);
                model.addAttribute(
                    "title", // list results html tag dynamic title
                    "Jobs with "+getColumnChoices().get(column)+": "+value+" & "+filterColumn+": "+filterValue
                );
            }
        }
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }
}

