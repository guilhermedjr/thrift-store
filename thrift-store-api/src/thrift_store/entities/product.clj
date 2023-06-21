(ns thrift-store.entities.product
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]))

(s/def ::id uuid?)
(s/def ::name string?)
(s/def ::description string?)
(s/def ::price double?)
(s/def ::stock integer?)

(s/def ::Product
  (s/keys
   :req [::id ::name ::price ::stock]
   :opt [::description]))

(defn generate-test-product [name description price stock]
  (let [product {::id (java.util.UUID/randomUUID)
                  ::name name
                  ::description description
                  ::price price
                  ::stock stock}]
     (if (s/valid? ::Product product)
       product
       (throw (ex-info "Test product does not conform to the specification" {:product product})))))

(def test-product (generate-test-product "TV LG" "TV LG" 1999.00 5))

(with-test
  (defn dec-stock
    ([p] (update p ::stock dec))
    ([p qty] (update p ::stock - qty)))
  (testing "Testing 'dec-stock'" 
    (let [product test-product]
      (is (= 4 (::stock (dec-stock product))))
      (is (= 2 (::stock (dec-stock product 3)))))))

(with-test
  (defn inc-stock
    ([p] (update p ::stock inc))
    ([p qty] (update p ::stock + qty)))
  (testing "Testing 'inc-stock'"
     (let [product test-product]
       (is (= 6 (::stock (inc-stock product))))
       (is (= 8 (::stock (inc-stock product 3)))))))

(with-test
  (defn change-price
    [p
     price]
    (assoc p ::price price))
  (testing "Testing 'change-price'"
    (let [product test-product]
      (is (= 1599.00 (::price (change-price product 1599.00)))))))

(with-test
  (defn apply-discount
    [p
     discount]
    (update p ::price - (* (/ (get p ::price) 100) discount)))
  (testing "Testing 'apply-discount'"
    (let [product test-product]
      (is (= 1199.40 (::price (apply-discount product 40)))))))

(with-test
  (defn change-description
    [p
     d]
    (assoc p ::description d))
  (testing "Testing 'change-description'"
   (let [product test-product]
     (is (= "TV LG de ótima qualidade"
            (::description (change-description product "TV LG de ótima qualidade")))))))

(with-test
  (defn change-name
    [p
     name]
    (assoc p ::name name))
  (testing "Testing 'change-name'"
   (let [product test-product]
     (is (= "TV LG 42" (::name (change-name product "TV LG 42")))))))