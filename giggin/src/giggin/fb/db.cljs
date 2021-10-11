(ns giggin.fb.db
  (:require ["firebase/database" :refer (getDatabase ref set onValue)]
            [clojure.string :as str]
            [giggin.state :as state]))

(defn db-ref [path]
  (let [db (getDatabase)]
    (ref db (str/join "/" path))))

(defn db-save! [path value]
  (set (db-ref path) value))

; From: https://firebase.google.com/docs/database/web/read-and-write#web-version-9_2
;
; const db = getDatabase();
; const starCountRef = ref(db, 'posts/' + postId + '/starCount');
; onValue(starCountRef, (snapshot) => {
;   const data = snapshot.val();
;   updateStarCount(postElement, data);
; });

(defn db-subscribe [path]
  (onValue (db-ref path)
           (fn [^js/firebase.database.DataSnapshot snapshot]
             (reset! state/gigs (js->clj (.val snapshot) :keywordize-keys true)))))
