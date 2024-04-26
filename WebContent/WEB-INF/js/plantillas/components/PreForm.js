Vue.component('PreForm', {
    props: ['form', 'removeField'],
    template: //html
        `
<div class="row">
                    <h3>Campos agregados</h3> 
				<table class="table">
					<thead>
						<tr>
							<th>Nombre</th><th>Descripci&oacute;n</th><th>Vista previa</th><th>Remover</th>
						</tr>
					</thead>
					<tbody>
						<tr  v-for="field in form">
							<td>{{field.name}}</td>
							<td>{{field.desc}}</td>
							<td v-if="field.type === 'number' || field.type === 'text' || field.type === 'date'">
								<input :id="field.name" :type="field.type" :placeholder="field.desc"/>
							</td>
							<td v-else-if="field.type === 'textArea'">
							<textarea :id="field.name" rows="10" cols="50" :placeholder="field.desc"></textarea>
							</td>
							<td v-else-if="field.type === 'select'">
								<select :id="field.name">
                            		<option v-for="option in field.options" :value="option">{{option}}</option>
                        		</select>
							</td>
							<td v-else-if="field.type === 'radio'">
								<div class="form-group" v-for="option in field.options">
                            		<input type="radio" :id="field.name" :name="field.name" :value="option">
                            		<label :for="field.name">{{option}}</label>
                        		</div>
							</td>
							<td v-else-if="field.type === 'checkbox'">
								<div class="form-group" v-for="option in field.options">
                            		<input type="checkbox" :id="field.name"  :name="field.name" :value="option">
                            		<label :for="field.name">{{option}}</label>
                        		</div>
							</td>
							<td v-else-if="field.type === 'switch'">
								<label :for="field.name" class="switch">{{field.name}}
                            		<input type="checkbox" :id="field.name"  :name="field.name">
                            		<span class="slider round"></span>
                        		</label>
							</td>
							<td>
								<div class="col-3">
                    				<button @click="removeField(field)" class="btn btn-danger btn-sm">Remover</button>
                				</div>
							</td>
						</tr>
					</tbody>
				</table>
</div>
        `,
    mounted: function () {
        
    },
    methods: {
    },
    data() {
        return {
        }
    }
})