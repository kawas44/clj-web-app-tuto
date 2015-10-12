(ns webapp-tuto.web
  (:require [clojure.pprint :refer [pprint]]
            [compojure.core :refer [GET POST defroutes]]
            [ring.middleware.defaults :as rdefaults]
            [ring.util.anti-forgery :as af]
            [net.cgrand.enlive-html :as html]))

(defn index-page [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str
          "Render index page<br><br><pre>"
          (with-out-str  (pprint request))
          "</pre>")})

(html/deftemplate new-form-template "templates/new-form.html"
  []
  [:#__anti-forgery-token] (html/html-content (af/anti-forgery-field)))

(defn shrink-page [request]
  (new-form-template))

(defn do-shrink [request]
  (let [url (get (:params request) :url-input)]
    (str "DO SHRINK: " url "<br> <pre>" (with-out-str (pprint request)) "</pre>")))

(defn redirect-action [request]
  (let [surl (get (:params request) :surl)]
    (str "Redirection based on " surl)))

(defroutes app
  (GET "/" [] #'index-page)
  (GET "/shrink" [] #'shrink-page)
  (POST "/shrink" [] #'do-shrink)
  (GET "/:surl" [surl] #'redirect-action))

(def site
  (rdefaults/wrap-defaults #'app rdefaults/site-defaults))
