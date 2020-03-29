graphs:
	./generate-graph.sh

report:
	rebar3 clojerl run -m benchmarks.report
