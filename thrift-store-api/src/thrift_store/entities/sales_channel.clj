(ns thrift-store.entities.sales-channel
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [thrift-store.entities.store :refer :all]
            [thrift-store.entities.marketplace :refer :all]))

(s/def ::sales-channel
  (s/cat
   :store #(s/map-of ::store %)
   :marketplace #(s/map-of ::marketplace %)
   :description (s/nilable string?)))

(defn generate-test-sales-channel [store marketplace description] 
  (let [channel {:store store
                 :marketplace marketplace
                 :description description}]
    (if (s/valid? ::sales-channel channel)
      channel
      (throw (ex-info "Test sales channel does not conform to the specification" {:channel channel})))))

(def test-sales-channel (generate-test-sales-channel test-store test-marketplace nil))

(with-test
  (defn change-sales-channel-description
    [c d]
    (assoc c :description d))
  (testing "Testing 'change-description' for SalesChannel"
    (let [channel test-sales-channel]
      (is (= "Loja de eletrônicos" 
             (:description (change-sales-channel-description channel "Loja de eletrônicos")))))))