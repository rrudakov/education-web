(ns education.views.article.single
  (:require ["react-draft-wysiwyg" :refer [Editor]]
            [re-frame.core :as rf]
            ["@material-ui/core" :as mui]
            [education.subs.article :as subs]))

(defn single-article-component
  []
  (let [post @(rf/subscribe [::subs/single-article])]
    [:main
    [:> mui/Typography {:variant :h3} (:title post)]
    [:> Editor {:editorState (:body post)
                :toolbarHidden true
                :readOnly true}]]))
