(ns thrift-store.entities.product
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]))

(s/def ::product
  (s/cat
   :id uuid?
   :name string?
   :description (s/nilable string?)
   :price double?
   :stock (s/and integer? pos?)))

(defn generate-test-product [name description price stock]
  (let [product {:id (java.util.UUID/randomUUID)
                  :name name
                  :description description
                  :price price
                  :stock stock}]
     (if (s/valid? ::product product)
       product
       (throw (ex-info "Test product does not conform to the specification" {:product product})))))

(def test-product (generate-test-product "TV LG" "TV LG" 1999.00 5))
(def test-product-2 (generate-test-product "TekPix" nil 500.00 3))

(with-test
  (defn dec-product-stock
    ([p] (update p :stock dec))
    ([p qty] (update p :stock - qty)))
  (testing "Testing 'dec-stock'" 
    (let [product test-product]
      (is (= 4 (:stock (dec-product-stock product))))
      (is (= 2 (:stock (dec-product-stock product 3)))))))

(with-test
  (defn inc-product-stock
    ([p] (update p :stock inc))
    ([p qty] (update p :stock + qty)))
  (testing "Testing 'inc-stock'"
     (let [product test-product]
       (is (= 6 (:stock (inc-product-stock product))))
       (is (= 8 (:stock (inc-product-stock product 3)))))))

(with-test
  (defn change-product-price
    [p
     price]
    (assoc p :price price))
  (testing "Testing 'change-price'"
    (let [product test-product]
      (is (= 1599.00 (:price (change-product-price product 1599.00)))))))

(with-test
  (defn apply-product-discount
    [p
     discount]
    (update p :price - (* (/ (get p :price) 100) discount)))
  (testing "Testing 'apply-discount'"
    (let [product test-product]
      (is (= 1199.40 (:price (apply-product-discount product 40)))))))

(with-test
  (defn change-product-description
    [p
     d]
    (assoc p :description d))
  (testing "Testing 'change-description'"
   (let [product test-product]
     (is (= "TV LG de ótima qualidade"
            (:description (change-product-description product "TV LG de ótima qualidade")))))))

(with-test
  (defn change-product-name
    [p
     name]
    (assoc p :name name))
  (testing "Testing 'change-name'"
   (let [product test-product]
     (is (= "TV LG 42" (:name (change-product-name product "TV LG 42")))))))