(ns education.db.article
  (:require ["draft-js" :refer [EditorState]]
            [cljs.spec.alpha :as s]))

(s/def ::title (s/and string? #(< (count %) 100)))
(s/def ::editor-state #(instance? EditorState %))
(s/def ::main-featured boolean?)
(s/def ::main-featured-image (s/and string? #(< (count %) 200)))
(s/def ::new-article
  (s/keys :req-un
          [::title
           ::editor-state
           ::main-featured
           ::main-featured-image]))
(s/def ::article (s/keys :req-un [::new-article]))

(def default-db
  {:new-article
   {:title ""
    :editor-state (.createEmpty EditorState)
    :main-featured false
    :main-featured-image ""}})
