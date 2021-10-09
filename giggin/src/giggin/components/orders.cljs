(ns giggin.components.orders
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]))

(defn total []
  (->> @state/orders
       (map (fn [[id qty]] (* qty (get-in @state/gigs [id :price]))))
       (reduce +)
       format-price))

(defn orders []
  (let [remove-from-order #(swap! state/orders dissoc %)]
    [:aside
     (if (empty? @state/orders)
       [:div.empty
        [:div.title "You don't have any orders"]
        [:div.subtitle "Click on + to add an order"]]
       [:div.order
        [:div.body
         (doall (for [[id qty] @state/orders]
            [:div.item {:key id}
             [:div.img
              [:img {:src (get-in @state/gigs [id :img])
                     :alt (get-in @state/gigs [id :title])}]]
             [:div.content
              [:p.title (str (get-in @state/gigs [id :title]) " \u00D7 " qty)]]
             [:div.action
              [:div.price (format-price (* (get-in @state/gigs [id :price]) qty))]
              [:button.btn.btn--link.tooltip
               {:data-tooltip "Remove"
                :on-click #(remove-from-order id)}
               [:i.icon.icon--cross]]]]))]
        [:div.total
         [:hr]
         [:div.item
          [:div.content "Total"]
          [:div.action
           [:div.price (total)]]
          [:button.btn.btn--link.tooltip
           {:data-tooltip "Remove all"
            :on-click (fn [] (reset! state/orders {}))}
           [:i.icon.icon--delete]]]]])]))
