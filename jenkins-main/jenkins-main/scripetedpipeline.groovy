node {  
     stage('pull') { 
        echo 'pull successful'
    }
    stage('Build') { 
         echo 'build successful'
    }
    stage('Test') { 
         echo 'test successful'
    }
    stage('Deploy') { 
        echo 'deploy successful' 
    }
}