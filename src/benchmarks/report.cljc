(ns benchmarks.report
  (:require [benchmarks :as b]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

(def graphs
  [:histogram
   :linespoints
   :boxplot])

(defn image-link
  [path]
  (str "![](" path ")"))

(defn image-path
  [type i suffix]
  (str b/*graphs-dir* "/" (name type) "-" i suffix ".png"))

(defn report
  [suffix]
  (let [items (for [i (sort (keys b/experiments))]
                (reduce (fn [acc graph]
                          (assoc acc graph
                                 (-> (image-path graph i suffix)
                                     image-link)))
                        {:n i
                         :experiment (get-in b/experiments [i :name])}
                        graphs))
        output (with-out-str
                 (pp/print-table (into [:n :experiment] graphs)
                                 items))
        output (str/replace output "-+-" "-|-")]
    (spit (str "result" suffix ".md") output)))

(defn -main
  []
  (report "")
  (report "-filtered")
  (report "-no-overhead"))
