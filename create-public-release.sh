#!/bin/bash

set -e

# Check params
if [ $# -ne 1 ]
	then
		echo Usage: $0 version;
    echo E.g: $0 0.1.0
		echo Version is MAJOR.MINOR.BUGFIX
		echo Latest versions:
		git tag | tail -n 5
		exit 1;
fi

export VERSION=$1
RUN_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $RUN_PATH

echo ----==[ Update copyrights ]==----
./scripts/javaheaderchanger.sh > /dev/null

echo ----==[ Compile and deploy to jcenter ]==----
./gradlew clean bintrayUpload

echo ----==[ Git Tag ]==----
git tag -a -m $VERSION $VERSION

echo ----==[ Operation completed successfully ]==----

echo
echo You can see published items on https://bintray.com/foilen/maven
echo You can send the tag: git push --tags