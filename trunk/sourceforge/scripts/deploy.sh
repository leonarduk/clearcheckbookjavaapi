#!/bin/bash -ex

## -e flag means fail script if any command fails
## -x flag shows the command that is being run

VERSION=${1?-Must pass version, e.g. 0.1}
SRC=/home/stephen/workspace/clearcheckbookjavaapi-code/target/
FILESDIR=/home/stephen/Desktop/sourceforge/clearcheckbookjavaapi/files/
if [[ ! -d $FILESDIR/$VERSION ]] ; then
mkdir $FILESDIR/$VERSION
fi
cp $SRC/clearcheckbookapi-${VERSION}*jar $FILESDIR/$VERSION

cp -r /home/stephen/.m2/repository/com/leonarduk/clearcheckbook/clearcheckbookapi/* /home/stephen/Desktop/sourceforge/clearcheckbookjavaapi/web/htdocs/maven/com/leonarduk/clearcheckbook/clearcheckbookapi
cd /home/stephen/Desktop/sourceforge/clearcheckbookjavaapi/web/htdocs/maven/com/leonarduk/clearcheckbook/clearcheckbookapi/

mv maven-metadata-local.xml maven-metadata.xml
md5sum maven-metadata.xml > maven-metadata.xml.md5
sha1sum maven-metadata.xml > maven-metadata.xml.sha1
rm maven-metadata-local*

rm -rf /home/stephen/Desktop/sourceforge/clearcheckbookjavaapi/web/htdocs/javadoc/*
cp -r /home/stephen/workspace/clearcheckbookjavaapi-code/target/apidocs/* /home/stephen/Desktop/sourceforge/clearcheckbookjavaapi/web/htdocs/javadoc

