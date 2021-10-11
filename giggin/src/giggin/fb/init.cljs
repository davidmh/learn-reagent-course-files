(ns giggin.fb.init
  (:require ["firebase/app" :as firebase]
            ["firebase/database"]
            ["firebase/auth"]
            [giggin.fb.auth :refer [on-auth-state-change]]))

(defn firebase-init []
  (if (zero? (alength (firebase/getApps)))
    (firebase/initializeApp
     #js {:apiKey "AIzaSyCmwEfVFEPL5DYg7T2WQEA3z7VX7ogtVL0"
          :authDomain "reminder-325705.firebaseapp.com"
          :databaseURL "https://reminder-325705-default-rtdb.firebaseio.com"
          :projectId "reminder-325705"
          :appId "1:980874332000:web:2548347ddc7b5cffef4005"
          :measurementId "G-0GEE6NGF9Y"})
    (firebase/getApp))
  (on-auth-state-change))
