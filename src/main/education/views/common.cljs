(ns education.views.common
  (:require ["@material-ui/core" :as mui]))

(defn circular-progress
  "Display circular progress aligned horizontally and vertically."
  []
  [:> mui/Grid {:container true
                :justify :center
                :alignItems :center}
   [:> mui/CircularProgress]])
