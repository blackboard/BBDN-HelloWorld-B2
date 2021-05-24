# BBDN-HelloWorld-B2
This project provides an example Hello World type Building Block that we will use to demonstrate migration of a Building Block to an LTI and REST application. 

The B2 itself is very simple. It provides a tool type integration that a user can click from the 'Tools' section in a Learn Original course and it will display information about the current context:
* Username
* User UUID
* User BatchUid
* Course Id
* Course Title
* Course UUID
* Course BatchUid

This project is built with Java 11. You can access the already-built War file in the  [build/libs](/build/libs) folder in this project. All of the code is located in the [/src/main/webapp](/src/main/webapp) folder. 

To build, simply clone this repository to your local environment. Ensure you have Java 11 and Gradle installed and in your PATH, and issue `gradle build` at the commandline.