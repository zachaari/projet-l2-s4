# Définition des variables
JUNIT-JAR=junit-console.jar
MAIN=liv.FinalMain

# Compilation des sources
.PHONY: compile
compile:
	javac -sourcepath src src/tools/*.java -d classes
	javac -sourcepath src src/game/*.java -d classes
	javac -sourcepath src src/liv/*.java -d classes

# Génération de la documentation javadoc
.PHONY: doc
doc:
	javadoc -sourcepath src -subpackages tools game -d docs

# Compilation des tests
.PHONY: compile-test
compile-test:
	javac -classpath $(JUNIT-JAR):classes test/**/*.java


# Exécution du programme principal
run:
	java -classpath classes $(MAIN)

# Exécution des tests
.PHONY: test
test:
	java -jar $(JUNIT-JAR) -classpath test:classes -scan-classpath

# Exécution d'une classe de test spécifique
.PHONY: test-class
test-class:
	java -jar $(JAR) -classpath test:classes -select-class pack.ClassedeTest

# Création du fichier JAR exécutable
.PHONY: jar
jar:
	jar cvfe pack.jar $(MAIN) -C classes .

# Exécution du JAR
.PHONY: run-jar
run-jar:
	java -jar pack.jar

# Nettoyage des fichiers compilés
.PHONY: clean
clean:
	rm -rf classes/* docs/* pack.jar

.PHONNY: test-all
test-all: compile-test test