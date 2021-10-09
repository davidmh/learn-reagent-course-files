(ns giggin.fb.init
  (:require ["firebase/app" :as firebase]
            ["firebase/database"]
            ["firebase/auth"]))

(defn firebase-init []
  (firebase/initializeApp
    {:apiKey "AIzaSyCmwEfVFEPL5DYg7T2WQEA3z7VX7ogtVL0"
     :authDomain "reminder-325705.firebaseapp.com"
     :projectId "reminder-325705"
     :appId "1:980874332000:web:2548347ddc7b5cffef4005"
     :measurementId "G-0GEE6NGF9Y"}))
