# jenkins - CI/CD
   1. Jenkins® is an open source automation server. With Jenkins, organizations can accelerate the software development process by automating it.
   2. Jenkins offers a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories.
   3. Written in Java programming language
   4. It helps automate the parts of software development related to building, testing, and deploying, facilitating continuous integration and continuous delivery.
   5. hudson    - old name of jenkins
    * jenkins is seperated from hudson still we can see some packages are as hudson.

## install jenkins in centos/Redhat
Jenkins is java based automation tool so we need java to work with jenkins.
```shell

#!bin/bash
yum install wget -y
sudo wget -O /etc/yum.repos.d/jenkins.repo \
    https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install java-11-openjdk -y
sudo yum install jenkins -y
sudo systemctl start jenkins
sudo systemctl enable jenkins
```

# install jenkins in Ubuntu/Debian
```shell
#install java 
sudo apt-get update
apt install openjdk-17-jdk -y
#install jenkins
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update
sudo apt-get install jenkins -y
sudo systemctl start jenkins
sudo systemctl enable jenkins
```
* Jenkins works in stages of SDLC, 
    * Pulling code from remote repository
    * pulled code need to build using Manven tool. Building nothing but creating artifact of pulled code files.
    * Created artifact must me tested for that testing stage, we are using sonarcube for artifact unit testing.
    * *pull, Build and testing this 3 stages combining called as continuous intigration*
    * After testing that code is ready for *delevery or deploy*. *this comes under the continuous delevery or continious deployment.*
    * we can set few more stages such as quality check as per requirements befor deploying.
* Jenkins support master slave architecture
    * java must be installed in all slave nodes as well.
## plugins we use
1. git              -- to integrate git
2. ssh build agent --- to create secure connection with nodes
3. pipeline         -- to create pipeline in jenkins
4. pipeline stages -- to view stage wise structure
5. sonarqube scanner -- to create pipeline sonarqube scanner
6. sonarqube server  -- to intigrate jenkins with sonarqube
6. gitlab build trigger - to integrate gitlab with jenkins, for auto build
7. seed jenkins        - it is used to create multiple jobs using single build
8. 
# SCM Tool
## Git
1. Git, github and gitlab are use to save and manage code as well as jenkins pipeline.

# Building Tool
Devloper Team has to select the building tool for the project building or creating artifact.
## Maven
*https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html*
Maven is a Java tool, so you must have Java installed in order to proceed.
Maven is  java based building tool in devops devlaped by apache, used to build java based application.
If you found POM.XML in project it means that project used maven tool for build process.
POM.XML is the  main configuration file.
## maven lifecycle
 maven lifecycle is collection of maven phases
1. default          -- 8 phases in default lifecycle.
    1. validate    -- validate source code.
    2. compile     -- compile means it check syntax errors in code.
    3. test        -- it test the source code.
    4. package     -- it  create package, archive or artifact.
    5. integration tool -- test the artifact
    6. verify      -- it verify all previous steps and gives good to go mark
    7. install     -- extraction of artifact means installing
    8. deploy      -- run on the one of platform
2. clean           -- clear the old builds
3. site            -- helps to create web documentation

## maven installation 
To install maven JAVA is  required.
```shell
 #Documentation for installation
link:- https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

sudo yum install java-11-openjdk -y             #we already installed for jenkins
# use latest version of maven always, old version will not work.
curl -O https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz  #maven package link
tar -xzf  apache-maven-3.9.5-bin.tar.gz  -C /opt/                    # unzip the maven package and #save it  in /opt
# to select mvn command path from /opt/apache/bin
export PATH=$PATH:/opt/apache-maven-3.9.6/bin


#if its not working then create alias in /etc/bashrc file
vim /etc/bashrc
alias mvn='/opt/apache-maven-3.9.5/bin/mvn'
source /etc/bashrc          # to update system
mvn --verison
```
## maven commands
```shell
maven clean         #it will clear old build 
maven package       # it create new build or package
maven clean package # it will do both cleaning old build and create new build of your project
```
# Testing tool
- we used sonarqube tool for testing our project source code after creating artifact in building stage
- SonarQube is an open-source platform developed by SonarSource.
- What is SonarQube used for?
SonarQube is a Code Quality Assurance tool that collects and analyzes source code, and provides reports for the code quality of your project. It combines static and dynamic analysis tools and enables quality to be measured continually over time.
- The SonarQube server can test and analyze 29 popular programming languages. With SonarQube, your developers can ensure source code quality and application security by identifying and rectifying code duplications and potential bugs.
- for sonarqube installation use this file 

 https://gitlab.com/SurajBele/jenkins/-/blob/c366677dcf769e3915ecad26a22ed24f006b3316/sonarqube_installation.md
    
## SonarQube Webhook
- Webhooks are configured for sending notification to a web address that an event has occurred. In SonarQube, webhooks are configured to send notification to third party services to notify them of the SonarQube code analysis completion. In this post we will discuss in detail why to configure a webhook and how to configure a webhook in SonarQube with Jenkins as the third party CI tool. 
- webhook configuration location ->Administration > Configurations > Webhooks 
- Configured the Webhook in SQ. project settings….. https://jenkins-****-****-****.****.***/sonarqube-webhook/

## Configure GitLab with Jenkins
- We are configuring Gitlab with jenkins to impliment automation in the building process.
- Whenever devloper push code in gitlab, gitlab build trigger plugin will initiate artifact building in jenkins, no need to build manualy.

