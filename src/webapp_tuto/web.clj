(ns webapp-tuto.web
  (:require [compojure.core :refer [GET defroutes]]))


(defn index [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Render index page"})

(defroutes app
  (GET "/" [] index))
