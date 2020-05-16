(ns education.db.main
  (:require [cljs.spec.alpha :as s]
            [education.db.home :as home]
            [education.db.signup :as signup]))

(s/def ::error_message (s/or :error string?
                             :success nil?))
(s/def ::active-panel
  #{:home
    :article-index
    :article
    :not-found})
(s/def ::db
  (s/keys :req-un
          [::active-panel
           ::home/home
           ::signup/signup]
          :opt-un [::error_message]))

(def db  {:active-panel :home
          :error_message nil
          :home home/default-db
          :signup signup/default-db})
