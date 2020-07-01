(ns education.subs.home
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::full-sized-articles
 (fn [db _]
   (get-in db [:home :full-sized-articles])))

(rf/reg-sub
 ::main-featured-article
 (fn [db _]
   (get-in db [:home :main-featured-article])))

(rf/reg-sub
 ::featured-articles
 (fn [db _]
   (get-in db [:home :featured-articles])))
