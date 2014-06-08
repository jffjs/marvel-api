(ns marvel-api.core-test
  (:use midje.sweet
        [midje.util :only [testable-privates]])
  (:require [marvel-api.core :refer :all]))

(testable-privates marvel-api.core md5-hash timestamp)

(fact "`md5-hash` concats its arguments and applies md5 hash"
      (println )
     (md5-hash "foo") => "acbd18db4cc2f85cedef654fccc4a4d8"
     (md5-hash "foo" "bar") => "3858f62230ac3c915f300c664312c63f"
     (md5-hash "foo" "bar" "qaaz") => "014c410e8ca5ac3c30b695192ea3a3d3")

;; There's probably a better way I should do this
;; public-key and private-key get changed everytime test runs
;(with-state-changes [(before :facts (do (def tmp-public-key public-key)
                                        ;(def tmp-private-key private-key)))
                     ;(after :facts (do (def public-key tmp-public-key)
                                       ;(def private-key tmp-private-key)))]
  ;(fact "`set-credentials` stores public-key and private-key for later calls"
        ;(set-credentials "publickey" "privatekey")
        ;public-key => "publickey"
        ;private-key => "privatekey"))

(facts "`characters` constructs url representation of "
       (fact "collection"
             (characters) => "characters")
       (fact "single resource"
             (characters 12345) => "characters/12345")
       (fact "nested resource collections"
             (characters 12345 comics) => "characters/12345/comics"))

(facts "`comics` constructs url representation of"
       (fact "collection"
             (comics) => "comics")
       (fact "single resource"
             (comics 123) => "comics/123")
       (fact "nested resource collections"
             (comics 123 characters) = "comics/123/characters"))
