(ns education.views.main
  (:require ["@material-ui/core" :as mui :refer [createMuiTheme ThemeProvider]]
            ["react-draft-wysiwyg" :refer [Editor]]
            ["draft-js" :refer [EditorState convertFromRaw convertToRaw]]
            [education.subs.main :as subs]
            [education.views.error-message :refer [error-message]]
            [education.views.footer :refer [footer]]
            [education.views.header :refer [header]]
            [education.views.home.main :refer [home-component]]
            [education.views.signup.main :refer [signup-dialog]]
            [re-frame.core :as rf]
            [reagent.core :as r]))

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
    :article-index [:div]
    :article [:div]
    :not-found [:div]))

(def editorState (r/atom (.createEmpty EditorState)))
(def content (r/atom (.stringify js/JSON (convertToRaw (.getCurrentContent (.createEmpty EditorState))))))

(defn readonly-editor
  []
  [:div
   [:> Editor {:editorState (.createWithContent EditorState (convertFromRaw (.parse js/JSON @content)))
               :toolbarHidden true
               :readOnly true}]])

(defn editor
  []
  [:> Editor {:editorState @editorState
              :onChange #(reset! content (.stringify js/JSON (convertToRaw (.getCurrentContent @editorState))))
              :onEditorStateChange #(reset! editorState %)}])

(defn main-panel
  "Main application component."
  []
  [:> ThemeProvider {:theme (theme)}
   [:> mui/CssBaseline]
   [:> mui/Container {:maxWidth :lg}
    [error-message]
    [signup-dialog]
    ;; [readonly-editor]
    [header {:title    "Blog"
             :sections sections}]
    [panels @(rf/subscribe [::subs/active-panel])]
    [editor]]
   [footer
    {:description "Some description"
     :title       "Footer title"}]])
