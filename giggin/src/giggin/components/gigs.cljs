(ns giggin.components.gigs
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]
            [giggin.components.gig-editor :refer [gig-editor]]
            [reagent.core :as r]
            [clojure.string :as str]))

(defn gigs []
  (let [modal (r/atom false)
        values (r/atom {:id nil
                        :title ""
                        :desc ""
                        :img ""
                        :price 0
                        :sold-out false})
        add-to-order #(swap! state/orders update % inc)
        insert-gig (fn [{:keys [id title desc price img sold-out]}]
                     (swap! state/gigs assoc id {:id (str "gig-" (random-uuid))
                                                 :title (str/trim title)
                                                 :desc (str/trim desc)
                                                 :img (str/trim img)
                                                 :price (js/parseInt price 10)
                                                 :sold-out sold-out})
                     (reset! modal false))]
    [:main
     [:div.gigs
      [:button.add-gig
       {:on-click #(reset! modal true)}
       [:div.add__title
        [:i.icon.icon--plus]
        [:p "Add gig"]]]
      [gig-editor modal values insert-gig]
      (for [{:keys [id img title price description]} (vals @state/gigs)]
        [:div.gig {:key id}
         [:img.gig__artwork {:src img :alt title}]
         [:div.gig__body
          [:div.gig__title
           [:button.btn.btn--primary.float--right.tooltip
            {:data-tooltip "Add to order"
             :on-click #(add-to-order id)}
            [:i.icon.icon--plus]] title]
          [:p.gig__price (format-price price)]
          [:p.gig__description description]]])]]))
