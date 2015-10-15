(ns webapp-tuto.web
  (:require [clojure.pprint :refer [pprint]]
            [compojure.core :refer [GET POST defroutes]]
            [ring.middleware.defaults :as rdefaults]
            [ring.util.anti-forgery :as af]
            [ring.util.response :as ur]
            [net.cgrand.enlive-html :as html]))

(defn index-page [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str
          "Render index page<br><br><pre>"
          (with-out-str  (pprint request))
          "</pre>")})

(html/deftemplate new-form-template "templates/new-form.html"
  [params]
  [:#__anti-forgery-token] (html/substitute
                            (html/html-snippet
                             (af/anti-forgery-field)))
  [:#shorten-url-form] (html/set-attr :action "/new")
  [:#shorten-url-form :.url-form-error] (if (get params "error")
                                          (html/remove-class "hidden")
                                          identity))

(defn shrink-page [request]
  (new-form-template (:query-params request)))

(defn do-shrink [request]
  (let [url (get (:params request) :url-input)]
    (if (empty? url)
      (ur/redirect "/new?error=1")
      (str "DO SHRINK: " url "<br> <pre>" (with-out-str (pprint request)) "</pre>"))))

(defn redirect-action [request]
  (let [surl (get (:params request) :surl)]
    (str "Redirection based on " surl)))

(defroutes app
  (GET "/" [] #'index-page)
  (GET "/new" [] #'shrink-page)
  (POST "/new" [] #'do-shrink)
  (GET "/:surl" [surl] #'redirect-action))

(def site
  (rdefaults/wrap-defaults #'app rdefaults/site-defaults))
