#!/usr/local/bin/gnuplot -c

# Number of experiment
N=ARG1
# Second argument is the file suffix which can be empty
SUFFIX=ARG2

Clojure="data/clj-data-".N.SUFFIX.".dat"
Clojerl="data/clje-data-".N.SUFFIX.".dat"

CLJ_COLOR="dark-red"
CLJE_COLOR="dark-green"

UNITS_LABEL=N eq "3" || N eq "6"? "ms" : "ns"
UNITS_VALUE=N eq "3" || N eq "6"? 1000000 : 1

UNITS_LABEL=N eq "5"? "μs" : UNITS_LABEL
UNITS_VALUE=N eq "5"? 1000 : UNITS_VALUE

################################################################################
# Histogram
################################################################################

OUTPUT="graphs/histogram-".N.SUFFIX.".png"

maximum(x, y) = (x > y ? x : y)
minimum(x, y) = (x < y ? x : y)

reset

# Get stats from data
stats Clojure u 1 nooutput
max=STATS_max
min=STATS_min

stats Clojerl u 1 nooutput
max=maximum(STATS_max, max)
min=minimum(STATS_min, min)

n=100 #number of intervals
width=(max-min)/n #interval width

#function used to map a value to the intervals
hist(x,width)=width*floor(x/width)+width/2.0

#output terminal and file
set term png truecolor
set output OUTPUT

# Determine ranges for axis
set xrange [min:max]
set yrange [0:]

# to put an empty boundary around the
# data inside an autoscaled graph.
set offset graph 0.05,0.05,0.05,0.05
set xtics min,(max-min)/5,max
set boxwidth width*0.9
set style fill transparent solid 0.5 #fillstyle
set tics out nomirror
set xlabel "Duration [".UNITS_LABEL."]"
set ylabel "Number of Samples"

set grid

#count and plot
plot Clojure using (hist($1,width)):(1) \
             smooth freq w boxes lc rgb(CLJ_COLOR) \
             title "Clojure", \
     Clojerl using (hist($1,width)):(1) \
             smooth freq w boxes lc rgb(CLJE_COLOR) \
             title "Clojerl"

################################################################################
# Points
################################################################################

OUTPUT="graphs/points-".N.SUFFIX.".png"

reset

# Output png file
set term png
set output OUTPUT

# Labels
set offset graph 0,0.05,0.05,0
set xlabel "Sample"
set ylabel "Duration [".UNITS_LABEL."]"

# Logarithmic scale
# set logscale y 10

set grid

#count and plot
plot Clojure \
     using 0:($1/UNITS_VALUE) \
     with points lc rgb(CLJ_COLOR) \
     title "Clojure", \
     Clojerl \
     using 0:($1/UNITS_VALUE) \
     with points lc rgb(CLJE_COLOR) \
     title "Clojerl"

################################################################################
# Boxplot
################################################################################

OUTPUT="graphs/boxplot-".N.SUFFIX.".png"

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
set yrange [(min/UNITS_VALUE):(max/UNITS_VALUE)]

# Labels
set style fill solid 0.5 border -1
# set style boxplot outliers pointtype 7
set style boxplot nooutliers
# set style boxplot fraction 0.95
set style data boxplot
set boxwidth  0.5
set pointsize 0.5

unset key
set offset graph 0, 0, 10, 10
set border 2
set xtics ("Clojure" 1, "Clojerl" 2) scale 0.0
set xtics nomirror
set ytics nomirror
# set ytics format "%.1s%c"

set ylabel "Duration [".UNITS_LABEL."]"

set grid

plot Clojure using (1):($1/UNITS_VALUE) lc rgb CLJ_COLOR, \
     Clojerl using (2):($1/UNITS_VALUE) lc rgb CLJE_COLOR
