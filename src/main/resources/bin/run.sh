#!/bin/sh

java -cp "../lib/*:../etc" com.mhgad.za.vitel.billing.batch.Application -Dspring.profiles.active=prod
