(ns education.routes
  (:require [bidi.bidi :as bidi]
            [education.events.main :as events]
            [pushy.core :as pushy]
            [re-frame.core :as rf]))

(def routes ["/" {"" :home
                  "articles/" {"" :article-index
                              [:id] :article}
                  true :not-found}])

(defn- dispatch-route
  "Dispatch re-frame event when route changes."
  [matched-route]
  (let [panel (:handler matched-route)]
    (rf/dispatch [::events/set-active-panel panel])))

(defn app-routes
  "Setup application routes."
  []
  (pushy/start! (pushy/pushy dispatch-route (partial bidi/match-route routes))))

(def url-for (partial bidi/path-for routes))
