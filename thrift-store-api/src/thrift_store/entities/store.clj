(ns thrift-store.entities.store
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [thrift-store.entities.product :refer :all]))

(s/def ::store-product #(s/map-of ::product %))

(s/def ::store
  (s/cat
   :id uuid?
   :name string?
   :description (s/nilable string?)
   :products (s/nilable (s/coll-of ::store-product))))

(defn generate-test-store [name description products] 
  (let [store {:id (java.util.UUID/randomUUID)
               :name name
               :description description
               :products products}]
    (if (s/valid? ::store store)
      store
      (throw (ex-info "Test store does not conform to the specification" {:store store})))))

(def test-store (generate-test-store "High Tech" nil nil))

(with-test
  (defn change-store-description
    [c d]
    (assoc c :description d))
  (testing "Testing 'change-description' for Store"
    (let [store test-store]
      (is (= "Loja de eletrônicos"
             (:description (change-store-description store "Loja de eletrônicos")))))))