(ns thrift-store.entities.store
  (:require [schema.core :as s]
            [thrift-store.entities.marketplace :refer :all]))

(s/defrecord
 Store [name :- s/Str
        description :- s/Str
        marketplaces :- '#{Marketplace}])

(s/defrecord MarketplaceStore [marketplace :- Marketplace
                               store :- Store])

;;(s/defn bind-store-product-to-marketplace
  ;;([product marketplaceStore] (new-product-store product marketplaceStore))
  ;;([product marketplaceStore name description price stock] ()))