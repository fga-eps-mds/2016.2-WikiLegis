#!/bin/bash
#	Created by FreemanPivo - Metrics Log Filtering Automator

# Working directories
javaProjectFolderPath='app/src/main/java/'
logsPath='metrics/logs'

# Run analizo metrics
cd ..
analizo metrics $javaProjectFolderPath > $logsPath/general.log
cd metrics

# Erases previous filtered log, if created before.
> logs/filtered.log

#analizo javaDirectory > logs/general.log;

# Append ACC metrics in file
grep acc_mean logs/general.log >> logs/filtered.log;
grep acc_quantile_upper logs/general.log >> logs/filtered.log;
grep acc_quantile_ninety_five logs/general.log >> logs/filtered.log;

# Append ACCM metrics in file
grep accm_mean logs/general.log >> logs/filtered.log;
grep accm_quantile_upper logs/general.log >> logs/filtered.log;
grep accm_quantile_ninety_five logs/general.log >> logs/filtered.log;

# Append AMLOC metrics in file
grep amloc_mean logs/general.log >> logs/filtered.log;
grep amloc_quantile_upper logs/general.log >> logs/filtered.log;
grep amloc_quantile_ninety_five logs/general.log >> logs/filtered.log;

# Append LCOM4 metrics in file
grep lcom4_mean logs/general.log >> logs/filtered.log;
grep lcom4_quantile_upper logs/general.log >> logs/filtered.log;
grep lcom4_quantile_ninety_five logs/general.log >> logs/filtered.log;

echo 'DONE!\n'

# Print filtered file on terminal
cd logs/
cat filtered.log

cd ..


