(ns education.subs.article
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::new-article
 (fn [db _]
   (get-in db [:article :new-article])))

(rf/reg-sub
 ::editor-state
 :<- [::new-article]
 (fn [new-article _]
   (:editor-state new-article)))

(rf/reg-sub
 ::new-article-title
 :<- [::new-article]
 (fn [new-article _]
   (:title new-article)))

(rf/reg-sub
 ::new-article-main-featured-image
 :<- [::new-article]
 (fn [new-article _]
   (:main-featured-image new-article)))

(rf/reg-sub
 ::main-featured
 :<- [::new-article]
 (fn [new-article _]
   (:main-featured new-article)))
