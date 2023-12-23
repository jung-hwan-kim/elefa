(ns battery-mon
  (:require [babashka.process :refer [sh shell process exec]]))


(defn run
  "Main run ..
  This is about ru"
  {:org.babashka/cli {:exec-args
                      {:t 3}
                      :coerce {:t :long}}}
  [m]
  (println m))
