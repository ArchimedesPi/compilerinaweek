(ns zed.parser-test
  (:require [clojure.test :refer :all]
            [zed.parser :refer :all]))

(defn wrapped-primitive-exp
  [exp]
  [:program [:statement exp]])

(deftest parse-strings
  (testing "simple string"
    (is (= (wrapped-primitive-exp [:string "foo bar"])
           (parse-code "'foo bar';"))))

  (testing "funky unicode"
    (is (= (wrapped-primitive-exp [:string "foo♥bar…‴★ ✪ ✭ ✡ ⛤ ❉ ❄ ✿ɝ λ     Λάμ(β)δα lam(b)da 𝖋𝖔𝖔𝖇𝖆𝖗"])
           (parse-code "'foo♥bar…‴★ ✪ ✭ ✡ ⛤ ❉ ❄ ✿ɝ λ     Λάμ(β)δα lam(b)da 𝖋𝖔𝖔𝖇𝖆𝖗';")))))

(deftest parse-numbers
  (testing "integers"
    (is (= (wrapped-primitive-exp [:number 4])
           (parse-code "4;"))))
  (testing "really big numbers"
    (is (= (wrapped-primitive-exp [:number 10000000000000000111000000000000000000323400000000000000000267328100000000000000000000000000234000000001])
           (parse-code "10000000000000000111000000000000000000323400000000000000000267328100000000000000000000000000234000000001;"))))
  (testing "small numbers"
    (is (= (wrapped-primitive-exp [:number 0.2133458752378532789579237589327895723897984327985798327017820174837859269])
           (parse-code "0.2133458752378532789579237589327895723897984327985798327017820174837859269;")))))

