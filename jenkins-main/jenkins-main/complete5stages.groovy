pipeline {
    agent any
    stages {
        stage ('pull') {
            steps {
               git 'https://github.com/SurajBele/studentapp.ui.git'
               echo 'pull sucessful'
            }
         }
         
        stage ('build') {
            steps {
               sh '/opt/apache-maven-3.9.5/bin/mvn clean package'
               echo 'build sucessful'
            }
         }
        stage ('Test') {
            steps {
               withSonarQubeEnv( installationName:'sonar-server', credentialsId: 'sonar_token'){
                  sh '/opt/apache-maven-3.9.5/bin/mvn sonar:sonar -Dsonar.projectKey=studentapp'
                  echo ' Test sucessful'
               }
            }
         }
         stage("Quality Gate") {
               steps { 
                  waitForQualityGate abortPipeline: true
                  echo ' Quality Gate completed'
               }
            }
         stage ('deploy') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://54.221.43.201:8080')], contextPath: '/', war: '**/*.war'
                echo 'deploy completed'
                echo 'final deployment successfully done'
            }
         }
    }
}
