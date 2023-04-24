def sonarchecks() {
    sh '''
      sonar-scanner -Dsonar.host.url=http://172.31.0.15:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CREDENTIALS_USR} -Dsonar.password=${SONAR_CREDENTIALS_PSW}
      curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh
      bash -x quality-gate.sh ${SONAR_CREDENTIALS_USR} ${SONAR_CREDENTIALS_PSW} ${SONAR_URL} ${COMPONENT}
     '''
}