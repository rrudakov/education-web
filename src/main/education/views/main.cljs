(ns education.views.main
  (:require ["@material-ui/core" :as mui :refer [createMuiTheme ThemeProvider]]
            [education.views.featured-post :refer [featured-post]]
            [education.views.footer :refer [footer]]
            [education.views.header :refer [header]]
            [education.views.main-featured-post :refer [main-featured-post]]))

(def sections
  "Sections for main navigation."
  [{:title "Technology" :url "#"}
   {:title "Design"     :url "#"}
   {:title "Culture"    :url "#"}
   {:title "Business"   :url "#"}
   {:title "Politics"   :url "#"}
   {:title "Opinion"    :url "#"}
   {:title "Science"    :url "#"}
   {:title "Health"     :url "#"}
   {:title "Style"      :url "#"}
   {:title "Travel"     :url "#"}])


(def main-featured-post-item
  "Main featured post"
  {:title "Main featured post"
   :description "Long description for main featured posts."
   :image "https://source.unsplash.com/random"
   :image-text "main image description"
   :link-text "Continue reading..."})

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

(defn theme
  "Define custom Material-UI theme."
  []
  (createMuiTheme
   (clj->js
    {:palette
     {:type :dark}})))

(defn main-panel
  "Main application component."
  []
  [:> ThemeProvider {:theme (theme)}
   [:> mui/CssBaseline]
   [:> mui/Container {:maxWidth :lg}
    [header {:title    "Blog"
             :sections sections}]
    [:main
     [main-featured-post main-featured-post-item]
     [:> mui/Grid {:container true
                   :spacing   4}
      (for [post featured-posts]
        ^{:key (:title post)}
        [featured-post post])]
     [:> mui/Grid {:container true
                   :spacing   5}
      [:> mui/Grid {:item true :xs 12 :md 6}
       [:> mui/Typography {:variant :h6 :gutterBottom true} "From the firehouse..."]]]]]
   [footer
    {:description "Some description"
     :title       "Footer title"}]])
