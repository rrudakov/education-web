(ns education.events.common
  (:require [education.events.interceptors :refer [check-spec-interceptor]]
            [re-frame.core :as rf]))

(rf/reg-event-db
 ::set-error-message
 [check-spec-interceptor]
 (fn [db [_ {:keys [status response]}]]
   (if (= status 401)
     (-> db
         (update :signup dissoc :token)
         (assoc :error-message (:message response)))
     (-> db
         (assoc :error-message (:message response))))))

(rf/reg-event-db
 ::set-success-message
 [check-spec-interceptor
  (rf/path [:success-message])]
 (fn [_ [_ message]] message))
