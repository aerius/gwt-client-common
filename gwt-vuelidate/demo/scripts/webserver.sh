cd ../..
mkdir -p target/gwt/launcherDir/

mvn jetty:run -pl :gwt-vuelidate-demo-server -Denv=dev -Djetty.http.port=8000 -Pdemo
