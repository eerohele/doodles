(set-env!
 :resource-paths #{"src" "html"}
 :dependencies '[[adzerk/boot-cljs            "2.1.4"          :scope "test"]
                 [adzerk/boot-cljs-repl       "0.3.3"          :scope "test"]
                 [adzerk/boot-reload          "0.5.2"          :scope "test"]
                 [pandeiro/boot-http          "0.8.3"          :scope "test"]
                 [org.clojure/clojure         "1.9.0-RC1"]
                 [org.clojure/clojurescript   "1.9.946"]
                 [http-kit                    "2.2.0"]
                 [com.cemerick/piggieback     "0.2.2"          :scope "test"]
                 [weasel                      "0.7.0"          :scope "test"]
                 [org.clojure/tools.nrepl     "0.2.12"         :scope "test"]
                 [onetom/boot-lein-generate   "0.1.3"          :scope "test"]
                 [reagent                     "0.8.0-alpha2"]])

(require
  '[boot.lein             :refer [generate]]
  '[adzerk.boot-cljs      :refer [cljs]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[adzerk.boot-reload    :refer [reload]]
  '[pandeiro.boot-http    :refer [serve]])

(generate)

(deftask testing
  []
  (merge-env! :resource-paths #{"test"})
  identity)


(deftask dev
  []
  (comp (serve :dir "target/")
        (watch)
        (speak)
        (reload :on-jsload 'app.core/main)
        (cljs-repl)
        (cljs :source-map true :optimizations :none)))

(deftask release
  []
  (comp (cljs :optimizations :advanced)
        (target :dir #{"docs"})))
