pipeline {
    agent any
    stages {
        stage ('pull') {
            steps {
               
               echo 'pull sucessful'
            }
         }
         stage ('build') {
            steps {
               
               echo 'build sucessful'
            }
         }
          stage ('test') {
            steps {
               echo 'test completed'
            }
         }
          stage ('deploy') {
            steps {
               echo 'deploy completed'
            }
         }
    }
}
