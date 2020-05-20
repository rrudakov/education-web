(ns education.events.common
  (:require [education.events.interceptors :refer [check-spec-interceptor]]
            [re-frame.core :as rf]))

(rf/reg-event-db
 ::set-error-message
 [check-spec-interceptor
  (rf/path [:error-message])]
 (fn [_ [_ {:keys [response]}]]
   (:message response)))

(rf/reg-event-db
 ::set-success-message
 [check-spec-interceptor
  (rf/path [:success-message])]
 (fn [_ [_ message]] message))
