# onez

This repository contains all the required factors of out website

## Project Description: This project is related to web development. Gaming website etc etc


## Technologies Used

- Java
    - Used for backend programming language
- HTML
    - Used for designing the website's structure
- CSS
    - Used for styling the frontend design
- JS
    - Used for adding dynamic content and creating responsive interactions in the website

## Features

- 
- ## Getting Started [Run Locally]
Follow these steps to set up and run the project on your local machine using Apache Tomcat.

**Required**
Java JDK 17+ 

Apache Tomcat 9+ 

**Steps to Run**
  - Clone the Repository
      - git clone <repository-url>
      - cd <project-directory>

**Copy the WAR File to Tomcat**  
  - Move the .war file to the Tomcat webapps folder:
      - cp <your-app>.war /path/to/tomcat/webapps/
        
    Replace <your-app>.war with your actual file name.

**Start Tomcat**
**For Linux/macOS:**
  - cd /path/to/tomcat/bin
  - ./startup.sh
  
**For Windows:**

  - cd C:\path\to\tomcat\bin
  - startup.bat
    
**Access the Website**
Once Tomcat is running, open your browser and visit:
http://localhost:8080/<your-app>/

To stop Tomcat:

**For Linux/macOS:** ./shutdown.sh

**For Windows:** shutdown.bat


