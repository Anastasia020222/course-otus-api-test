timeout(60) {
    node('maven-slave') {

        wrap([$class: 'BuildUser']) {
            currentBuild.description = """
                build user : $BUILD_USER
                branch : $BRANCH
            """

            params = readYaml text: env.YAML_CONFIG ?: null
            if (params != null) {
                for (param in params.entrySet()) {
                    env.setProperty(param.getKey(), param.getValue())
                }
            }
        }

        stage("Checkout") {
            checkout scm
        }

        stage("Create configurations") {
            sh "echo BASE_API_URL=${env.getProperty('BASE_API_URL')} > ./.env"
            sh "echo WIREMOCK_URL=${env.getProperty('WIREMOCK_URL')} >> ./.env"
        }

        stage("Build Docker image") {
            // Сборка Docker-образа
            sh "docker build -t api_tests:1.0.0 ."
        }

        try {
            stage("Run UI tests") {
                sh "pwd"
                sh("rm -rf /root/api-allure/*")
                sh "docker run --rm --env-file ./.env -v m2:/root/.m2 -v api-allure:/home/unixuser/api_tests/allure-results -t api_tests:1.0.0"
            }
        }
        finally {
            stage("Publication of the allure report") {
                sh "pwd"
                sh("mkdir ./allure-results")
                sh("cp /root/api-allure/* ./allure-results/")
                generateAllure()
            }
        }
    }
}

def generateAllure() {
    allure([
            includeProperties: true,
            jdk              : '',
            properties       : [],
            reportBuildPolicy: 'ALWAYS',
            results          : [[path: './allure-results']]
    ])
}
