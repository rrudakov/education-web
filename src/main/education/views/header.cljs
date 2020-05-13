(ns education.views.header
  (:require [reagent.core :as r]
            ["@material-ui/core" :as mui]
            ["@material-ui/icons/Search" :default SearchIcon]
            ["@material-ui/core/styles" :refer [withStyles]]))

(defn header-styles
  "Define custom CSS for header."
  [theme]
  (clj->js
   {:toolbar          {:border-bottom (str "1px solid " (.. theme -palette -divider))}
    :toolbarTitle     {:flex 1}
    :toolbarSecondary {:justify-content :space-between
                       :overflow-x      :auto}
    :toolbarLink      {:padding     ((.. theme -spacing) 1)
                       :flex-shrink 0}}))

(def with-header-styles
  "Wrapper for element to use custom styles."
  (withStyles header-styles))

(defn header-component
  "Plain header component."
  [{:keys [sections title]}]
  (fn [{:keys [classes] :as props}]
    [:<>
     [:> mui/Toolbar {:class (.-toolbar classes)}
      [:> mui/Button {:size :small} "Subscribe"]
      [:> mui/Typography {:class     (.-toolbarTitle classes)
                          :component :h2
                          :variant   :h5
                          :color     :inherit
                          :align     :center
                          :noWrap    true} title]
      [:> mui/IconButton [:> SearchIcon]]
      [:> mui/Button {:variant :outlined
                      :size    :small} "Sign up"]]
     [:> mui/Toolbar {:class     (.-toolbarSecondary classes)
                      :component :nav
                      :variant   :dense}
      (for [section sections]
        ^{:key (:title section)}
        [:> mui/Link {:class   (.-toolbarLink classes)
                      :color   :inherit
                      :noWrap  true
                      :variant :body2
                      :href    (:url section)} (:title section)])]]))

(defn header
  "Header component with custom styles."
  [{:keys [title sections]}]
  [:> (with-header-styles
        (r/reactify-component
         (header-component {:sections sections
                            :title    title})))])
