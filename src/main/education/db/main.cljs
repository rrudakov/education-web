(ns education.db.main
  (:require [cljs.spec.alpha :as s]
            [education.db.article :as article]
            [education.db.home :as home]
            [education.db.signup :as signup]))

(s/def ::error-message (s/or :error string?
                             :nothing nil?))
(s/def ::success-message (s/or :sucess string?
                               :nothing nil?))
(s/def ::active-panel
  #{:home
    :article-index
    :article-add
    :article
    :not-found})
(s/def ::db
  (s/keys :req-un
          [::active-panel
           ::home/home
           ::signup/signup
           ::article/article]
          :opt-un [::error-message]))

(def db {:active-panel    :home
         :error-message   nil
         :success-message nil
         :home            home/default-db
         :signup          signup/default-db
         :article         article/default-db})
