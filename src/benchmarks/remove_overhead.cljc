(ns benchmarks.remove-overhead
  (:require [clojure.string :as str]
            [benchmarks :as b]
            [benchmarks.core :as core]))

(defn quantile
  [samples n]
  (get samples
       (int (* n (count samples)))))

(defn filename
  [platform suffix]
  (str "data/" platform "-data-" suffix ".dat"))

(defn parse-file
  [path]
  (->> (str/split (slurp path) "\n")
       (map read-string)))

(defn quantile-form-file
  [path]
  (-> path parse-file sort vec (quantile 0.025)))

(defn -main
  []
  (doseq [platform ["clj" "clje"]]
    (let [overhead (quantile-form-file (filename platform -1))
          indexes (-> b/experiments keys sort)]
      (doseq [n indexes]
        (println "Remove overhead from" (filename platform n))
        (->> (filename platform n)
             parse-file
             (mapv #(- % overhead))
             (core/write-samples (filename platform
                                           (str n "-no-overhead"))))))))
