(ns benchmarks
  #_(:use criterium.core)
  (:use benchmarks.core)
  (:require [clojure.string :as str]))

(def ^:dynamic *samples* 10)
(def ^:dynamic *target-execution-time-seconds* 1)

(def ^:dynamic *data-dir* "data")
(def ^:dynamic *graphs-dir* "graphs")

#?(:clj (def ^:dynamic *file-prefix* "clj")
   :clje (def ^:dynamic *file-prefix* "clje"))

;; (defmacro run-experiment
;;   [expr]
;;   `(with-progress-reporting
;;      (bench ~expr
;;             :verbose
;;             :samples *samples*
;;             :target-execution-time (* *target-execution-time-seconds*
;;                                       s-to-ns))))

(defn output-file
  [num dir prefix]
  (let [filename (str prefix "-data-" num ".dat")]
    (str/join "/" [dir filename])))

(defmacro run-experiment
  [num expr]
  `(experiment ~expr
               10000
               (output-file ~num *data-dir* *file-prefix*)))

;; -----------------------------------------------------------------------------
;; Experiment 0
;; -----------------------------------------------------------------------------

(defn constant-expression
  []
  (run-experiment 0 1))

;; -----------------------------------------------------------------------------
;; Experiment 1
;; -----------------------------------------------------------------------------

(defn simple-function-call
  []
  (run-experiment 1 (identity 1)))

;; -----------------------------------------------------------------------------
;; Experiment 2
;; -----------------------------------------------------------------------------

(defn list-creation
  []
  (run-experiment 2 (list 1 2 3 4 5)))

;; -----------------------------------------------------------------------------
;; Experiment 3
;; -----------------------------------------------------------------------------

(defn dynamic-function-application
  []
  (let [x (into [] (range 1000000))]
    (run-experiment 3 (apply + x))))

;; -----------------------------------------------------------------------------
;; Experiment 4
;; -----------------------------------------------------------------------------

(defprotocol Foo
  (foo [_]))

(deftype Bar []
  Foo
  (foo [_] 42))

(defn protocol-dispatch
  []
  (let [x (Bar.)]
    (run-experiment 4 (foo x))))

;; -----------------------------------------------------------------------------
;; Experiment 5
;; -----------------------------------------------------------------------------

(defn read-expr-from-string
  []
  (run-experiment 5 (read-string "{:foo [1 2 3]}")))

;; -----------------------------------------------------------------------------
;; Experiment 6
;; -----------------------------------------------------------------------------

(defn last-item-in-range
  []
  (let [r (range 1000000)]
    (run-experiment 6 (last r))))

;; -----------------------------------------------------------------------------
;; Experiment 7
;; -----------------------------------------------------------------------------

(defn tight-loop
  []
  (run-experiment 7 (loop [n 100000] (when (pos? n) (recur (dec n))))))

;; -----------------------------------------------------------------------------
;; Main entry point
;; -----------------------------------------------------------------------------

(def experiments
  {0 {:name "Constant expression"
      :f constant-expression}
   1 {:name "Simple function call"
      :f simple-function-call}
   2 {:name "List creation"
      :f list-creation}
   3 {:name "Dynamic function application"
      :f dynamic-function-application}
   4 {:name "Protocol dispatch"
      :f protocol-dispatch}
   5 {:name "Read expression from string"
      :f read-expr-from-string}
   6 {:name "Last item in range"
      :f last-item-in-range}
   7 {:name "Tight loop"
      :f tight-loop}})

(defn -main
  [& args]
  #?(:clje (application/ensure_all_started :criterium))
  (let [num (-> args first read-string)
        {:keys [name f]} (experiments num)]
    (println "Running experiment:" name)
    (f)))
