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

Later in this assignment, you will receive instructions for adding a second handler to deal with user input and display the search results.

The Views
Let’s turn our attention to the views.

The fragments File
If the application is not running, launch it and navigate to the site’s home page in your browser. Also open up the src/main/resources/templates/index.html file in IntelliJ. You’ll notice that there is a fair amount of content visible on the page that isn’t contained in index.html. This is because we’re using two fragments from fragments.html (head and page-header). These allow for some basic page structure and navigation to be shared across all of our views.

Have a look at the structure of fragments.html, but you will NOT need to do any work within this file for this assignment.

 Tip
We use Twitter’s Bootstrap CSS, HTML, and JS framework to provide some styling and functionality to our views. The appropriate files are included at the top of fragments.html and thus are included on every page of our app.

You won’t have to explicitly use Bootstrap at all in this assignment, but it’s a great way to make your sites look good with minimal work. Consider using it in your own projects!

The List Views
Turn your attention to list.html. This page displays a table of links broken down into several categories. Data from columnChoices is used to fill in the header row, and information stored in tableChoices generates the link text.

The most interesting part of this template is how we generate the links:

   <td th:each="category : ${tableChoices}">
      <ul>
         <li th:each="item : ${category.value}">
            <a th:href="@{/list/jobs(column=${category.key},value=${item})}" th:text="${item}"></a>
         </li>
      </ul>
   </td>
tableChoices is a HashMap from JobData, and it contains the names of the Job fields as keys (employer, etc.). The value for each key is an ArrayList of Employer, Location, CoreCompetency, or PositionType objects.
In line 1, category represents one key/value pair from tableChoices, and in line 3, item represents one entry from the stored ArrayList.
We’ve seen the syntax @{/list/jobs} to generate a link within a Thymeleaf template, but we haven’t seen the other portion of the link: (column=${category.key},value=${position}). This syntax causes Thymeleaf to dynamically generate query parameters for our URL.
In line 4, we set these parameters by using column= and value=. The values of these parameters are determined dynamically based on ${category.key} and ${item}. Since these values come from tableChoices, the keys will be employer, location, etc. The values will be the individual elements from the related ArrayList. When the user clicks on these links, they will be routed to the listJobsByColumnAndValue handler in ListController, which looks for these parameters.

Clicking on one of the links will display a list of jobs that relate to the choice, via the listJobsByColumnAndValue handler method. However, that view, list-jobs.html isn’t working yet. While the handler method is fully implemented, the view template needs some work.

For now, click one of the Location links. This sends a request as we outlined above, but doing so only displays a page with a title and no job list.

The page you see at /list/values?column=location&value=... is generated by the list-jobs.html template. It has a similar structure as list.html, but the table consists of only one column.

 Note
Select “Kansas City” from the list of locations, and then check the address bar of your browser:

   /list/jobs?column=location&value=Kansas%20City
Thymeleaf inserts %20 for us, to represent a space, but this may actually be hidden in your browser’s address bar.

The Search View
Finally, click on Search from the home page, or the navigation bar, and open up search.html in IntelliJ. You’ll see a search form (in both the browser and template file) that gives the user the option of searching by a given Job field, or across all fields. This is an exact visual analog of our console application.

This template will be used to display search results, in addition to rendering the form. This will give the nice user experience of easily searching multiple times in a row.

Wrap Up the Code Review
Once you understand the controllers and views that are already in place, you’re ready to begin your work.
*/