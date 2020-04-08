# TextAnalyzer Client

## Overview

## What does this program do?

## Code design

## Test plans and standards

## System requirements
The program is a JavaFX application using version 8 of Amazon's distribution of the Open Java Development Kit (OpenJDK) [Corretto 8](https://aws.amazon.com/corretto/), which includes JavaFX 8. Unit tests were created using [Junit 5](https://github.com/junit-team/junit5/).


## How to use this program.
The program requires no user interaction other than compilation and execution. However, the user must first create a local MySQL user for the program to interact with the database (see [System Requirements](#system-requirements)). 

The GUI consists of a single stage (window) and a single scene (window content). Although the scene's input field is pre-populated with the exercise's target URL, the user may specify the URL of a different file to parse. The results will appear in the TableVIew.

After the results are displayed, the user may enter the URL of another file for analysis.

## Installation.
To install the program, simply clone the repo, open it in your favorite IDE, and run it. Make sure that Java JDK 8 and JavaFX 8 are installed in your system. 

## Known Issues
Client submits URL to process in a loop, without requiring the user to click the 'Analyze!' button. Also, once the client receives the word/frequency pairs from the server, it does not populate the TableView. 

## Todo
Fix known issues.

## Version history
The version numbering of this project does not follow most version numbering guidelines. Instead, it is limited to a two-token concept:

```(major).(course module)``` 

```
Version 1.11 (current) - Converted program into server/client
Version 1.10 - Added database support
Version 1.9 - Added JavaDocs
Version 1.7 - Added unit tests using JUnit
Version 1.6 - Added GUI functionality
Version 1.2 - First version
```

## Screenshots

## Unit Tests
