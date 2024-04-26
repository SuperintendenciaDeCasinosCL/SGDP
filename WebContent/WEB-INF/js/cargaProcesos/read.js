

function parseXml(xml) {
    var dom = null;
    if (window.DOMParser) {
        try {
            dom = (new DOMParser()).parseFromString(xml, "text/xml");
        }
        catch (e) { dom = null; }
    }
    else if (window.ActiveXObject) {
        try {
            dom = new ActiveXObject('Microsoft.XMLDOM');
            dom.async = false;
            if (!dom.loadXML(xml)) // parse error ..

                window.alert(dom.parseError.reason + dom.parseError.srcText);
        }
        catch (e) { dom = null; }
    }
    else
        alert("cannot parse xml string!");
    return dom;
}

function readTextFile(file) {
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function () {
        if (rawFile.readyState === 4) {
            if (rawFile.status === 200 || rawFile.status == 0) {
                var allText = rawFile.responseText;
                alert(allText);
            }
        }
    }
    rawFile.send(null);
}

const getParallelGateway = ({ definitions: data }) => {
    return data && data.process && data.process.parallelGateway ? data.process.parallelGateway.map(it => it['@id']) : [];
}

const getExclusiveGateways = ({ definitions: data }) => {
    return data && data.process && data.process.exclusiveGateway ? data.process.exclusiveGateway.map(it => it['@id']) : [];
}

const getStartEvent = ({ definitions: data }) => {
    let startEvent = {};

    if (data.process[0]) {
        startEvent = data.process[1].startEvent;
    } else {
        startEvent = data.process.startEvent;
    }
    return startEvent;
}

const getEndEvent = ({ definitions: data }) => {
    const { process: { endEvent } } = data;
    return endEvent;
}

const getRoles = ({ definitions: data }) => {
    const NAME = '@name';
    const FLOW = 'flowNodeRef';

    return data.process.laneSet.lane.map(it => ({
        name: it[NAME],
        elementos: it[FLOW]
    }))

}

const getOutputParameters = (task) => {
    const base = task.extensionElements.inputOutput ? task.extensionElements.inputOutput : null;
    if (!base) return null;

    return base.outputParameter.list.value.map(p => {
            const splited = p.split('|');
            const type = splited[0];
            const name = splited[1];
            const esSNC = type === 'SNC';
            const typeTaskId = esSNC ? 7 : 6;

            const res = {
                idParamTarea: 0,
                nombreParamTarea: name,
                tipoParametroDeTareaDTO: {
                    idTipoParametroDeTarea: typeTaskId
                },
                titulo: null,
                textoHtml: null,
                vigente: true,
                esSNC,
                nombreDeTipoDeRequisito: ''
            }

            return res

    });
}

const getTasks = ({ definitions: data }) => {
    const ID = '@id';
    const NAME = '@name';

    return data.process.task.map(it => ({
        id: it[ID],
        name: it[NAME],
        idDB: 't',
        tipo: '',
        docs: it.dataOutputAssociation,
        prop: it.extensionElements.properties,
        extensionElements: it.extensionElements

    }))
}

const getDocuments = ({ definitions: data }) => {
    const ID = '@id';
    const NAME = '@name';
    const DATAOBJeCt = '@dataObjectRef';

    return data.process.dataObjectReference.map(it => {

        let prop = [];
        if (!it.extensionElements) {
            prop = [];
        } else
            if (!Array.isArray(it.extensionElements.properties.property)) {
                prop = [it.extensionElements.properties.property];
            } else {
                prop = it.extensionElements.properties.property
            }



        return {
            id: it[ID],
            name: it[NAME],
            dataObjectRef: it[DATAOBJeCt],
            idDB: 'd',
            prop,
        }
    })
}


const getGateways = ({ definitions: data }) => {
    return data.process.gateway ? data.process.gateway.map(it => ({
        id: it[ID],
        name: it[NAME],
        dataObjectRef: it[DATAOBJeCt],
        idDB: 'd',
        prop: it.extensionElements.properties.property,
    })) : []
}

const getGRelations = ({ definitions: data }) => {
    return data.process.sequenceFlow ? data.process.sequenceFlow.map(it => ({
        id: it['@id'],
        from: it['@sourceRef'],
        to: it['@targetRef'],
    })) : []
}

const mapRoles = (roles) => {
    mappedRoles = {};
    roles.forEach(rol => typeof rol.elementos === 'string' ? mappedRoles[rol.elementos] = rol.name : rol.elementos.forEach(el => mappedRoles[el] = rol.name));
    return mappedRoles;
}

const mapDocs = (docs) => {
    mappedDocs = {};
    docs.forEach(doc => mappedDocs[doc.id] ? mappedDocs[doc.id].push(doc.name) : mappedDocs[doc.id] = [doc.name]);
    return mappedDocs;
}

