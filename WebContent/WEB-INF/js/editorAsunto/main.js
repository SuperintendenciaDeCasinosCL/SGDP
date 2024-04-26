


const store_asunto = new Vuex.Store({
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
  store_asunto,
  template: //html
    `<div>
        <Container/>
      </div>
  `,
  data() {
    return {
      
    }
  },
});