(ns giggin.helpers)

(def currency
  (js/Intl.NumberFormat "en-US" #js {:style "currency"
                                     :currency "USD"}))

(defn format-price [cents]
  (currency.format (/ cents 100)))
