Vue.component('SelectableList', {
    props: ['list'],
    template: //html
    `
    <div class="container">
        <ul class="list-group">
            <li v-for="gr in (list && list.length > 0 ? list : [])">
                {{gr.name}}
                <ul>
                    <li  v-for="it in (gr.items && gr.items.length > 0 ? list : [])" class="list-group-item" @click="action(it)">
                        {{it.name}}
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    `,
    methods: {
        action: function (it) {
            console.log(it)
        }
    }
})