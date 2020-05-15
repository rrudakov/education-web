(ns education.views.signup.main
  (:require ["@material-ui/core" :as mui]
            ["@material-ui/lab/Alert" :default Alert]
            ["@material-ui/core/styles" :refer [withStyles]]
            [education.subs.signup :as subs]
            [re-frame.core :as rf]
            [education.events.signup :as signup-events]
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
    (let [signup @(rf/subscribe [::subs/signup])
          open (not (nil? (:error_message signup)))
          message (or (:error_message signup) "")]
      [:div {:class (.-root classes)}
       [:> mui/Snackbar {:open open
                         :autoHideDuration 6000
                         :onClose #(rf/dispatch [::signup-events/set-error-message nil])}
        [:> Alert {:elevation 6
                   :variant :filled
                   :onClose #(rf/dispatch [::signup-events/set-error-message nil])
                   :severity :error} message]]])))

(defn error-message
  []
  [:> (with-error-styles
        (r/reactify-component
         (error-message-component {})))])

(defn signup-dialog
  "Modal with sign-up form."
  []
  (let [signup @(rf/subscribe [::subs/signup])]
    [:> mui/Dialog {:open (:dialog-open signup)
                    :onClose #(rf/dispatch [::signup-events/close-signup])
                    :aria-labelledby :signup-dialog-title}
     [:> mui/DialogTitle {:id :signup-dialog-title} "Sign up"]
     [:> mui/DialogContent
      [:> mui/DialogContentText "To sign in please enter your login and password below."]
      [:> mui/TextField {:autoFocus true
                         :value (:username signup)
                         :onChange #(rf/dispatch [::signup-events/set-username (-> % .-target .-value)])
                         :margin :dense
                         :id :username
                         :fullWidth true
                         :label "Username"
                         :type :text}]
      [:> mui/TextField {:margin :dense
                         :value (:password signup)
                         :onChange #(rf/dispatch [::signup-events/set-password (-> % .-target .-value)])
                         :id :password
                         :fullWidth true
                         :label "Password"
                         :type :password}]]
     [:> mui/DialogActions
      [:> mui/Button {:onClick #(rf/dispatch [::signup-events/close-signup])
                      :color :primary} "Close"]
      [:> mui/Button {:onClick #(rf/dispatch [::signup-events/login])
                      :color :primary} "Login"]]]))
