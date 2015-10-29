#!/bin/sh

java -cp "../lib/*:../etc" -Dspring.profiles.active=prod com.mhgad.za.vitel.billing.batch.aspivia.Application $1 $2

