#!/bin/bash

DATA_DIR=data
GRAPHS_DIR=graphs

mkdir -p $DATA_DIR
mkdir -p $GRAPHS_DIR

for i in $(seq -1 7)
do
    echo "==================================="
    echo "Generating graphs for $i"
    ./graphs.gp $i
    ./graphs.gp $i -filtered
    ./graphs.gp $i -no-overhead
    ./graphs.gp $i -no-overhead-filtered
done
