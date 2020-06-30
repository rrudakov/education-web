(ns education.events.main
  (:require [ajax.core :as ajax]
            day8.re-frame.http-fx
            [education.db.main :as db]
            [education.db.signup :refer [token-key]]
            [education.events.article :as article-events]
            [education.events.interceptors :refer [check-spec-interceptor]]
            [education.utils.editor :refer [to-editor-state]]
            [re-frame.core :as rf]))

(rf/reg-event-fx
 ::initialize-db
 [(rf/inject-cofx ::local-storage-token)
  check-spec-interceptor]
 (fn [{:keys [_ local-store-token]} _]
   {:db (assoc-in db/db [:signup :token] local-store-token)}))

(rf/reg-event-fx
 ::fetch-articles-list
 [check-spec-interceptor]
 (fn [{:keys [db]} _]
   {:db         (-> db
                    (assoc-in [:home :full-sized-articles :fetching] true)
                    (assoc-in [:home :main-featured-article :fetching] true)
                    (assoc-in [:home :article :fetching] true))
    :http-xhrio [{:method          :get
                  :uri             "http://educationapp-api.herokuapp.com/api/articles/latest"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :params          {:limit 3}
                  :on-success      [::latest-articles-fetched-success]
                  :on-failure      [::set-error-message]}
                 {:method          :get
                  :uri             "http://educationapp-api.herokuapp.com/api/articles/featured/main"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::main-featured-article-fetched-success]
                  :on-failure      [::set-error-message]}]}))

(defn to-full-sized-article
  [{:keys [updated_on created_on body] :as article}]
  (-> article
      (assoc :updated_on (js/Date. updated_on))
      (assoc :created_on (js/Date. created_on))
      (assoc :body (to-editor-state body))))

(rf/reg-event-db
 ::latest-articles-fetched-success
 [check-spec-interceptor]
 (fn [db [_ result]]
   (->> result
        (map to-full-sized-article)
        (assoc-in db [:home :full-sized-articles]))))

(rf/reg-event-db
 ::main-featured-article-fetched-success
 [check-spec-interceptor
  (rf/path [:home :main-featured-article])]
 (fn [_ [_ result]]
   (update result :updated_on #(js/Date. %))))

(rf/reg-event-fx
 ::set-active-panel
 [check-spec-interceptor]
 (fn [{:keys [db]} [_ {:keys [panel article-id]}]]
   (let [set-page (assoc db :active-panel panel)]
     (case panel
       :home    {:db       set-page
                 :dispatch [::fetch-articles-list]}
       :article {:db       set-page
                 :dispatch [::article-events/fetch-article article-id]}
       {:db set-page}))))

(rf/reg-cofx
 ::local-storage-token
 (fn [cofx _]
   (assoc cofx :local-store-token
          (.getItem js/localStorage token-key))))
