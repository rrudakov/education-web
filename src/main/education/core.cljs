(ns education.core
  (:require [education.events.main :as events]
            [education.views.main :refer [main-panel]]
            [re-frame.core :as rf]
            [reagent.dom :as rdom]))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [main-panel] root-el)))

(defn init []
  ;; (routes/app-routes)
  (rf/dispatch-sync [::events/initialize-db])
  (rf/dispatch [::events/fetch-articles-list])
  (mount-root))
