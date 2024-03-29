(ns education.events.article
  (:require [ajax.core :as ajax]
            day8.re-frame.http-fx
            [education.events.common :as common-events]
            [education.events.interceptors :refer [check-spec-interceptor]]
            [education.utils.editor :refer [raw-editor-state to-editor-state]]
            [re-frame.core :as rf]))

(defn auth-header
  "Get user token and format for API authorization"
  [db]
  (when-let [token (get-in db [:signup :token])]
    [:Authorization (str "Token " token)]))

(rf/reg-event-db
 ::set-editor-title
 [check-spec-interceptor
  (rf/path [:article :new-article :title])]
 (fn [_ [_ new-title]] new-title))

(rf/reg-event-db
 ::set-new-article-main-featured-image
 [check-spec-interceptor
  (rf/path [:article :new-article :main-featured-image])]
 (fn [_ [_ new-image]] new-image))

(rf/reg-event-db
 ::set-editor-state
 [check-spec-interceptor
  (rf/path [:article :new-article :editor-state])]
 (fn [_ [_ new-state]] new-state))

(rf/reg-event-db
 ::toggle-main-featured
 [check-spec-interceptor
  (rf/path [:article :new-article :main-featured])]
 (fn [main-featured [_ _]]
   (not main-featured)))

(rf/reg-event-db
 ::set-description
 [check-spec-interceptor
  (rf/path [:article :new-article :description])]
 (fn [_ [_ description]] description))

(rf/reg-event-fx
 ::post-article
 [check-spec-interceptor]
 (fn [{:keys [db]} _]
   (let [new-article (get-in db [:article :new-article])
         {:keys [title editor-state main-featured main-featured-image description]} new-article]
     {:http-xhrio {:method :post
                   :uri "http://educationapp-api.herokuapp.com/api/articles"
                   :format (ajax/json-request-format)
                   :headers (auth-header db)
                   :params {:title title
                            :body (raw-editor-state editor-state)
                            :featured_image main-featured-image
                            :is_main_featured main-featured
                            :description description}
                   :timeout 30000
                   :response-format (ajax/json-response-format {:keywords? true})
                   :on-success [::post-article-success]
                   :on-failure [::common-events/set-error-message]}})))

(rf/reg-event-fx
 ::fetch-article
 [check-spec-interceptor]
 (fn [{:keys [db]} [_ article-id]]
   {:db (-> db
            (assoc-in [:article :single-article] {:fetching true}))
    :http-xhrio {:method :get
                 :uri (str "http://educationapp-api.herokuapp.com/api/articles/" article-id)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [::article-fetched-success]
                 :on-failure [::common-events/set-error-message]}}))

(rf/reg-event-db
 ::article-fetched-success
 [check-spec-interceptor]
 (fn [db [_ result]]
   (->> (update (update result :body to-editor-state) :updated_on #(js/Date. %))
        (assoc-in db [:article :single-article]))))

;; TODO: fix it
(rf/reg-fx
 ::set-url
 (fn [{:keys [url]}]
   (education.routes/redirect-to url)))

(rf/reg-event-fx
 ::post-article-success
 [check-spec-interceptor]
 (fn [_ [_ {:keys [id]}]]
   {::set-url {:url (education.routes/url-for :article :article-id id)}}))
