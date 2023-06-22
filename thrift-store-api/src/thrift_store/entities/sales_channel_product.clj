(ns thrift-store.entities.sales-channel-product
  (:require [clojure.spec.alpha :as s] 
            [clojure.test :refer :all]
            [thrift-store.entities.sales-channel :refer :all]
            [thrift-store.entities.product :refer :all]))

(s/def :sales-channel-product/channel #(s/map-of :sales-channel %))
(s/def :sales-channel-product/product #(s/map-of :product %))
(s/def :sales-channel-product/name (s/nilable string?))
(s/def :sales-channel-product/description (s/nilable string?))
(s/def :sales-channel-product/price (s/nilable number?))

(s/def ::sales-channel-product
  (s/keys
   :req [:sales-channel-product/channel 
         :sales-channel-product/product]
   :opt [:sales-channel-product/name 
         :sales-channel-product/description 
         :sales-channel-product/price]))

(defn generate-test-sc-product [channel product name description price]
  (let [product {:sales-channel-product/channel channel
                 :sales-channel-product/product product
                 :sales-channel-product/name name
                 :sales-channel-product/description description
                 :sales-channel-product/price price}]
    (if (s/valid? ::sales-channel-product product)
      product
      (throw (ex-info "Test sales channel product does not conform to the specification" {:product product})))))

(def test-sales-channel-product (generate-test-sc-product test-sales-channel test-product nil nil nil))

(with-test
  (defn change-sc-product-price
    [p
     price]
    (assoc p :sales-channel-product/price price))
  (testing "Testing 'change-price' for product in a sales channel"
    (let [product test-sales-channel-product]
      (is (= 1599.00 (:sales-channel-product/price (change-sc-product-price product 1599.00)))))))