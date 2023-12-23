(ns databricks
  (:require [cheshire.core :refer :all]
            [babashka.curl :as curl]
            [babashka.process :refer [sh shell]]))




(defn create-headers [profile]
  {:headers {"Authorization" (str "Bearer " (:token profile))}})

(defn list-clusters [profile]
  (let [resp (curl/get (str (:host profile) "/api/2.0/clusters/list")
                       (create-headers profile)
                       )]
    (parse-string (:body resp) true)))

(defn fs-ls [profile path]
  (let [resp (curl/get (str (:host (:poc profile)) "/api/2.0/dbfs/list?path=" path)
                       (create-headers profile)
                       )]
    (parse-string (:body resp) true)))


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

;(list-groups (:scim profiles))

(defn list-metastores [account-profile]
  (let [resp (curl/get (str (:host account-profile) "/metastores")
                       (create-headers account-profile)
                       )]
    resp))
;    (parse-string (:body resp) true)))