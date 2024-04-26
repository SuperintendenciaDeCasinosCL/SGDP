Vue.component("ReporteTareas", {
    props: ['headers', 'repoData'],
  template: ` //html
        <div>
            <template>
                <v-data-table
                    :headers="headers"
                    :items="repoData"
                    :items-per-page="300"
                    class="elevation-1"
                    mobile="NaN"
                    mobile-breakpoint="NaN"
                ></v-data-table>
            </template>
        </div>
        `,
	mounted: function () {
    	console.log("antes de anadir clases");
		$("table").addClass('table table-striped table-hover table-responsive');
		$(".v-data-footer").hide();
    },
 
});
