# What is SonarQube?
SonarQube is an open-source platform developed by SonarSource for continuous inspection of code quality to perform automatic reviews with static analysis of code to detect bugs and code smells on 29 programming languages.

# Why do I need to use SonarQube?
With SonarQube, developers can ensure source code quality and application security by identifying and rectifying code duplication and potential bugs. Take a closer look at how the Sonar scanner ensures code reliability, detects bugs, and alerts developers to other source code issues.

# Download and Install
1. Install OpenJDK 17
```shell 
    sudo apt-get update
    sudo apt-get install openjdk-17-jdk -y 
```
2. Add the PostgreSQL repository

```shell 
sudo sh -c 'echo “deb http://apt.postgresql.org/pub/repos/apt/ `lsb_release -cs`-pgdg main” /etc/apt/sources.list.d/pgdg.list' 
```

3. Install PostgreSQL

```shell 
sudo apt install postgresql postgresql-contrib -y 
```

4. Enable the database server to start automatically on reboot

```shell 
sudo systemctl enable postgresql 
```

5. Start the database server

```shell 
sudo systemctl start postgresql
```

6. Change the default PostgreSQL password

```shell 
sudo passwd postgres
```

7. Switch to the postgres user

```shell 
su - postgres
```

8. Create a user sonar

```shell
createuser sonar
```
9. Log in to PostgreSQL

```shell 
psql 
```

10. Set a password for the sonar user

```shell 
ALTER USER sonar WITH ENCRYPTED password 'yourPassword';
```

11. Create a sonarqube database and set the owner to sonar

```shell 
CREATE DATABASE sonarqube OWNER sonar;
```

12. Grant all the privileges on the sonarqube database to the sonar user

```shell
 GRANT ALL PRIVILEGES ON DATABASE sonarqube to sonar;
 ```

13. Exit PostgreSQL

```shell 
\q 
```

14. Return to your non-root sudo user account

```shell
 exit 
 ```

15. Install the zip utility.

```shell
 sudo apt-get install zip -y
 ```

16. Download the SonarQube distribution files. I am using the version 9.9 LTS.

```shell 
sudo wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-9.9.3.79811.zip
```

17. Unzip the downloaded file

```shell 
sudo unzip sonarqube-9.9.3.79811.zip
```

18. Move the unzipped files to /opt/sonarqube directory

```shell 
sudo mv sonarqube-9.9.3.79811 /opt/sonarqube
```

19. Create a sonar user

```shell 
sudo useradd sonar
```

20. Create a sonar user and set /opt/sonarqube as the home directory

sudo useradd -d /opt/sonarqube -g sonar sonar
21. Grant the sonar user access to the /opt/sonarqube directory

```shell 
sudo chown sonar:sonar /opt/sonarqube -R
```

22. Configure SonarQube. Edit the SonarQube configuration file

```shell 
sudo nano /opt/sonarqube/conf/sonar.properties
```

* Find the following lines and uncomment it:

#sonar.jdbc.username=
#sonar.jdbc.password=
* Add the database user and password you created in Step 10

```shell 
sonar.jdbc.username=sonar
sonar.jdbc.password=yourPassword 
``` 

Below those two lines, add this:

```shell 
sonar.jdbc.url=jdbc:postgresql://localhost:5432/sonarqube 
```

Save and exit the file.

## Setup systemd service

Create a systemd service file to start SonarQube at system boot.

```shell
sudo nano /etc/systemd/system/sonar.service

#Paste the following lines to the file

[Unit]
Description=SonarQube service
After=syslog.target network.target

[Service]
Type=forking
User=sonar
Group=sonar
ExecStart=/opt/sonarqube/bin/linux-x86-64/sonar.sh start
ExecStop=/opt/sonarqube/bin/linux-x86-64/sonar.sh stop
StandardOutput=journal
LimitNOFILE=131072
LimitNPROC=8192
TimeoutStartSec=5
Restart=always
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```
24. Start & Enable the SonarQube service to run at system startup
```shell
sudo systemctl start sonar
sudo systemctl enable sonar
sudo systemctl status sonar
```
25. Modify Kernel System Limits

SonarQube uses Elasticsearch to store its indices in an MMap FS directory. It requires some changes to the system defaults.

Edit the sysctl configuration file
```shell
sudo nano /etc/sysctl.conf
#Add the following lines. Save and exit

vm.max_map_count=262144
fs.file-max=65536
ulimit -n 131072
ulimit -u 8192
```
Reboot the system to apply the changes

sudo reboot
26. Access SonarQube Web Interface

Access SonarQube in a web browser at your server’s IP address on port 9000. For example:

http://<yourIP>:9000
Log in with username admin and password admin

SonarQube will prompt you to change your password