const taskTypes = (relations, startEvent, endEvent, paralellGateways, exclusiveGateway) => {
    const mappedTypes = {};
    const startId = startEvent['@id'];

    let endId = endEvent['@id'];
    endId = endId === undefined ? Object.values(endEvent).map(it => it['@id']) : endId;

    relations.forEach(rel => {

        if (rel.from === startId) {
            mappedTypes[rel.to] = typeof mappedTypes[rel.to] === 'array' ? mappedTypes[rel.to].push('INICIO') : mappedTypes[rel.to] = ['INICIO'];
        }

        if (Array.isArray(endId) && endId.includes(rel.to)) {
            mappedTypes[rel.from] = typeof mappedTypes[rel.from] === 'array' ? mappedTypes[rel.from].push('FIN') : mappedTypes[rel.from] = ['FIN'];
        }

        if (rel.to === endId) {
            mappedTypes[rel.from] = typeof mappedTypes[rel.from] === 'array' ? mappedTypes[rel.from].push('FIN') : mappedTypes[rel.from] = ['FIN'];
        }

        if (paralellGateways.includes(rel.to)) {
            mappedTypes[rel.from] = typeof mappedTypes[rel.from] === 'array' ? mappedTypes[rel.from].push('AND') : mappedTypes[rel.from] = ['AND'];
        }

        if (exclusiveGateway.includes(rel.to)) {
            mappedTypes[rel.from] = typeof mappedTypes[rel.from] === 'array' ? mappedTypes[rel.from].push('OR') : mappedTypes[rel.from] = ['OR'];
        }

    });

    return mappedTypes;
}

const mapDestinys = (relations) => {

	const mappedDestinys = {};

    for (const rel of relations) {
		const alld = mappedDestinys[rel.from];
		
		if (Array.isArray(alld)) {
			const res = mappedDestinys[rel.from];
			res.push(rel.to);
			mappedDestinys[rel.from] = res;
		} else {
			const arr = [];
			arr.push(rel.to);
			mappedDestinys[rel.from] = arr;
		}
	}

	for( const rel of Object.keys(mappedDestinys)) {
		for (const t of mappedDestinys[rel]) {
			if (t.includes('ateway')) {
				const arr = mappedDestinys[t];
				const concated = arr.concat(mappedDestinys[rel].filter(it => !it.includes('ateway')).map(it => it.inludes('End') ? 'fin' : it));
				mappedDestinys[rel] = concated;
			}
		}
	} 
console.log(mappedDestinys)
    return mappedDestinys;
}

const getAdv = (plazo, taskName, roles, stage, fea) => {
    const advs = [];

    if (plazo === '') advs.push('La tarea no tiene plazo asociado</br>');
    if (taskName === '') advs.push('La tarea no tiene nombre</br>');
    if (roles === '') advs.push('La tarea no tiene roles asociados</br>');
    if (stage === '') advs.push('La tarea no tiene una etapa asociada</br>');

    return advs;
}

const getPropertyValue = (task, prop, isBoolean) => {
    let p = '';
    p = task.prop && task.prop.property && task.prop.property.reduce((acc, it) => it['@name'] === prop ? it['@value'] : acc, '')
    return isBoolean ? p.toLowerCase() === 'si' || p.toLowerCase() === 'sí' || p.toLowerCase() === 'yes'  : p;
}

const parse = (tasks, mappedRoles, mappedDocs, mappedTypes, mappedDestinys, dataOutput) => {
	
	const tasksMap = {};
	
	for(const t of tasks) {
		tasksMap[t.id] = `(${t.id}) ${t.name}`;
	} 

    return tasks.map(task => {
        const taskId = task.id;
        const taskName = task.name;
        const rol = mappedRoles[taskId];
        const docs = task.docs === undefined || task.docs === null ? [] : typeof task.docs === 'object' ? mappedDocs[task.docs.targetRef] : task.docs.map(doc => mappedDocs[doc.targetRef]);
        const docsRef = task.docs === undefined || task.docs === null ? [] : typeof task.docs === 'object' ? [task.docs.targetRef] : task.docs.map(doc => doc.targetRef);

        const type = mappedTypes[taskId] ? mappedTypes[taskId] : [];
        const stage = getPropertyValue(task, 'etapa', false);
        
        const visa = getPropertyValue(task, 'visa', true);;
        const fea = getPropertyValue(task, 'fea', true);;
        const num = getPropertyValue(task, 'num', true);;
        const esperar = getPropertyValue(task, 'esperar', true);;
        const plazo = getPropertyValue(task, 'plazo', false);;
        const tiporesteo = getPropertyValue(task, 'tiporesteo', false);
        const diasresteo = getPropertyValue(task, 'diasresteo', false);
        const advs = getAdv(plazo, taskName, rol, stage, fea);

        const conformaExpediente = getPropertyValue(task, 'expediente', true);
        const numeracionAuto = getPropertyValue(task, 'numauto', true);
        const distribuye = getPropertyValue(task, 'distribuir', true);
        const obligatoria = getPropertyValue(task, 'obligatoria', true);
        const codigotipodoc = getPropertyValue(task, 'codtipodoc', false);

		const finAlaSiguiente = mappedDestinys[task.id].filter(it => it.includes('End')).length > 0;
        const nextTasks = mappedDestinys[task.id].filter(it => !it.includes('fin'));

		const to = nextTasks.map(it => tasksMap[it]);
        const roles = [mappedRoles[task.id]];
        const sendDocs = task.docs === undefined || task.docs === null ? [] : typeof task.docs === 'object' ? [task.docs.targetRef] : task.docs.map(doc => doc.targetRef);

        const res = {
            taskId,
            taskName,
            rol,
            docs,
            docsRef,
            type,
            stage,
            to,
            visa,
            fea,
            num,
            esperar,
            plazo,
            salNoConf: '',
            tiporesteo,
            diasresteo,
            advs,
            nextTasks,
            roles,
            sendDocs,
            conformaExpediente,
            numeracionAuto,
            distribuye,
            obligatoria,
            codigotipodoc,
            output: getOutputParameters(task),
			finAlaSiguiente
        };

        return res;
    })
        .sort((a, b) => a.stage - b.stage);


}





