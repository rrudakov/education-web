(ns education.subs.home
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::articles
 (fn [db _]
   (get-in db [:home :articles])))

(rf/reg-sub
 ::main-featured-article
 (fn [db _]
   (get-in db [:home :main-featured-article])))
