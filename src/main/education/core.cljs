(ns education.core
  (:require [re-frame.core :as re-frame]
            [reagent.dom :as rdom]
            [education.views.main :refer [main-panel]]))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [main-panel] root-el)))

(defn init []
  ;; (routes/app-routes)
  ;; (re-frame/dispatch-sync [::events/initialize-db])
  (mount-root))
