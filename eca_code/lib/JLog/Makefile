BUILD_PATH = build/JLog.build/JLog.build/JavaClasses
JLOG_PATH = Source/ubc/cs/JLog

# Uncomment to target Java VM 1.1 (needed to load Applet in MS Internet Explorer)
# JAVAC_TARGET = -target 1.1 -source 1.2

# Uncomment to create a class path including external libraries
# JAVAC_CLASSPATH = $(BUILD_PATH):lib/bsf.jar

JAVAC_CLASSPATH = $(BUILD_PATH)

# Uncomment to create a debugging version (or invoke as: make build JAVAC_FLAGS="-g -deprecation")
# JAVAC_FLAGS = -g -deprecation

JAVAC_FLAGS = -g:none -O -deprecation

JAVAC_PATHS = -sourcepath Source -classpath $(JAVAC_CLASSPATH) -d $(BUILD_PATH) 

all:   
	@echo ""
	@echo "JLog Prolog in Java -- Makefile"
	@echo ""
	@echo "make build -- compiles all source files, creates all jars (including examples)"
	@echo "     build_main -- compiles JLog java source files, creates JLog jars (non-examples)"
	@echo "make compile -- compiles all java source files"
	@echo "     compile_primary -- compiles primary java source files (not dependent upon external libraries)"
	@echo "     compile_bsf -- compiles the Extras/BSF java source files (depends on lib/bsf.jar)"
	@echo "make jar -- creates all the jar files"
	@echo "     jar_jlog -- creates the main app JLog jar file"
	@echo "     jar_jlogic -- creates the core prolog engine jlogic jar file"
	@echo "     jar_builtins -- creates builtinsLib jar file"
	@echo "     jar_animation -- creates animationLib jar file"
	@echo "     jar_extras -- creates extras jar files"
	@echo "      jar_database -- creates databaseLib extras jar file"
	@echo "      jar_filesystem -- creates filesystemLib extras jar file"
	@echo "      jar_bsf -- creates bsfLib extras jar file (depends on lib/bsf.jar)"
	@echo "make install -- installs JLog and extras into JLog folder"
	@echo "     install_jlog -- installs main JLog jar files into JLog folder"
	@echo "     install_extras -- installs extras Lib.jar files into JLog folder"
	@echo "     install_examples -- installs examples into JLog folder"
	@echo "     install_help -- installs help filesinto JLog folder"
	@echo "make build_examples -- builds Java based examples"
	@echo "     build_api_example -- builds APIQueens example"
	@echo "     build_bsf_example -- builds BSF example (depends on lib/bsf.jar)"
	@echo "make doc -- generate documentation"
	@echo "make clean -- remove documentation, class and jar files"
	@echo "make info -- information about typical usage, external library dependencies, and old build options"
	@echo ""


build: build_main build_examples 

build_main: compile jar 

build_examples: make_buildpath_dir build_api_example
	@if [ -f lib/bsf.jar ] ; then make JAVAC_FLAGS="$(JAVAC_FLAGS)" JAVAC_TARGET="$(JAVAC_TARGET)" JAVAC_CLASSPATH="$(JAVAC_CLASSPATH):lib/bsf.jar" build_bsf_example ; fi

build_api_example: make_buildpath_dir 
	@javac $(JAVAC_FLAGS) $(JAVAC_TARGET) -sourcepath Examples -classpath $(JAVAC_CLASSPATH) -d build Examples/API/APIQueens.java
	@jar -cvfm build/APIQueens.jar Examples/API/MANIFEST.MF -C build APIQueens.class 

build_bsf_example: make_buildpath_dir 
	@javac $(JAVAC_FLAGS) $(JAVAC_TARGET) -sourcepath Examples -classpath $(JAVAC_CLASSPATH) -d build Examples/BSF/ScriptedUI.java
	@jar -cvfm build/BSFScriptedUI.jar Examples/BSF/MANIFEST.MF 
	@cd build; for i in ScriptedUI*.class ; do jar -uvf BSFScriptedUI.jar `basename $$i` ; done ; cd .. 


compile: info_compile make_buildpath_dir compile_primary
	@if [ -f lib/bsf.jar ] ; then make JAVAC_FLAGS="$(JAVAC_FLAGS)" JAVAC_TARGET="$(JAVAC_TARGET)" JAVAC_CLASSPATH="$(JAVAC_CLASSPATH):lib/bsf.jar" compile_bsf ; fi

