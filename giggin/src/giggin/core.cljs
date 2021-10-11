(ns giggin.core
  (:require [reagent.dom :as rdom]
            [giggin.fb.init :refer [firebase-init]]
            [giggin.fb.db :refer [db-subscribe]]
            [giggin.components.header :refer [header]]
            [giggin.components.gigs :refer [gigs]]
            [giggin.components.orders :refer [orders]]
            [giggin.components.footer :refer [footer]]))

(defn app
  []
  [:div.container
   [header]
   [gigs]
   [orders]
   [footer]])

(defn ^:export main
  []
  (rdom/render
    [app]
    (.getElementById js/document "app"))
  (firebase-init)
  (db-subscribe ["gigs"]))
