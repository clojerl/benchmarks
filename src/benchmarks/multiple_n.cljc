(ns benchmarks.multiple-n
  (:require [benchmarks :as b]
            [benchmarks.stats :as stats]
            [benchmarks.utils :as utils]
            [clojure.string :as str]))

(defn input-file
  [platform i n]
  (str b/*data-dir* "/" platform "-data-" i "-" n ".dat"))

(defn output-file
  [platform i]
  (str b/*data-dir* "/" platform "-data-" i "-multiple-n.dat"))

(defn -main
  []
  (doseq [i [3 6] platform ["clj" "clje"]]
    (let [samples (map #(utils/parse-file (input-file platform i %))
                       (range 100000 1100000 100000))
          content (->> samples
                       (mapv #(vector %1 (stats/mean %2))
                             (range 100000 1100000 100000))
                       (mapv #(str/join " " %))
                       (str/join "\n"))]
      (spit (output-file platform i) content))))
