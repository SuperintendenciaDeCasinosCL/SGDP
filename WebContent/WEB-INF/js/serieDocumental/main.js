


const store_serie_docu = new Vuex.Store({
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
  store_serie_docu,
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