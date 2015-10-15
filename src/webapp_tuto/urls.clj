(ns webapp-tuto.urls
  (:require [clojure.pprint :refer [pprint]]))

(defonce ^:private repository (atom {}))

(defn- mk-shorter-url [url]
  (subs url 2))

(defn- store-shorter-url [url shorter]
  (swap! repository assoc url shorter))

(defn- new-shorter-url [url]
  (let [shorter (mk-shorter-url url)]
    (store-shorter-url url shorter)
    shorter))

(defn shorten-url [url]
  (if-let [shorter (get @repository url)]
    shorter
    (new-shorter-url url)))
