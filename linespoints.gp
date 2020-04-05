#!/usr/local/bin/gnuplot -c

# Number of experiment
N=ARG1
# Check if second argument is 'filtered'
FILTERED=ARG2 eq "filtered" ? "-filtered" : ""

Clojure="data/clj-data-".N.FILTERED.".dat"
Clojerl="data/clje-data-".N.FILTERED.".dat"

OUTPUT="graphs/linespoints-".N.FILTERED.".png"

CLJ_COLOR="blue"
CLJE_COLOR="green"

reset

# Output png file
set term png
set output OUTPUT

# Labels
set xlabel "Sample"
set ylabel "Duration [ns]"

# Logarithmic scale
# set logscale y 10

set grid

#count and plot
plot Clojure with linespoints lc rgb(CLJ_COLOR), \
     Clojerl with linespoints lc rgb(CLJE_COLOR)
