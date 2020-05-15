(ns education.subs.signup
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::signup
 (fn [db _]
   (:signup db)))
