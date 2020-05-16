(ns education.events.signup
  (:require [ajax.core :as ajax]
            [education.db.signup
             :refer
             [remove-token-from-local-storage token->local-storage]]
            [education.events.interceptors :refer [check-spec-interceptor]]
            [education.events.main :as main-events]
            [re-frame.core :as rf]))

(rf/reg-event-db
 ::open-signup
 [check-spec-interceptor
  (rf/path [:signup :dialog-open])]
 (fn [_ _] true))

(rf/reg-event-db
 ::close-signup
 [check-spec-interceptor
  (rf/path [:signup :dialog-open])]
 (fn [_ _] false))

(rf/reg-event-db
 ::set-username
 [check-spec-interceptor
  (rf/path [:signup :username])]
 (fn [_ [_ new-username]] new-username))

(rf/reg-event-db
 ::set-password
 [check-spec-interceptor
  (rf/path [:signup :password])]
 (fn [_ [_ new-password]] new-password))

(rf/reg-event-fx
 ::login
 [check-spec-interceptor]
 (fn [{:keys [db]} _]
   {:http-xhrio {:method :post
                 :uri "http://educationapp-api.herokuapp.com/api/login"
                 :format (ajax/json-request-format)
                 :params {:username (get-in db [:signup :username])
                          :password (get-in db [:signup :password])}
                 :timeout 30000
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::login-successful]
                 :on-failure [::login-failed]}}))

(def ->local-storage (rf/after token->local-storage))
(def <-local-storage (rf/after remove-token-from-local-storage))

(rf/reg-event-db
 ::logout
 [check-spec-interceptor
  (rf/path :signup)
  <-local-storage]
 (fn [signup _]
   (dissoc signup :token)))

(rf/reg-event-fx
 ::login-successful
 [check-spec-interceptor
  (rf/path :signup)
  ->local-storage]
 (fn [{signup :db} [_ {:keys [token]}]]
   {:db (-> signup
            (assoc :token token)
            (assoc :username "")
            (assoc :password ""))
    :dispatch [::close-signup]}))

(rf/reg-event-fx
 ::login-failed
 [check-spec-interceptor
  (rf/path :signup)
  <-local-storage]
 (fn [{signup :db} [_ {:keys [response]}]]
   {:db (dissoc signup :token)
    :dispatch [::main-events/set-error-message (:message response)]}))
