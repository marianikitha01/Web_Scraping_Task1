# Web_Scraping_Task1
## Project Overview
**This project involves two main tasks:**

**Web Scraping and NLP Statistics Calculation:**

- A Python script scrapes approximately 100 health-related web pages from a single website.
- The website used in this project is "https://www.everydayhealthgroup.com/". This can be replaced by any other website
- For each page, the script calculates at least 5 NLP-related statistics and stores the results in a CSV file(nlp_statistics.csv).
- An aggregated result file(aggregated_results) is also generated, which contains the average of these statistics.

**Java Web Application for NLP Statistics Comparison:**

- A Spring Boot web application is created to accept input (text or text file).
- The application generates NLP statistics for the input and compares them with the previously computed aggregated results.
- The comparison is displayed on a web page in table format.

## Prerequisites
Ensure you have the following installed:

- Python (version 3.7 or above)
- Java (JDK 8 or above)
- Maven (for managing Java dependencies)

## Python Setup
1. Clone the repository

2. Install the required Python libraries:
   ```bash
   pip install vaderSentiment
    ```
3. Run the Python script to scrape the web pages and calculate the statistics:
   ```bash
   python Task1.ipynb
   ```
4. This will generate two CSV files:
- nlp_statistics.csv: Contains the NLP statistics for each web page.
- aggregated_results.csv: Contains the average of the NLP statistics.

## Java Setup

1. Open the project in your preferred IDE like Eclipse.

2. Replace the path to the aggregated_results.csv file in the NlpController class with your own path:
```bash
private static final String AGGREGATED_STATS_FILE = "C:\\Users\\Nikitha\\Desktop\\NLP- RA\\aggregated_results.csv";
```
3. Make sure to install all the Maven dependencies listed in the pom.xml file.

4. To run the Spring Boot application:

- Open your IDE and locate the NlpAppApplication file (NLP_Project\src\main\java\com\task1\NLP_Project).
- Right-click on it and select Run As -> Spring Boot App.

**Note:** By default, the server will run on server.port=8081. If port 8081 is busy, or if you prefer to use the default port 8080, you can change the port configuration in the 'application.properties' file located at: NLP_Project\src\main\resources\application.properties

5. Access the application at http://localhost:8081 in your web browser.

## How to Use

- Upload a text file or input text directly into the web form and click on 'Generate Statistics' button.
- The application will calculate NLP statistics and compare them with the precomputed aggregated results.
- The comparison will be displayed on the results page.

## Demo
For demonstration purposes, a screenshot of the results page, showing the comparison results, is included in the results folder.
