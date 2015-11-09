(ns zed.repl
  (:require [zed.parser :as parser]
            [instaparse.core :as insta]
            [clojure.string :as string]
            [puget.printer :as puget]))

(def result (atom []))

(defn- special-commands [input]
  (cond
    (= (string/lower-case input) "/quit") (System/exit 0)
    (= (string/lower-case input) "/viz") (insta/visualize @result)
    (= (string/lower-case input) "*1") (println @result)
    :else input))

(defn repl []
  (do (print "» ")
      (flush))
  (let [input (read-line)]
    (if (not (= nil (special-commands input)))
      (->> input
           parser/parse-code
           (reset! result)
           puget/cprint
           ))
    (recur)))
