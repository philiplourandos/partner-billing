#!/bin/sh

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -cp "../lib/*:../etc" -Dspring.profiles.active=prod com.mhgad.za.vitel.billing.batch.aspivia.Application -i $1 -s $2 -o $3
