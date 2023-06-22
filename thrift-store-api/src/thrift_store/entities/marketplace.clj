(ns thrift-store.entities.marketplace
  (:require [clojure.spec.alpha :as s]))

(s/def :marketplace/id uuid?)
(s/def :marketplace/name string?)

(s/def ::marketplace
  (s/keys
   :req [:marketplace/id 
         :marketplace/name]))

(defn generate-test-marketplace [name]
  (let [marketplace {:marketplace/id (java.util.UUID/randomUUID)
                     :marketplace/name name}]
    (if (s/valid? ::marketplace marketplace)
      marketplace
      (throw (ex-info "Test marketplace does not conform to the specification" {:marketplace marketplace})))))

(def test-marketplace (generate-test-marketplace "Mercado Livre"))