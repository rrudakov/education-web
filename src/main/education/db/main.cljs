(ns education.db.main
  (:require [cljs.spec.alpha :as s]
            [education.db.home :as home]
            [education.db.signup :as signup]))

(s/def ::active-panel
  #{:home
    :article-index
    :article
    :not-found})
(s/def ::db
  (s/keys :req-un
          [::active-panel
           ::home/home
           ::signup/signup]))

(def db  {:active-panel :home
          :home home/default-db
          :signup signup/default-db})
