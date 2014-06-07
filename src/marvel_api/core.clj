(ns marvel-api.core
  (:require digest [clj-http.client :as client]))

(declare public-key private-key)
(def base-url "http://gateway.marvel.com/")

(defn- timestamp
  "Generates a timestamp for use in requests."
  []
  (System/currentTimeMillis))

(defn- md5-hash
  "Concat arguments and hash using md5."
  [& args]
  (digest/md5 (apply str args)))

(defn- get-response
  [method params public-key private-key]
  (let [ts (timestamp)]
      (client/get (str base-url "v1/public/" (name method))
                  {:as :json
                   :query-params (merge params {"ts" ts
                                                "hash" (md5-hash ts private-key public-key)
                                                "apikey" public-key})})))
(defn set-credentials
  [public-key private-key]
  (do
    (def public-key public-key)
    (def private-key private-key)))

(defn get-comics
  [params public-key private-key]
  (get-response :comics params public-key private-key))

(defn lazy-comics
  ([]
   (lazy-comics {} public-key private-key))
  ([params]
   (lazy-comics params public-key private-key))
  ([params public-key private-key]
    (lazy-seq
      (let [data (get-in (get-response :comics (merge params {:limit 100}) public-key private-key) [:body :data])
          s (:results data)]
        (if-not (empty? s)
          (concat s (lazy-comics (assoc params :offset (+ (:offset data) (:limit data))) public-key private-key)))))))

(defn get-characters
  [public-key private-key]
  (get-response :characters public-key private-key))
