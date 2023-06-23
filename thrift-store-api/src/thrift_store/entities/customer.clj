(ns thrift-store.entities.customer
  (:require [clojure.spec.alpha :as s]
            [thrift-store.entities.address :refer :all]))

(s/def ::customer
  (s/cat
   :id uuid?
   :name string?
   :document string?
   :email (s/nilable string?)
   :phone-number (s/nilable string?)
   :address (s/nilable #(s/map-of ::address %))))

(defn generate-test-customer [name document email phone-number address]
  (let [customer {:id (java.util.UUID/randomUUID)
                  :name name
                  :document document
                  :email email
                  :phone-number phone-number
                  :address address}]
     (if (s/valid? ::customer customer)
       customer
       (throw (ex-info "Test customer does not conform to the specification" 
                       {:customer customer} (s/explain ::customer customer))))))

(def test-customer (generate-test-customer "Arcles" "86007877000" nil nil test-address))