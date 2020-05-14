(ns education.views.main
  (:require ["@material-ui/core" :as mui :refer [createMuiTheme ThemeProvider]]
            [education.views.footer :refer [footer]]
            [education.views.header :refer [header]]
            [education.views.home :refer [home-component]]
            [re-frame.core :as rf]
            [education.subs.main :as subs]))

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

(defn theme
  "Define custom Material-UI theme."
  []
  (createMuiTheme
   (clj->js
    {:palette
     {:type :dark}})))

(defn- panels
  [panel-name]
  (case panel-name
    :home [home-component]
    :login [:div [:p "Login page"]]
    :article-index [:div]
    :article [:div]
    :not-found [:div]))

(defn main-panel
  "Main application component."
  []
  [:> ThemeProvider {:theme (theme)}
   [:> mui/CssBaseline]
   [:> mui/Container {:maxWidth :lg}
    [header {:title    "Blog"
             :sections sections}]
    [panels @(rf/subscribe [::subs/active-panel])]]
   [footer
    {:description "Some description"
     :title       "Footer title"}]])
