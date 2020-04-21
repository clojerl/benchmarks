(ns benchmarks.report
  (:require [benchmarks :as b]
            [benchmarks.stats :as stats]
            [benchmarks.utils :as utils]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

(def graphs
  [:histogram
   :points
   :boxplot])

(defn image-link
  [path]
  (str "![](" path ")"))

(defn image-path
  [type i suffix]
  (str b/*graphs-dir* "/" (name type) "-" i suffix ".png"))

(defn data-path
  [type i suffix]
  (str b/*data-dir* "/" (name type) "-data-" i suffix ".dat"))

(defn metrics
  [path]
  (let [samples (utils/parse-file path)
        mean (stats/mean samples)
        std-dev (stats/std-dev mean samples)
        median (stats/quantile samples 0.5)]
    (str "Mean = " mean "<br/>"
         "StdDev = " std-dev "<br/>"
         "Median = " median "<br/>")))

(defn report
  [suffix]
  (let [items (for [i (sort (keys b/experiments))]
                (let [init {:n i
                            :experiment (get-in b/experiments [i :name])
                            :metrics-clj (metrics (data-path "clj" i suffix))
                            :metrics-clje (metrics (data-path "clje" i suffix))}]
                  (reduce (fn [acc graph]
                            (assoc acc graph
                                   (-> (image-path graph i suffix)
                                       image-link)))
                          init
                          graphs)))
        items (vec items)
        columns (into [:n :experiment :metrics-clj :metrics-clje] graphs)
        output (with-out-str (pp/print-table columns items))
        output (str/replace output "-+-" "-|-")]
    (spit (str "result" suffix ".md") output)))

(defn -main
  []
  (report "")
  (report "-filtered")
  (report "-no-overhead")
  (report "-no-overhead-filtered"))
