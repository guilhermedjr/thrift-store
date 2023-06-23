(ns thrift-store.entities.sales-channel-product
  (:require [clojure.spec.alpha :as s] 
            [clojure.test :refer :all]
            [thrift-store.entities.sales-channel :refer :all]
            [thrift-store.entities.product :refer :all]))

(s/def ::sales-channel-product
  (s/cat
   :channel #(s/map-of ::sales-channel %)
   :product #(s/map-of ::product %)
   :name (s/nilable string?)
   :description (s/nilable string?)
   :price (s/nilable number?)))

(defn generate-test-sc-product [channel product name description price]
  (let [product {:channel channel
                 :product product
                 :name name
                 :description description
                 :price price}]
    (if (s/valid? ::sales-channel-product product)
      product
      (throw (ex-info "Test sales channel product does not conform to the specification" {:product product})))))

(def test-sales-channel-product (generate-test-sc-product test-sales-channel test-product nil nil nil))
(def test-sales-channel-product-2 (generate-test-sc-product test-sales-channel test-product-2 nil nil nil))

(with-test
  (defn change-sc-product-price
    [p
     price]
    (assoc p :price price))
  (testing "Testing 'change-price' for product in a sales channel"
    (let [product test-sales-channel-product]
      (is (= 1599.00 (:price (change-sc-product-price product 1599.00)))))))