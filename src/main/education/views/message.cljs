(ns education.views.message
  (:require ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [withStyles]]
            ["@material-ui/lab/Alert" :default Alert]
            [education.events.common :as events]
            [education.subs.main :as subs]
            [re-frame.core :as rf]
            [reagent.core :as r]))

(defn error-styles
  "Define custom CSS for error message."
  [theme]
  (clj->js
   {:root {:width "100%"
           "& > * + *" {:margin-top ((.. theme -spacing) 2)}}}))

(def with-error-styles (withStyles error-styles))

(defn- error-message-component
  "Error message snack bar."
  [& props]
  (fn [{:keys [classes]}]
    (let [message @(rf/subscribe [::subs/error-message])
          open (not (nil? message))]
      [:div {:class (.-root classes)}
       [:> mui/Snackbar {:open open
                         :autoHideDuration 6000
                         :onClose #(rf/dispatch [::events/set-error-message nil])}
        [:> Alert {:elevation 6
                   :variant :filled
                   :onClose #(rf/dispatch [::events/set-error-message nil])
                   :severity :error} message]]])))

(defn- success-message-component
  "Successful message snack bar."
  [& props]
  (fn [{:keys [classes]}]
    (let [message @(rf/subscribe [::subs/success-message])
          open (not (nil? message))]
      [:div {:class (.-root classes)}
       [:> mui/Snackbar {:open open
                         :autoHideDuration 6000
                         :onClose #(rf/dispatch [::events/set-success-message nil])}
        [:> Alert {:elevation 6
                   :variant :filled
                   :onClose #(rf/dispatch [::events/set-success-message nil])
                   :severity :success} message]]])))

(defn error-message
  []
  [:> (with-error-styles
        (r/reactify-component
         (error-message-component {})))])

(defn success-message
  []
  [:> (with-error-styles
        (r/reactify-component
         (success-message-component {})))])
