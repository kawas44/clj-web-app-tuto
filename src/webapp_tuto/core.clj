(ns webapp-tuto.core
  (:require [org.httpkit.server :as hks]))

(defn app [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello HTTP-KIT!"})

(defn -main [& args]
  (hks/run-server app {:port 8080})
  (println "Server started on localhost:8080"))
