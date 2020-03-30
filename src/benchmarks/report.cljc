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

(defn -main
  []
  (let [items (for [i (sort (keys b/experiments))]
                (reduce (fn [acc graph]
                          (assoc acc graph
                                 (-> b/*graphs-dir*
                                     (str "/" (name graph) "-" i ".png")
                                     image-link)))
                        {:n i
                         :experiment (get-in b/experiments [i :name])}
                        graphs))
        output (with-out-str
                 (pp/print-table (into [:n :experiment] graphs)
                                 items))
        output (str/replace output "-+-" "-|-")]
    (spit "result.md" output)))
