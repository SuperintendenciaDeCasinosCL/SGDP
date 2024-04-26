


const store_bitacora = new Vuex.Store({
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
  el: "#bitacora",
  store_bitacora,
  template: //html
    `<div>
        <ContenedorBitacora/>
      </div>
  `,
  data() {
    return {
      
    }
  },
});