
def call() {
      node {
          env.APPTYPE ="python"
          common.lintchecks()
          env.ARGS="-Dsonar.sources=."
          common.sonarchecks()
          common.testcases()
      }
}


//********* UNCOMMENT IT WHEN YOU USE DECLERATIVE PIPELINE ***********

// def lintchecks() {
//     sh '''
//             echo lint checks for ${COMPONENT}
//             #pylint *.py    
//             echo performing lint checks for ${COMPONENT}
//             echo performing lint checks completed ${COMPONENT}
//     '''
// }


// // call is the default function which will be called when you call the filename
// def call() {
//     pipeline {
//         agent any
//         environment { 
//         SONAR_CREDENTIALS = credentials('SONAR')
//         SONAR_URL = "172.31.0.15"
//         }
//         stages{
//             stage ('lint checks') {
//                 steps {
//                     script {
//                         lintchecks()
//                     }
//                 }
//             }
//                 stage ('sonar checks') {
//                 steps {
//                     script {
//                        env.ARGS="-Dsonar.sources=."
//                        common.sonarchecks()
//                     }
//                 }
//             }
//                      stage ('test cases'){
//          parallel{
//             stage ('unit testing'){
//                steps{
//                   //sh "py test"
//                   sh "echo perfoming unit testing"
//                }
//             }
//             stage ('integrated testing'){
//                steps{
//                   // sh "py verify"
//                   sh "echo performing integrated testing"
//                }
//             }
//             stage ('functional testing'){
//                steps{
//                  sh "echo performing functional testing"
//                  }
//              }
//           }
//        }
//         }
//     }
// }