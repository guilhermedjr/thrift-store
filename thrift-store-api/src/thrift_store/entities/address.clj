(ns thrift-store.entities.address
  (:require [clojure.spec.alpha :as s]))

(s/def ::address
  (s/cat
   :address-line string? 
   :address-second-line (s/nilable string?)
   :complement (s/nilable string?)
   :zip string?
   :city string?
   :state string?
   :country string?))

(def test-address {:address-line ""
                   :address-second-line ""
                   :complement ""
                   :zip ""
                   :city ""
                   :state ""
                   :country ""})