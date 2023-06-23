(ns thrift-store.utils
  (:require [clojure.spec.alpha :as spec]
            [clojure.string :as str]
            [clojure.test.check.generators :as gen]))

;; DateTime utils
(defn utc-now []
  (let [*formated* (new java.text.SimpleDateFormat "yyyy-MM-dd hh:mm:ss")]
    (.setTimeZone
     *formated*
     (java.util.TimeZone/getTimeZone "UTC"))
    (.format
     *formated*
     (java.util.Date.))))