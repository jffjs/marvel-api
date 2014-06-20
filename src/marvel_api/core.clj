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
  [url params public-key private-key]
  (let [ts (timestamp)]
      (client/get (str base-url "v1/public/" url)
                  {:as :json
                   :query-params (merge params {"ts" ts
                                                "hash" (md5-hash ts private-key public-key)
                                                "apikey" public-key})})))
(defn set-credentials
  [public-key private-key]
  (do
    (def public-key public-key)
    (def private-key private-key)))

(defn get-resource
  ([url]
   (get-resource url {} public-key private-key))
  ([url params]
   (get-resource url params public-key private-key))
  ([url params public-key private-key]
    (get-in (get-response url params public-key private-key) [:body :data])))

(defn resource-fn
  ([url]
   (resource-fn url {} public-key private-key))
  ([url params]
   (resource-fn url params public-key private-key))
  ([url params public-key private-key]
   (fn [new-params]
     (get-resource url (merge params new-params) public-key private-key))))

(defn lazy-collection
  "Returns a lazy seq for the given resource collection"
  ([rsc-fn]
   (lazy-collection rsc-fn nil))
  ([rsc-fn params]
   (lazy-seq
    (let [data (rsc-fn params) s (:results data)]
      (if-not (empty? s)
        (concat s (lazy-collection rsc-fn {:offset (+ (:offset data) (:limit data))})))))))

(defn characters
  "Constructs url for Characters resource"
  ([]
   "characters")
  ([id]
   (str (characters) "/" id))
  ([id nested-rsc]
   (str (characters id) "/" (nested-rsc))))

(defn comics
  "Constructs url for Comics resource"
  ([]
   "comics")
  ([id]
   (str (comics) "/" id))
  ([id nested-rsc]
   (str (comics id) "/" (nested-rsc))))

(defn resource
  ([url]
   (resource url {}))
  ([url params]
   ((resource-fn url) params)))
