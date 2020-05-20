(ns education.db.home
  (:require [cljs.spec.alpha :as s]))

(s/def ::fetching boolean?)
(s/def ::id int?)
(s/def ::user_id int?)
(s/def ::title string?)
(s/def ::body string?)
(s/def ::featured_image string?)
(s/def ::created_on inst?)
(s/def ::updated_on inst?)
(s/def ::article
  (s/or :fetched (s/keys :req-un
                         [::id
                          ::user_id
                          ::title
                          ::body
                          ::featured_image
                          ::created_on
                          ::updated_on])
        :fetching (s/keys :req-un [::fetching])))
(s/def ::short-article
  (s/keys :req-un
          [::id
           ::user_id
           ::title
           ::featured_image
           ::updated_on]))
(s/def ::main-featured-article (s/or :fetched ::short-article
                                     :fetching (s/keys :req-un [::fetching])))
(s/def ::articles (s/or :fetched (s/coll-of ::short-article)
                        :fetching (s/keys :req-un [::fetching])))
(s/def ::home
  (s/keys :req-un
          [::article
           ::articles
           ::main-featured-article]))
(def default-db
  {:article {:fetching true}
   :articles {:fetching true}
   :main-featured-article {:fetching true}})
