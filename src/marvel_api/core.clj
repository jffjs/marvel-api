(ns marvel-api.core
  (:require digest [clj-http.client :as client]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn timestamp
  "Generates a timestamp for use in requests."
  []
  (System/currentTimeMillis))

(defn md5-hash
  "Concat arguments and hash using md5."
  [& args]
  (digest/md5 (apply str args)))

(def base-url "http://gateway.marvel.com/")

(defn get-comics
  [public-key private-key]
  (let [ts (timestamp)]
    (client/get (str base-url "v1/public/comics")
                {:query-params {"ts" ts
                                "hash" (md5-hash ts private-key public-key)
                                "apikey" public-key}})))
