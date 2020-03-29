(ns benchmarks.report
  (:require [benchmarks :as b]
            [clojure.pprint :as pp]
            [clojure.string :as str]))

(defn image-link
  [path]
  (str "![](" path ")"))

(defn -main
  []
  (let [items (for [i (sort (keys b/experiments))]
                {:n i
                 :experiment (get-in b/experiments [i :name])
                 :histogram (-> (str b/*graphs-dir* "/histogram-" i ".png")
                                image-link)
                 :linespoints (-> (str b/*graphs-dir* "/linespoints-" i ".png")
                                  image-link)})
        output (with-out-str
                 (pp/print-table [:n :experiment :histogram :linespoints] items))
        output (str/replace output "-+-" "-|-")]
    (spit "result.md" output)))
