#!/bin/sh

### Check if the java command is available ###

if [ -z "$(command -v java)" ]; then
    echo "Error! Java is not installed or the "Java" command is not available"
    exit 1
fi

### Search for bot jar ###

JAR=$(ls build/libs/Chesnay-bot-*-all.jar | tail -n 1)
### Execute it or print an error message ###

if [ ! -z "$JAR" ]; then
    java -jar $JAR
else
    echo "JAR not found in the usual path, do you want to search the repo for the JAR? (y/n"
    select yn in "Yes" "No"; do
    	case $yn in
        	Yes ) find . -name "Chesnay-bot-*-all.jar"; break;;
        	No ) exit;;
    	esac
    done

    echo "Error! A Chesnay-bot JAR was not found."
fi

# TODO: Restarting the bot automatically
