(ns marvel-api.core
  (:require digest))

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
