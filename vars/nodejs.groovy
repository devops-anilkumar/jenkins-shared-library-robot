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

// def sonarchecks() {
//     sh '''
//       sonar-scanner -Dsonar.host.url=http://172.31.0.15:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CREDENTIALS_USR} -Dsonar.password=${SONAR_CREDENTIALS_PSW}
//       curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh
//       bash -x quality-gate.sh ${SONAR_CREDENTIALS_USR} ${SONAR_CREDENTIALS_PSW} ${SONAR_URL} ${COMPONENT}
//      '''
// }
// call is the default function which will be called when you call the filename
def call() {
    pipeline {
        agent any
        environment { 
        SONAR_CREDENTIALS = credentials('SONAR')
       // NEXUS = credentials('NEXUS')
        SONAR_URL = "172.31.0.15"
        NEXUS_URL = "44.203.61.5"
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
                       env.ARGS="-Dsonar.sources=."
                       common.sonarchecks()
                    }
                }
            }

                     stage ('test cases'){
         parallel{
            stage ('unit testing'){
               steps{
                 // sh "npm test"
                  sh "echo perfoming unit testing"
               }
            }
            stage ('integrated testing'){
               steps{
                  // sh "npm verify"
                  sh "echo performing integrated testing"
               }
            }
            stage ('functional testing'){
               steps{
                 sh "echo performing functional testing"
                 }
             }
          }
       }
    //     stage('checking the artifacts version') {
    //    when { expression { env.TAG_NAME != null} }
    //        steps {
    //           script{
    //             env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl -L -s http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true')
    //             print UPLOAD_STATUS
    //           }
    //       }
    //    }
       stage('prepare the artifacts') {
       when { expression { env.TAG_NAME != null}
              //expression { env.UPLOAD_STATUS == "" }
        }
           steps {
               sh "npm install"
               sh "echo preparing the artifacts"
               sh "zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
          }
       }
       stage('publish the artifacts') {
       when { expression { env.TAG_NAME != null} }
           steps {
              sh "curl -f -v -u admin:password --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
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