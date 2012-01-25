# Makefile to build rplot standalone jarfile
CODEHOME:=$(HOME)/sandbox/rplot
SRCHOME:=$(CODEHOME)/src
TESTHOME:=$(CODEHOME)/test
TMPMANIFEST:=/tmp/manifest

JARS:=RPlot.jar

RPSRC=$(shell find $(SRCHOME)/ -name "*.java")
RPSRC2=$(shell find $(RPSRC) | sed -e "s|$(SRCHOME)/||g")
RPTEST=$(shell find $(TESTHOME)/ -name "*.java")
RPTEST2=$(shell find $(RPTEST) | sed -e "s|$(TESTHOME)/||g")
RPCLASSES=$(shell echo $(RPSRC)" "$(RPTEST) | sed "s/\.java/\.class/g")
RPCLASSES2=$(shell echo $(RPSRC) | sed -e "s|$(SRCHOME)/||g" -e "s/\.java/\*\.class/g")
RPCLASSES3=$(shell echo $(RPTEST) | sed -e "s|$(TESTHOME)/||g" -e "s/\.java/\*\.class/g")


LIB_CLASSPATH:=$(shell find $(CODEHOME)/lib -name "*.jar" | tr '\n' ':')
JIKES_OPTS:=+E +P -source 1.4 -deprecation
JIKES_CMD:=jikes $(JIKES_OPTS) -cp $(SRCHOME):$(TESTHOME):$(LIB_CLASSPATH)
JAVAC_OPTS=-J-Xmx256m -g:lines,source -deprecation -Xlint -Xlint:-unchecked -Xlint:-serial -source 1.5
JAVAC_CMD:=javac $(JAVAC_OPTS) -cp $(SRCHOME):$(TESTHOME):$(LIB_CLASSPATH)

all: $(JARS)

clean:
	rm -f $(JARS)

vars:
	@echo "CODEHOME=$(CODEHOME)"
	@echo "JIKES_CMD=$(JAVAC_CMD)"

# generic .class from .java building
%.class : %.java
	$(JAVAC_CMD) $<


RPlot.jar: $(RPCLASSES)
	echo "Main-Class: com.reeltwo.plot.demo.SwingPlot" >$(TMPMANIFEST)
	jar cfm $@ $(TMPMANIFEST)
	(cd $(SRCHOME);            jar uf $(CODEHOME)/$@ $(RPCLASSES2))
	(cd $(SRCHOME);            jar uf $(CODEHOME)/$@ com/reeltwo/plot/patterns/*.png $(RPSRC2))
	(cd $(TESTHOME);           jar uf $(CODEHOME)/$@ $(RPCLASSES3) $(RPTEST2))
	rm -rf $(TMPMANIFEST)

