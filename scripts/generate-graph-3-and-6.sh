#!/bin/bash

DATA_DIR=data

mkdir -p $DATA_DIR

for i in 3 6
do
    echo "==================================="
    echo "Generating graphs for $i"
    scripts/graphs-multiple-n.gp $i
done