compile_primary: info_compile make_buildpath_dir
	@javac $(JAVAC_FLAGS) $(JAVAC_TARGET) $(JAVAC_PATHS) Source/*.java $(JLOG_PATH)/Foundation/*.java $(JLOG_PATH)/Terms/*.java $(JLOG_PATH)/Terms/Entries/*.java $(JLOG_PATH)/Terms/Goals/*.java $(JLOG_PATH)/Parser/*.java $(JLOG_PATH)/Builtins/*.java $(JLOG_PATH)/Builtins/Entries/*.java $(JLOG_PATH)/Builtins/Goals/*.java $(JLOG_PATH)/Animation/*.java $(JLOG_PATH)/Applet/*.java $(JLOG_PATH)/Extras/DataBase/*.java $(JLOG_PATH)/Extras/FileSystem/*.java

compile_bsf: info_compile make_buildpath_dir
	@javac $(JAVAC_FLAGS) $(JAVAC_TARGET) $(JAVAC_PATHS) $(JLOG_PATH)/Extras/BSF/*.java


info_compile:
	@echo "COMPILE INFORMATION:"
	@echo " TARGET: " $(JAVAC_TARGET)
	@echo " FLAGS: " $(JAVAC_FLAGS)
	@echo " CLASSPATH: " $(JAVAC_CLASSPATH)


jar: jar_jlog jar_extras

jar_jlog: jar_jlogic jar_animation
	@jar -cvfm build/JLog.jar Source/MANIFEST.MF -C $(BUILD_PATH) ubc/cs/JLog/Applet/ 
	@cd build; for i in JLog.build/JLog.build/JavaClasses/*.class ; do jar -uvf JLog.jar -C JLog.build/JLog.build/JavaClasses `basename $$i` ; done ; cd .. 
	@cd build ; jar -i JLog.jar ; cd ..

jar_jlogic: jar_builtins
	@jar -cvf build/jlogic.jar -C $(BUILD_PATH) ubc/cs/JLog/Foundation/ -C $(BUILD_PATH) ubc/cs/JLog/Terms/ -C $(BUILD_PATH) ubc/cs/JLog/Parser/  
	@cd build ; jar -i jlogic.jar ; cd ..

jar_builtins:
	@jar -cvf build/builtinsLib.jar -C $(BUILD_PATH) ubc/cs/JLog/Builtins/ -C $(JLOG_PATH)/Builtins INIT_BUILTINSLIB.TOC
	@cd build ; jar -i builtinsLib.jar ; cd ..

jar_animation:
	@jar -cvf build/animationLib.jar -C $(BUILD_PATH) ubc/cs/JLog/Animation/ -C $(JLOG_PATH)/Animation INIT_ANIMATIONLIB.TOC
	@cd build ; jar -i animationLib.jar ; cd ..

jar_extras: jar_database jar_filesystem 
	@if [ -f lib/bsf.jar ] ; then make JAVAC_FLAGS="$(JAVAC_FLAGS)" JAVAC_TARGET="$(JAVAC_TARGET)" JAVAC_CLASSPATH="$(JAVAC_CLASSPATH):lib/bsf.jar" jar_bsf ; fi

jar_database:
	@jar -cvf build/databaseLib.jar -C $(BUILD_PATH) ubc/cs/JLog/Extras/DataBase/ -C $(JLOG_PATH)/Extras/DataBase INIT_DATABASELIB.TOC
	@cd build ; jar -i databaseLib.jar ; cd ..

jar_filesystem:
	@jar -cvf build/filesystemLib.jar -C $(BUILD_PATH) ubc/cs/JLog/Extras/FileSystem/ -C $(JLOG_PATH)/Extras/FileSystem INIT_FILESYSTEMLIB.TOC
	@cd build ; jar -i filesystemLib.jar ; cd ..

jar_bsf:
	@jar -cvf build/bsfLib.jar -C $(BUILD_PATH) ubc/cs/JLog/Extras/BSF/ -C $(JLOG_PATH)/Extras/BSF INIT_BSFLIB.TOC
	@cd build ; jar -i bsfLib.jar ; cd ..


install: make_install_dir install_readme install_jlog install_extras install_examples install_help

install_readme: make_install_dir
	@cp GPL.txt JLog
	@cp LICENSE.txt JLog
	@cp VERSIONS.txt JLog
	@cp README.txt JLog

install_jlog: make_install_dir
	@cp build/JLog.jar JLog
	@cp build/jlogic.jar JLog
	@cp build/builtinsLib.jar JLog
	@cp build/animationLib.jar JLog

install_extras: make_install_dir
	@cp build/databaseLib.jar JLog
	@cp build/filesystemLib.jar JLog
	@if [ -f lib/bsf.jar ] ; then cp lib/bsf.jar JLog ; fi
	@if [ -f build/bsfLib.jar ] ; then cp build/bsfLib.jar JLog ; fi

install_examples: make_install_dir
	@cp Examples/*.data JLog
	@cp Examples/*.html JLog
	@cp Examples/*.plog JLog
	@if [ -f build/APIQueens.jar ] ; then make install_api_example ; fi
	@if [ -f build/BSFScriptedUI.jar ] ; then make install_bsf_example ; fi

install_api_example: make_install_dir
	@cp build/APIQueens.jar JLog

install_bsf_example: make_install_dir
	@cp Examples/BSF/*.plog JLog
	@cp build/BSFScriptedUI.jar JLog

install_help: make_install_dir
	@cp -R Help JLog


doc:
	@mkdir -p Documentation
	@javadoc -author -d Documentation -sourcepath Source -classpath $(BUILD_PATH):lib/bsf.jar Source/*.java $(JLOG_PATH)/Foundation/*.java $(JLOG_PATH)/Terms/*.java $(JLOG_PATH)/Terms/Entries/*.java $(JLOG_PATH)/Terms/Goals/*.java $(JLOG_PATH)/Parser/*.java $(JLOG_PATH)/Builtins/*.java $(JLOG_PATH)/Builtins/Entries/*.java $(JLOG_PATH)/Builtins/Goals/*.java $(JLOG_PATH)/Animation/*.java $(JLOG_PATH)/Applet/*.java $(JLOG_PATH)/Extras/DataBase/*.java $(JLOG_PATH)/Extras/FileSystem/*.java $(JLOG_PATH)/Extras/BSF/*.java 


clean:
	@rm -f build/JLog.jar
	@rm -f build/jlogic.jar
	@rm -f build/builtinsLib.jar
	@rm -f build/animationLib.jar
	@rm -f build/databaseLib.jar
	@rm -f build/filesystemLib.jar
	@rm -f build/bsfLib.jar
	@rm -f build/APIQueens.jar
	@rm -f build/BSFScriptedUI.jar
	@rm -f build/*.class
	@rm -rf build/JLog.build/JLog.build/*
	@rm -rf JLog/*
	@rm -rf Documentation/*
	@for i in  . Source Source/ubc Source/ubc/cs Source/ubc/cs/JLog ; do rm -f $$i/.DS_Store ;  done
	@for i in Applet Foundation Terms Terms/Entries Terms/Goals Parser Builtins/Entries Builtins/Goals Animation Extras Extras/DataBase Extras/FileSystem Extras/BSF ; do rm -f $(JLOG_PATH)/$$i/.DS_Store ; done


info:
	@echo ""
	@echo "Typical usage is: make build ; make install"
	@echo ""
	@echo "bsfLib.jar requires the bsf.jar file from http://jakarta.apache.org/bsf/ in the lib/ directory."
	@echo ""
	@echo "make build_old -- build suitable for Java VM 1.1 (needed for MS IE)"
	@echo "     build_api_example_old -- build APIQueens for Java VM 1.1 (with old classpath in Manifest)"
	@echo "make install_old -- install counterpart for build_old target (make build_old ; make install_old)"
	@echo ""


make_install_dir:
	@mkdir -p JLog

make_buildpath_dir:
	@mkdir -p $(BUILD_PATH)


##############################################################
#
# DEPRECATED
#
# These make actions create a version of the JLog.jar file that runs on old Java VM version (like in MS IE).
#
##############################################################
build_old:
	@make JAVAC_FLAGS="$(JAVAC_FLAGS)" JAVAC_CLASSPATH="$(JAVAC_CLASSPATH)" JAVAC_TARGET="-target 1.1 -source 1.2" compile
	@make JAVAC_FLAGS="$(JAVAC_FLAGS)" JAVAC_CLASSPATH="$(JAVAC_CLASSPATH)" JAVAC_TARGET="-target 1.1 -source 1.2" jar_old
	@make JAVAC_FLAGS="$(JAVAC_FLAGS)" JAVAC_CLASSPATH="$(JAVAC_CLASSPATH)" JAVAC_TARGET="-target 1.1 -source 1.2" build_api_example_old

jar_old: jar_extras
	@jar -cvfm build/JLog.jar Source/MANIFEST.MF -C $(BUILD_PATH) ubc/cs/JLog/Applet/ 
	@cd build; for i in JLog.build/JLog.build/JavaClasses/*.class ; do jar -uvf JLog.jar -C JLog.build/JLog.build/JavaClasses `basename $$i` ; done ; cd .. 
	@jar -uvf build/JLog.jar -C $(BUILD_PATH) ubc/cs/JLog/Foundation/ -C $(BUILD_PATH) ubc/cs/JLog/Terms/ -C $(BUILD_PATH) ubc/cs/JLog/Parser/
	@jar -uvf build/JLog.jar -C $(BUILD_PATH) ubc/cs/JLog/Builtins/ -C $(JLOG_PATH)/Builtins INIT_BUILTINSLIB.TOC
	@jar -uvf build/JLog.jar -C $(BUILD_PATH) ubc/cs/JLog/Animation/ -C $(JLOG_PATH)/Animation INIT_ANIMATIONLIB.TOC
	@cd build ; jar -i JLog.jar ; cd ..

build_api_example_old:
	@make JAVAC_TARGET="-target 1.1 -source 1.2" build_api_example
	@jar -uvfm build/APIQueens.jar Examples/API/MANIFEST.MF.old

install_old: make_install_dir install_readme install_extras install_examples install_help
	@cp build/JLog.jar JLog
