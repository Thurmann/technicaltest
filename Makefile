run: $(file)
	java -jar technicaltest-main/target/technicaltest-main-1.0-SNAPSHOT-jar-with-dependencies.jar $(file)

runtest: 
	java -jar technicaltest-main/target/technicaltest-main-1.0-SNAPSHOT-jar-with-dependencies.jar technicaltest-service/src/test/resources/cardgame/blackjacktest.txt

package:
	mvn clean install -Dpackaging=true