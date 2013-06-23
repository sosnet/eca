BSF Examples 

ScriptedUI 
==========

NOTE: This example originally came with BSF (where it demonstrates the use of JavaScript and NetRexx), so the files ScriptedUI.java and ui.plog  are licensed under the Apache License, not the GPL.

Demonstrates scripting use of bsfLib. The Java code sets up a simple AWT application which is then modified by the Prolog code in the ui.plog file. It demonstrates how to create Java objects in Prolog, invoke methods on them, and how to register AWT event handler that are called when buttons are clicked. One can play around with the Prolog code in the file ui.plog without having to recompile the Java code. Usage (in build directory): 

java -classpath .:jlogic.jar:builtinsLib.jar:bsfLib.jar:../lib/bsf.jar ScriptedUI ../Examples/BSF/bsf_ui.plog 

Or, in the JLog install directory:

java -jar BSFScriptedUI.jar bsf_ui.plog



PiApp 
=====

Demonstrates scripting use of bsfLib. The application is really just a shell for running external scripts, which in this case calculates the mathematical constant π. The pi.plog script creates a piiterator Object, and invokes its calcPi method to do the calculation. Usage is: 

java -classpath build/JLog.jar:lib/bsf-2.3.0-rc1.jar:Examples/bsf PiApp Examples/bsf/bsf_pi.plog 

Since the name of the script to run is passed as parameter to the program, this application can be used to run any script, in any language supported by BSF. The language to use is inferred from the file extension; for Prolog it‘s either .plog or .prolog. 

All the interesting action is in the PiApp class and the bsf_pi.plog Prolog code. The other Java classes are interesting only if you want to find out how to calculate PI iteratively.



Ant <script> task 
=================

NOTE: This has not been tested in the current configuration...

The Ant build file (build.xml) also includes an example of how Ant can be scripted via the <script> task that comes as part of Ant. For this to work, the various jar files that come with JLog need to be in Ants CLASSPATH, which can be accomplished by copying them into the lib directory of your Ant installation. Usage is: 

ant script

Besides demonstrating how to create Java objects and invoke methods on them, the Ant project object is manipulated via its getProperty, setProperty, createTask and log methods. project is put into the common object registry by the script task precisely for this reason: so that scripts can access it, and influence the way Ant executes. 

Two examples can be found in the build.xml file that comes with JLog. The first calculates the n-th number of the Fibonacci sequence, with n being supplied on the command line. 

% ant fib -Dn=15 

Buildfile: build.xml 
ensure-fib-n: 
fib: 
15 
610 
BUILD SUCCESSFUL 

The second example has two parts: first, a property is read from Ant, incremented by 11, and stored in another Ant property. Then the current date is retrieved and printed to the console using the Ant project log method. 

% ant script 
Buildfile: build.xml 
script: 
     [echo] prop=42 
     [echo] prop2=53 
Sun Jul 31 18:50:36 CEST 2005 
BUILD SUCCESSFUL 
                                                                                                    

Web Page Scripting 
==================

The Jakarta Taglibs project provides a JSP tag library that allows BSF-enabled languages to be used as scripting languages in a JSP page. The library is part of the Jakarta Taglibs project, and can be downloaded at: http://jakarta.apache.org/taglibs 

The library provides the <bsf:scriptlet> tag, which sources a Prolog script and evaluates its last term. The following is the adapted version of an example that ships with the tag library. It prints a table of Fahrenheit and Celsius temperatures. This is not so much a practical example, but rather a demonstration that wherever BSF is used, Prolog code can run. 

<bsf:scriptlet language="prolog"> 

 f2c(Start, End) :- 
	Start =< End, 
	bsf_lookup('out', OUT), 
	bsf_static('Math', MATH), 
	bsf_invoke(_, OUT, 'print', ['<tr><td>']), 
	bsf_invoke(_, OUT, 'print', [Start]), 
	bsf_invoke(_, OUT, 'print', ['</td><td>']),
	T is (Start-32) * 5/9, 
	bsf_invoke(T1, MATH, 'round', [T]), 
	bsf_invoke(_, OUT, 'print', [T1]), 
	bsf_invoke(_, OUT, 'println', ['</td></tr>']), 
	Start1 is Start + 10, 
	f2c(Start1, End). 

 f2c(30, 100). 

</bsf:scriptlet> 

