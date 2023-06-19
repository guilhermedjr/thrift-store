(ns thrift-store.config
  (:require [clojure.edn :as edn]))

;; TODO: load env file (project.clj :resource-paths)
;;([] (load-config "config.edn"))
(defn load-config
  ([] (load-config "resources/dev/config.edn"))
  ([filename] (edn/read-string  (slurp filename))))