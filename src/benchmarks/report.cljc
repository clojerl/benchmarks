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
                 :clj (-> (b/output-file i b/*graphs-dir* "clj")
                          (str ".png")
                          image-link)
                 :clje (-> (b/output-file i b/*graphs-dir* "clje")
                           (str ".png")
                           image-link)})
        output (with-out-str
                 (pp/print-table [:n :experiment :clj :clje] items))
        output (str/replace output "-+-" "-|-")]
    (spit "result.md" output)))
