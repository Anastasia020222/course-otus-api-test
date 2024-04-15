#!/bin/bash

mvn clean test -Dapi.base.url=$BASE_API_URL -Dwiremock.url=$WIREMOCK_URL