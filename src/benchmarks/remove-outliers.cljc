(ns benchmarks.remove-outliers
  (:require [benchmarks :as b]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

(defn mean
  [samples]
  (/ (reduce + samples)
     (count samples)))

(defn std-dev
  [mu samples]
  (let [over-n (/ 1 (count samples))]
    (->> samples
         (reduce #(+ %1 (math/pow (- %2 mu) 2)) 0)
         (* over-n)
         math/sqrt)))

(defn non-outlier?
  [mu std-dev x]
  (< x (+ mu (* 3 std-dev))))

(defn -main
  []
  (doseq [i (sort (keys b/experiments)) clj ["clj" "clje"]]
    (let [input (str b/*data-dir* "/" clj "-data-" i ".dat")
          output (str b/*data-dir* "/" clj "-data-" i "-filtered.dat")
          samples (-> (slurp input)
                      (str/split "\n"))
          samples (mapv read-string samples)
          mu (mean samples)
          std-dev (std-dev mu samples)
          samples' (filter #(non-outlier? mu std-dev %) samples)]
      (println clj i "=>"
               :mu mu
               :std-dev std-dev
               :count (count samples)
               :count' (count samples'))
      (spit output (str/join "\n" samples')))))
