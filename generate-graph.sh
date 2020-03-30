#!/bin/bash

DATA_DIR=data
GRAPHS_DIR=graphs

mkdir -p $DATA_DIR
mkdir -p $GRAPHS_DIR

for i in $(seq 0 7)
do
    echo "==================================="
    echo "Generating graphs for $i"
    ./histogram.gp $i
    ./linespoints.gp $i
    ./boxplot.gp $i
done
