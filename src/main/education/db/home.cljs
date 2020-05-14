(ns education.db.home
  (:require [cljs.spec.alpha :as s]))

(s/def ::id int?)
(s/def ::user_id int?)
(s/def ::title string?)
(s/def ::body string?)
(s/def ::featured_image string?)
(s/def ::created_on inst?)
(s/def ::updated_on inst?)
(s/def ::fetching boolean?)
(s/def ::article
  (s/keys :req-un
          [::id
           ::user_id
           ::title
           ::body
           ::featured_image
           ::created_on
           ::updated_on]))
(s/def ::short-article
  (s/keys :req-un
          [::id
           ::user_id
           ::title
           ::featured_image
           ::updated_on]))
(s/def ::main-featured-article ::short-article)
(s/def ::articles (s/coll-of ::short-article))
(s/def ::home (s/or :ready (s/keys :req-un
                                   [::article
                                    ::articles
                                    ::main-featured-article])
                    :fetching (s/keys :req-un [::fetching])))
(def default-db {:fetching true})
