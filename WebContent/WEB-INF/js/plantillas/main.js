


const store_platilla = new Vuex.Store({
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
  store_platilla,
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