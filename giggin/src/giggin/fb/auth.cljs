(ns giggin.fb.auth
  (:require ["firebase/auth" :as firebase]
            [giggin.fb.db :refer [db-save!]]
            [giggin.state :as state]))

(defn sign-in-with-google []
  (firebase/signInWithPopup (firebase/getAuth) (firebase/GoogleAuthProvider.)))

(defn sign-out []
  (firebase/signOut (firebase/getAuth)))

(defn on-auth-state-change []
  (firebase/onAuthStateChanged
    (firebase/getAuth)
    (fn [^js/firebase.auth.User user]
      (if user
        (let [uid (.-uid user)
              display-name (.-displayName user)
              photo-url (.-photoURL user)
              email (.-email user)]
          (reset! state/user {:uid uid
                              :display-name display-name
                              :photo-url photo-url
                              :email email})
          (db-save!
            ["users" uid "profile"]
            #js {:uid uid
                 :display-name display-name
                 :photo-url photo-url
                 :email email}))
        (reset! state/user nil)))))
