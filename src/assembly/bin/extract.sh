#!/bin/sh

java -cp "../lib/*:../etc" -Dspring.profiles.active=prod com.mhgad.za.vitel.billing.batch.extract.Application $1 $2
