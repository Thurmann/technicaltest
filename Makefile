run: $(file)
	java -jar technicaltest-main/target/technicaltest-main-1.0-SNAPSHOT-jar-with-dependencies.jar $(file)

runexample: 
	java -jar technicaltest-main/target/technicaltest-main-1.0-SNAPSHOT-jar-with-dependencies.jar technicaltest-service/src/test/resources/cardgame/example.txt

package:
	mvn clean install -Dpackaging=true