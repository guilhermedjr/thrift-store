(ns thrift-store.server
  (:require [ring.adapter.jetty :as ring-jetty]
            [reitit.ring :as ring]
            [ring.middleware.reload :refer [wrap-reload]]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [clojure.tools.logging :as log]
            [thrift-store.config :as config]))

(defonce products (atom {}))

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

(defn stop-server! [stop-fn] 
  (log/info "Stopping server")
  (when (ifn? stop-fn)
    (try
      (stop-fn)
      (catch Throwable e
        (log/info ::stop-server! " - Caught exception while stopping the server:" (pr-str e))))))

(defn start-server! [& [args]]
  (log/info "Starting server (PROD)") 
  (let [{:keys [host port]} (config/load-config)]
    (log/info "Starting server on" (str host ":" port))
    (ring-jetty/run-jetty app {:port port
                               :join? false})))

(defn dev-start-server! [& [args]]
  (log/info "Starting server (DEV)")
  (let [{:keys [host port]} (config/load-config)]
    (log/info "Starting server on" (str host ":" port))
    (ring-jetty/run-jetty (wrap-reload #'app) {:port port
                               :join? false})))