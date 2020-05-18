(ns education.events.article
  (:require [ajax.core :as ajax]
            [education.events.interceptors :refer [check-spec-interceptor]]
            [education.events.main :as main-events]
            [re-frame.core :as rf]
            ["draft-js" :refer [convertFromRaw convertToRaw]]))

(defn auth-header
  "Get user token and format for API authorization"
  [db]
  (when-let [token (get-in db [:signup :token])]
    [:Authorization (str "Token " token)]))

(defn- raw-editor-state
  "Convert `editor-state` to raw to save to database."
  [editor-state]
  (.stringify js/JSON (convertToRaw (.getCurrentContent editor-state))))

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

(rf/reg-event-fx
 ::post-article
 [check-spec-interceptor]
 (fn [{:keys [db]} _]
   (let [new-article (get-in db [:article :new-article])
         {:keys [title editor-state main-featured main-featured-image]} new-article]
     {:http-xhrio {:method :post
                   :uri "http://educationapp-api.herokuapp.com/api/articles"
                   :format (ajax/json-request-format)
                   :headers (auth-header db)
                   :params {:title title
                            :body (raw-editor-state editor-state)
                            :featured_image main-featured-image
                            :is_main_featured main-featured}
                   :timeout 30000
                   :response-format (ajax/json-response-format {:keywords? true})
                   :on-success [::main-events/set-active-panel :home]
                   :on-failure [::main-events/set-error-message]}})))
