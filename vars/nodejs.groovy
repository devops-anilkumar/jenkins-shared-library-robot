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
// call is the default function which will be called when you call the filename
def call() {
    pipeline
         agent any
         stages{
            stage ('lintchecks') {
                steps {
                    script {
                       nodejs.lintchecks()
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