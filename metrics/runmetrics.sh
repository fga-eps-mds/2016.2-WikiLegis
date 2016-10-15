#!/bin/bash
#	Created by FreemanPivo - Metrics Log Filtering Automator

# Working directories
javaProjectFolderPath='app/src/main/java/'
logsPath='metrics/logs'
logsFolder='logs/'

if [ ! -d "$logsFolder" ]; then
   mkdir logs
fi

# Run analizo metrics
cd ..
analizo metrics $javaProjectFolderPath > $logsPath/general.log
cd metrics

# Erases previous filtered log, if created before.
> logs/filtered.log

evaluateAcc () {
   grep acc_mean logs/general.log >> logs/filtered.log;
   grep acc_quantile_upper logs/general.log >> logs/filtered.log;
   grep acc_quantile_ninety_five logs/general.log >> logs/filtered.log;
}

evaluateAccm () {
   grep accm_mean logs/general.log >> logs/filtered.log;
   grep accm_quantile_upper logs/general.log >> logs/filtered.log;
   grep accm_quantile_ninety_five logs/general.log >> logs/filtered.log;
}

evaluateAmloc () {
   grep amloc_mean logs/general.log >> logs/filtered.log;
   grep amloc_quantile_upper logs/general.log >> logs/filtered.log;
   grep amloc_quantile_ninety_five logs/general.log >> logs/filtered.log;
}

evaluateLcom () {
   grep lcom4_mean logs/general.log >> logs/filtered.log;
   grep lcom4_quantile_upper logs/general.log >> logs/filtered.log;
   grep lcom4_quantile_ninety_five logs/general.log >> logs/filtered.log;
}

evaluateCof () {
   grep cof logs/general.log >> logs/filtered.log;
}

# Append ACC metrics in file
echo 'Calculating ACC...'
evaluateAcc

# Append ACCM metrics in file
echo 'Calculating ACCM...'
evaluateAccm

# Append AMLOC metrics in file
echo 'Calculating AMLOC...'
evaluateAmloc

# Append LCOM4 metrics in file
echo 'Calculating LCOM4...'
evaluateLcom

# Append COF metrics in file
echo 'Calculating COF...'
evaluateCof

echo '[OK]'
echo ''
echo ''

# Print filtered file on terminal
echo '                        ### METRICS ###'
echo ''
cat logs/filtered.log

