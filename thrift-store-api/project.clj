(defproject thrift-store "0.1.0-SNAPSHOT"
  :description "A simple thrift store manager - to learn FP with Clojure"
  :url "https://github.com/guilhermedjr/thrift-store" 
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [ring "1.9.0"]
                 [metosin/reitit "0.5.12"]
                 [metosin/muuntaja "0.6.8"]
                 [prismatic/schema "1.4.1"]]
  :main thrift-store.core
  :target-path "target/%s"
  :test-paths ["test" "src/thrift_store/entities"]
  :test-selectors {:default (complement :integration)
                   :integration :integration}
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:main thrift-store.core/-dev-main 
                   :dependencies [[org.clojure/tools.logging "1.2.4"]] }})