(ns thrift-store.entities.sales-channel
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [thrift-store.entities.store :as st]
            [thrift-store.entities.marketplace :as m]))

(s/def ::store #(instance? ::st/Store %))
(s/def ::marketplace #(instance? ::m/Marketplace %))
(s/def ::description string?)

(s/def ::SalesChannel
  (s/keys
   :req [::store ::marketplace]
   :opt [::description]))

(defn generate-test-sales-channel [store marketplace description] 
  (let [channel {::store store
                 ::marketplace marketplace
                 ::description description}]
    (if (s/valid? ::SalesChannel channel)
      channel
      (throw (ex-info "Test sales channel does not conform to the specification" {:channel channel})))))

(def test-sales-channel (generate-test-sales-channel st/test-store m/test-marketplace nil))

(with-test
  (defn change-description
    [c d]
    (assoc c ::description d))
  (testing "Testing 'change-description' for SalesChannel"
    (let [channel test-sales-channel]
      (is (= "Loja de eletrônicos" 
             (::description (change-description channel "Loja de eletrônicos")))))))