#!/bin/sh

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000 -cp "../lib/*:../etc" -Dspring.profiles.active=prod com.mhgad.za.vitel.billing.batch.extract.Application $1 $2