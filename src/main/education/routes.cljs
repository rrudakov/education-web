(ns education.routes
  (:require [bidi.bidi :as bidi]
            [education.events.main :as events]
            [pushy.core :as pushy]
            [re-frame.core :as rf]))

(def routes ["/" {"" :home
                  "articles/" {"" :article-index
                               "add" :article-add
                               [:article-id] :article}
                  true :not-found}])

(defn- dispatch-route
  "Dispatch re-frame event when route changes."
  [matched-route]
  (rf/dispatch [::events/set-active-panel
                {:panel      (:handler matched-route)
                 :article-id (get-in matched-route [:route-params :article-id])}]))

(def history
  (pushy/pushy dispatch-route (partial bidi/match-route routes)))

(defn app-routes
  "Setup application routes."
  []
  (pushy/start! history))

(def url-for (partial bidi/path-for routes))

(defn redirect-to
  [url]
  (pushy/set-token! history url))
