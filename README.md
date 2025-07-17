# Onez – E-commerce Website

This repository contains the complete source code, SQL database, and configurations for Onez, an educational e-commerce website project. It demonstrates a Java-based full-stack web application using Servlets and JSP, with a responsive frontend and MySQL integration.

## Project Description

Onez is a dynamic e-commerce website developed using Java Servlets and JSP for the backend, and HTML/CSS/JS for the frontend. It simulates the structure of a typical online shopping platform, including product listings, basic UI interactivity, and modular development practices.

This project was developed as part of a web development coursework to demonstrate knowledge of MVC architecture, server-side programming, and frontend integration.

## Technologies Used

### Backend:
- Java (Servlets)
- JSP (JavaServer Pages)
- JDBC – for database connectivity with MySQL

### Frontend:
- HTML5
- CSS3
- JavaScript

### Database:
- MySQL
  - Schema and seed data in `onez.sql`

### Server:
- Apache Tomcat 9+

## Features

- Responsive homepage with product display
- Organized folder structure (MVC-like)
- Servlet-based routing and form handling
- Integrated CSS and JS assets
- Ready-to-import SQL schema (`onez.sql`)
- Deployable WAR structure under `webapp/`

## Getting Started – Run Locally

Follow the steps below to run this project on your machine using Apache Tomcat.

### Prerequisites

- Java JDK 17 or later
- Apache Tomcat 9 or later
- MySQL Server
- IDE (Eclipse, IntelliJ, or NetBeans)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd E-commerce_Website_Onez-main
```

### 2. Import Project to IDE

- Open your IDE (Eclipse/IntelliJ)
- Choose "Import as Maven/Java Project" (depending on your IDE)
- Make sure source folders are configured correctly (`java/` and `webapp/`)

### 3. Configure MySQL Database

- Open MySQL Workbench or CLI
- Import the SQL script:

```sql
SOURCE path/to/onez.sql;
```

- Update your DB connection URL, username, and password in the Java code (usually inside DAO or utility classes)

### 4. Deploy on Tomcat

- Package the project as a `.war` file (your IDE may do this automatically)
- Move the `.war` file to the `tomcat/webapps` folder:

```bash
cp onez.war /path/to/tomcat/webapps/
```

### 5. Start Tomcat Server

**Linux/macOS:**
```bash
cd /path/to/tomcat/bin
./startup.sh
```

**Windows:**
```cmd
cd \path\to\tomcat\bin
startup.bat
```

### 6. Access the Website

Open your browser and visit:

```
http://localhost:8080/onez/
```

## Stop Tomcat

**Linux/macOS:**
```bash
./shutdown.sh
```

**Windows:**
```cmd
shutdown.bat
```

## Project Structure Overview

```
E-commerce_Website_Onez-main/
│
├── java/                      # Java Servlet code (com/onez/...)
├── webapp/                    # Web content (JSP, CSS, JS, Images)
│   ├── css/
│   ├── js/
│   ├── resources/
│   ├── META-INF/
│   └── WEB-INF/
│
├── onez.sql                   # MySQL schema and sample data
├── README.md                  # Project documentation
```

## Authors

- Developed by Team Onez (Add names here if applicable)

## License

This project is provided for educational purposes only.
