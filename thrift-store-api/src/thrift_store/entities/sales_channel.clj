(ns thrift-store.entities.sales-channel
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [thrift-store.entities.store :refer :all]
            [thrift-store.entities.marketplace :refer :all]))

(s/def :sales-channel/store #(s/map-of :store %))
(s/def :sales-channel/marketplace #(s/map-of :marketplace %))
(s/def :sales-channel/description (s/nilable string?))

(s/def ::sales-channel
  (s/keys
   :req [:sales-channel/store 
         :sales-channel/marketplace]
   :opt [:sales-channel/description]))

(defn generate-test-sales-channel [store marketplace description] 
  (let [channel {:sales-channel/store store
                 :sales-channel/marketplace marketplace
                 :sales-channel/description description}]
    (if (s/valid? ::sales-channel channel)
      channel
      (throw (ex-info "Test sales channel does not conform to the specification" {:channel channel})))))

(def test-sales-channel (generate-test-sales-channel test-store test-marketplace nil))

(with-test
  (defn change-sales-channel-description
    [c d]
    (assoc c :sales-channel/description d))
  (testing "Testing 'change-description' for SalesChannel"
    (let [channel test-sales-channel]
      (is (= "Loja de eletrônicos" 
             (:sales-channel/description (change-sales-channel-description channel "Loja de eletrônicos")))))))