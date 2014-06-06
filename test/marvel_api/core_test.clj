(ns marvel-api.core-test
  (:use midje.sweet)
  (:require [marvel-api.core :refer :all]))

(facts "`md5-hash`"
       (fact "it concats it's arguments and applies md5 hash"
             (md5-hash "foo") => "acbd18db4cc2f85cedef654fccc4a4d8"
             (md5-hash "foo" "bar") => "3858f62230ac3c915f300c664312c63f"
             (md5-hash "foo" "bar" "qaaz") => "014c410e8ca5ac3c30b695192ea3a3d3"))
