def call() {
  node {
    stage('Checkout') {
      checkout scm
    }
    Eval.me("${pipelineCfg().type}Pipeline()")
  }
}
