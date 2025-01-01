pipeline {
agent any
stages {
    stage ('pull') { 
        steps {
            git 'https://github.com/SurajBele/studentapp.ui.git'
            echo "pull successful"    
        }
    }
    stage ('build') {
        steps {
           sh '/opt/apache-maven-3.9.6/bin/mvn clean package'
           echo "building successful"
        }
    }
    stage ('testing') {
        steps {
            withSonarQubeEnv( installationName: 'sonar-server', credentialsId: 'sonar-token') {
            sh '/opt/apache-maven-3.9.6/bin/mvn sonar:sonar   -Dsonar.projectKey=myproject'
            }
            echo "testing successful"
        }
    }
    stage ('quality_gate') {
        steps {
            timeout(time: 10, unit: 'SECONDS') 
            waitForQualityGate abortPipeline: false, credentialsId: 'sonar-token'
            echo "testing successful"
        }
    }
    stage ('deploy') {
        steps {
            deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://13.233.96.55:8080/')], contextPath: '/', war: '**/*.war'
            echo "deploy successful"
        }  
    }     
}
}