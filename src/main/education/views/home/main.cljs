(ns education.views.home.main
  (:require ["@material-ui/core" :as mui]
            [education.subs.home :as subs]
            [education.views.home.featured-post :refer [featured-post]]
            [education.views.home.main-featured-post :refer [main-featured-post]]
            [re-frame.core :as rf]))

(def featured-posts
  "Secondary featured posts."
  [{:title       "Featured post"
    :date        "Nov 11"
    :description "This is a wider card with supporting text below as a natural lead-in to additional content."
    :image       "https://source.unsplash.com/random"
    :image-text  "Image Text"}
   {:title       "Post title"
    :date        "Nov 11"
    :description "This is a wider card with supporting text below as a natural lead-in to additional content."
    :image       "https://source.unsplash.com/random"
    :image-text  "Image Text"}])

(defn home-component
  []
  [:main
   [main-featured-post @(rf/subscribe [::subs/main-featured-article])]
   [:> mui/Grid {:container true
                 :spacing   4}
    (for [post featured-posts]
      ^{:key (:title post)}
      [featured-post post])]
   [:> mui/Grid {:container true
                 :spacing   5}
    [:> mui/Grid {:item true :xs 12 :md 6}
     [:> mui/Typography {:variant :h6 :gutterBottom true} "From the firehouse..."]
     (for [post @(rf/subscribe [::subs/articles])]
       ^{:key (:id post)}
       [:div
        [:h1 (:title post)]
        [:p (:updated_on post)]])]]])
