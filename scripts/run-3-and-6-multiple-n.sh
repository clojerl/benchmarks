#!/bin/bash

DATA_DIR=data

mkdir -p $DATA_DIR

for i in 3 6
do
    echo "==================================="
    echo "Running experiment $i"
    for n in $(seq 100000 100000 1000000)
    do
        echo "with n=$n ..."
        echo "--- Clojure -----------------------"
        ./lein run -m benchmarks -- $i $n
        echo "--- Clojerl -----------------------"
        ./rebar3 clojerl run -m benchmarks -- $i $n
    done
done
