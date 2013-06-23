JLog is a standard Prolog interpreter written in Java, so it runs almost anywhere. It is quite fast, and perfectly suited for educational purposes. It includes built-in source editor, query panels, online help, animation primitives, and a GUI debugger.  It works as both an Applet and an Application (which supports loading, editing, and saving source files).

JLog is LICENSED under the GPL (see GPL.txt) and provided WITHOUT ANY WARRANTY.  Please read and abide by the LICENSE (see LICENSE.txt) 


======== COMPILATION ==========

If you have the JLog-X.X.X.src.zip file, you can build the JLog application and documentation.

To see the options for make, just type:

make

To create an installable version of JLog, for use either locally or via the 
Web, try the following commands (Unix like systems):

make build
make install

The JLog directory will contain the JLog binary file, and some example *.html 
files to launch the JLog applet.

To create the documentation for JLog, use the command:

make doc

The documentation appears in HTML format in the Documentation directory.

The APIQueens example (this is a command line program which solves the N-Queens problem, and demonstrates how to use the JLog API for the Prolog engine) is automatically built and installed.

The Extras bsfLib.jar library, and the ScriptedUI example require requires the bsf.jar file from http://jakarta.apache.org/bsf/ in the lib/ directory.  If the lib/bsf.jar exists, then both the library and the example built and installed as part of the standard build / install process.

There is also a JLog.xcodeproj project file for MacOS X Xcode IDE.  This 
is useful for debugging and developing.

There is now a secondary build system, using Ant <ant.apache.org>, that has the main targets above.  To create an installable version of JLog, use the command: 

ant build install

---

For Windows users who may not have the 'make' command, there are three options:

1) Get Cygwin <www.cygwin.com>, a free UNIX environment for Windows.  Then follow the commands for Unix like systems above.
2) Install Ant <ant.apache.org>, a Java based build system, and use the provided build.xml file.
3) Open the 'Makefile' file in a text editor, and note the commands used to compile the java source files, install the jar, build the documentation, etc.  Commands begin with a tab and an @ sign - don't include these when you enter the commands manually.  Replace variables (i.e., $(VAR_NAME) ) with the value assigned to them at the start of the Makefile (e.g., replace $(BUILD_PATH) with 'build/JLog.build/JLog.build/JavaClasses', sans quotes).  You can copy and paste the commands in the order they appear under each label (e.g., to compile the source and create the JLog.jar file issue all commands after the 'jar:' label until the 'install:' label) .  Note: the Unix 'cp' command would be the 'copy' command under Windows.  The remaining commands should work (I've not verified this).  You'll also need to use backslash (\) instead of forward slash (/), so replace any occurance of /, in the Makefile commands, with \.  Make sure you are in the JLog directory (the one that contains the Makefile) when issuing those commands.


======== IDE DEVELOPMENT ========

Developers wishing to use an Integrated Development Environment (IDE), can either duplicate the build products of the Makefile (e.g., jlogic.jar, and the *Lib.jar files), or create a single *.jar file.

The most typical way to set up an IDE project is to add all source files to a single target, and produce a single *.jar file (e.g., JLog.jar) Ð even the JLog.xcodeproj project file (for Xcode) does this Ð but this is not how the Makefile builds the the project.

Note: If the IDE compiles into class files inside the build directory, just as the Makefile does (e.g., the JLog.pbproj for Xcode), you should clean out those class files before using the IDE (e.g., Build->Clean Target in Xcode).  The reason is that the Makefile compiles the source files without debug information (and the IDE may not overwrite them), but for development you'll likely want to debug.

The Makefile compilation commands do create a working system, but hopefully the above will help you to get things working nicely in your preferred IDE.


======== WEB SITE ==========

Check out:  <http://jlogic.sourceforge.net/> or 
            <http://sourceforge.net/projects/jlogic> 
for further information about JLog, or to contact the authors.


======== USING ==========

JLog works as both an Applet and an Application (all from the same *.jar file).  The applet mode is invoked via a *.hmtl file (such as JLog.html), and it is useful for demo purposes, and online availability (it runs in the browser).  The application mode supports multiple knowledge bases (i.e., source files), a multiple window interface, along with opening and saving local files.

To use as an applet try the command:

appletviewer JLog.html

Or, open the *.html files from your browser.

To use as an application try the command:

java -jar JLog.jar

Or double-click the JLog.jar file in MacOS X.

To run the APIQueens example, try the command (a number argument determines the size of the board -- 8x8 with 8 queens in this example):

java -jar APIQueens.jar 8


======== USING BSFLIB.JAR ========

The pre-compiled JLog-X.X.X.app.zip comes with the bsfLib.jar file, but not the bsf.jar library from http://jakarta.apache.org/bsf/.  

To get bsfLib.jar to work, download the bsf.jar library from:

http://jakarta.apache.org/bsf/

And copy it into the directory that contains bsfLib.jar (typically, the JLog directory).


======== REQUIREMENTS ==========

Requires Java 1.2.x or above. See notes below.ÊJava 1.2.x and above are preferred the default binaries target later versions.  Users can recompile JLog from the source to create a version that will run on older versions of Java (for now a nag dialog appears, reminding users to upgrade their Java VM).                                         

Note for MS Internet Explorer:Ê JLog can work with Explorer if compiled with 'make build_old' and 'make install_old', however MS Internet Explorer is stuck with verion 1.1 of the Java VM. Current versions of the Java VM to upgrade Internet Explorer are available from the download section of www.java.com.  Ê                                         

Note for Appletviewer:Ê Sun's appletviewer program supplied with the JDK 1.2.x and up works well with JLog and our examples.Ê To run the JLog applet using appletviewer, specify the JLog URL as a parameter to appletviewer.                                         

Note for MacOS X Java Runtime: The Java 1.4 runtime works very well with JLog and the preloaded examples below. To run the JLog applet using the Applet Launcher, open the JLog URL.  To launch the application, double click on the JLog.jar file.  