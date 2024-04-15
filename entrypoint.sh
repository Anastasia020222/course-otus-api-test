#!/bin/bash

mvn clean test -Dbase.url=$BASE_URL -Dbrowser=$BROWSER -DversionBrowser=$VERSION_BROWSER -Dremote=$REMOTE