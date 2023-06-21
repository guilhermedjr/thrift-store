(ns thrift-store.entities.store
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [thrift-store.entities.product :as p]))

(s/def ::id uuid?)
(s/def ::name string?)
(s/def ::description string?)

(s/def ::product #(instance? ::p/Product %))
(s/def ::products (s/coll-of ::product))

(s/def ::Store
  (s/keys
   :req [::id ::name]
   :opt [::description ::products]))

(defn generate-test-store [name description products] 
  (let [store {::id (java.util.UUID/randomUUID)
               ::name name
               ::description description
               ::products products}]
    (if (s/valid? ::Store store)
      store
      (throw (ex-info "Test store does not conform to the specification" {:store store})))))

(def test-store (generate-test-store "High Tech" nil nil))

(with-test
  (defn change-description
    [c d]
    (assoc c ::description d))
  (testing "Testing 'change-description' for Store"
    (let [store test-store]
      (is (= "Loja de eletrônicos"
             (::description (change-description store "Loja de eletrônicos")))))))