


const store = new Vuex.Store({
  state: {
    processData: null
  },
  mutations: {
    setProcessData(state, data) {
      state.processData = data;
    }
  }
})

var app = new Vue({
  el: "#app",
  store,
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