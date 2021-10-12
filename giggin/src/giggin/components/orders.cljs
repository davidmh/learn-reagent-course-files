(ns giggin.components.orders
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]
            [giggin.components.checkout-modal :refer [checkout-modal]]))

(defn total []
  (->> @state/orders
       (filter (fn [[id]] (not (get-in @state/gigs [id :sold-out]))))
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
         (doall
           (for [[id qty] @state/orders]
             (let [gig (id @state/gigs)]
               [:div.item {:key id}
                [:div.img
                 [:img {:src (:img gig)
                        :alt (:title gig)}]]
                [:div.content
                 (if (:sold-out gig)
                   [:p.sold-out "Sold out"]
                   [:p.title (:title gig) " \u00D7 " qty])]
                [:div.action
                 [:div.price (format-price (* (:price gig) qty))]
                 [:button.btn.btn--link.tooltip
                  {:data-tooltip "Remove"
                   :on-click #(remove-from-order id)}
                  [:i.icon.icon--cross]]]])
             ))]
        [:div.total
         [:hr]
         [:div.item
          [:div.content "Total"]
          [:div.action
           [:div.price (total)]]
          [:button.btn.btn--link.tooltip
           {:data-tooltip "Remove all"
            :on-click (fn [] (reset! state/orders {}))}
           [:i.icon.icon--delete]]]
         [checkout-modal]]])]))
