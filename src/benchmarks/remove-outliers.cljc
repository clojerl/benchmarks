(ns benchmarks.remove-outliers
  (:require [benchmarks :as b]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [benchmarks.stats :as stats]
            [benchmarks.utils :as utils]))

(defn not-outlier?
  [mu std-dev x]
  (< x (+ mu (* 3 std-dev))))

(defn -main
  [& [suffix]]
  (doseq [i (sort (keys b/experiments)) clj ["clj" "clje"]]
    (let [input (str b/*data-dir* "/" clj "-data-" i suffix ".dat")
          output (str b/*data-dir* "/" clj "-data-" i suffix "-filtered.dat")
          samples (utils/parse-file input)
          mu (stats/mean samples)
          std-dev (stats/std-dev mu samples)
          samples' (filter #(not-outlier? mu std-dev %) samples)]
      (println clj i "=>"
               :mu mu
               :std-dev std-dev
               :count (count samples)
               :count' (count samples'))
      (spit output (str/join "\n" samples')))))
