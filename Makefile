.PHONY: graphs report

graphs:
	scripts/generate-graph.sh

run-all:
	scripts/run-all.sh

run-3-and-6:
	scripts/run-3-and-6-multiple-n.sh

report:
	rebar3 clojerl run -m benchmarks.report

remove-overhead:
	rebar3 clojerl run -m benchmarks.remove-overhead

remove-outliers:
	rebar3 clojerl run -m benchmarks.remove-outliers

remove-outliers-no-overhead:
	rebar3 clojerl run -m benchmarks.remove-outliers -- -no-overhead
