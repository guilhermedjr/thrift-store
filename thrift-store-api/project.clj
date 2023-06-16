(defproject thrift-store "0.1.0-SNAPSHOT"
  :description "A simple thrift store manager - to learn FP with Clojure"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring "1.9.0"]
                 [metosin/reitit "0.5.12"]
                 [metosin/muuntaja "0.6.8"]]
  :main ^:skip-aot thrift-store.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
