package org.launchcode.techjobsmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechjobsMvcApplication {
	public static void main(String[] args) {
		SpringApplication.run(TechjobsMvcApplication.class, args);
	}
}

/*
ASSIGNMENT OUTLINE:

“model” is in models.JobData class
this class isn’t a typical MVC/OOP model

JobData class serves same purpose as before
	reading data from job_data.csv file
	  to store in usable format --> ArrayList of Job objects

path to job_data.csv changed to src/main/resources folder

use static methods provided by JobData in controllers
review their familiar functionality as you go

Controllers:

HomeController --> only one handler, index() displaying index.html home page

ListController --> displays either:
  table showing all Job field options (employer, location, coreCompetency, positionType)
		or selected job detail list

	/list page has an incomplete “All” table column

	constructor populates columnChoices & tableChoices with values
	  HashMaps are same as in console app
	    providing centralized collection of different List & Search options
	      presented throughout user interface

	list() displays table of clickable links for each job category
	listJobsByColumnAndValue() displays info for jobs relating to selected category
	both handlers implement JobData class methods to obtain data

	listJobsByColumnAndValue/() has query params for column & value
		that fetch data from JobData
		 "all" option fetches all job data
		    otherwise, retrieves limited set of info
		      then displays list-jobs.html

	listJobsByColumnAndValue() also “searches” for value in field
		then displays matching jobs
			differs only slightly since user clicks link in list.html
				rather than a form submission to arrive at this handler method
	listJobsByColumnAndValue() handles the “all” option differently
	  than when user clicks a category link

SearchController:
	search controller contains only search()
	 displays form defined in the search.html template.

await instructions for second user input handler displaying search results

Views:

	in fragments File,
		if app not running,
			launch & navigate to homepage
				open src/main/resources/templates/index.html
					some content isn’t contained in index.html
						due to using two fragments from fragments.html (head & page-header)
							allowing basic page structure & shared navigation across all views

	look at structure of fragments.html -- don't touch this file

	Tip -- using Twitter Bootstrap (CSS, HTML, JS) framework
		appropriate files included atop fragments.html
	    thus included on each app page

	No need to explicitly use Bootstrap in this assignment
		but makes sites look good with minimal work

List Views:

	list.html displays table of link categories

	columnChoices data used in header row,
		tableChoices data generates link text

	to generate links:

   <td th:each="category : ${tableChoices}">
      <ul>
         <li th:each="item : ${category.value}">
            <a th:href="@{/list/jobs(column=${category.key},value=${item})}" th:text="${item}"></a>
         </li>
      </ul>
   </td>

tableChoices is HashMap from JobData containing names of Job fields as keys
	 each key value is ArrayList of Employer, Location, CoreCompetency, or PositionType objects

line 1, category represents a key/value pair from tableChoices,
line 3, item represents an entry from stored ArrayList
	We saw syntax @{/list/jobs} generating link in Thymeleaf template,
		we haven’t seen the other link portion:
	    (column=${category.key},value=${position})
	      causing Thymeleaf to dynamically generate query parameters for our URL
line 4, sets these parameters values via column= & value=
	depending on ${category.key} & ${item}
	  since these values come from tableChoices,
	    keys will be employer, location, etc.
	      values will be individual elements from related ArrayList
When user clicks link,
	routed to listJobsByColumnAndValue handler in ListController,
		which looks for these parameters

Clicking link displays list of jobs relating to choice,
	via listJobsByColumnAndValue handler method
	  yet list-jobs.html view fails to work
	    handler method fully implemented,
	      but view template needs some work

click a Location link sending request as outlined above,
  doing so only displays page with title & no job list

list-jobs.html displays page /list/values?column=location&value=...
	similar structure to list.html but table has only one column

select “Kansas City” from the list of locations,
	check address bar:
		/list/jobs?column=location&value=Kansas%20City
			Thymeleaf inserts %20 for us, to represent a space
				but may be hidden in your browser’s address bar

Search View:
	finally, click Search from home page or nav bar,
		open search.html
		  search form (in both browser & html file) gives user option to search
		    by given Job field or across all fields
		      an exact visual analog of our console app

	this template displays search results & renders form
    this gives user nice experience to easily search multiple times

Finish Code Review:
	understand repo controllers & views then begin working

/////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

Bonus

when selecting given field to search & submit,
  our choice is forgotten & returns to “All” by default
    modify html to keep previous search field selected when displaying results

in tables displaying full job data, manipulate font, style, capitalization, etc.
  to further distinguish labels from data (e.g. Employer: LaunchCode)

Hint: title string capitalized in multiple templates

in tables of job results,
	make each value (except name) hyperlinked
	  to new listing of all jobs with same value
      for example, if a list of jobs with JS skill,
        clicking on location generates new list
          with all jobs available there

/////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////

Super Bonus

actionChoices HashMap passed to view in HomeController.index method
  HomeController is responsible for presenting actions, not the view,
    but we did not do this for nav links displayed on every page

In order to similarly detach navigation links,
	must pass actionChoices to every view,
    since nav links generated in fragments.html
	must do like line below in every handler method
    which is a pain & error-prone & difficult to update
	    model.addAttribute("actions", actionChoices);

to fix this:

	make new controller, TechJobsController
	  having static HashMap, actionChoices
	    populated via no-argument constructor,
	      as columnChoices populated in ListController

	do not add @Controller annotation to this class

	write static method getActionChoices in TechJobsController
	  returning actions HashMap

	add annotation @ModelAttribute("actions") to getActionChoices method
	 causing method's return value to be set in model with key "actions"
	  for each controller extending TechJobsController

	modify all other controllers to extend TechJobsController

	modify fragments.html to use passed-in action choices to generate nav links

	place columnChoices in TechJobsController
*/