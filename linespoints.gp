#!/usr/local/bin/gnuplot -c

# Number of experiment
N=ARG1
# Second argument is the file suffix which can be empty
SUFFIX=ARG2

Clojure="data/clj-data-".N.SUFFIX.".dat"
Clojerl="data/clje-data-".N.SUFFIX.".dat"

OUTPUT="graphs/linespoints-".N.SUFFIX.".png"

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
