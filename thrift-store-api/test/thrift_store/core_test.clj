(ns ^:integration thrift-store.core-test
  (:require [clojure.test :refer :all]
            [thrift-store.core :refer :all]))

(deftest zero-equals-one
   (is (not (= 0 1))))

(deftest one-equals-one (is (= 1 1)))

(deftest absolute-truth
  (is true))