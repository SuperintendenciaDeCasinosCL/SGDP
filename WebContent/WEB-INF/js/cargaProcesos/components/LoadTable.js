Vue.component('LoadTable', {
    props: ['processData'],
    template: //html
        `<div class="container">
        <div class="card">
        <div class="card-body">
    <table class='table table-striped table-hover table-responsive"' style='overflow-x: auto; max-width: 80%' v-if="$store.state.processData">
    <thead>
    <tr>
    <td scope="col">ID Tarea</td>
    <td scope="col">Nombre Tarea</td>
    <td scope="col">Roles</td>
    <td scope="col">Documentos requeridos</td>
    <td scope="col">Tipo de Tarea</td>
    <td scope="col">Etapa</td>
    <td scope="col">Destinos</td>
    <td scope="col">Visa</td>
    <td scope="col">FEA</td>
    <td scope="col">Numera</td>
    <td scope="col">Espera</td>
    <td scope="col">Duraci&oacute;n</td>
    <td scope="col">Par&aacute;metros</td>
    <td scope="col">Tipo Reset</td>
    <td scope="col">D&iacute;as reset</td>
    <td scope="col">Alertas</td>	
    </tr>
    </thead>
    <tbody>
        <tr v-for="task in $store.state.processData.parsed">
            <td>{{task.taskId}}</td>
            <td>{{task.taskName}}</td>
            <td>{{task.rol}}</td>
            <td>
                <ul class="list-group list-group-flush">
                    <li v-for="doc in task.docs" class="list-group-item">{{doc}}</li>
                </ul>
            </td>
            <td>
                <ul class="list-group list-group-flush">
                    <li v-for="ty in task.type" class="list-group-item">{{ty}}</li>
                </ul>
            </td>
            <td class="text-center">{{task.stage}}</td>
            <td>
                <ul class="list-group list-group-flush">
                    <li v-for="to of task.to" class="list-group-item">{{to}}</li>
                </ul>
            </td>
            <td class="text-center">{{task.visa ? 'si' : ''}}</td>
            <td class="text-center">{{task.fea ? 'si' : ''}}</td>
            <td class="text-center">{{task.num ? 'si' : ''}}</td>
            <td class="text-center">{{task.esperar ? 'si' : ''}}</td>
            <td class="text-center">{{task.plazo}}</td>
            <td></td>
            <td class="text-center">{{task.tiporesteo}}</td>
            <td class="text-center">{{task.diasresteo}}</td>
            <td class="text-center" >
                <ul class="list-group list-group-flush">
                    <li v-for="adv in task.advs" class="list-group-item">{{adl}}</li>
                </ul>
            </td>
        </tr>
    </tbody>
    </table>
    </div>
</div>
</div>
    `,
})