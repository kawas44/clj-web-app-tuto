(ns user
  (:require [reloaded.repl :refer [system start stop reset]]
            [webapp-tuto.core :refer [create-system]]))

(reloaded.repl/set-init! #'create-system)
