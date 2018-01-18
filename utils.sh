#!/bin/bash

function getVersion(){
    version=`grep '^version' build.sbt | sed -e 's/.*"\(.*\)".*/\1/'`
    echo $version
}
    
function getName(){
    name=`grep '^name' build.sbt | sed -e 's/.*"\(.*\)".*/\1/'`
    echo $name
}
