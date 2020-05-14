(ns education.db.signup
  (:require [cljs.spec.alpha :as s]))

(s/def ::dialog-open boolean?)
(s/def ::email string?)
(s/def ::username string?)
(s/def ::password string?)
(s/def ::password_confirm string?)
