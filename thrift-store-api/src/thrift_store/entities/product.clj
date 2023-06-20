(ns thrift-store.entities.product
  (:require [schema.core :as s]
            [clojure.test :refer :all]
            [thrift-store.entities.store :refer :all]))

(s/defrecord
 Product [id :- s/Uuid
          name :- s/Str
          description :- s/Str
          price :- Double
          stock :- s/Int])

(s/defn ^:private product
  ([name price stock] (product name nil price stock))
  ([name description price stock] (->Product (str (java.util.UUID/randomUUID)) name description price stock)))

(def test-product 
  (product "TV LG" "TV LG" 1999.00 5))

(with-test
  (s/defn dec-stock
    ([p :- Product] (update p :stock dec))
    ([p :- Product qty :- s/Int] (update p :stock - qty)))
  (testing "Testing 'dec-stock'" 
    (let [product test-product]
      (is (= 4 (:stock (dec-stock product))))
      (is (= 2 (:stock (dec-stock product 3)))))))

(with-test
  (s/defn inc-stock
    ([p :- Product] (update p :stock inc))
    ([p :- Product qty :- s/Int] (update p :stock + qty)))
  (testing "Testing 'inc-stock'"
     (let [product test-product]
       (is (= 6 (:stock (inc-stock product))))
       (is (= 8 (:stock (inc-stock product 3)))))))

(with-test
  (s/defn change-price
    [p :- Product
     price :- Double]
    (assoc p :price price))
  (testing "Testing 'change-price'"
    (let [product test-product]
      (is (= 1599.00 (:price (change-price product 1599.00)))))))

(with-test
  (s/defn apply-discount
    [p :- Product
     discount :- Float]
    (update p :price - (* (/ (get p :price) 100) discount)))
  (testing "Testing 'apply-discount'"
    (let [product test-product]
      (is (= 1199.40 (:price (apply-discount product 40)))))))

(with-test
  (s/defn change-description
    [p :- Product
     d :- s/Str]
    (assoc p :description d))
  (testing "Testing 'change-description'"
   (let [product test-product]
     (is (= "TV LG de ótima qualidade"
            (:description (change-description product "TV LG de ótima qualidade")))))))

(with-test
  (s/defn change-name
    [p :- Product
     name :- s/Str]
    (assoc p :name name))
  (testing "Testing 'change-name'"
   (let [product test-product]
     (is (= "TV LG 42" (:name (change-name product "TV LG 42")))))))