#!/bin/bash

DATA_DIR=data
GRAPHS_DIR=graphs

mkdir -p $DATA_DIR
mkdir -p $GRAPHS_DIR

for f in $(ls $DATA_DIR)
do
    echo "==================================="
    echo "Generating histogram for $f"
    ./histogram.gp $DATA_DIR/$f $GRAPHS_DIR/$f.png
done
