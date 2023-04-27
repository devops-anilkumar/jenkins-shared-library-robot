
def call() {
      node {
          git branch: 'main', url: "https://github.com/devops-anilkumar/${COMPONENT}.git"
          env.APPTYPE ="maven"
          common.lintchecks()
          env.ARGS="-Dsonar.java.binaries=target/"
          common.sonarchecks()
          common.testcases()
      }
}


// ******* UNCOMMENT IT WHEN WE USE DECLARATIVE PIPELINE *************
// def lintchecks() {
//     sh '''
//             echo lint checks for ${COMPONENT}
//             # checkstyle:check
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
//                        sh "mvn clean compile"
//                        env.ARGS="-Dsonar.java.binaries=target/"
//                        common.sonarchecks()
//                     }
//                 }
//             }
//          stage ('test cases'){
//          parallel{
//             stage ('unit testing'){
//                steps{
//                   //sh "mvn test"
//                   sh "echo perfoming unit testing"
//                }
//             }
//             stage ('integrated testing'){
//                steps{
//                   // sh "mvn verify"
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
//      } 
//   }
// }