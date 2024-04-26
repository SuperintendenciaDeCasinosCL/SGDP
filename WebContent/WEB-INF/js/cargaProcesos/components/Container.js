Vue.component('Container', {
    template: //html
        `
        <template>
            <div class="container">
 				<div class="row">
					&nbsp;
                </div>
                <div class="row">
                    <button class="btn btn-primary outline" @click="toggle" >{{buttonMessage}}</button>
                </div>
				<div class="row">
					&nbsp;
                </div>
                <div class="row">
                    <div v-if="option === load">
                        <h4>Carga de procesos</h4>
                        <LoadForm ></LoadForm>
                        <LoadTable></LoadTable>
                    </div>
                    <div v-else-if="option === users" >
                        <h4>Asignacion de usuarios a procesos</h4>
                        <UserForm />
                    </div>
                </div>
            </div>
        </template>
        `,
    mounted: function () {
        this.option = this.load;
        this.buttonMessage = this.toUsers;
    },
    methods: {
        toggle: function (){
            this.option = this.option === this.load ? this.users : this.load;
            this.buttonMessage = this.option === this.load ? this.toUsers : this.toLoad;
        }
    },
    data() {
        return {
            option: 'load',
            users: 'users',
            load: 'load',
            toUsers: 'Ir a asignacion de usuarios',
            toLoad: 'Ir a carga de procesos',
            buttonMessage: ''
        }
    }
})