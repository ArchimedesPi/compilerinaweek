(ns zed.core
  (:require [zed.repl :as repl]))

(defn -main [& args]
  (repl/repl))

