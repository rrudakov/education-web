(ns education.views.article.add
  (:require ["@material-ui/core" :as mui]
            ["draft-js" :refer [convertFromRaw convertToRaw EditorState]]
            ["react-draft-wysiwyg" :refer [Editor]]
            [education.events.article :as article-events]
            [education.subs.article :as subs]
            [re-frame.core :as rf]
            [reagent.core :as r]))

(defn- editor
  "Post editor component."
  []
  [:> Editor {:editorState @(rf/subscribe [::subs/editor-state])
              :onEditorStateChange #(rf/dispatch [::article-events/set-editor-state %])}])

(defn- new-post-form
  "Form for new post settings."
  []
  [:> mui/FormGroup {:row true :style {:margin 8}}
   [:> mui/TextField
    {:id :new-post-title
     :label "Post title"
     :placeholder "Enter post title here..."
     :helperText "Required"
     :value @(rf/subscribe [::subs/new-article-title])
     :onChange #(rf/dispatch [::article-events/set-editor-title (-> % .-target .-value)])
     :fullWidth true
     :margin :normal
     :InputLabelProps {:shrink true}}]
   [:> mui/FormControlLabel
    {:label "Make main featured"
     :control (r/create-element
               mui/Checkbox
               (clj->js
                {:checked @(rf/subscribe [::subs/main-featured])
                 :onChange #(rf/dispatch [::article-events/toggle-main-featured])}))}]
   [:> mui/TextField
    {:id :new-post-main-featured-image
     :label "Main featured image"
     :placeholder "Paste image URL here..."
     :helperText "Required (this will appear only on main page)"
     :value @(rf/subscribe [::subs/new-article-main-featured-image])
     :onChange #(rf/dispatch [::article-events/set-new-article-main-featured-image (-> % .-target .-value)])
     :fullWidth true
     :margin :normal
     :InputLabelProps {:shrink true}}]
   [:> mui/TextField
    {:id :new-post-description
     :label "Post description"
     :placeholder "Put post description here..."
     :helperText "Optional (this will be rendered on featured cards)"
     :value @(rf/subscribe [::subs/new-article-description])
     :onChange #(rf/dispatch [::article-events/set-description (-> % .-target .-value)])
     :fullWidth true
     :margin :normal
     :InputLabelProps {:shrink true}}]])

(defn new-post-component
  "Main component for new post page."
  []
  [:main
   [:> mui/Typography {:variant :h3 :gutterBottom true} "New post"]
   [new-post-form]
   [editor]
   [:> mui/Button
    {:variant :outlined
     :size :small
     :style {:margin-bottom 8}
     :onClick #(rf/dispatch [::article-events/post-article])}
    "Save"]])
