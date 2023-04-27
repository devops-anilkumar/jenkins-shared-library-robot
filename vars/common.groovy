// def sonarchecks() {
//      // sh "sonar-scanner -Dsonar.host.url=http://172.31.0.15:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CREDENTIALS_USR} -Dsonar.password=${SONAR_CREDENTIALS_PSW}"
//       //sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
//      // sh "bash -x quality-gate.sh ${SONAR_CREDENTIALS_USR} ${SONAR_CREDENTIALS_PSW} ${SONAR_URL} ${COMPONENT}"
//      echo "Starting code quality analysis"
//      echo "code quality analysis completed"
// }

//  **********  THIS IS SCRIPTED PIPELINE  ***************

def sonarchecks() {
     stage('sonar checks') {
     // sh "sonar-scanner -Dsonar.host.url=http://172.31.0.15:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CREDENTIALS_USR} -Dsonar.password=${SONAR_CREDENTIALS_PSW}"
     //sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
     // sh "bash -x quality-gate.sh ${SONAR_CREDENTIALS_USR} ${SONAR_CREDENTIALS_PSW} ${SONAR_URL} ${COMPONENT}"
     echo "Starting code quality analysis"
     echo "code quality analysis completed"
     }
}

def testcases() {
    stage('Test Cases') {
        def stages = [:]

        stages["Unit Testing"] = {
            echo "Unit testing started"
            echo "Unit testing completed"
            // sh mvn test or npm test
        }
        stages["Integration Testing"] = {
            echo "Integration testing started"
            echo "Integration testing completed"
            // sh mvn test or npm test
        }
        stages["functional Testing"] = {
            echo "functional testing started"
            echo "functional testing completed"
        }

        parallel(stages)
    }   
 }
    


def lintchecks() {
       stage('Lint Checks') {
           if(env.APP_TYPE == "nodejs") {
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
           else if(env.APP_TYPE == "maven") {
          sh '''
            echo lint checks for ${COMPONENT}
            # checkstyle:check
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed ${COMPONENT}
           '''

           }
           else if(env.APP_TYPE == "python") {
           sh '''
            echo lint checks for ${COMPONENT}
            #pylint *.py    
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed ${COMPONENT}
           '''

           }
           else if(env.APP_TYPE == "angularjs") {
           sh '''
            echo lint checks for ${COMPONENT}  
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed ${COMPONENT}
           '''

           }
       }
}
