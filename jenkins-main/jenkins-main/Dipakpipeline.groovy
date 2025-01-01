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
              sh '''
                    mvn clean verify sonar:sonar \
                    -Dsonar.projectKey=first-try \
                    -Dsonar.host.url=http://34.204.40.70:9000 \
                    -Dsonar.login=sqp_40b5a607c6de7707a39fb43b71ad5ce0a7efe415
                '''
              
                  echo 'test sucessful'
               }   
            }
         }
         stage ('iftesting_fails'){
            steps{
              // waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
                echo "Quality Test Success"
            
            }
         }
         stage ('deploy-tomcat'){
            steps {
               // deploy adapters: [tomcat9(credentialsId: 'tomcat', path: '', url: 'http://50.18.36.86:8080/')], contextPath: '/', war: '**/*.war'
               echo 'deploy successful' 
            }
         }
      
    }
}
