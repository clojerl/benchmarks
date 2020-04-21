.PHONY: graphs report

graphs:
	./generate-graph.sh

report:
	rebar3 clojerl run -m benchmarks.report

remove-overhead:
	rebar3 clojerl run -m benchmarks.remove-overhead

remove-outliers:
	rebar3 clojerl run -m benchmarks.remove-outliers

remove-outliers-no-overhead:
	rebar3 clojerl run -m benchmarks.remove-outliers -- -no-overhead
