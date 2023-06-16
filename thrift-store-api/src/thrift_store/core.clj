(ns thrift-store.core
  (:require [ring.adapter.jetty :as ring-jetty]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja])
  (:gen-class))

(def products (atom {}))

(defn string-handler [_]
  {:status 200
   :body "Thrift Store"})

(defn create-product [{product :body-params}]
  (let [id (str (java.util.UUID/randomUUID))
        products (->> (assoc product :id id)
                      (swap! products assoc id))]
    {:status 200
     :body products}))

(defn get-products [_]
  {:status 200
   :body @products})

(defn get-product-by-id [{{:keys [id]} :path-params}]
  {:status 200
   :body (get @products id)})

(def app
  (ring/ring-handler
   (ring/router
    ["/" 
     ["products/:id" get-product-by-id]
     ["products" {:get get-products
                  :post create-product}]
     ["" string-handler]] 
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(defn start []
  (ring-jetty/run-jetty app {:port  3000
                             :join? false}))

(defn -main
  [& args]
  (start))
