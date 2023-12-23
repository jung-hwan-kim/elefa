(ns databricks-c
  (:require [cheshire.core :refer :all]
            [babashka.process :refer [sh shell]]))


(defn fs-ls
  "List dbfs folders (dbfs:/)
  Argument
  - profile: Configuration profile (string)
  - dir: directory
  eg. (dbfs-ls \"poc\" \"\")"
  [profile dir]
  (-> (sh "databricks -o" "json" "-p" profile "fs" "ls" (str "dbfs:/" dir))
      :out
      (parse-string true)))

(defn list-clusters
  "List databricks clusters.
  Argument
  - profile: Configuration profile (string)
  - keywords: Select keys
  eg. (list-clusters \"poc\")
  eg. (list-clusters \"poc\" :cluster_id :cluster_name :state)"
  ([profile]
   (-> (shell {:out :string} "databricks" "-o" "json" "-p" profile "clusters"  "list")
       :out
       (parse-string true)))
  ([profile & keywords]
   (map #(select-keys % (vec keywords)) (list-clusters profile))))

(defn start-cluster
  "Start databricks cluster"
  [profile cluster-id]
  (shell "databricks" "-p" profile "clusters" "start" cluster-id))
(defn stop-cluster
  "Start databricks cluster"
  [profile cluster-id]
  (shell "databricks" "-p" profile "clusters" "delete" cluster-id))

(defn current-users
  [profile]
  (-> (sh "databricks" "-o" "json" "-p" profile "current-user" "me")
      :out
      (parse-string true)))

(defn list-tables
  [profile catalog database]
  (-> (sh "databricks" "-o" "json" "-p" profile "tables" "list" catalog database)
      :out
      (parse-string true)))

