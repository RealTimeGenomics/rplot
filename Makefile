# Makefile to build rplot standalone jarfile
CODEHOME:=$(HOME)/java/rplot
SRCHOME:=$(CODEHOME)/src
TESTHOME:=$(CODEHOME)/test
TMPMANIFEST:=/tmp/manifest
TMPAPDIR:=/tmp/rpdist

JARS:=RPlot.jar

RPSRC=$(shell find $(SRCHOME)/ -name "*.java")
RPTEST=$(shell find $(TESTHOME)/ -name "*.java")
RPCLASSES=$(shell echo $(RPSRC)" "$(RPTEST) | sed "s/\.java/\.class/g")
RPCLASSES2=$(shell echo $(RPSRC) | sed -e "s|$(SRCHOME)/||g" -e "s/\.java/\*\.class/g") com/reeltwo/plot/ui/ZoomListener.class
RPCLASSES3=$(shell echo $(RPTEST) | sed -e "s|$(TESTHOME)/||g" -e "s/\.java/\*\.class/g")


LIB_CLASSPATH:=$(shell find $(CODEHOME)/lib -name "*.jar" | tr '\n' ':')
# JIKES_OPTS is inherited from the user environment
JIKES_CMD:=jikes $(JIKES_OPTS) -cp $(SRCHOME):$(TESTHOME):$(LIB_CLASSPATH)

all: $(JARS)

clean:
	rm -f $(JARS)

vars:
	@echo "CODEHOME=$(CODEHOME)"
	@echo "JIKES_CMD=$(JIKES_CMD)"

# generic .class from .java building
%.class : %.java
	$(JIKES_CMD) $<


RPlot.jar: $(RPCLASSES)
	echo "Main-Class: com.reeltwo.plot.ui.SwingPlot" >$(TMPMANIFEST)
	jar cfm $@ $(TMPMANIFEST)
	(cd $(SRCHOME);            jar uf $(CODEHOME)/$@ $(RPCLASSES2))
	(cd $(TESTHOME);            jar uf $(CODEHOME)/$@ $(RPCLASSES3))
	rm -rf $(TMPMANIFEST)

