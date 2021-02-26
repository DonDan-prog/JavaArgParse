@javac ArgumentParser/*.java
@echo Compiled classes
@jar -cf ArgumentParser.jar  ArgumentParser/*.class
@echo Making jar successful
@cd ArgumentParser
@del *.class
@cd ..
@echo deleting the temprary classes
@pause