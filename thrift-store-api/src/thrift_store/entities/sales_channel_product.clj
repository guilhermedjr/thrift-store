(ns thrift-store.entities.sales-channel-product
  (:require [clojure.spec.alpha :as s] 
            [clojure.test :refer :all]
            [thrift-store.entities.sales-channel :as sc]
            [thrift-store.entities.product :as p]))

(s/def ::channel #(instance? ::sc/SalesChannel %))
(s/def ::product #(instance? ::p/Product %))
(s/def ::name string?)
(s/def ::description string?)
(s/def ::price number?)

(s/def ::SalesChannelProduct
  (s/keys
   :req [::channel ::product]
   :opt [::name ::description ::price]))

(defn generate-test-product [channel product name description price]
  (let [product {::channel channel
                 ::product product
                 ::name name
                 ::description description
                 ::price price}]
    (if (s/valid? ::SalesChannelProduct product)
      product
      (throw (ex-info "Test sales channel product does not conform to the specification" {:product product})))))

(def test-sales-channel-product (generate-test-product sc/test-sales-channel p/test-product nil nil nil))

(with-test
  (defn change-price
    [p
     price]
    (assoc p :price price))
  (testing "Testing 'change-price' for product in a sales channel"
    (let [product test-sales-channel-product]
      (is (= 1599.00 (:price (change-price product 1599.00)))))))