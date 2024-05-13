#GitHub Repository Interview Task

### Requirements
Create a simple REST service which will return details of given Github repository and cache the response in database of your choice. Details of the response should include:
•	Full name of the repository
•	Description of the repository
•	Git clone url
•	Number of stars
•	Date of creation

The API of the service should look as follows:

GET /repositories/{owner}/{repository­name}
{
"fullName": "...",
"description": "...",
"cloneUrl": "...",
"stars": 0,
"createdAt": "..."
}


GitHub API reference can be found at: https://docs.github.com/en/rest. Service should be coded in Java (preferably version 21) and Spring Framework. We expect to find good design, quality, production ready code. A set of tests (including integration tests) that can be run using build tool of your choice is also required (maven is preferred dependency manager).

Your solution should be delivered as a repository in github. Create a Pull Request from any branch to master and send us a link.

### Technologies

Service based on Spring Boot 3.2.2.The following is required to setup on the workstation

* Java 21
* Apache Maven
* IDE with Lombok Plugin

Additional technologies
* H2 database
* Swagger

### IDE-less build

1. Go to location of the pom.xml file and from preffered terminal execute the build with command: 'mvn clean install'. 
2. After successful build go to directory where is the newly generated jar file (project root/target)
3. Run the executable jar file with command:  'java -jar api-github-interview'


### LocalTesting

For test you can use local swagger link: 'http://localhost:8080/swagger-ui/index.html#'
As path variables you can provide details from this repo:
owner: cforemny
repository-name: api-github-interview


