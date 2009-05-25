#! /bin/sh

LOGNAME=${LOGNAME:-$USER}
if [ ! "$HOME" ]; then
    export HOME=/home/$LOGNAME
fi

export JAVA_HOME=/usr/local/java/jdk1.5
export SYSNAME=cvscheck_rplot

# Set variables for where to check out to and where to put results
if [ "$LOGNAME" == "syscheck" ]; then
    export WEBROOT=/share/reeltwo/html/keachecker
    export SCRIPTS=$WEBROOT/cvscheck
    export CODEHOME=$WEBROOT/${SYSNAME}_java
    export WEB=$WEBROOT/html/${SYSNAME}
else
    export CODEHOME=$HOME/reeltwo_sandboxes
    export WEB=$HOME/.public_html/${SYSNAME}
fi

# Description of modules in subversion
export MAIN_MODULE="rplot"
export CVSROOT=
if [ -f "$CODEHOME/$MAIN_MODULE/.svn/entries" ]; then
    export SVNROOT=$(cd "$CODEHOME/$MAIN_MODULE"; svn info | sed -n "/^URL/s/^URL: //p")
else
    export SVNROOT="svn://giger/home/svn/$MAIN_MODULE/trunk"
fi
export SRC_MODULES="rplot/src"
export LIB_MODULES="rplot/lib"
export TEST_MODULES="rplot/test"
export RES_MODULES=" "
export JAVADOC_MODULES="rplot/src"

# Options controlling how things get run
export JIKES_OPTS="+E +P -source 1.4 -deprecation"
export PLOT_PACKAGE_DEPENDENCIES_OPTS='com.reeltwo com.reeltwo 4 4'
if [ "$USER" == "syscheck" ]; then
    export EMAIL_NOTIFY=1
    export IM_NOTIFY=1
else
    export DISABLE_JUMBLE=1
fi
export LEVEL=3


#(
    (sh $SCRIPTS/cvscheck.sh cvscheck_main 2>&1)
#) >/dev/null



