(ns education.db.main
  (:require [cljs.spec.alpha :as s]
            [education.db.home :as home]))

(s/def ::active-panel
  #{:home
    :login
    :article-index
    :article
    :not-found})

(s/def ::db (s/keys :req-un [::active-panel ::home/home]))
(def db  {:active-panel :home
          :home home/default-db})
