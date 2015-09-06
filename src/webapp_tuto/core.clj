(ns webapp-tuto.core
  (:require [com.stuartsierra.component :as sc]
            [org.httpkit.server :as hks]))


(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello HTTP-KIT!"})


(defn- start-http-server [handler host port]
  (hks/run-server app {:ip host :port port}))

(defrecord HTTPServer [host port]
  sc/Lifecycle

  (start [this]
    (if-let [_ (get this :httpserver)]
      (do
        (println "HTTP server already running!")
        this)
      (do
        (println (format "Starting HTTP server on %s:%s" host port))
        (assoc this :httpserver (start-http-server app host port)))))

  (stop [this]
    (if-let [stop-httpserver-fn (get this :httpserver)]
      (do
        (println "Stopping HTTP server")
        (stop-httpserver-fn)
        (assoc this :httpserver nil))
      (do
        (println "HTTP server not running!")
        this))))


(defrecord TutoCore [httpserver]
  sc/Lifecycle
  (start [this]
    (println "Staring TutoCore")
    this)
  (stop [this]
    (println "Stopping TutoCore")
    this))


(defn create-system []
  (sc/system-map
    :httpserver (->HTTPServer "localhost" 8000)
    :app (sc/using
           (map->TutoCore {})
           [:httpserver])))


(defn -main [& args]
  (.start (create-system)))
