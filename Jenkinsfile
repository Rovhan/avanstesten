pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
    }
    stages {
        stage('Build') {
            steps{
                sh 'mvn -Dmaven.test.failure.ignore=true site'
            }
            post {
                always {
                    junit 'target/surefire-report/**/*.xml'
                }
            }
        }
    }
}