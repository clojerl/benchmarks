#!/usr/local/bin/gnuplot -c

# Number of experiment
N=ARG1
# Second argument is the file suffix which can be empty
SUFFIX=ARG2

Clojure="data/clj-data-".N."-multiple-n.dat"
Clojerl="data/clje-data-".N."-multiple-n.dat"

CLJ_COLOR="dark-red"
CLJE_COLOR="dark-green"

UNITS_LABEL=N eq "3" || N eq "6"? "ms" : "ns"
UNITS_VALUE=N eq "3" || N eq "6"? 1000000 : 1

LINE_WIDTH=2.5

################################################################################
# Points
################################################################################

OUTPUT="graphs/linespoints-".N."-multiple-n.png"

reset

# Output png file
set term png
set output OUTPUT

# Labels
set offset graph 0,0.05,0.05,0
set xlabel "Number of Elements (x 1000)"
set ylabel "Mean Time (".UNITS_LABEL.")"
set key left top

# Logarithmic scale
# set logscale y 10

set grid

#count and plot
plot Clojure \
     using ($1/1000):($2/UNITS_VALUE) \
     with linespoints lc rgb(CLJ_COLOR) lw LINE_WIDTH \
     title "Clojure", \
     Clojerl \
     using ($1/1000):($2/UNITS_VALUE) \
     with linespoints lc rgb(CLJE_COLOR) lw LINE_WIDTH \
     title "Clojerl"
