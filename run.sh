#!/bin/bash
#SBATCH --job-name ECJ_sample
#SBATCH --mail-user=yourmail@post.bgu.ac.il
##SBATCH --mail-type=END,FAIL,REQUEU

#SBATCH --cpus-per-task=8 # 8 cpus per task - use for multithreading, usually with --tasks=1
#SBATCH --tasks=1 # 1 processes - use for multiprocessing

time $JAVA_HOME/bin/java -Dfile.encoding=UTF-8 -classpath target/classes:local-maven-repo/edu/gmu/cs/ecj/27/ecj-27.jar SampleEvolve

