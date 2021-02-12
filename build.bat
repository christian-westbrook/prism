@ECHO off

REM ----------------------------------------------------------------------------
REM Script   : build.bat
REM Engineer : Christian Westbrook
REM Output   : Generation of class files for the system.
REM Abstract : This compiles Prism source code and generates a system
REM            executable.
REM ----------------------------------------------------------------------------
cd src
javac -d ../bin prism/Launcher.java
cd ..
jar cvmf ./docs/Manifest.txt build/Prism.jar -C bin/ .

REM Remove the contents of bin.
DEL /S /q bin
FOR /D %%p IN (bin\*.*) DO rmdir "%%p" /s /q
