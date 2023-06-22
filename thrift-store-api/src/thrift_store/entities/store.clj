(ns thrift-store.entities.store
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [thrift-store.entities.product :refer :all]))

(s/def :store/id uuid?)
(s/def :store/name string?)
(s/def :store/description (s/nilable string?))

(s/def :store/product #(s/map-of :product %))
(s/def :store/products (s/nilable (s/coll-of :store/product)))

(s/def ::store
  (s/keys
   :req [:store/id 
         :store/name]
   :opt [:store/description 
         :store/products]))

(defn generate-test-store [name description products] 
  (let [store {:store/id (java.util.UUID/randomUUID)
               :store/name name
               :store/description description
               :store/products products}]
    (if (s/valid? ::store store)
      store
      (throw (ex-info "Test store does not conform to the specification" {:store store})))))

(def test-store (generate-test-store "High Tech" nil nil))

(with-test
  (defn change-store-description
    [c d]
    (assoc c :store/description d))
  (testing "Testing 'change-description' for Store"
    (let [store test-store]
      (is (= "Loja de eletrônicos"
             (:store/description (change-store-description store "Loja de eletrônicos")))))))