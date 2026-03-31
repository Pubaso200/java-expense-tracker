# Bring Your Own Project (BYOP): Capstone Report
**Project Title:** Personal Expense Analyzer (Java Desktop Edition)  
**Submitted by:** Asmit Upadhyay  

---

### 1. Introduction
Managing personal finances is a universal challenge. The Personal Expense Analyzer is a desktop-based capstone project designed to bridge the gap between earning and conscious spending by providing a simple way to track money out.

### 2. Problem Statement
The core issue addressed by this project is the lack of awareness regarding micro-transactions. Daily, unmonitored spending on food, entertainment, and transit often goes unnoticed. Traditional spreadsheet tracking is too high-friction for most users.

### 3. Solution & Approach
To solve this, I developed a lightweight Java application with a Graphical User Interface (GUI) that allows users to log expenses in seconds. By keeping the application purely desktop-based and using local file storage, the tool is incredibly fast, completely private, and requires zero internet connection or server setup to run.

### 4. Methodology & Tech Stack
* **Core Java:** The primary programming language used for all logic and data processing.
* **Java Swing & AWT:** Utilized to build the interactive desktop window, input forms, and data tables.
* **CSV File Handling:** Implemented FileWriter and BufferedReader to create a persistent, lightweight database using a standard comma-separated values format.

### 5. Implementation Details
1. **The User Interface:** Built using a JFrame with a BorderLayout. The top panel handles inputs, the center panel displays a JTable of records, and the bottom panel shows the dynamically calculated total.
2. **Data Persistence:** When the "Add Expense" button is clicked, an ActionListener captures the data, updates the live table, and appends the record to expenses.csv. 
3. **Initialization:** Upon launching, the loadData() method parses the expenses.csv file and populates the table, ensuring the user resumes exactly where they left off.

### 6. Challenges Faced
* **GUI Layout Management:** Ensuring the input fields, buttons, and data table resized correctly required careful tuning of Swing's FlowLayout and BorderLayout managers.
* **Data Type Conversion:** Capturing string inputs from text fields and safely converting them to Double values for calculation required implementing try-catch blocks to handle potential formatting errors.

### 7. Key Learnings & Conclusion
Building this project provided hands-on experience in Object-Oriented Programming, GUI development, and File I/O operations in Java. It successfully meets its goal of improving financial awareness through a well-executed, functional desktop application.
