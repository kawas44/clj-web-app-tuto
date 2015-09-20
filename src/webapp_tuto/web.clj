(ns webapp-tuto.web
  (:require [clojure.pprint :refer [pprint]]
            [compojure.core :refer [GET defroutes]]
            [ring.middleware.defaults :as rdefaults]))

(defn index [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str
          "Render index page<br><br><pre>"
          (with-out-str  (pprint request))
          "</pre>")})

(defroutes app
  (GET "/" [] index))

(def site
  (rdefaults/wrap-defaults #'app rdefaults/site-defaults))
