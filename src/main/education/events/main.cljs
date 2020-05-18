(ns education.events.main
  (:require [ajax.core :as ajax]
            [cljs.spec.alpha :as s]
            day8.re-frame.http-fx
            [education.db.main :as db]
            [education.events.interceptors :refer [check-spec-interceptor]]
            [re-frame.core :as rf]))

(rf/reg-event-db
 ::initialize-db
 [check-spec-interceptor]
 (fn [_ _] db/db))

(rf/reg-event-db
 ::set-error-message
 [check-spec-interceptor
  (rf/path [:error_message])]
 (fn [_ [_ {:keys [response]}]]
   (:message response)))

(rf/reg-event-fx
 ::fetch-articles-list
 [check-spec-interceptor]
 (fn [{:keys [db]} _]
   {:db (assoc db :fetching true)
    :http-xhrio [{:method :get
                  :uri "http://educationapp-api.herokuapp.com/api/articles"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::articles-fetched-success]
                  :on-failure [::set-error-message]}
                 {:method :get
                  :uri "http://educationapp-api.herokuapp.com/api/articles/featured/main"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::main-featured-article-fetched-success]
                  :on-failure [::set-error-message]}]}))

(rf/reg-event-db
 ::articles-fetched-success
 [check-spec-interceptor]
 (fn [db [_ result]]
   (-> db
       (assoc :fetching false)
       (assoc-in [:home :articles] result))))

(rf/reg-event-db
 ::main-featured-article-fetched-success
 [check-spec-interceptor
  (rf/path :home :main-featured-article)]
 (fn [_ [_ result]] result))

(rf/reg-event-db
 ::main-featured-article-fetched-fail
 [check-spec-interceptor]
 (fn [db _]
   (assoc db :fetching true)))

(rf/reg-event-db
 ::set-active-panel
 [check-spec-interceptor]
 (fn [db [_ panel]]
   (assoc db :active-panel panel)))
