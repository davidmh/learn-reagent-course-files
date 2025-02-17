(ns giggin.components.header
  (:require [giggin.fb.auth :as auth]
            [giggin.state :as state]))

(defn header []
  [:header
   [:img.logo {:src "img/giggin-logo.svg" :alt "giggin logo"}]
   (if @state/user
     [:button.btn.btn--link.float--right.tooltip
      {:data-tooltip "Sign out"
       :on-click #(auth/sign-out)}
      [:figure.avatar
       [:img {:src (:photo-url @state/user)
              :alt (:display-name @state/user)}]]]
     [:button.btn.btn--link.float--right
      {:on-click #(auth/sign-in-with-google)}
      "Login"])])
