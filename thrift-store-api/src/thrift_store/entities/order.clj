(ns thrift-store.entities.order
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [thrift-store.entities.sales-channel :refer :all]
            [thrift-store.entities.sales-channel-product :refer :all]
            [thrift-store.entities.customer :refer :all]
            [thrift-store.entities.address :refer :all]
            [thrift-store.utils :refer :all]))

(def statuses
  #{:order.status/draft
    :order.status/unpaid
    :order.status/paid
    :order.status/acknowledged
    :order.status/ready
    :order.status/shipped
    :order.status/cancelled
    :order.status/rejected})

(s/def ::order-item
  (s/cat
   :product #(s/map-of ::sales-channel-product %)
   :quantity (s/and integer? pos?)))

(s/def ::order
  (s/cat
   :id uuid?
   :channel #(s/map-of ::sales-channel %)
   :customer #(s/map-of ::customer %)
   :items (s/coll-of ::order-item)
   :datetime string?
   :shipping-address #(s/map-of ::address %)
   :status (s/and keyword? #(contains? statuses %))))

(defn generate-test-order [channel customer items shipping-address]
  (let [order {:id (java.util.UUID/randomUUID)
               :channel channel
               :customer customer
               :items items
               :datetime (utc-now)
               :shipping-address shipping-address
               :status :order.status/acknowledged}]
     (if (s/valid? ::order order)
       order
       (throw (ex-info "Test order does not conform to the specification" 
                       {:order order} (s/explain ::order order))))))

(def test-items [{:product test-sales-channel-product
                  :quantity 2}
                 {:product test-sales-channel-product-2
                  :quantity 1}])

(def test-shipping-address test-address)

(def test-order (generate-test-order test-sales-channel test-customer test-items test-shipping-address))

(with-test
  (defn change-order-status
    [order status]
    (assoc order :status status))
  (testing "Testing 'change-order-status'"
    (let [order test-order]
      (is (= :order.status/shipped (:status (change-order-status order :order.status/shipped))))
      (is (= :order.status/shipped (:status (change-order-status order :order.status/rejected)))))))