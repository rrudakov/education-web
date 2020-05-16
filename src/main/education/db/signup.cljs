(ns education.db.signup
  (:require [cljs.spec.alpha :as s]))

(s/def ::dialog-open boolean?)
(s/def ::email string?)
(s/def ::username string?)
(s/def ::password string?)
(s/def ::password_confirm string?)
(s/def ::token (s/or :logout nil?
                     :login string?))
(s/def ::signup
  (s/keys :req-un
          [::dialog-open
           ::email
           ::username
           ::password
           ::password_confirm]
          :opt-un [::token]))
(def default-db {:dialog-open false
                 :email ""
                 :username ""
                 :password ""
                 :password_confirm ""})

(def token-key
  "Key for local storage token."
  "auth-token")

(defn token->local-storage
  "Put authorization token to local storage."
  [signup]
  (.setItem js/localStorage token-key (:token signup)))

(defn remove-token-from-local-storage
  "Remove token from local storage if exist."
  []
  (.removeItem js/localStorage token-key))
