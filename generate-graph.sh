#!/bin/bash

DATA_DIR=data
GRAPHS_DIR=graphs

mkdir -p $DATA_DIR
mkdir -p $GRAPHS_DIR

for i in $(seq -1 7)
do
    echo "==================================="
    echo "Generating graphs for $i"
    ./histogram.gp $i
    ./histogram.gp $i -filtered
    ./histogram.gp $i -no-overhead
    ./linespoints.gp $i
    ./linespoints.gp $i -filtered
    ./linespoints.gp $i -no-overhead
    ./boxplot.gp $i
    ./boxplot.gp $i -filtered
    ./boxplot.gp $i -no-overhead
done
