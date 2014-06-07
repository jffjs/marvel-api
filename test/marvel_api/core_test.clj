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

(fact "`set-credentials` stores public-key and private-key for later calls"
      (set-credentials "publickey" "privatekey")
      public-key => "publickey"
      private-key => "privatekey")
