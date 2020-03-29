#!/usr/local/bin/gnuplot -c

N=ARG1

Clojure="data/clj-data-".N.".dat"
Clojerl="data/clje-data-".N.".dat"

OUTPUT="graphs/linespoints-".N.".png"

CLJ_COLOR="blue"
CLJE_COLOR="green"

reset

# Output png file
set term png
set output OUTPUT

# Labels
set xlabel "Sample"
set ylabel "Duration [ns]"
#count and plot
plot Clojure with linespoints lc rgb(CLJ_COLOR), \
     Clojerl with linespoints lc rgb(CLJE_COLOR)
