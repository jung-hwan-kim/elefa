(ns util)

(ns main
  (:require [babashka.process :refer [sh shell process exec]]
            [clojure.tools.reader.edn :as edn]
            [databricks :as db]
            [script :as s]))

(def METER_INCH_RATIO 39.37)

(defn cm-to-inch [cm]
  (* (/ METER_INCH_RATIO 100) cm))

(defn inch-to-cm [inch]
  (* (/ inch METER_INCH_RATIO) 100))
