#!/bin/bash
#SBATCH --job-name ECJ_sample
#SBATCH --mail-user=yourmail@post.bgu.ac.il
##SBATCH --mail-type=END,FAIL,REQUEU

#SBATCH --cpus-per-task=8 # 8 cpus per task - use for multithreading, usually with --tasks=1
#SBATCH --tasks=1 # 1 processes - use for multiprocessing

# required to support both simple runs from bash (without parameters), and SLURM from regressions.sh
if [ $# -eq 0 ]
then
    TARGET_CLASSES=target/classes
else
    TARGET_CLASSES=$1/target/classes
fi
echo $TARGET_CLASSES

time $JAVA_HOME/bin/java -Dfile.encoding=UTF-8 -classpath $TARGET_CLASSES:$HOME/.m2/repository/com/github/ZvikaZ/ecj/27.1-zvika-3/ecj-27.1-zvika-3.jar:/home/zvikah/.m2/repository/org/ejml/all/0.27/all-0.27.jar SampleEvolve

