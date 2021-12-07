pipeline {
    agent any
    tools {
        maven 'Maven 4.0.0'
        jdk 'JDK17'
    }
    stages {
        stage('Build') {
            steps{
                echo "JAVA_HOME = ${JAVA_HOME}"
                sh 'mvn clean install -Dwebdriver.chrome.driver=/var/jenkins_home/tools/chromedriver/chromedriver'
            }
            post {
                always {
                    junit 'target/surefire-report/**/*.xml'
                }
            }
        }
    }
}