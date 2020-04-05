.PHONY: graphs report

graphs:
	./generate-graph.sh

report:
	rebar3 clojerl run -m benchmarks.report

remove-outliers:
	rebar3 clojerl run -m benchmarks.remove-outliers
