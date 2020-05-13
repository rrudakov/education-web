(ns education.events.main
  (:require [ajax.core :as ajax]
            day8.re-frame.http-fx
            [education.db.main :as db]
            [re-frame.core :as rf]
            [cljs.spec.alpha :as s]))

(defn check-and-throw
  "Throws an exception if `db` doesn't match the `spec`."
  [spec db]
  (when-not (s/valid? spec db)
    (throw (ex-info (str "Spec check failed: " (s/explain-str spec db)) {}))))

(def check-spec-interceptor (rf/after (partial check-and-throw ::db/db)))

(rf/reg-event-db
 ::initialize-db
 [check-spec-interceptor]
 (fn [_ _] db/db))

(rf/reg-event-fx
 ::fetch-articles-list
 [check-spec-interceptor]
 (fn [{:keys [db]} _]
   {:db (assoc db :fetching true)
    :http-xhrio [{:method :get
                  :uri "http://educationapp-api.herokuapp.com/api/articles"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::articles-fetched-success]
                  :on-failure [::articles-fetched-fail]}
                 {:method :get
                  :uri "http://educationapp-api.herokuapp.com/api/articles/featured/main"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success [::main-featured-article-fetched-success]
                  :on-failure [::main-featured-article-fetched-fail]}]}))

(rf/reg-event-db
 ::articles-fetched-success
 [check-spec-interceptor
  (rf/path :articles)]
 (fn [_ [_ result]] result))

(rf/reg-event-db
 ::articles-fetched-fail
 [check-spec-interceptor]
 (fn [db _]
   (assoc db :fetching true)))

(rf/reg-event-db
 ::main-featured-article-fetched-success
 [check-spec-interceptor
  (rf/path :main-featured-article)]
 (fn [_ [_ result]] result))

(rf/reg-event-db
 ::main-featured-article-fetched-fail
 [check-spec-interceptor]
 (fn [db _]
   (assoc db :fetching true)))
