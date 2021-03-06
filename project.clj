(defproject
  com.degel/sodium "0.4.0-SNAPSHOT"
  :description "A wrapper around soda-ash and semantic-ui-react"
  :url "https://github.com/deg/sodium"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.908"]
                 [re-frame "0.10.1"]
                 [reagent "0.7.0"]
                 [soda-ash "0.4.0"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-doo "0.1.7"]]
  :cljsbuild
  {:builds
   [{:id "dev"
     :source-paths ["src"]
     :compiler {:pretty-print true}}
    {:id "test"
     :source-paths  ["src" "test"]
     :compiler {:main          sodium.runner
                :output-to     "resources/public/js/compiled/test.js"
                :output-dir    "resources/public/js/compiled/test/out"
                :optimizations :none}}]})
