(ns thrift-store.entities.product
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]))

(s/def :product/id uuid?)
(s/def :product/name string?)
(s/def :product/description (s/nilable string?))
(s/def :product/price double?)
(s/def :product/stock integer?)

(s/def ::product
  (s/keys
   :req [:product/id 
         :product/name 
         :product/price 
         :product/stock]
   :opt [:product/description]))

(defn generate-test-product [name description price stock]
  (let [product {:product/id (java.util.UUID/randomUUID)
                  :product/name name
                  :product/description description
                  :product/price price
                  :product/stock stock}]
     (if (s/valid? ::product product)
       product
       (throw (ex-info "Test product does not conform to the specification" {:product product})))))

(def test-product (generate-test-product "TV LG" "TV LG" 1999.00 5))

(with-test
  (defn dec-product-stock
    ([p] (update p :product/stock dec))
    ([p qty] (update p :product/stock - qty)))
  (testing "Testing 'dec-stock'" 
    (let [product test-product]
      (is (= 4 (:product/stock (dec-product-stock product))))
      (is (= 2 (:product/stock (dec-product-stock product 3)))))))

(with-test
  (defn inc-product-stock
    ([p] (update p :product/stock inc))
    ([p qty] (update p :product/stock + qty)))
  (testing "Testing 'inc-stock'"
     (let [product test-product]
       (is (= 6 (:product/stock (inc-product-stock product))))
       (is (= 8 (:product/stock (inc-product-stock product 3)))))))

(with-test
  (defn change-product-price
    [p
     price]
    (assoc p :product/price price))
  (testing "Testing 'change-price'"
    (let [product test-product]
      (is (= 1599.00 (:product/price (change-product-price product 1599.00)))))))

(with-test
  (defn apply-product-discount
    [p
     discount]
    (update p :product/price - (* (/ (get p :product/price) 100) discount)))
  (testing "Testing 'apply-discount'"
    (let [product test-product]
      (is (= 1199.40 (:product/price (apply-product-discount product 40)))))))

(with-test
  (defn change-product-description
    [p
     d]
    (assoc p :product/description d))
  (testing "Testing 'change-description'"
   (let [product test-product]
     (is (= "TV LG de ótima qualidade"
            (:product/description (change-product-description product "TV LG de ótima qualidade")))))))

(with-test
  (defn change-product-name
    [p
     name]
    (assoc p :product/name name))
  (testing "Testing 'change-name'"
   (let [product test-product]
     (is (= "TV LG 42" (:product/name (change-product-name product "TV LG 42")))))))