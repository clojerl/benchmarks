(ns benchmarks.stats)

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

(defn quantile
  [samples n]
  (get samples
       (int (* n (count samples)))))
