(ns education.views.main
  (:require ["@material-ui/core" :as mui :refer [createMuiTheme ThemeProvider]]
            [education.subs.main :as subs]
            [education.views.article.add :refer [new-post-component]]
            [education.views.article.single :refer [single-article-component]]
            [education.views.footer :refer [footer]]
            [education.views.header :refer [header]]
            [education.views.home.main :refer [home-component]]
            [education.views.message :refer [error-message success-message]]
            [education.views.signup.main :refer [signup-dialog]]
            [re-frame.core :as rf]))

(def sections
  "Sections for main navigation."
  [{:title "Technology" :url "#"}
   {:title "Design"     :url "#"}
   {:title "Culture"    :url "#"}
   {:title "Business"   :url "#"}
   {:title "Politics"   :url "#"}
   {:title "Opinion"    :url "#"}])

(defn theme
  "Define custom Material-UI theme."
  []
  (createMuiTheme
   (clj->js
    {:palette
     {:type :light}})))

(defn- panels
  "Render panel depends on `panel-name`."
  [panel-name]
  (case panel-name
    :home [home-component]
    :article-index [:div]
    :article-add [new-post-component]
    :article [single-article-component]
    :not-found [:div]))

(defn main-panel
  "Main application component."
  []
  [:> ThemeProvider {:theme (theme)}
   [:> mui/CssBaseline]
   [:> mui/Container {:maxWidth :lg}
    [error-message]
    [success-message]
    [signup-dialog]
    [header {:title    "Education portal"
             :sections sections}]
    [panels @(rf/subscribe [::subs/active-panel])]]
   [footer
    {:description "Some description"
     :title       "Footer title"}]])
