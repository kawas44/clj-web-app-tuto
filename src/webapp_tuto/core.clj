(ns webapp-tuto.core
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :as hkit]
            [webapp-tuto.web :refer [app]]))

(defn- start-http-server [handler host port]
  (hkit/run-server handler {:ip host :port port}))

(defrecord HTTPServer [host port]
  component/Lifecycle

  (start [this]
    (if-let [_ (get this :httpserver)]
      (do
        (println "HTTP server already running!")
        this)
      (do
        (println (format "Starting HTTP server on %s:%s" host port))
        (assoc this :httpserver (start-http-server #'app host port)))))

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
  component/Lifecycle
  (start [this]
    (println "Starting TutoCore")
    this)
  (stop [this]
    (println "Stopping TutoCore")
    this))

(defn create-system []
  (component/system-map
   :httpserver (->HTTPServer "localhost" 8000)
   :app (component/using
         (map->TutoCore {})
         [:httpserver])))

(defn -main [& args]
  (.start (create-system)))
