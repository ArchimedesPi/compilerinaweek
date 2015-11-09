(ns zed.parser
  (:require [instaparse.core :as insta]))

(def parser
  "parse some Zed code"
  (insta/parser
   (clojure.java.io/resource "zed.ebnf")))

(def rawop->operator
  {"+" :addition
   "-" :subtraction
   "*" :multiplication
   "/" :division
   "^" :exponent
   "|>" :datastream})

(defn transform-ast
  [ast]
  (insta/transform {:number (fn [number] (read-string number))
                    :string identity
                    :expression identity ; pourthrough for expressions
                    :operator rawop->operator}
                   ast))

(defn parse-code
  [code]
  (->> code
       parser
       transform-ast))

