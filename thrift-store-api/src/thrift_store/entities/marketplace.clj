(ns thrift-store.entities.marketplace
  (:require [clojure.spec.alpha :as s]))

(s/def ::marketplace
  (s/cat
   :id uuid?
   :name string?))

(defn generate-test-marketplace [name]
  (let [marketplace {:id (java.util.UUID/randomUUID)
                     :name name}]
    (if (s/valid? ::marketplace marketplace)
      marketplace
      (throw (ex-info "Test marketplace does not conform to the specification" {:marketplace marketplace})))))

(def test-marketplace (generate-test-marketplace "Mercado Livre"))