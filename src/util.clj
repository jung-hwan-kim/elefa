(ns util
  (:require [babashka.process :refer [sh shell process exec]]
            [clojure.tools.reader.edn :as edn]))

(def METER_INCH_RATIO 39.37)

(defn cm-to-inch [cm]
  (* (/ METER_INCH_RATIO 100) cm))

(defn inch-to-cm [inch]
  (* (/ inch METER_INCH_RATIO) 100))
