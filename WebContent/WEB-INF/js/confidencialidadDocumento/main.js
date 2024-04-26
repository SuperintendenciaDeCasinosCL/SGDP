


const store_conf  = new Vuex.Store({
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
  store_conf,
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