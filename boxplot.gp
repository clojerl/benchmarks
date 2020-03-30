#!/usr/local/bin/gnuplot -c

N=ARG1

Clojure="data/clj-data-".N.".dat"
Clojerl="data/clje-data-".N.".dat"

OUTPUT="graphs/boxplot-".N.".png"

CLJ_COLOR="blue"
CLJE_COLOR="green"

maximum(x, y) = (x > y ? x : y)
minimum(x, y) = (x < y ? x : y)

reset

stdev_factor=0.1

# Get stats from data
stats Clojure u 1 nooutput
clj_min=STATS_mean-STATS_stddev*stdev_factor
clj_max=STATS_mean+STATS_stddev*stdev_factor

stats Clojerl u 1 nooutput
clje_min=STATS_mean-STATS_stddev*stdev_factor
clje_max=STATS_mean+STATS_stddev*stdev_factor

min=minimum(clj_min, clje_min)
max=maximum(clj_max, clje_max)

# Don't go below 0
min=maximum(0, min)

# Output png file
set term png
set output OUTPUT

# Labels
set style fill solid 0.5 border -1
# set style boxplot outliers pointtype 7
set style boxplot nooutliers
# set style boxplot fraction 0.95
set style data boxplot
set boxwidth  0.5
set pointsize 0.5

unset key
set border 2
set xtics ("Clojure" 1, "Clojerl" 2) scale 0.0
set xtics nomirror
set ytics nomirror
set yrange [min:max]

# set xlabel "Sample"
# set ylabel "Duration [ns]"

#count and plot
plot Clojure using (1):1, \
     Clojerl using (2):1
