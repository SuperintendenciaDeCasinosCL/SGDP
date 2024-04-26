


const store_repo  = new Vuex.Store({
  state: {
    processData: {}
  },
  mutations: {
    setProcessData(state, data) {
      state.processData = data;
    }
  }
})

var app = new Vue({
  el: "#app",
  store_repo,
  template: //html
    `<div>
        <Container/>
      </div>
  `,
  data() {
    return {
      processData: 'mmmm'
    }
  },
});