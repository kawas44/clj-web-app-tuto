(defproject clj-web-app-tuto "0.1.0-SNAPSHOT"
  :description "Clojure web app tuto"
  :url "http://fix.me/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [http-kit "2.1.18"]
                 [com.stuartsierra/component "0.2.3"]]
  :profiles {:dev {:plugins [[lein-pprint "1.1.2"]]
                   :dependencies []}}
  :main webapp-tuto.core)
