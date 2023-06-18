(ns thrift-store.core
  (:require [ring.adapter.jetty :as ring-jetty]
            [reitit.ring :as ring]
            [ring.middleware.reload :refer [wrap-reload]]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [clojure.tools.logging :as log])
  (:gen-class))

(def products (atom {}))

(defn string-handler [_]
  {:status 200
   :body "Thrift Store"})

(defn create-product [{product :body-params}]
  (let [id (str (java.util.UUID/randomUUID))
        products (try (->> (assoc product :id id)
                           (swap! products assoc id))
                      (catch Exception e
                        (do (log/error e) (products))))]
    {:status 200
     :body products}))

(defn get-products [_]
  {:status 200
   :body @products})

(defn get-product-by-id [{{:keys [id]} :path-params}]
  {:status 200
   :body (get @products id)})

(def routes 
  [["products/:id" get-product-by-id]
   ["products" {:get get-products
                :post create-product}]
   ["" string-handler]])

(def app
  (ring/ring-handler
   (ring/router
    ["/"
    routes]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(defn -dev-main 
  [& args]
  (ring-jetty/run-jetty (wrap-reload #'app) {:port 3000 
                             :join? false}))

(defn -main
  [& args]
  (ring-jetty/run-jetty app {:port 3000
                             :join? false}))