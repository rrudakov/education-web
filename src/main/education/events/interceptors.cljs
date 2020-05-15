(ns education.events.interceptors
  (:require [cljs.spec.alpha :as s]
            [re-frame.core :as rf]
            [education.db.main :as db]))

(defn check-and-throw
  "Throws an exception if `db` doesn't match the `sped`."
  [spec db]
  (when-not (s/valid? spec db)
    (throw (ex-info (str "Spec check failed: " (s/explain-str spec db)) {}))))

(def check-spec-interceptor (rf/after (partial check-and-throw ::db/db)))
