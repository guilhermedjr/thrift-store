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

;;(s/defrecord
 ;;ProductStore [product :- Product
               ;;store :- Store
               ;;name :- s/Str
               ;;description :- s/Str
               ;;price :- Double
               ;;stock :- s/Int])

;; todo: unit tests
;;(with-test "Stock update"
  ;;(s/defn dec-stock
    ;;([p :- Product] (update p :stock dec))
    ;;([p :- Product qty :- s/Int] (update p :stock - qty)))
  ;;(testing "by simple increment"
    ;;(is (= 9 (dec-stock ))))
  ;;(testing "by specified increment"))(

(with-test (s/defn dec-stock
             ([p :- Product] (update p :stock dec))
             ([p :- Product qty :- s/Int] (update p :stock - qty))))

(s/defn inc-stock 
  ([p :- Product] (update p :stock inc)) 
  ([p :- Product qty :- s/Int] (update p :stock + qty)))

(s/defn change-price
  [p :- Product
   price :- Double]
    (update p :price price))

(s/defn apply-discount
  [p :- Product
   discount :- Float]
    (update p :price - (* (/ (get p :price) 100) discount)))

(s/defn change-description
  [p :- Product
   d :- s/Str]
    (update p :description d))

(s/defn change-name
  [p :- Product
   name :- s/Str]
    (update p :name name))

;;(s/defn bind-product-to-store
  ;;([product stores] ())
  ;;([product store] ()))

(s/defn ^:private product
  ([name price stock] (product name nil price stock))
  ([name description price stock] (->Product (str (java.util.UUID/randomUUID)) name description price stock)))