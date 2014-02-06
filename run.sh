#!/bin/bash

USER=${1?-$0 user password}
PASSWORD=${2?-$0 user password}
java -Dclearcheckbook.user=$USER -Dclearcheckbook.password=$PASSWORD -jar target/clearcheckbookapi-0.1-jar-with-dependencies.jar

