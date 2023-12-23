(ns battery-mon
  (:require [babashka.process :refer [sh shell process exec]]
            [clojure.tools.reader.edn :as edn]))

(defn say[voice message]
  (shell "say" "-v" voice message))

(defn play-audio [file-path]
  (sh "afplay" file-path))


(defn play-macos-sound[name]
  (play-audio (str "/System/Library/Sounds/" name ".aiff")))

(defn display-notification [title message]
  (let [script (str "display notification \"" message "\" with title \"" title "\"")]
    (sh "osascript" "-e" script)))

(defn get-battery-percentage []
  (let [output (:out (sh "pmset" "-g" "batt"))
        regex-pattern #"(\d+)%"
        match (re-find regex-pattern output)]
    ;(println "checking battery..")
    (if match
      (Integer/parseInt (second match))
      nil)))

(defn current-time []
  (let [today (java.time.LocalDateTime/now)
        formatter (java.time.format.DateTimeFormatter/ofPattern "yyyyMMdd HH:mm")]
    (.format today formatter)))


(defn run
  "Main run ..
  This is about ru"
  {:org.babashka/cli {:exec-args
                      {:t 3}
                      :coerce {:t :long}}}
  [m]
  (println m)
  (let [config (edn/read-string (slurp "resources/config.edn"))]
    (loop[bat (get-battery-percentage)
          num 1]
      (println (str "[" num "] " (current-time) " Battery:" bat "%"))
      (if (>= bat 80)
        (let [c (nth config (rem num (count config)))
              msg (format (:msg c) bat)
              voice (:voice c)
              title (:title c)]
          (println msg)
          (display-notification title msg)
          (shell "say -v" voice msg)))
      (Thread/sleep (* (:t m) 6 1000))
      (recur (get-battery-percentage) (inc num)))))
