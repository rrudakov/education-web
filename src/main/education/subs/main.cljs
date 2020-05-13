(ns education.subs.main
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::fetching
 (fn [db _]
   (:fetching db)))

(rf/reg-sub
 ::articles
 (fn [db _]
   (:articles db)))

(rf/reg-sub
 ::main-featured-article
 (fn [db _]
   (:main-featured-article db)))
