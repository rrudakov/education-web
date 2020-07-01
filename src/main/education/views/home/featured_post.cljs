(ns education.views.home.featured-post
  (:require [reagent.core :as r]
            ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [withStyles]]
            [education.routes :refer [url-for]]))

(def featured-post-styles
  "Define custom CSS for featured post."
  (clj->js
   {:card        {:display :flex}
    :cardDetails {:flex 1}
    :cardMedia   {:width 160}}))

(defn- featured-post-component
  "Plain featured post component."
  [{:keys [post]}]
  (fn [{:keys [classes]}]
    [:> mui/Grid {:item true :xs 12 :md 6}
     [:> mui/CardActionArea {:component :a
                             :href (url-for :article :article-id (:id post))}
      [:> mui/Card {:class (.-card classes)}
       [:div {:class (.-cardDetails classes)}
        [:> mui/CardContent
         [:> mui/Typography {:component :h2 :variant :h5} (:title post)]
         [:> mui/Typography {:variant :subtitle1 :color :textSecondary} (:date post)]
         [:> mui/Typography {:variant :subtitle1 :paragraph true} (or (:description post)
                                                                      "No description.")]
         [:> mui/Typography {:variant :subtitle1 :color :primary} "Continue reading..."]]]
       [:> mui/Hidden {:xsDown true}
        [:> mui/CardMedia {:class (.-cardMedia classes)
                           :image (:featured_image post)
                           :title (:title post)}]]]]]))

(defn featured-post
  "Featured post component with custom styles."
  [post]
  [:> ((withStyles featured-post-styles)
        (r/reactify-component
         (featured-post-component {:post post})))])
