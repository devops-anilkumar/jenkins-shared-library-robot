def lintchecks() {
    sh '''
            echo lint checks for ${COMPONENT}
            # checkstyle:check
            echo performing lint checks for ${COMPONENT}
            echo performing lint checks completed ${COMPONENT}
    '''
}
// call is the default function which will be called when you call the filename
def call() {
    pipeline {
        agent any
        stages{
            stage ('lint checks') {
                steps {
                    script {
                        lintchecks()
                    }
                }
            }
        }
    }
}