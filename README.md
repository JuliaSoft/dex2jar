This README file explains how build and import this project in Eclipse.
This project is a "fork" from the original Dex2Jar (https://github.com/pxb1988/dex2jar). 
It has been modified by Flavio T. and it has been simply moved from https://bitbucket.org/tregua87/dex2jar to the current repository in github.
In particular, this project is aimed as a prototype only for experiment, not more.
How build and import it:
	1) Make a clone of the project:
			git clone https://bitbucket.org/tregua87/dex2jar.git
	2) Build the project typing in order the following commands. Open terminal and once in the clones folder: 
			$ gradlew build
			$ gradlew eclipse
	3) Import the project in Eclipse:
			Open eclipse, File -> import -> Existing project into Workspace. Select Dex2Jar folder on your PC (where you run git 			clone at point 1 and build at point 2), so import all projects inside.
Done