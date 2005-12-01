#! /bin/sh

LOGNAME=${LOGNAME:-$USER}
if [ ! "$HOME" ]; then
    export HOME=/home/$LOGNAME
fi

export JAVA=/usr/local/java/jdk
export PATH=$PATH:$JAVA/bin:$HOME/bin:/usr/local/bin
export SYSNAME=cvscheck_rplot

if [ "$LOGNAME" == "syscheck" ]; then
    export WEBROOT=/share/reeltwo/html/keachecker
    export SCRIPTS=$WEBROOT/cvscheck
    export CODEHOME=$WEBROOT/${SYSNAME}_java
    export WEB=$WEBROOT/html/${SYSNAME}
    export EMAIL_NOTIFY=1
else
    export CODEHOME=$HOME/reeltwo_sandboxes
fi

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
export JIKES_OPTS="+E +P -source 1.4 -deprecation"
export LEVEL=2


(
    (sh $SCRIPTS/cvscheck.sh cvscheck_main 2>&1)
) >/dev/null



