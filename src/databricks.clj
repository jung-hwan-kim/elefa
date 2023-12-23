(ns databricks
  (:require [cheshire.core :refer :all]
            [babashka.curl :as curl]
            [clojure.tools.reader.edn :as edn]))


(defn get-profiles[]
  (-> (System/getProperty "user.home")
      (str "/.profiles.edn")
      slurp
      edn/read-string
      ))

(defn get-profile[key]
  (get (get-profiles) key))

(defn create-headers [profile]
  {:headers {"Authorization" (str "Bearer " (:token profile))}})

(defn list-clusters [profile]
  (let [resp (curl/get (str (:host profile) "/api/2.0/clusters/list")
                       (create-headers profile)
                       )]
    (parse-string (:body resp) true)))

(defn fs-ls [profile path]
  (let [resp (curl/get (str (:host profile) "/api/2.0/dbfs/list?path=" path)
                       (create-headers profile)
                       )]
    (parse-string (:body resp) true)))
;(fs-ls (get-profile :poc) "/")

(defn list-users [scim-profile]
  (let [resp (curl/get (str (:host scim-profile) "/Users")
                       (create-headers scim-profile)
                       )]
    (parse-string (:body resp) true)))

(defn list-groups [scim-profile]
  (let [resp (curl/get (str (:host scim-profile) "/Groups")
                       (create-headers scim-profile)
                       )]
    (parse-string (:body resp) true)))

;(-> (get-profile :scim)
;    list-groups)

(defn list-metastores [account-profile]
  (let [resp (curl/get (str (:host account-profile) "/metastores")
                       (create-headers account-profile)
                       )]
    resp))
;    (parse-string (:body resp) true)))