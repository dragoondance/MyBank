#!/bin/bash

# this script is used to clean target folder, jar the project and run the jar file
version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
projectId=$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)

mvn clean package

mvn jar:jar -f "pom.xml"

cd target

java -jar $projectId-$version.jar