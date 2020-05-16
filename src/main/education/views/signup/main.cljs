(ns education.views.signup.main
  (:require ["@material-ui/core" :as mui]
            [education.events.signup :as signup-events]
            [education.subs.signup :as subs]
            [re-frame.core :as rf]))

(defn signup-dialog
  "Modal with sign-up form."
  []
  (let [signup @(rf/subscribe [::subs/signup])]
    [:> mui/Dialog {:open (:dialog-open signup)
                    :onClose #(rf/dispatch [::signup-events/close-signup])
                    :aria-labelledby :signup-dialog-title}
     [:> mui/DialogTitle {:id :signup-dialog-title} "Sign in"]
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
                      :color :primary} "Sign in"]]]))
