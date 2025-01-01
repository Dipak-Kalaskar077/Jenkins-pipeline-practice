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
               sh 'mvn clean package'
               echo 'build sucessful'
            }
         }
         
         stage ('test') {
            steps {
               withSonarQubeEnv(installationName: 'sonar-server', credentialsId: 'sonar-token'){
                  sh 'mvn clean verify sonar:sonar \
                       -Dsonar.projectKey=myproject'
                  echo 'test sucessful'
               }   
            }
         }
         stage ('iftesting_fails'){
            steps{
               waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
            
            }
         }
         stage ('deploy-tomcat'){
            steps {
               deploy adapters: [tomcat9(credentialsId: 'tomcat', path: '', url: 'http://50.18.36.86:8080/')], contextPath: '/', war: '**/*.war'
               echo 'deploy successful' 
            }
         }
      
    }
}
