pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'jdk_17.0.1'
    }
    stages {
        stage('Build') {
            steps{
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