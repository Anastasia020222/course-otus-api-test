FROM maven:3.9.0

RUN mkdir -p /home/unixuser/api_tests

WORKDIR /home/unixuser/api_tests

COPY . /home/unixuser/api_tests

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]