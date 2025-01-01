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
    }
}
