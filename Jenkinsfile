pipeline {
    agent any

    tools {
        maven 'Maven'   // configure in Jenkins
        jdk 'Java17'    // configure in Jenkins
    }

    environment {
        HEADLESS = 'true'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git 'https://github.com/YOUR_USERNAME/automation-framework.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test -Dheadless=true'
            }
        }

        stage('Publish TestNG Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'reports/**', fingerprint: true
                archiveArtifacts artifacts: 'screenshots/**', fingerprint: true
            }
        }
    }
}