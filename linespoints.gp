#!/usr/local/bin/gnuplot -c

N=ARG1

Clojure="data/clj-data-".N.".dat"
Clojerl="data/clje-data-".N.".dat"

OUTPUT="graphs/linespoints-".N.".png"

CLJ_COLOR="blue"
CLJE_COLOR="green"

reset
set term png #output terminal and file
set output OUTPUT
set xlabel "Sample"
set ylabel "Duration [ns]"
#count and plot
plot Clojure with linespoints lc rgb(CLJ_COLOR), \
     Clojerl with linespoints lc rgb(CLJE_COLOR)
