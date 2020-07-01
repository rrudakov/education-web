(ns education.views.home.main-featured-post
  (:require ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [withStyles]]
            [education.routes :refer [url-for]]
            [education.views.common :refer [circular-progress]]
            [goog.object :as g]
            [reagent.core :as r]))

(defn- main-featured-post-styles
  "Define custom CSS for main featured post."
  [theme]
  (clj->js
   {:mainFeaturedPost
    {:position            :relative
     :background-color    (g/get (.. theme -palette -grey) "900")
     :color               (.. theme -palette -common -white)
     :margin-bottom       ((.. theme -spacing) 4)
     :background-image    "url(https://source.unsplash.com/random)"
     :background-size     :cover
     :background-repeat   :no-repeat
     :background-position :center}
    :overlay
    {:position         :absolute
     :top              0
     :bottom           0
     :right            0
     :left             0
     :background-color "rgba(0,0,0,.3)"}
    :mainFeaturedPostContent
    {:position :relative
     :padding  ((.. theme -spacing) 3)
     ((.. theme -breakpoints -up) "md")
     {:padding       ((.. theme -spacing) 6)
      :padding-right 0}}}))

(def with-main-featured-post-styles
  "Wrapper for element to use custom styles."
  (withStyles main-featured-post-styles))

(defn- main-featured-post-component
  "Plain main featured post component."
  [{:keys [post]}]
  (fn [{:keys [classes]}]
    (if (:fetching post)
      [circular-progress]
      [:> mui/Paper {:class (.-mainFeaturedPost classes)
                    :style {:background-image (str "url(" (:featured_image post) ")")}}
      [:div {:class (.-overlay classes)}]
      [:> mui/Grid {:container true}
       [:> mui/Grid {:item true :md 6}
        [:div {:class (.-mainFeaturedPostContent classes)}
         [:> mui/Typography {:component    :h1
                             :variant      :h3
                             :color        :inherit
                             :gutterBottom true} (:title post)]
         [:> mui/Typography {:variant   :h5
                             :color     :inherit
                             :paragraph true} (or (:description post)
                                                  "No description.")]
         [:> mui/Link {:variant :subtitle1
                       :href    (url-for :article :article-id (:id post))} "Continue reading..."]]]]])))

(defn main-featured-post
  "Main featured post component with custom styles."
  [post]
  [:> (with-main-featured-post-styles
        (r/reactify-component
         (main-featured-post-component {:post post})))])
