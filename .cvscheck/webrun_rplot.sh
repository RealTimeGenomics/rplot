#! /bin/sh

LOGNAME=${LOGNAME:-$USER}
if [ ! "$HOME" ]; then
    export HOME=/home/$LOGNAME
fi

export JAVA_HOME=/usr/local/java/jdk1.4
export SYSNAME=cvscheck_rplot

# Set variables for where to check out to and where to put results
if [ "$LOGNAME" == "syscheck" ]; then
    export WEBROOT=/share/reeltwo/html/keachecker
    export SCRIPTS=$WEBROOT/cvscheck
    export CODEHOME=$WEBROOT/${SYSNAME}_java
    export WEB=$WEBROOT/html/${SYSNAME}
    export EMAIL_NOTIFY=1
else
    export CODEHOME=$HOME/reeltwo_sandboxes
fi

# Description of modules in CVS
export MAIN_MODULE="rplot"
if [ -f "$CODEHOME/$MAIN_MODULE/CVS/Root" ]; then
    export CVSROOT=$(cat "$CODEHOME/$MAIN_MODULE/CVS/Root")
else
    export CVSROOT=:pserver:$LOGNAME@giger:/home/cvs-repository2
fi
export SRC_MODULES="rplot/src"
export LIB_MODULES="rplot/lib"
export TEST_MODULES="rplot/test"
export DOC_MODULES=" "
export JAVADOC_MODULES="rplot/src"

# Options controlling how things get run
export JIKES_OPTS="+E +P -source 1.4 -deprecation"
if [ "$USER" == "syscheck" ]; then
    export EMAIL_NOTIFY=1
    export IM_NOTIFY=1
fi
export LEVEL=2


(
    (sh $SCRIPTS/cvscheck.sh cvscheck_main 2>&1)
) >/dev/null



