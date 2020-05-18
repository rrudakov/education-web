(ns education.views.header
  (:require ["@material-ui/core" :as mui]
            ["@material-ui/core/styles" :refer [withStyles]]
            ["@material-ui/icons/Search" :default SearchIcon]
            [education.events.signup :as signup-events]
            [education.routes :refer [url-for]]
            [re-frame.core :as rf]
            [reagent.core :as r]
            [education.subs.signup :as subs]))

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

(defn auth-button
  []
  (if @(rf/subscribe [::subs/logged-in?])
    [:> mui/Button {:variant :outlined
                    :size    :small
                    :onClick #(rf/dispatch [::signup-events/logout])} "Logout"]
    [:> mui/Button {:variant :outlined
                    :size    :small
                    :onClick #(rf/dispatch [::signup-events/open-signup])} "Sign in"]))

(defn new-post-button
  []
  (when @(rf/subscribe [::subs/logged-in?])
    [:> mui/Button {:style {:margin-right 10}
                    :variant :outlined
                    :size :small
                    :component :a
                    :href (url-for :article-add)} "New post"]))

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
      [new-post-button]
      [auth-button]]
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
