(ns benchmarks.core
  (:require [clojure.string :as str]))

(def none ::none)

(defmacro timestamp
  []
  #?(:clj `(System/nanoTime)
     :clje `(erlang/monotonic_time :nano_seconds)))

(defmacro time-body
  [body]
  `(let [start# (timestamp)
         ~@(when (not= body 'none)
             `[_# ~body])
         end# (timestamp)]
     (- end# start#)))

(defmacro with-label
  [label & body]
  `(do
     (println ~label)
     ~@body))

(defmacro collect-samples
  [n expr]
  `(with-label (str "Collecting " ~n " samples...")
    (loop [n# ~n
            samples# []]
       (if (pos? n#)
         (recur (dec n#) (conj samples# (time-body ~expr)))
         samples#))))

(defn write-samples
  [samples filename]
  (spit filename
        (str/join "\n" samples)))

(defmacro warmup
  [expr]
  `(with-label "Running warm-up..."
     (collect-samples 100 ~expr)))

(defn show-info
  [samples]
  (println "Min:" (apply min samples))
  (println "Avg:" (float (/ (reduce + samples) (count samples))))
  (println "Max:" (apply max samples)))

(defmacro experiment
  [expr n filename]
  `(let [_# (warmup ~expr)
         samples# (collect-samples ~n ~expr)]
     (show-info samples#)
     (with-label (str "Writing " ~n " samples to " ~filename)
       (write-samples samples# ~filename))))
