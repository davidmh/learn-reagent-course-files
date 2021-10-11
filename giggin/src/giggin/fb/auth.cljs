(ns giggin.fb.auth
  (:require ["firebase/auth" :as firebase]
            [giggin.fb.db :refer [db-save!]]))

(defn sign-in-with-google []
  (firebase/signInWithPopup (firebase/getAuth) (firebase/GoogleAuthProvider.)))

(defn sign-out []
  (firebase/signOut (firebase/getAuth)))

(defn on-auth-state-change []
  (firebase/onAuthStateChanged
    (firebase/getAuth)
    (fn [^js/firebase.auth.User user]
      (when user
        (let [uid (.-uid user)
              display-name (.-displayName user)
              photo-url (.-photoURL user)
              email (.-email user)]
          (db-save!
            ["users" uid "profile"]
            #js {:photo-url photo-url
                 :display-name display-name
                 :email email}))))))
