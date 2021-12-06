pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'jdk17'
    }
    stages {
        stage('Build') {
            withEnv(['JAVA_HOME=/var/jenkins_home/tools/hudson.model.JDK/jdk17/jdk-17.0.1']) {
                steps {
                    echo "JAVA_HOME = ${JAVA_HOME}"
                    sh 'mvn clean install'
                }
                post {
                    always {
                        junit 'target/surefire-report/**/*.xml'
                    }
                }
            }
        }
    }
}