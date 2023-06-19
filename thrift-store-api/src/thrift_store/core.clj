(ns thrift-store.core
  (:require [thrift-store.server :as server])
  (:gen-class))

(defn -dev-main 
  [& args]
  (server/dev-start-server!))

(defn -main
  [& args]
  (server/start-server!))