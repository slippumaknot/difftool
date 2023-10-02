# difftool
Java Tool to compare objects and get the differences
# Description
This is the DiffTool.class which compare two objects and returns the differences.
It's one class and one method, which is called recursive way.
It compares Custom Objects and Lists, For other implementations like Maps we will need to add the code to handle it.

# Software requirements:
In order to run this project, we need:
* java 17 sdk installed and configured
* maven installed with "mvn" available in the PATH(I use version 3.9.4)
* Git in order to download the project 
* An java compatible IDE to import the project and run the Tests

This is a basic maven project that can be imported in any maven compatible IDE. The only needed dependencies are
for the Unit tests.

# How to Run the JUnit tests:
1. Clone the project (git clone https://github.com/slippumaknot/difftool.git)
2. Import the project as Maven project in any java IDE
3. Execute 'maven clean' to compile the project
4. Run the test unit class: DiffToolTest

#Execution description
There are two basic tests in the DiffToolTest class:

* DiffSimpleObjects: This test create example objects with fields and lists and displays the results in the console, we
  can update the object and values to check the DiffTool results in the console.

* DiffSimpleObjectsExceptions: This test has two classes with invalid Id values:
- one object list has the "AuditKey field' and the "id" fields in the same class and only one is allowed per object,
  so the DuplicateIdTypeException is thrown
- the second one has a list with objects without the  "AuditKey field' or the "id" field, which one is  mandators,
  so the NoIdTypeInObjectsListException is thrown

This code was developed in 2 hours for the object part, 3 hours for the list part and 1.5 hours for the tests and fix bugs.