# Clojerl & Clojure Benchmarks

Benchmarks can be run in Clojerl and Clojure alike:

```
# Running on Clojure
lein run -m benchmarks -- 1
# Running on Clojerl
rebar3 clojerl run -m benchmarks -- 1
```

## Measuring time on the BEAM

- Issue on Mac OS X reporting nanosecond accuracy
  - https://github.com/bencheeorg/benchee/issues/313
  - Bug report in https://bugs.erlang.org/browse/ERL-1067. "Fixed" in
    Erlang/OTP 22.2, but the problem is that Mac OS X system call provides
    only microsecond resolution (even though it reports nanosecond
    precision).
