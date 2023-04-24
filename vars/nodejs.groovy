def lintchecks() {
    sh '''
            echo lint checks for ${COMPONENT}
            echo installing jslint
            # npm install jslint
            # ls -ltr node-modules/jslint/bin/
            # /home/centos/jslint/bin/jslint.js server.js
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed ${COMPONENT}
    '''
}

def sonarchecks() {
    sh '''
      sonar-scanner -Dsonar.host.url=http://172.31.0.15:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CREDENTIALS_USR} -Dsonar.password=${SONAR_CREDENTIALS_PSW}
      https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh
      bash -x quality-gate.sh ${SONAR_CREDENTIALS_PSW} ${SONAR_CREDENTIALS_USR} ${SONAR_URL} ${COMPONENT}
     '''
}
// call is the default function which will be called when you call the filename
def call() {
    pipeline {
        agent any
        environment { 
        SONAR_CREDENTIALS = credentials('SONAR')
        SONAR_URL = "172.31.0.15"
        }

        stages{
            stage ('lint checks') {
                steps {
                    script {
                        lintchecks()
                    }
                }
            }
            stage ('sonar checks') {
                steps {
                    script {
                        sonarchecks()
                    }
                }
            }
            stage ('performing npm install') {
            steps {
                sh "echo hai"
            }
            }
        }
    }
}

// @Library('roboshop-shared-library') _
// pipeline{
//     agent any
//      stages {
//                 stage ('performing the lint checks') {
//             steps{
//                 script {
//                     sample.info('sharedlibrary' , 'stage.google.com')
//                 }
//                    // sh "echo installing jslint"
//                    // sh "npm install jslint"
//                    // sh "ls -ltr node-modules/jslint/bin/"
//                    // sh "/home/centos/jslint/bin/jslint.js server.js"
//                 // sh "echo performing lint checks"
//                 // sh "echo performing lint checks completed"
//             }
//         }
//         stage ('downloading the dependencies') {
//             steps{
//                 sh "npm install"
//             }
//         }
//      }
// }