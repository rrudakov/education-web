(ns education.db.article
  (:require ["draft-js" :refer [EditorState]]
            [cljs.spec.alpha :as s]))

(s/def ::fetching boolean?)
(s/def ::title (s/and string? #(< (count %) 100)))
(s/def ::editor-state #(instance? EditorState %))
(s/def ::body ::editor-state)
(s/def ::main-featured boolean?)
(s/def ::main-featured-image (s/and string? #(< (count %) 200)))
(s/def ::new-article
  (s/keys :req-un
          [::title
           ::editor-state
           ::main-featured
           ::main-featured-image]))
(s/def ::single-article
  (s/or :fetched (s/keys :req-un
                         [::title
                          ::body])
        :fetching (s/keys :req-un [::fetching])))
(s/def ::article (s/keys :req-un [::new-article ::single-article]))

(def default-db
  {:new-article
   {:title ""
    :editor-state (.createEmpty EditorState)
    :main-featured false
    :main-featured-image ""}
   :single-article
   {:fetching true}})
