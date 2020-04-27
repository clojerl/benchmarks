#!/bin/bash

DATA_DIR=data

mkdir -p $DATA_DIR

for i in $(seq 0 7)
do
    echo "==================================="
    echo "Running experiment $i"
    echo "--- Clojure -----------------------"
    ./lein run -m benchmarks -- $i
    echo "--- Clojerl -----------------------"
    ./rebar3 clojerl run -m benchmarks -- $i
done
