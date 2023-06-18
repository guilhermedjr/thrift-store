(ns thrift-store.entities.marketplace
  (:require [schema.core :as s]))

(s/def ^:private marketplaces #{"Amazon" "Mercado Livre" "Shopee"})

(s/defrecord Marketplace [name :- s/Str])

(s/defn bind-store-to-marketplace
  ([store marketplace] ()))