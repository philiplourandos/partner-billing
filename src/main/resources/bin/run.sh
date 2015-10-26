#!/bin/sh

java -jar ../lib/${project.build.finalName}.jar -Dspring.profiles.active=prod
