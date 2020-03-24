(ns benchmarks.core)

(defmacro timestamp
  []
  #?(:clj `(System/nanoTime)
     :clje `(erlang/monotonic_time :nano_seconds)))

(defmacro time-body
  [body]
  `(let [start# (timestamp)
         _# ~body
         end# (timestamp)]
     (- end# start#)))

(defmacro collect-samples
  [n expr]
  `(loop [n# ~n
          samples# []]
     (if (pos? n#)
       (recur (dec n#) (conj samples# (time-body ~expr)))
       samples#)))

(defn write-samples
  [samples filename]
  (spit filename "")
  (doseq [x samples]
    (spit filename (str x "\n") :append true))
  {:min (apply min samples)
   :avg (/ (reduce + samples) (count samples))
   :max (apply max samples)})

(defmacro warmup
  [expr]
  `(do
     (println "Running warm-up...")
     (collect-samples 100 ~expr)))

(defmacro experiment
  [expr n filename]
  `(let [_# (warmup ~expr)
         samples# (collect-samples ~n ~expr)]
     (write-samples samples# ~filename)))
