def lintchecks(COMPONENT) {
    sh '''
                   sh "echo installing jslint"
                   sh "npm install jslint"
                   sh "ls -ltr node-modules/jslint/bin/"
                   sh "/home/centos/jslint/bin/jslint.js server.js"
                   sh "echo performing lint checks"
                   sh "echo performing lint checks completed"

    '''
}
// call is the default function which will be called when you call the filename
def call(COMPONENT) {
    pipeline
         agentany
         stages{
            stage ('lintchecks') {
                steps {
                    script {
                       nodejs.lintchecks(COMPONENT)
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