(ns giggin.core
  (:require [reagent.dom :as rdom]
            [giggin.api :as api]
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
  (api/fetch-gigs)
  (rdom/render
    [app]
    (.getElementById js/document "app")))
