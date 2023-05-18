def call() {
    if(!env.TFDIR) {
        env.TFDIR = "./"
    }
  properties([
        parameters([
            choice(choices: 'dev\nprod', description: "choose the environment", name: "ENV"),
            choice(choices: 'apply\ndestroy', description: "choose the ACTION", name: "ACTION"),
            string(choices: 'APP_VERSION', description: "enter the backend version to be deployed ; Ignore this if it is a backend component", name: "APP_VERSION"),
        ]),
    ])
    node {
    ansiColor('xterm') {
         sh "rm -rf *"
         git branch: 'main', url: "https://github.com/devops-anilkumar/${REPONAME}.git"
         stage('Terraform Init') {
             sh '''
                cd ${TFDIR}
                terrafile -f env-${ENV}/Terrafile
                terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars
               '''
         }
         stage('Terraform Plan') {
             sh '''
                cd ${TFDIR}
                export TF_VAR APP_VERSION=${APP_VERSION}
                terraform plan -var-file=env-${ENV}/${ENV}.tfvars 
               '''
         }
         stage('Terraform Apply') {
             sh '''
                cd ${TFDIR}
                export TF_VAR APP_VERSION=${APP_VERSION}
                terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars 
               '''
            }
         }
     }
}










// pipeline {
//     agent any
//     parameters {
//         choice(name: 'ENV', choices: ['dev','prod'], description: 'chose the environment') 
//         choice(name: 'ACTION', choices: ['apply','destroy'], description: 'chose apply or destroy') 
//     }
//     options {
//         ansiColor('xterm')
//     }
//     stages {
//         stage('Terraform init') {
//             steps {
//               sh "terrafile -f env-${ENV}/Terrafile"
//               sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"
//             }
//         }
//                 stage('Terraform plan') {
//             steps {
//              sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"     
//             }
//         }
//                 stage('Terraform apply/destroy') {
//             steps {
//              sh "terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars"  
//             }
//         }
//     }
//   }
