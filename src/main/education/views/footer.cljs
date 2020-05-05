(ns education.views.footer
  (:require [reagent.core :as r]
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [withStyles]]))

(defn footer-styles
  "Define custom CSS for footer."
  [theme]
  (clj->js
   {:footer
    {:backgroundColor (.. theme -palette -background -paper)
     :padding         ((.. theme -spacing) 6 6)}}))

(def with-footer-styles
  "Wrapper for element to use custom styles."
  (withStyles footer-styles))

(defn copyright
  "Copyright component."
  []
  [:> mui/Typography {:variant :body2
                      :color   :textSecondary
                      :align   :center}
   "Copyright © "
   [:> mui/Link {:color :inherit
             :href  "https://material-ui.com"}
    (str "My Website " (.getFullYear (js/Date.)))]])

(defn footer-component
  "Plain footer component."
  [{:keys [description title]}]
  (fn [{:keys [classes] :as props}]
    [:footer {:class (.-footer classes)}
     [:> mui/Container {:max-width :lg}
      [:> mui/Typography {:variant       :h6
                          :align         :center
                          :gutter-bottom true} title]
      [:> mui/Typography {:variant   :subtitle1
                          :align     :center
                          :color     :textSecondary
                          :component :p} description]
      [copyright]]]))

(defn footer
  "Footer component with custom styles."
  [{:keys [description title]}]
  [:> (with-footer-styles
        (r/reactify-component
         (footer-component {:description description
                            :title       title})))])
