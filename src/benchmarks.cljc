(ns benchmarks
  #_(:use criterium.core)
  (:use benchmarks.core))

(def ^:dynamic *samples* 10)
(def ^:dynamic *target-execution-time-seconds* 1)

;; (defmacro run-experiment
;;   [expr]
;;   `(with-progress-reporting
;;      (bench ~expr
;;             :verbose
;;             :samples *samples*
;;             :target-execution-time (* *target-execution-time-seconds*
;;                                       s-to-ns))))

(defmacro run-experiment
  [expr]
  `(experiment ~expr 10000 "/tmp/data.dat"))

;; -----------------------------------------------------------------------------
;; Experiment 1
;; -----------------------------------------------------------------------------

(defn constant-expression
  []
  (run-experiment 1))

;; -----------------------------------------------------------------------------
;; Experiment 1
;; -----------------------------------------------------------------------------

(defn simple-function-call
  []
  (run-experiment (identity 1)))

;; -----------------------------------------------------------------------------
;; Experiment 2
;; -----------------------------------------------------------------------------

(defn list-creation
  []
  (run-experiment (list 1 2 3 4 5)))

;; -----------------------------------------------------------------------------
;; Experiment 3
;; -----------------------------------------------------------------------------

(defn dynamic-function-application
  []
  (let [x (into [] (range 1000000))]
    (run-experiment (apply + x))))

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
    (run-experiment (foo x))))

;; -----------------------------------------------------------------------------
;; Experiment 5
;; -----------------------------------------------------------------------------

(defn read-expr-from-string
  []
  (run-experiment (read-string "{:foo [1 2 3]}")))

;; -----------------------------------------------------------------------------
;; Experiment 6
;; -----------------------------------------------------------------------------

(defn last-item-in-range
  []
  (let [r (range 1000000)]
    (run-experiment (last r))))

;; -----------------------------------------------------------------------------
;; Experiment 7
;; -----------------------------------------------------------------------------

(defn tight-loop
  []
  (run-experiment (loop [n 100000] (when (pos? n) (recur (dec n))))))

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
