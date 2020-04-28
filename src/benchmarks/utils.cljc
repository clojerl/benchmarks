(ns benchmarks.utils
  (:require [clojure.string :as str]))

(defn parse-file
  [path]
  (->> (str/split (slurp path) "\n")
       (mapv read-string)))
