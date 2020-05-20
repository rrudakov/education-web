(ns education.subs.main
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(rf/reg-sub
 ::error-message
 (fn [db _]
   (:error-message db)))

(rf/reg-sub
 ::success-message
 (fn [db _]
   (:success-message db)))
