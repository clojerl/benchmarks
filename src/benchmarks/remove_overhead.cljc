(ns benchmarks.remove-overhead
  (:require [clojure.string :as str]
            [benchmarks :as b]
            [benchmarks.core :as core]
            [benchmarks.stats :as stats]
            [benchmarks.utils :as utils]))

(defn filename
  [platform suffix]
  (str "data/" platform "-data-" suffix ".dat"))

(defn quantile-form-file
  [path]
  (-> path utils/parse-file sort vec (stats/quantile 0.025)))

(defn -main
  []
  (doseq [platform ["clj" "clje"]]
    (let [overhead (quantile-form-file (filename platform -1))
          indexes (-> b/experiments keys sort)]
      (doseq [n indexes]
        (println "Remove overhead from" (filename platform n))
        (->> (filename platform n)
             utils/parse-file
             (mapv #(- % overhead))
             (core/write-samples (filename platform
                                           (str n "-no-overhead"))))))))
