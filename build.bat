@ECHO off

REM ----------------------------------------------------------------------------
REM Script   : build.bat
REM Engineer : Christian Westbrook
REM Input    : A string indicating which system to build. This string must be
REM            the name of a package within the prism package that contains a
REM            valid Launcher class and also have a supporting manifest located
REM            in the /docs directory.
REM Output   : Generation of class files for the indicated system.
REM Abstract : This script takes in the name of a system to compile performs a
REM            compilation and jarring process. Any package within the prism
REM            package containing a valid Launcher class with a main method and
REM            a supporting manifest file located in /docs is an eligible system
REM            to be compiled by this script.
REM ----------------------------------------------------------------------------

REM This line causes variables to be expanded at execution time rather than at
REM parse time. This results in the following iterator 'index' being properly
REM incremented from within the following for loops.

SETLOCAL EnableDelayedExpansion

REM For each package in the prism package, add the package name to an array.
REM This array will contain the names of any systems eligible for compilation.

SET /A index=0
FOR /d %%d IN (src/prism/*) DO (
	SET a[!index!]=%%d
	SET /A index+=1
)

REM For each eligible system, compare the name of the system with the system
REM name provided as input to the script. Keep track of whether the system
REM given as input matches any valid system name.

SET /A length=index
SET /A index=0
SET valid=false

:Iteration
IF !a[%index%]! EQU %1 (SET valid=true)
SET /A index+=1
IF %length% GTR %index% (GOTO Iteration)

REM If we determined that the name given as input refers to a valid system, then
REM jump to a block of code for handling compilation. Otherwise, jump to a block
REM of code that will handle an invalid name being provided.

IF %valid% EQU true GOTO ValidInput
GOTO InvalidInput

REM Handle valid input by attempting to compile and jar the given system.

:ValidInput
	REM Compile the target system
	cd src
	javac -d ../bin prism/%1/Launcher.java
	cd ..

	REM Convert the first character of the input system name to uppercase for the
	REM Jar file name.
	SET cstring=%1
	SET lower=%cstring:~1,20%
	SET upper=
		SET "partial=%cstring:~0,1%"
		FOR /F "skip=2 delims=" %%I IN ('tree "\%partial%"') DO IF NOT DEFINED upper SET "upper=%%~I"
		SET "upper=%upper:~3%"

	REM Generate the Jar file.
	IF NOT EXIST build MKDIR build
	jar cvmf ./docs/%upper%%lower%Manifest.txt build/%upper%%lower%.jar -C bin/ .

	REM Remove the contents of bin.
	DEL /S /q bin
	FOR /D %%p IN (bin\*.*) DO rmdir "%%p" /s /q

	GOTO :End

REM Handle invalid input by notifying the user that the given system couldn't be
REM found.

:InvalidInput
	ECHO The system '%1' could not be found.

:End
