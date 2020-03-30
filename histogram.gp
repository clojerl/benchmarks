#!/usr/local/bin/gnuplot -c

N=ARG1

Clojure="data/clj-data-".N.".dat"
Clojerl="data/clje-data-".N.".dat"

OUTPUT="graphs/histogram-".N.".png"

CLJ_COLOR="blue"
CLJE_COLOR="green"

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
set offset graph 0.05,0.05,0.05,0.0
set xtics min,(max-min)/5,max
set boxwidth width*0.9
set style fill transparent solid 0.5 #fillstyle
set tics out nomirror
set xlabel "Duration [ns]"
set ylabel "Number of Samples"

#count and plot
plot Clojure u (hist($1,width)):(1) smooth freq w boxes lc rgb(CLJ_COLOR) title "Clojure", \
     Clojerl u (hist($1,width)):(1) smooth freq w boxes lc rgb(CLJE_COLOR) title "Clojerl"
